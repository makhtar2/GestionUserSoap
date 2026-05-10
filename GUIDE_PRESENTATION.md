# GUIDE DE PRÉSENTATION ORALE : PROJET WEB SERVICES SOAP
## Intervenants : Pape Makhtar Aidara & Makhtar WADE
---

Ce document sert de support pour une présentation fluide, étape par étape.

### INTRODUCTION (2 minutes)
*   Accroche : "Bonjour à tous. Aujourd'hui, nous vous présentons le Projet #03 : la migration d'une application de gestion d'utilisateurs vers une architecture de Web Services SOAP."
*   Objectif : Expliquer que le but est de comprendre comment exposer des fonctionnalités métier à distance sans utiliser de frameworks lourds comme Spring Boot.
*   Transition : "Commençons par le coeur du système : le contrat de service."

---

### ÉTAPE 1 : LE CONTRAT (L'INTERFACE)
*   Action : Montrer le fichier IUtilisateurService.java.
*   Points clés à dire :
    1.  "Nous utilisons l'annotation @WebService pour définir notre interface comme un service SOAP."
    2.  "Chaque méthode CRUD possède une annotation @WebMethod avec un operationName. C'est ce nom qui apparaîtra dans le fichier XML final."
    3.  "Notez l'usage de @WebParam : cela permet de nommer proprement les paramètres dans les requêtes SOAP pour plus de clarté."

---

### ÉTAPE 2 : LE MODÈLE ET LA LOGIQUE (LE SERVEUR)
*   Action : Montrer Utilisateur.java puis UtilisateurServiceImpl.java.
*   Points clés à dire :
    1.  "Notre modèle est annoté avec @XmlRootElement. C'est crucial pour que JAX-WS sache comment transformer nos objets Java en XML (le Marshalling)."
    2.  "Dans l'implémentation, nous avons intégré une logique métier : la génération automatique du matricule (UCAK001, etc.). Le client n'a plus à s'en soucier, c'est le serveur qui gère l'intégrité."
    3.  "Le stockage est géré en mémoire via une ArrayList pour cette démonstration."

---

### ÉTAPE 3 : PUBLICATION ET WSDL
*   Action : Lancer UtilisateurPublisher.java et ouvrir le navigateur sur http://localhost:8080/ws/utilisateurs?wsdl.
*   Points clés à dire :
    1.  "La publication est simple grâce à Endpoint.publish(). Pas besoin de serveur Tomcat ou Glassfish ici."
    2.  "Le WSDL que vous voyez ici est le document le plus important. C'est le contrat universel."
    3.  "On y retrouve nos types complexes (Utilisateur), nos messages et nos opérations (ajout, liste, etc.). C'est grâce à ce fichier qu'un client Python ou C# pourrait discuter avec notre code Java."

---

### ÉTAPE 4 : LE CLIENT ET LA DÉMO (LIVE ACTION)
*   Action : Lancer UtilisateurClient.java.
*   Points clés à dire :
    1.  "Côté client, nous utilisons un Proxy. C'est un objet qui ressemble à notre service local mais qui, en réalité, envoie des requêtes réseau."
    2.  Démonstration :
        *   Faire une Liste (1) pour montrer les données de départ.
        *   Faire un Ajout (3) : Insistez sur le fait que vous ne saisissez pas de matricule. Montrer le message de succès avec le matricule généré par le serveur.
        *   Faire une Recherche (2) avec le matricule qui vient d'être créé.
        *   Faire une Modification (4) ou Suppression (5) pour prouver que l'état est maintenu sur le serveur.

---

### ÉTAPE 5 : COMPARAISON THÉORIQUE (RMI vs SOAP)
*   Question probable du jury : "Pourquoi SOAP est-il préférable à RMI ?"
*   Réponse type :
    1.  "RMI est binaire et 100% Java. C'est comme une conversation privée entre deux personnes parlant la même langue rare."
    2.  "SOAP utilise le XML et le HTTP. C'est comme utiliser l'Anglais (XML) sur Internet (HTTP). Tout le monde peut comprendre, peu importe sa technologie (Linux, Windows, Mobile, etc.)."
    3.  "C'est ce qu'on appelle l'Interopérabilité."

---

### CONCLUSION (1 minute)
*   Synthèse : "En résumé, nous avons réussi à transformer une application locale en un service réseau standardisé, prêt pour une intégration moderne."
*   Clôture : "Merci de votre attention. Nous sommes maintenant prêts pour vos questions."

---
### CONSEILS POUR LA PRÉSENTATION EN LIGNE :
1.  Zoom : Augmentez la taille de la police dans IntelliJ (File > Settings > Editor > Font) pour que le jury voie bien le code sur le partage d'écran.
2.  Préparation : Ayez déjà l'URL du WSDL prête dans un onglet de votre navigateur.
3.  Calme : Si le serveur plante, expliquez simplement qu'il faut le relancer.
