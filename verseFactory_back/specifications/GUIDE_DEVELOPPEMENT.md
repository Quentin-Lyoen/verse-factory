# 👨‍💻 Guide de Développement

## 1. Environnement Local
- **JDK 21** requis.
- **Docker Compose** pour l'infrastructure (PostgreSQL, Keycloak, Minio).
- **Profil `local`** : Utilisé par défaut pour le développement.

## 2. Cycle de Développement Standard

### Étape 1 : Base de données (Flyway)
Ajoutez vos scripts de migration dans `src/main/resources/db/migration/`.
Format : `V1.0.1__description.sql`.

### Étape 2 : Spécification API (OpenAPI)
Modifiez le fichier `src/main/resources/openapi/openapi-common.yaml` pour définir vos nouveaux endpoints et modèles.

### Étape 3 : Génération de code
Lancez la génération pour mettre à jour les interfaces Java et le schéma jOOQ :
```bash
mvn clean install -Pgenerate-openapi-common,generate-jooq
```

### Étape 4 : Implémentation
1.  **Repository** : Créez une classe héritant de `TemplateRepositoryV1` utilisant jOOQ.
2.  **Representation** : Créez l'objet métier encapsulant le Record jOOQ.
3.  **Service** : Implémentez la logique et le mapping vers le DTO.
4.  **Controller** : Implémentez l'interface API générée.

## 3. Tests
- Utilisez le profil `test` pour désactiver Keycloak localement.
- Tests unitaires : Utilisez `@WebMvcTest` et moquez vos services.
- Tests d'intégration : Utilisez `@SpringBootTest` avec Testcontainers (activé par défaut).

```bash
mvn test
```
