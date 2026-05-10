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

    // Données initiales
    static {
        utilisateurs.add(new Utilisateur("MAT001", "Wade", "Makhtar", "Etudiant", "771234567", "makhtar.wade@ucak.univ.sn"));
        utilisateurs.add(new Utilisateur("MAT002", "Aidara", "Pape Makhtar", "Professeur", "789876543", "pape.aidara@ucak.univ.sn"));
        utilisateurs.add(new Utilisateur("MAT003", "Samb", "Mame Bara", "Admin", "705554433", "mamebara.samb@ucak.univ.sn"));
        utilisateurs.add(new Utilisateur("MAT004", "Ndiaye", "Fatou", "Etudiant", "764443322", "fatou.ndiaye@ucak.univ.sn"));
    }

    @Override
    // Retourne l'Utilisateur ajouté avec matricule généré, ou null en cas d'échec.
    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
        // Génère un nouveau matricule (ex: UCAK001, UCAK002, ...)
        String newMatricule = "UCAK" + String.format("%03d", matriculeCounter++);
        utilisateur.setMatricule(newMatricule);
        
        // Ajoute l'utilisateur avec le matricule généré.
        // La vérification de doublon sur d'autres champs pourrait être ajoutée ici si nécessaire.
        if (utilisateurs.add(utilisateur)) {
            return utilisateur; // Retourne l'utilisateur complet avec son matricule assigné
        } else {
            // Ce cas est improbable avec un simple compteur, mais gère une éventuelle erreur d'ajout.
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
        // Pour la modification, on suppose que le matricule est déjà présent sur l'objet utilisateur.
        // La logique de modification cherche par matricule existant et met à jour.
        for (int i = 0; i < utilisateurs.size(); i++) {
            if (utilisateurs.get(i).getMatricule().equalsIgnoreCase(utilisateur.getMatricule())) {
                utilisateurs.set(i, utilisateur);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean supprimerUtilisateur(String matricule) {
        return utilisateurs.removeIf(u -> u.getMatricule().equalsIgnoreCase(matricule));
    }
}
