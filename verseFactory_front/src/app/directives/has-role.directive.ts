import { Directive, Input, TemplateRef, ViewContainerRef, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Directive({
  selector: '[appHasRole]',
  standalone: true
})
export class HasRoleDirective implements OnInit {
  @Input('appHasRole') requiredRole!: string;

  constructor(
    private readonly templateRef: TemplateRef<unknown>,
    private readonly viewContainer: ViewContainerRef,
    private readonly authService: AuthService
  ) {}

  ngOnInit(): void {
    const hasPermission = this.authService.hasRole(this.requiredRole);

    this.viewContainer.clear();
    if (hasPermission) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    }
  }
}