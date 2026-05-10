package sn.ucak.gestutil.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import sn.ucak.gestutil.model.Utilisateur;
import java.util.ArrayList;
import java.util.List;

@WebService(name = "IUtilisateurService") // Nom du portType (WSDL)
public interface IUtilisateurService {

    @WebMethod(operationName = "ajout") // Nom de l'opération XML
    // Changement : retourne l'objet Utilisateur avec le matricule généré, ou null si échec
    Utilisateur ajouterUtilisateur(@WebParam(name = "utilisateur") Utilisateur utilisateur); // Paramètre explicite XML

    @WebMethod(operationName = "liste")
    List<Utilisateur> listerUtilisateurs();

    @WebMethod(operationName = "recherche")
    Utilisateur rechercherUtilisateur(@WebParam(name = "matricule") String matricule);

    @WebMethod(operationName = "modification")
    boolean modifierUtilisateur(@WebParam(name = "utilisateur") Utilisateur utilisateur);

    @WebMethod(operationName = "suppression")
    boolean supprimerUtilisateur(@WebParam(name = "matricule") String matricule);
}
