# Sakila Migration: PostgreSQL → Redis & MongoDB

Ce projet migre les données de la base PostgreSQL **sakila** vers :
- Redis (pour les pays et les villes)
- MongoDB (pour les langues, catégories, acteurs, films)

## 🔧 Technologies utilisées

- Java 21
- Maven
- PostgreSQL
- Redis
- MongoDB (via Docker)
- Jedis (client Redis)
- MongoDB Java Driver
- Docker (pour MongoDB)

---

## ▶️ Exécution du projet

### 1. 📦 Pré-requis

- Java 21 installé
- Maven installé ou fourni par NetBeans
- PostgreSQL avec la base **sakila**
- Redis installé ou lancé
- Docker avec un conteneur MongoDB actif :
```bash
docker run -d --name mongo -p 27017:27017 mongo
## 📦 Installation



1. Cloner le projet :
```bash
git clone https://github.com/bouchra2025P/sakila-migration.git
cd sakila-migration
