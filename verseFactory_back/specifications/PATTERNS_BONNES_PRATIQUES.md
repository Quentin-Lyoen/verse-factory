# 💎 Patterns & Bonnes Pratiques

## 1. Architecture des Couches

### Contrôleurs
- Doivent rester "maigres" (pas de logique métier).
- Retournent uniquement des `ResponseEntity<DTO>`.
- Utilisent `@RestController`.

### Services
- Gèrent les transactions (`@Transactional`).
- Font le mapping entre DTO (OpenAPI) et Representation (Interne).
- Ne doivent pas exposer les Records jOOQ en dehors de la couche Repository/Representation.

### Representations
- Encapsulent un `Record` jOOQ.
- Fournissent des méthodes d'accès typées et protègent l'intégrité de la donnée.

---

## 2. Gestion des Exceptions
- Utilisez les exceptions personnalisées dans `fr.emothep.template.exception`.
- Ne levez pas de `RuntimeException` générique.
- Utilisez l'enum `ErrorMessages` pour centraliser les messages d'erreur.

---

## 3. Logs & Observabilité
- Utilisez `Slf4j` pour les logs.
- Niveau `INFO` pour les actions métier importantes.
- Niveau `ERROR` pour les exceptions (avec stacktrace si nécessaire).
- Le template inclut un `CustomLogging` qui trace automatiquement l'utilisateur authentifié (via JWT).

---

## 4. jOOQ
- Préférez l'API DSL de jOOQ pour les requêtes complexes.
- Profitez du typage fort généré pour éviter les erreurs au runtime.
- Les Records jOOQ sont générés à chaque `mvn install` si le profil `generate-jooq` est actif.

---

---

## 5. Organisation de l'API & Naming des Classes

Pour éviter les conflits de noms (ex: deux contrôleurs `ExampleController` dans des versions ou rôles différents), nous préconisons de donner des noms uniques aux classes :

- **Package Structure** : `fr.emothep.template.vX.{role}.{resource}`.
- **Naming des Classes** : Incluez le rôle et la version dans le nom de la classe pour garantir l'unicité globale au sein du projet :
    - `AdminExampleControllerV1`
    - `AdminExampleServiceV1`
    - `AdminExampleRepositoryV1`
    - `AdminExampleMapperV1`

- **Hiérarchie des Contrôleurs** : Utilisez des classes de base par rôle pour centraliser les chemins et configurations communes :
    - `TemplateControllerV1` : Base pour toute la V1 (`/v1`).
    - `CommonControllerV1` : Base pour les endpoints publics (`/v1`).
    - `AdminControllerV1` : Base pour les endpoints d'administration (`/v1/admin`).

Ceci permet à Spring d'instancier les beans sans ambiguïté sans avoir à utiliser de noms de beans explicites dans les annotations.

## 6. Séparation des Swaggers
L'API est divisée en plusieurs fichiers OpenAPI pour séparer les responsabilités :
- `openapi-common.yaml` : Endpoints publics (health, etc.) et schémas partagés.
- `openapi-admin.yaml` : Endpoints réservés aux administrateurs.
- etc.

Chaque fichier génère son propre package dans `src/main/v1/{role}/openapi`.
