# 📚 Spécifications & Documentation Technique

Bienvenue dans la documentation technique du projet. Ce répertoire contient les guides et spécifications nécessaires pour comprendre, développer et maintenir l'application.

## 🗂 Sommaire

### 1. [Architecture API](./ARCHITECTURE_API.md)
Principes REST, conventions de nommage, gestion des erreurs et utilisation d'OpenAPI.

### 2. [Guide de Développement](./GUIDE_DEVELOPPEMENT.md)
Installation de l'environnement, cycle de développement (TDD, génération de code) et gestion des tests.

### 3. [Patterns & Bonnes Pratiques](./PATTERNS_BONNES_PRATIQUES.md)
Standards de code, architecture en couches (Controller/Service/Representation) et bonnes pratiques jOOQ.

---

## 🚀 Philosophie du Projet
Ce projet suit une approche **API-First** et **Database-First** :
- L'API est définie en premier dans OpenAPI.
- Le schéma de données est défini via Flyway.
- Le code Java est généré pour garantir une synchronisation parfaite entre les couches.
