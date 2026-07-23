import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import Keycloak from 'keycloak-js';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {
  private keycloakInstance: Keycloak | undefined;
  private _profile: any | undefined;
  private isBrowser: boolean;

  constructor(@Inject(PLATFORM_ID) platformId: Object) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

  get keycloak(): Keycloak | undefined {
    return this.keycloakInstance;
  }

  get profile(): any | undefined {
    return this._profile;
  }

  get token(): string | undefined {
    return this.keycloakInstance?.token;
  }

  async getToken(): Promise<string | undefined> {
    if (!this.isBrowser || !this.keycloakInstance || !this.isLoggedIn()) {
      return undefined;
    }

    try {
      await this.keycloakInstance.updateToken(30);
      return this.keycloakInstance.token;
    } catch (error) {
      console.error('Failed to refresh token', error);
      return undefined;
    }
  }

  async init(): Promise<boolean> {
    if (!this.isBrowser) {
      return false; // Skip Keycloak init on the server (SSR)
    }

    this.keycloakInstance = new Keycloak({
      url: 'http://localhost:8080',
      realm: 'versefactory',
      clientId: 'versefactory-front'
    });

    try {
      const authenticated = await this.keycloakInstance.init({
        onLoad: 'check-sso',
        checkLoginIframe: false // Disabled by default for better compatibility with modern browsers
      });

      if (authenticated) {
        this._profile = await this.keycloakInstance.loadUserProfile();
      }

      return authenticated;
    } catch (error) {
      console.error('Keycloak init failed', error);
      return false;
    }
  }

  login(): Promise<void> {
    if (this.isBrowser && this.keycloakInstance) {
      return this.keycloakInstance.login();
    }
    return Promise.resolve();
  }

  logout(): Promise<void> {
    if (this.isBrowser && this.keycloakInstance) {
      return this.keycloakInstance.logout({ redirectUri: window.location.origin });
    }
    return Promise.resolve();
  }

  isLoggedIn(): boolean {
    return !!this.keycloakInstance?.authenticated;
  }
}
