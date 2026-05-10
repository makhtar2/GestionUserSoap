package sn.ucak.gestutil.server;

import jakarta.xml.ws.Endpoint;
import sn.ucak.gestutil.service.UtilisateurServiceImpl;

public class UtilisateurPublisher {
    public static void main(String[] args) {
        String url = "http://localhost:8080/ws/utilisateurs";
        System.out.println("Lancement du serveur Web Service...");
        System.out.println("Publication du service à l'adresse : " + url);
        
        // Suppression des logs JVM
        java.io.PrintStream originalErr = System.err;
        System.setErr(new java.io.PrintStream(new java.io.OutputStream() { @Override public void write(int b) {} }));
        
        try {
            // Publication effective du service
            Endpoint.publish(url, new UtilisateurServiceImpl());
        } finally {
            System.setErr(originalErr);
        }
        
        System.out.println("Service publié avec succès !");
        System.out.println("WSDL disponible à : " + url + "?wsdl");
    }
}
