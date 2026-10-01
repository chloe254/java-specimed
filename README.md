# Spécimed - prise de rendez-vous médicaux en Java

Projet Java de 3e année d'école d'ingénieur (ECE Paris, 2025), fait en équipe. C'est une application de bureau en Java Swing, reliée à une base MySQL, qui permet à des patients de prendre rendez-vous avec des spécialistes et à un administrateur de gérer les spécialistes et les patients.

## Fonctionnalités

- Connexion et inscription des patients (nouveau ou ancien patient).
- Prise de rendez-vous par spécialité, à partir des créneaux disponibles dans l'agenda du spécialiste.
- Consultation des rendez-vous à venir et de l'historique, annulation d'un rendez-vous.
- Espace administrateur : ajout et suppression de spécialistes, recherche et consultation d'un patient.

## Architecture

Le code suit une organisation MVC, avec une couche DAO pour l'accès à la base :

```
modele/       Personne, Patient, Specialiste, Admin, RendezVous
DAO/          accès MySQL en JDBC (requêtes préparées) : patients, spécialistes, agenda, rendez-vous, admin
controleur/   authentification, création / annulation / consultation des rendez-vous
Vue/          fenêtres Swing (connexion, inscription, espace patient, agenda, administration)
sql/          schéma de la base
Main.java     point d'entrée (ouvre la fenêtre de connexion)
```

## Lancer le projet

1. Créer la base avec `sql/schema.sql`. Ce schéma a été reconstitué à partir des requêtes des DAO, car le script d'origine n'était pas dans le projet.
2. Adapter l'URL, l'utilisateur et le mot de passe dans `DAO/DatabaseConnection.java`.
3. Ajouter le driver MySQL (`mysql-connector-j`) au classpath, puis compiler et lancer :

```bash
javac -encoding UTF-8 -d out Main.java DAO/*.java modele/*.java controleur/*.java Vue/*.java
cp Vue/logomedic.png out/Vue/
java -cp "out:mysql-connector-j-9.2.0.jar" Main
```

## Limites

- Les mots de passe sont stockés en clair dans la base. Il faudrait les hacher (par exemple avec bcrypt).
- La recherche d'un spécialiste par spécialité, côté administrateur, n'est pas terminée.
