# 🚀 Template Spring Boot Backend

Ce projet est un **GitHub Template** standardisé pour les microservices Spring Boot chez VerseFactory. Il intègre les meilleures pratiques d'architecture (Clean Architecture / Hexagonale), la sécurité Keycloak, et une chaîne de génération de code automatisée.

## 🛠 Stack Technique

- **Java 21** & **Spring Boot 3.4.x**
- **jOOQ** : Accès aux données avec typage fort (généré depuis le schéma SQL).
- **OpenAPI 3** : Design-first API avec génération automatique des interfaces et DTOs.
- **Flyway** : Gestion des migrations de base de données.
- **Keycloak** : Sécurité basée sur JWT (Resource Server).
- **Testcontainers** : Tests d'intégration sur de vraies instances Docker (PostgreSQL).

---

## 📖 Documentation Détaillée
Pour plus de détails sur l'architecture et les conventions du projet, consultez le répertoire [**specifications/**](./specifications/README.md) :
- [Architecture API](./specifications/ARCHITECTURE_API.md)
- [Guide de Développement](./specifications/GUIDE_DEVELOPPEMENT.md)
- [Patterns & Bonnes Pratiques](./specifications/PATTERNS_BONNES_PRATIQUES.md)

---

## 📋 Procédure de mise en route (après Fork)

### 1. Personnalisation du projet (Obligatoire)
Le template utilise par défaut le package `fr.versefactory.template`. Vous **devez** le renommer pour correspondre à votre projet :

#### A. Mise à jour du Maven (pom.xml)
Modifiez les propriétés suivantes :
- `<groupId>` : Votre package racine (ex: `fr.versefactory.monprojet`).
- `<artifactId>` : Le nom technique de votre application.
- `<openapi-common-generator.base-package>` : Package cible pour le code généré OpenAPI public (ex: `fr.versefactory.monprojet.v1.common.openapi`).
- `<openapi-admin-generator.base-package>` : Package cible pour le code généré OpenAPI admin (ex: `fr.versefactory.monprojet.v1.admin.openapi`).
- Section `jooq-codegen-maven` : Modifiez la balise `<packageName>` dans la configuration du générateur (ex: `fr.versefactory.monprojet.storage`).

#### B. Renommage des dossiers et packages Java
1.  Utilisez votre IDE (IntelliJ recommandé : `Shift+F6` sur le dossier `fr.versefactory.template`) pour renommer le package racine dans `src/main/java` et `src/test/java`.
2.  Vérifiez que la classe principale `@SpringBootApplication` est bien dans le nouveau package racine.
3.  Mettez à jour `spring.application.name` dans `src/main/resources/application.yml`.

#### C. Régénération du code
Une fois les packages renommés dans le `pom.xml`, vous devez forcer la régénération pour que les imports soient corrects :
```bash
mvn clean install -Pgenerate-openapi-common-v1,generate-openapi-admin-v1,generate-jooq
```

### 2. Configuration locale
Le projet utilise le profil `local` par défaut.
- Copiez et adaptez le fichier de configuration :
  ```bash
  cp src/main/resources/application-local.yml.example src/main/resources/application-local.yml
  ```
- Vérifiez les accès à votre base de données locale, Keycloak et Minio.

### 3. Lancement des dépendances
Utilisez Docker Compose pour démarrer l'infrastructure locale (DB, Keycloak, Minio) :
```bash
cd tools
docker-compose up -d
```

### 4. Génération du code
Le projet repose sur la génération de code pour garantir la cohérence entre le SQL, l'OpenAPI et le Java.
```bash
# Génère les DTOs/Interfaces OpenAPI et les classes jOOQ (nécessite la DB locale lancée)
mvn clean install -Pgenerate-openapi-common-v1,generate-openapi-admin-v1,generate-jooq
```

---

## 🏗 Architecture du Code

Le template suit une séparation stricte des responsabilités :

1.  **Controller** : Implémente les interfaces `Api` générées par OpenAPI. Manipule uniquement des **DTOs**.
2.  **Service** : Contient la logique métier. Gère le mapping entre **DTOs** et **Representations**.
3.  **Representation** : Objet interne encapsulant un **Record jOOQ**. C'est la couche qui connaît la structure de la base de données.
4.  **Repository** : Couche d'accès aux données utilisant jOOQ.

---

## 🔐 Sécurité & Tests

### Authentification JWT
Le projet est configuré comme un **Resource Server**. Il valide les jetons JWT émis par Keycloak via un `JwtFilter` personnalisé.

### Tests Unitaires et d'Intégration
Pour simplifier les tests, la sécurité Keycloak est **désactivée par profil** :
- **Profil `test`** : Activé automatiquement lors des tests (`@ActiveProfiles("test")`).
- Une classe `TestSecurityConfig` autorise toutes les requêtes (`permitAll()`) pour éviter d'avoir à gérer des tokens JWT factices dans chaque test.
- Les filtres de sécurité complexes (`JwtFilter`, `CustomLogging`) sont ignorés via `@Profile("!test")`.

Lancer les tests :
```bash
mvn test
```

---

## 🚀 CI/CD (GitHub Actions)

Les workflows sont présents dans `.github/workflows/` mais sont **désactivés par défaut** pour éviter des déploiements accidentels sur les projets de référence (Luna/QualiopSys).

**Étapes pour activer la CI/CD :**
1.  **Secrets GitHub** : Configurez `HARBOR_USERNAME`, `HARBOR_PASSWORD`, `APP_ID`, `APP_PEM`, etc.
2.  **Configuration** :
    *   Dans `generate-release.yml` et `build-specified-image.yml`, remplacez `APP_IMAGE_REPOSITORY: CHANGE_ME` par le nom de votre projet Harbor.
    *   Dans `deploy_image_to_env.yml`, configurez le repo Kustomize cible.
3.  **Activation** : Décommentez les déclencheurs (`on: pull_request: closed`) dans `generate-release.yml`.

---

## 📖 Endpoints Standards
- **API** : `http://localhost:8081/api/v1/...`
- **Healthcheck** : `http://localhost:8081/api/v1/common/healthz`
- **Actuator** : `http://localhost:8081/api/actuator/health`
