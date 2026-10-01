package modele;

import java.util.Date;

public class RendezVous {
    // Attributs
    private int id;
    private Date dateHeure;
    private String statut;
    private String note;
    private String lieu;
    private Patient patient;
    private Specialiste specialiste;

    // Constructeur
    public RendezVous(int id, Date dateHeure, String lieu, Patient patient, Specialiste specialiste) {
        this.id = id;
        this.dateHeure = dateHeure;
        this.lieu = lieu;
        this.patient = patient;
        this.specialiste = specialiste;
        this.statut = "Programmé";
        this.note = "";
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(Date dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Specialiste getSpecialiste() {
        return specialiste;
    }

    public void setSpecialiste(Specialiste specialiste) {
        this.specialiste = specialiste;
    }

    // Méthode : annuler
    public boolean annuler() {
        if (!this.statut.equals("Annulé")) {
            this.statut = "Annulé";
            return true;
        }
        return false;
    }

    // Méthode : estDisponible
    public boolean estDisponible() {
        Date maintenant = new Date();
        return dateHeure.after(maintenant) && !statut.equals("Annulé") && specialiste.estDisponible(dateHeure);
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "id=" + id +
                ", dateHeure=" + dateHeure +
                ", statut='" + statut + '\'' +
                ", note='" + note + '\'' +
                ", lieu='" + lieu + '\'' +
                ", patient=" + (patient != null ? patient.getNom() + " " + patient.getPrenom() : "Non spécifié") +
                ", specialiste=" + (specialiste != null ? specialiste.getNom() + " " + specialiste.getPrenom() : "Non spécifié") +
                '}';
    }
}
