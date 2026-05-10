# ÉTUDE COMPARATIVE : RMI VS WEB SERVICES SOAP
## Analyse des Architectures Distantes en Java

---

### 1. Comparaison des Méthodes d'Appel

*   **En RMI (Remote Method Invocation) :** Le client utilise un **stub** (souche) obtenu via un registre RMI. L'échange s'effectue via le protocole binaire **JRMP** (Java Remote Method Protocol). Cette méthode est spécifique à l'écosystème Java.
*   **En Web Service SOAP :** Le client utilise des messages **XML (SOAP)** transportés par le protocole standard **HTTP**. Un proxy local convertit les appels Java en requêtes standardisées, rendant la communication universelle.

---

### 2. Rôles des Éléments de Configuration

#### Le Registre RMI
Il sert d'annuaire (Service de nommage) permettant au serveur de publier ses objets distants et au client de localiser les références de ces objets par un nom logique.

#### Le contrat WSDL (Web Services Description Language)
Il s'agit du contrat formel du service. Il définit les méthodes disponibles, les types de données complexes et les points d'accès (URL) de manière totalement indépendante du langage de programmation.

---

### 3. Analyse de la Liaison Technologique

*   **RMI et Java :** RMI est fortement lié à Java car il repose sur la sérialisation native des objets Java. Cela impose que le client et le serveur s'exécutent tous deux sur une Machine Virtuelle Java (JVM).
*   **Interopérabilité de SOAP :** SOAP est interopérable par nature. Puisqu'il repose sur des standards universels comme le XML et le HTTP, un client écrit en Python, PHP ou C# peut consommer le service Java sans aucune difficulté.

---

### 4. Avantages et Inconvénients du SOAP

#### Points Forts
*   **Interopérabilité :** Communication fluide entre plateformes et langages hétérogènes.
*   **Standardisation :** Utilisation d'un contrat WSDL rigoureux pour garantir la structure des données.
*   **Accessibilité réseau :** Utilise les ports web standards (80/443), ce qui facilite le passage à travers les pare-feux.

#### Points Faibles
*   **Performance :** Le traitement (parsing) du XML génère un surcoût processeur par rapport au format binaire.
*   **Consommation de bande passante :** Les messages XML sont très verbeux, ce qui augmente la taille des données transférées sur le réseau.

---
*Auteurs : Pape Makhtar Aidara et Makhtar WADE — Université Cheikh Ahmadoul Khadim (UCAK) — 2026*
