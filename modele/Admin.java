package modele;

import java.util.ArrayList;
import java.util.List;

public class Admin extends Personne {
    private List<Specialiste> specialistes;
    private List<RendezVous> rendezVousList;

    public Admin(int id, String nom, String email, String motDePasse) {
        super(id, nom, email, motDePasse);
        this.specialistes = new ArrayList<>();
        this.rendezVousList = new ArrayList<>();
    }

    public void ajouterSpecialiste(Specialiste spe) {
        specialistes.add(spe);
    }

    public void gererRendezVous(RendezVous rdv) {
        rendezVousList.add(rdv);
    }

    public List<RendezVous> afficherHistoriqueGlobal() {
        return new ArrayList<>(rendezVousList);
    }

    public void voirPlanning() {
        System.out.println("Planning global des rendez-vous :");
        for (RendezVous rdv : rendezVousList) {
            System.out.println(rdv);
        }
    }
}
