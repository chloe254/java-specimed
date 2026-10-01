package controleur;

import DAO.PatientDAO;
import DAO.SpecialisteDAO;
import DAO.RendezVousDAO;
import modele.Patient;
import modele.Specialiste;
import modele.RendezVous;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class GestionRendezVousControleur {

    private final PatientDAO patientDAO;
    private final SpecialisteDAO specialisteDAO;
    private final RendezVousDAO rendezVousDAO;

    public GestionRendezVousControleur() {
        this.patientDAO = new PatientDAO();
        this.specialisteDAO = new SpecialisteDAO(); // à ajuster si ton constructeur prend un Connection
        this.rendezVousDAO = new RendezVousDAO(patientDAO); // Injection du DAO patient
    }

    /**
     * Crée un rendez-vous entre un patient et un spécialiste à une date donnée.
     */
    public void creerRendezVous(Patient patient, Specialiste specialiste, LocalDateTime dateHeure) {
        Date date = Date.from(dateHeure.atZone(ZoneId.systemDefault()).toInstant());

        if (!specialiste.estDisponible(date)) {
            System.out.println("Le spécialiste n'est pas disponible à cette date.");
            return;
        }

        RendezVous rdv = new RendezVous(0, date, "Lieu à définir", patient, specialiste);

        boolean success = rendezVousDAO.create(rdv);
        if (success) {
            System.out.println("Rendez-vous créé avec succès !");
        } else {
            System.out.println("Échec lors de la création du rendez-vous.");
        }
    }

    /**
     * Annule un rendez-vous à partir de son ID.
     */
    public void annulerRendezVous(int rdvId) {
        RendezVous rdv = rendezVousDAO.read(rdvId);
        if (rdv == null) {
            System.out.println("Rendez-vous introuvable.");
            return;
        }

        boolean annule = rendezVousDAO.annuler(rdvId);
        if (annule) {
            System.out.println("Rendez-vous annulé avec succès.");
        } else {
            System.out.println("Échec de l'annulation.");
        }
    }

    /**
     * Affiche le planning à venir d'un utilisateur (patient ou spécialiste) selon son ID.
     */
    public void consulterPlanningUtilisateur(int id) {
        Patient patient = patientDAO.read(id);

        if (patient != null) {
            List<RendezVous> planning = rendezVousDAO.findFutursByPatient(id);
            System.out.println("\nPlanning à venir du patient " + patient.getNom() + " :");
            planning.forEach(System.out::println);
            return;
        }

        try {
            Specialiste specialiste = specialisteDAO.getSpecialisteParId(id);
            if (specialiste != null) {
                List<RendezVous> planning = rendezVousDAO.findFutursBySpecialiste(id);
                System.out.println("\nPlanning à venir du spécialiste " + specialiste.getNom() + " :");
                planning.forEach(System.out::println);
                return;
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération du spécialiste : " + e.getMessage());
        }

        System.out.println("Utilisateur introuvable.");
    }

    /**
     * Affiche l'historique de tous les rendez-vous d’un patient.
     */
    public void consulterHistorique(int patientId) {
        Patient patient = patientDAO.read(patientId);
        if (patient == null) {
            System.out.println("Patient introuvable.");
            return;
        }

        List<RendezVous> historique = rendezVousDAO.findHistoriqueByPatient(patientId);
        System.out.println("\nHistorique des rendez-vous de " + patient.getNom() + " :");
        historique.forEach(System.out::println);
    }
}
