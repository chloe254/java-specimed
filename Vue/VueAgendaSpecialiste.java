package Vue;

import DAO.AgendaDAO;
import controleur.GestionRendezVousControleur;
import modele.Patient;
import modele.Specialiste;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VueAgendaSpecialiste extends JFrame {

    private final Patient patient;
    private final Specialiste specialiste;
    private final AgendaDAO agendaDAO = new AgendaDAO();
    private final GestionRendezVousControleur rdvController = new GestionRendezVousControleur();

    private List<LocalDateTime> disponibilites;
    private JList<String> listeDispos;

    public VueAgendaSpecialiste(Patient patient, Specialiste specialiste) {
        this.patient = patient;
        this.specialiste = specialiste;
        initUI();
    }

    private void initUI() {
        setTitle("Agenda de " + specialiste.getNom());
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        disponibilites = agendaDAO.getDisponibilitesPourSpecialiste(specialiste.getId());

        DefaultListModel<String> model = new DefaultListModel<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (LocalDateTime dispo : disponibilites) {
            model.addElement(dispo.format(formatter));
        }

        listeDispos = new JList<>(model);
        listeDispos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton boutonValider = new JButton("Réserver ce créneau");
        boutonValider.addActionListener(e -> validerCreneau());

        add(new JScrollPane(listeDispos), BorderLayout.CENTER);
        add(boutonValider, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void validerCreneau() {
        int index = listeDispos.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Choisissez un créneau.");
            return;
        }

        LocalDateTime dateChoisie = disponibilites.get(index);

        // 1. Création du rendez-vous
        rdvController.creerRendezVous(patient, specialiste, dateChoisie);

        // 2. Suppression du créneau de l'agenda
        agendaDAO.supprimerCreneau(specialiste.getId(), dateChoisie);

        JOptionPane.showMessageDialog(this, "Rendez-vous confirmé !");
        dispose();
    }
}
