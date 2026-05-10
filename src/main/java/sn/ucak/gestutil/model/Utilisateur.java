package sn.ucak.gestutil.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "utilisateur") // Sérialisation XML
@XmlAccessorType(XmlAccessType.FIELD) // Accès direct aux champs
public class Utilisateur implements Serializable {
    private String matricule;
    private String nom;
    private String prenom;
    private String role;
    private String telephone;
    private String email;

    public Utilisateur() {
    }

    public Utilisateur(String matricule, String nom, String prenom, String role, String telephone, String email) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.telephone = telephone;
        this.email = email;
    }

    // Getters et Setters
    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "matricule='" + matricule + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", role='" + role + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
