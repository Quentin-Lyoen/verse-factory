# 🌌 Verse Factory

> **Verse Factory** est un projet de jeu en ligne full-stack actuellement en cours de développement. Ce projet me sert avant tout de support d'apprentissage pour apprendre à développer en m'appuyant sur l'assistance de l'Intelligence Artificielle.

[![Statut du projet](https://img.shields.io/badge/Statut-En%20d%C3%A9veloppement-orange)](https://verse-factory.vercel.app/)
[![Démo en ligne](https://img.shields.io/badge/Version%20D%C3%A9ploy%C3%A9e-Vercel-blue)](https://verse-factory.vercel.app/)
[![Angular](https://img.shields.io/badge/Frontend-Angular-DD0031?logo=angular)](./verseFactory_front)
[![Spring Boot](https://img.shields.io/badge/Backend-Spring%20Boot-6DB33F?logo=springboot)](./verseFactory_back)

---

## 🌐 Démo en ligne

L'application est déployée et accessible publiquement à l'adresse suivante :
👉 **[https://verse-factory.vercel.app/](https://verse-factory.vercel.app/)**

---

## 🎯 Contexte et Objectifs

- **Apprentissage avec l'IA** : Ce projet est développé dans l'objectif principal d'expérimenter et d'apprendre les méthodes de développement logiciel assisté par IA.
- **Projet évolutif** : Le jeu est **encore en cours de développement actif**. De nouvelles fonctionnalités, optimisations et corrections sont progressivement intégrées.

---

## 🛠️ Stack Technique

### Frontend (`verseFactory_front`)
- **Framework** : Angular
- **Hébergement & Déploiement** : Vercel

### Backend (`verseFactory_back`)
- **Framework** : Java / Spring Boot
- **Architecture** : API REST & Sécurité

### Infrastructure (`verseFactory_docker`)
- **Containers** : Docker / Docker Compose pour l'environnement local et les services associés.

---

## 📂 Structure du Projet

```text
verse-factory/
├── verseFactory_back/      # API Backend (Spring Boot)
├── verseFactory_front/     # Interface utilisateur (Angular)
└── verseFactory_docker/    # Configurations Docker & environnement
```

---

## 🚀 Démarrage en Local

### Prérequis
- Java 21+ & Maven
- Node.js 20+ & npm
- Docker

### Backend (Spring Boot)
```bash
cd verseFactory_back
./mvnw spring-boot:run
```

### Frontend (Angular)
```bash
cd verseFactory_front
npm install
npm run start
```
L'interface sera disponible sur `http://localhost:4200/`.
