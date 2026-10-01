package modele;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Personne {

    public enum TypePatient {
        NOUVEAU, ANCIEN
    }

    private TypePatient type;
    private List<RendezVous> historique;

    public Patient(int id, String nom, String email, String motDePasse, TypePatient type) {
        super(id, nom, email, motDePasse);
        this.type = type;
        this.historique = new ArrayList<>(); // important
    }



    public TypePatient getType() {
        return type;
    }

    public void setType(TypePatient type) {
        this.type = type;
    }

    public void prendreRendezVous(Specialiste s, RendezVous rdv) {
        if (s.estDisponible(rdv.getDateHeure())) {
            historique.add(rdv);
            System.out.println("Rendez-vous pris avec le spécialiste " + s.getNom());
        } else {
            System.out.println("Le spécialiste n'est pas disponible à cette date.");
        }
    }

    public void consulterHistorique() {
        System.out.println("Historique des rendez-vous pour " + nom + " :");
        for (RendezVous rdv : historique) {
            System.out.println(rdv);
        }
    }

    public void parcourirDisponibilite(Specialiste s) {
        System.out.println("Disponibilités de " + s.getNom() + " :");
        s.consulterPlanningSpecialiste();
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", mot_de_passe='" + motDePasse + '\'' +
                ", type=" + type +
            '}';
    }
}
