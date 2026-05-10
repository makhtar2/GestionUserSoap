# GestionUserSoap

Système de gestion des utilisateurs universitaires basé sur une architecture Web Service SOAP (JAX-WS). Projet réalisé dans le cadre du cursus à l'Université Cheikh Ahmadoul Khadim (UCAK).

## 📌 Présentation
Ce projet implémente un service distant permettant de gérer le cycle de vie des utilisateurs (CRUD) sans utiliser de frameworks lourds comme Spring Boot. Il met en avant l'interopérabilité offerte par le protocole SOAP et le contrat WSDL.

## 🚀 Fonctionnalités
- **Ajout d'utilisateurs** avec génération automatique du matricule (`UCAK00x`).
- **Liste complète** des membres de l'université.
- **Recherche multicritère** via le matricule.
- **Mise à jour et Suppression** des profils.
- **Validation stricte** des rôles (Etudiant, Professeur, Admin).

## 🛠️ Stack Technique
- **Java 17**
- **JAX-WS / JAXB** (Jakarta XML Web Services)
- **Maven** (Gestion des dépendances)

## 📂 Structure du Projet
- `src/main/java/sn/ucak/gestutil/server` : Publication du service.
- `src/main/java/sn/ucak/gestutil/client` : Client console interactif.
- `src/main/java/sn/ucak/gestutil/service` : Logique métier et contrat SOAP.
- `src/main/java/sn/ucak/gestutil/model` : Modèles de données.
- `DOCUMENTATION.md` : Rapport technique détaillé et comparaison RMI/SOAP.

## 🚦 Installation et Lancement

### 1. Cloner le projet
```bash
git clone https://github.com/makhtar2/GestionUserSoap.git
cd GestionUserSoap
```

### 2. Lancer le Serveur
Exécutez la classe `sn.ucak.gestutil.server.UtilisateurPublisher`.
Le service sera disponible sur : `http://localhost:8080/ws/utilisateurs?wsdl`

### 3. Lancer le Client
Exécutez la classe `sn.ucak.gestutil.client.UtilisateurClient` pour commencer à interagir avec le service.

---
*Développé par Pape Makhtar Aidara et Makhtar WADE — UCAK 2026*
