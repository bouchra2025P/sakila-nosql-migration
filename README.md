# Sakila Migration: PostgreSQL → Redis & MongoDB

Ce projet migre les données de la base PostgreSQL **sakila** vers :
- Redis (pour les pays et les villes)
- MongoDB (pour les langues, catégories, acteurs, films)

## 🔧 Technologies utilisées

- Java 21
- Interface de NetBeans_21
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
- NetBeans 21
- Maven installé ou fourni par NetBeans
- PostgreSQL avec la base **sakila**
- Redis installé ou lancé
- Docker avec un conteneur MongoDB actif :

docker run -d --name mongo -p 27017:27017 mongo
## 📦 Installation

📁 **Étapes suivantes** :
1. Crée un fichier `README.md` dans mon dossier `sakila-migration/`.
2. Colle le contenu ci-dessus.
3. Sauvegarde.
4. Ajoute-le à mon dépôt Git :
      git add README.md
      git commit -m "Ajout du fichier README"
      git push


5.Si vous souhaitez cloner  cloner le projet avec  NetBeans 21:

Ouvrez NetBeans 21

Cliquez sur File > New Project...

Dans la fenêtre qui s'ouvre, choisissez Java with Maven > Project from Git

Dans le champ Repository URL, collez l'adresse suivante :

https://github.com/bouchra2025P/sakila-migration.git

Cliquez sur Next, choisissez un dossier local et terminez l’importation.

NetBeans va automatiquement :

Cloner le projet

Télécharger les dépendances Maven

Indexer le code pour l’exécution

Une fois importé, vous pouvez exécuter le fichier Main.java pour lancer la migration.
------------------------------ou bien------------------------------------------------
 cloner ce projet depuis GitHub utilisez la commande suivante
git clone https://github.com/bouchra2025P/sakila-migration.git
cd sakila-migration
##Configurer la connexion PostgreSQL :

Modifiez les paramètres dans PostgresConfig.java :

private static final String URL = "jdbc:postgresql://localhost:5432/sakila";
private static final String USER = "postgres";
private static final String PASSWORD = "votre_mot_de_passe";

Configurer Redis et MongoDB si nécessaire dans :

.RedisService.java

.MongoService.java

▶️ Lancer la migration
Compilez et exécutez Main.java pour démarrer la migration :


mvn clean install
Ou directement depuis votre IDE (NetBeans, IntelliJ, etc.) en exécutant la classe Main.

📁 Structure du projet

sakila-migration/
├── src/
│   ├── postgres/       # Services pour extraire les données PostgreSQL
│   ├── redis/          # Services pour insérer les pays et villes dans Redis
│   ├── mongo/          # Services pour insérer les autres données dans MongoDB
│   └── Main.java       # Point d'entrée du projet
├── pom.xml             # Dépendances Maven

