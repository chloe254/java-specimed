package Vue;

import modele.Patient;
import controleur.GestionRendezVousControleur;

import javax.swing.*;
import java.awt.*;

public class VuePatient extends JFrame {

    private final Patient patient;
    private final GestionRendezVousControleur rdvController;

    public VuePatient(Patient patient) {
        this.patient = patient;
        this.rdvController = new GestionRendezVousControleur();
        initUI();
    }

    private void initUI() {
        setTitle("Espace Patient - " + patient.getNom());
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel bienvenueLabel = new JLabel("Bienvenue, " + patient.getNom());
        bienvenueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnPlanning = new JButton("Voir planning");
        JButton btnHistorique = new JButton("Historique");
        JButton btnRdv = new JButton("Prendre un rendez-vous");
        JButton btnQuitter = new JButton("Déconnexion");

        btnPlanning.addActionListener(e -> {
            rdvController.consulterPlanningUtilisateur(patient.getId());
        });

        btnHistorique.addActionListener(e -> {
            rdvController.consulterHistorique(patient.getId());
        });

        btnRdv.addActionListener(e -> {
            new VuePriseRendezVousParSpecialite(patient);
        });

        btnQuitter.addActionListener(e -> {
            dispose();
            new VueLogin();
        });

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.add(bienvenueLabel);
        panel.add(btnPlanning);
        panel.add(btnHistorique);
        panel.add(btnRdv);
        panel.add(btnQuitter);

        add(panel);
        setVisible(true);
    }
}
