package controleur;

import DAO.PatientDAO;
import modele.Patient;
import modele.Patient.TypePatient;

public class AuthentificationControleur {

    private PatientDAO patientDAO;

    public AuthentificationControleur() {
        this.patientDAO = new PatientDAO();
    }

    public Patient seConnecter(String email, String mdp) {
        Patient patient = patientDAO.findByEmail(email);

        if (patient != null) {
            System.out.println("Email trouvé : " + patient.getEmail());
            System.out.println("Mot de passe BDD : " + patient.getMotDePasse());
            System.out.println("Mot de passe entré : " + mdp);

            if (mdp.equals(patient.getMotDePasse())) {
                System.out.println("Connexion réussie !");
                return patient;
            }
        }

        System.out.println("Email ou mot de passe incorrect.");
        return null;
    }


    public void inscrirePatient(String nom, String email, String mdp) {
        // Vérifie si un patient existe déjà avec cet email
        if (patientDAO.findByEmail(email) != null) {
            System.out.println("Un compte existe déjà avec cet email.");
            return;
        }

        // Création d’un nouvel identifiant (à améliorer avec auto-incrément ou logique DAO)
        int id = (int) (Math.random() * 10000);  // exemple temporaire

        Patient nouveauPatient = new Patient(id, nom, email, mdp, TypePatient.NOUVEAU);
        patientDAO.create(nouveauPatient);
        System.out.println("Inscription réussie pour " + nom);
    }
}
