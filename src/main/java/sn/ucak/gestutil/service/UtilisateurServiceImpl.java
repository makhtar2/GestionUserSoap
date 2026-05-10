package sn.ucak.gestutil.service;

import jakarta.jws.WebService;
import sn.ucak.gestutil.model.Utilisateur;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays; // For role validation

@WebService(endpointInterface = "sn.ucak.gestutil.service.IUtilisateurService")
public class UtilisateurServiceImpl implements IUtilisateurService {
    
    // Stockage en mémoire
    private static List<Utilisateur> utilisateurs = new ArrayList<>();
    // Compteur pour générer des matricules uniques
    private static int matriculeCounter = 1;

    static {
        utilisateurs.add(new Utilisateur("MAT001", "Wade", "Makhtar", "Etudiant", "771234567", "makhtar.wade@ucak.univ.sn"));
        utilisateurs.add(new Utilisateur("MAT002", "Aidara", "Pape Makhtar", "Professeur", "789876543", "pape.aidara@ucak.univ.sn"));
        utilisateurs.add(new Utilisateur("MAT003", "Samb", "Mame Bara", "Admin", "705554433", "mamebara.samb@ucak.univ.sn"));
        utilisateurs.add(new Utilisateur("MAT004", "Ndiaye", "Fatou", "Etudiant", "764443322", "fatou.ndiaye@ucak.univ.sn"));
    }

    @Override
    // Retourne l'Utilisateur ajouté avec matricule généré, ou null en cas d'échec.
    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
        // Validation serveur : aucun champ ne doit être vide
        if (isInvalid(utilisateur)) {
            return null;
        }

        // Génère un nouveau matricule (ex: UCAK001, UCAK002, ...)
        String newMatricule = "UCAK" + String.format("%03d", matriculeCounter++);
        utilisateur.setMatricule(newMatricule);
        
        // Ajoute l'utilisateur avec le matricule généré.
        if (utilisateurs.add(utilisateur)) {
            return utilisateur; 
        } else {
            return null; 
        }
    }

    @Override
    public List<Utilisateur> listerUtilisateurs() {
        return new ArrayList<>(utilisateurs);
    }

    @Override
    public Utilisateur rechercherUtilisateur(String matricule) {
        return utilisateurs.stream()
                .filter(u -> u.getMatricule().equalsIgnoreCase(matricule))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean modifierUtilisateur(Utilisateur utilisateur) {
        // Validation serveur : aucun champ ne doit être vide (matricule inclus pour modif)
        if (isInvalid(utilisateur) || utilisateur.getMatricule() == null || utilisateur.getMatricule().isEmpty()) {
            return false;
        }

        // La logique de modification cherche par matricule existant et met à jour.
        for (int i = 0; i < utilisateurs.size(); i++) {
            if (utilisateurs.get(i).getMatricule().equalsIgnoreCase(utilisateur.getMatricule())) {
                utilisateurs.set(i, utilisateur);
                return true;
            }
        }
        return false;
    }

    // Méthode utilitaire de validation
    private boolean isInvalid(Utilisateur u) {
        return u == null || 
               u.getNom() == null || u.getNom().trim().isEmpty() ||
               u.getPrenom() == null || u.getPrenom().trim().isEmpty() ||
               u.getRole() == null || u.getRole().trim().isEmpty() ||
               u.getTelephone() == null || u.getTelephone().trim().isEmpty() ||
               u.getEmail() == null || u.getEmail().trim().isEmpty();
    }

    @Override
    public boolean supprimerUtilisateur(String matricule) {
        return utilisateurs.removeIf(u -> u.getMatricule().equalsIgnoreCase(matricule));
    }
}
