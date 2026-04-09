# API Commandes - R4.01 Architecture Logicielle

Composant **Commandes** d'une application web de livraison de repas, développé en Jakarta EE selon une **Clean Architecture**.

## Stack technique

- **Langage** : Java 17
- **Framework** : Jakarta EE 10
- **Serveur** : GlassFish 7
- **Persistance** : JDBC pur (MariaDB)
- **URL de base** : `http://localhost:8080/commandes/api`

## Endpoints

| Méthode | Route | Description |
|---|---|---|
| GET | `/commandes` | Lister toutes les commandes |
| GET | `/commandes/{id}` | Obtenir une commande par son id |
| POST | `/commandes` | Créer une nouvelle commande |
| PUT | `/commandes/{id}` | Modifier l'adresse ou la date de livraison |
| DELETE | `/commandes/{id}` | Annuler une commande |


## Lancer le projet

### Prérequis
- GlassFish 7 installé et démarré
- Datasource `jdbc/commandesDS` configurée (voir `src/main/webapp/WEB-INF/glassfish-resources.xml.example`)
- Mocks JSON-Server pour les APIs externes (développement)

### Démarrer les mocks
```bash
npx json-server@0.17.4 --watch projet-data/menus.json --port 3004
npx json-server@0.17.4 --watch projet-data/plats-utilisateurs.json --port 3003
```


