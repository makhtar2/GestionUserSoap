package sn.ucak.gestutil.client;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import sn.ucak.gestutil.model.Utilisateur;
import sn.ucak.gestutil.service.IUtilisateurService;

import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays; // Pour la validation du rôle

public class UtilisateurClient {

    // Rôles valides pour la validation
    private static final List<String> VALID_ROLES = Arrays.asList("Etudiant", "Professeur", "Admin");

    public static void main(String[] args) {
        try {
            printHeader();
            
            System.out.print(" > Tentative de connexion au service distant... ");
            
            // Suppression des logs JVM (Unsafe)
            java.io.PrintStream originalErr = System.err;
            System.setErr(new java.io.PrintStream(new java.io.OutputStream() { @Override public void write(int b) {} }));
            
            IUtilisateurService proxy;
            try {
                URL url = new URL("http://localhost:8080/ws/utilisateurs?wsdl");
                // Namespace inversé depuis le package sn.ucak.gestutil.service
                QName qname = new QName("http://service.gestutil.ucak.sn/", "UtilisateurServiceImplService");
                Service service = Service.create(url, qname);
                // Proxy JAX-WS
                proxy = service.getPort(IUtilisateurService.class);
            } finally {
                System.setErr(originalErr);
            }
            
            System.out.println("[CONNECTÉ]");

            Scanner scanner = new Scanner(System.in);
            String choix = "";

            do {
                printMenu();
                System.out.print(" > Saisissez le numéro de l'action souhaitée : ");
                choix = scanner.nextLine().trim();

                switch (choix) {
                    case "1": listerUtilisateurs(proxy); break;
                    case "2": rechercherUtilisateur(proxy, scanner); break;
                    case "3": ajouterUtilisateur(proxy, scanner); break; // Cette méthode gère la génération du matricule
                    case "4": modifierUtilisateur(proxy, scanner); break;
                    case "5": supprimerUtilisateur(proxy, scanner); break;
                    case "0": System.out.println("\n [SESSION] Fermeture de l'application. Au revoir."); break;
                    default:  System.out.println(" [ERREUR] Option invalide.");
                }
                
                if (!choix.equals("0")) {
                    System.out.println("\n (Appuyez sur ENTRÉE pour continuer)");
                    scanner.nextLine();
                }
            } while (!choix.equals("0"));

        } catch (Exception e) {
            System.err.println("\n [ERREUR CRITIQUE] Le serveur est injoignable.");
            System.err.println(" Vérifiez que la classe UtilisateurPublisher est bien lancée.");
            System.err.println(" Détail technique : " + e.getMessage());
        }
    }

    private static void printHeader() {
        System.out.println("=====================================================================");
        System.out.println("   UNIVERSITÉ CHEIKH AHMADOUL KHADIM - GESTION DES UTILISATEURS     ");
        System.out.println("=====================================================================");
    }

    private static void printMenu() {
        System.out.println("\n --- MENU DES OPÉRATIONS ---");
        System.out.println(" [1] LISTER      [2] RECHERCHER");
        System.out.println(" [3] AJOUTER     [4] MODIFIER");
        System.out.println(" [5] SUPPRIMER   [0] QUITTER");
        System.out.println(" ---------------------------");
    }

    private static void listerUtilisateurs(IUtilisateurService proxy) {
        System.out.println("\n [RÉSULTAT] LISTE DES MEMBRES :");
        List<Utilisateur> list = proxy.listerUtilisateurs();
        if (list.isEmpty()) {
            System.out.println("  -> Aucun utilisateur enregistré.");
        } else {
            System.out.println(" -------------------------------------------------------------------------------");
            System.out.printf(" | %-10s | %-15s | %-15s | %-12s |\n", "MATRICULE", "NOM", "PRÉNOM", "RÔLE");
            System.out.println(" -------------------------------------------------------------------------------");
            for (Utilisateur u : list) {
                System.out.printf(" | %-10s | %-15s | %-15s | %-12s |\n", 
                    u.getMatricule(), u.getNom(), u.getPrenom(), u.getRole());
            }
            System.out.println(" -------------------------------------------------------------------------------");
        }
    }

    private static void rechercherUtilisateur(IUtilisateurService proxy, Scanner scanner) {
        System.out.print("\n > Matricule recherché : ");
        String mat = scanner.nextLine().trim();
        if (mat.isEmpty()) {
            System.out.println(" [INFO] Matricule vide, opération annulée.");
            return;
        }
        Utilisateur u = proxy.rechercherUtilisateur(mat);
        if (u != null) {
            System.out.println("\n [SUCCÈS] Utilisateur trouvé :");
            System.out.println("   - Matricule   : " + u.getMatricule());
            System.out.println("   - Nom Complet : " + u.getNom() + " " + u.getPrenom());
            System.out.println("   - Fonction    : " + u.getRole());
            System.out.println("   - Contact     : " + u.getTelephone() + " | " + u.getEmail());
        } else {
            System.out.println(" [INFO] Matricule inconnu : " + mat);
        }
    }

    private static void ajouterUtilisateur(IUtilisateurService proxy, Scanner scanner) {
        System.out.println("\n --- FORMULAIRE D'INSCRIPTION ---");
        // Ne plus demander le matricule, il sera généré par le serveur.
        Utilisateur u = saisirInfos(scanner, null); 
        
        System.out.print(" > Envoi des données au serveur... ");
        // La méthode retourne maintenant l'utilisateur complet avec son matricule généré
        Utilisateur addedUser = proxy.ajouterUtilisateur(u); 

        if (addedUser != null) {
            System.out.println("[SUCCÈS]");
            System.out.println("   -> Nouvel utilisateur enregistré avec le matricule : " + addedUser.getMatricule());
        } else {
            System.out.println("[ÉCHEC] Impossible d'ajouter l'utilisateur (vérifiez les données ou si le serveur est actif).");
        }
    }

    private static void modifierUtilisateur(IUtilisateurService proxy, Scanner scanner) {
        System.out.print("\n > Matricule de l'utilisateur à modifier : ");
        String mat = scanner.nextLine().trim();
        if (mat.isEmpty()) {
             System.out.println(" [INFO] Matricule vide, opération annulée.");
             return;
        }

        // Vérifier si l'utilisateur existe avant de demander les détails
        Utilisateur existant = proxy.rechercherUtilisateur(mat);
        if (existant == null) {
            System.out.println(" [INFO] Utilisateur introuvable pour le matricule : " + mat);
            return;
        }

        System.out.println(" [INFO] Modification du profil de : " + existant.getNom() + " " + existant.getPrenom() + " (Matricule: " + existant.getMatricule() + ")");
        System.out.println(" (Laissez un champ vide pour conserver sa valeur actuelle)");
        // Passe le matricule existant pour qu'il ne soit pas modifié par saisirInfos
        Utilisateur modU = saisirInfos(scanner, mat); 
        
        if (proxy.modifierUtilisateur(modU)) {
            System.out.println(" [SUCCÈS] Le profil a été mis à jour.");
        } else {
            System.out.println(" [ÉCHEC] Erreur lors de la mise à jour.");
        }
    }

    private static void supprimerUtilisateur(IUtilisateurService proxy, Scanner scanner) {
        System.out.print("\n > Matricule à supprimer : ");
        String mat = scanner.nextLine().trim();
        if (mat.isEmpty()) {
            System.out.println(" [INFO] Matricule vide, opération annulée.");
            return;
        }

        System.out.print(" ATTENTION : Confirmez-vous la suppression de " + mat + " ? (oui/non) : ");
        if (scanner.nextLine().trim().equalsIgnoreCase("oui")) {
            if (proxy.supprimerUtilisateur(mat)) {
                System.out.println(" [SUCCÈS] Utilisateur " + mat + " supprimé.");
            } else {
                System.out.println(" [ÉCHEC] Impossible de trouver l'utilisateur " + mat);
            }
        } else {
            System.out.println(" [INFO] Opération annulée.");
        }
    }

    // Méthode utilitaire pour demander les informations utilisateur, gère la validation du rôle
    private static Utilisateur saisirInfos(Scanner scanner, String matFixe) {
        String matricule = matFixe; // Matricule pour la modification, null pour l'ajout
        if (matricule == null) { // On ne demande le matricule que si on est en mode modification
            // Le matricule est maintenant généré par le serveur lors de l'ajout.
            // Pour la modification, le matricule est conservé.
        }
        
        String nom = prompt(scanner, "Nom");
        String prenom = prompt(scanner, "Prénom");
        String role = promptWithRoleValidation(scanner, "Rôle"); // Utilise l'entrée validée pour le rôle
        String tel = prompt(scanner, "Téléphone");
        String email = prompt(scanner, "Email");

        return new Utilisateur(matricule, nom, prenom, role, tel, email);
    }

    // Méthode utilitaire pour demander une valeur string
    private static String prompt(Scanner scanner, String label) {
        System.out.print("  " + label + " : ");
        return scanner.nextLine().trim();
    }

    // Méthode utilitaire pour demander le rôle avec validation
    private static String promptWithRoleValidation(Scanner scanner, String label) {
        String role;
        do {
            System.out.print("  " + label + " (" + String.join("/", VALID_ROLES) + ") : ");
            role = scanner.nextLine().trim();
            if (role.isEmpty()) {
                System.out.println("  [!] Le rôle ne peut pas être vide.");
            } else if (!VALID_ROLES.contains(role)) {
                System.out.println("  [!] Rôle invalide. Veuillez choisir parmi : " + String.join("/", VALID_ROLES));
            }
        } while (role.isEmpty() || !VALID_ROLES.contains(role));
        return role;
    }
}
