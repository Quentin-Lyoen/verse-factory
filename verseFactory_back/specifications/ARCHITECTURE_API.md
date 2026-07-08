# 🌐 Architecture API - Template Backend

## 1. Principes de l'API
L'API suit les principes RESTful et est documentée via **OpenAPI 3.0**.

- **Format** : JSON (UTF-8)
- **Base URL** : `/api/v1`
- **Authentification** : JWT (Keycloak) via header `Authorization: Bearer <token>`
- **Versioning** : Inclus dans l'URL (`/v1`)

---

## 2. Conventions de Nommage
- **Ressources** : Noms au pluriel, kebab-case (ex: `/training-actions`).
- **Méthodes HTTP** :
    - `GET` : Récupération (Idempotent, Safe).
    - `POST` : Création.
    - `PUT` : Remplacement complet.
    - `PATCH` : Modification partielle.
    - `DELETE` : Suppression.

---

## 3. Structure des Réponses d'Erreur
Toutes les erreurs suivent un format standard JSON :

```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Resource not found",
  "path": "/api/v1/example/123"
}
```

Les codes HTTP utilisés sont :
- `200/201/204` : Succès.
- `400` : Bad Request (Erreur de syntaxe ou validation).
- `401` : Unauthorized (Token manquant ou invalide).
- `403` : Forbidden (Droits insuffisants).
- `404` : Not Found (Ressource inexistante).
- `500` : Internal Server Error.

---

## 4. Design-First avec OpenAPI
La spécification se trouve dans `src/main/resources/openapi/openapi-common-v1.yaml`.
**Toute modification de l'API doit commencer par une modification du fichier YAML.**

Le code (Interfaces Api et DTOs) est ensuite généré via le profil Maven :
```bash
mvn clean install -Pgenerate-openapi-common
```
