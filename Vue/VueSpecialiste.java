package Vue;

import modele.Specialiste;
import controleur.GestionRendezVousControleur;

import javax.swing.*;
import java.awt.*;

public class VueSpecialiste extends JFrame {

    private final Specialiste specialiste;
    private final GestionRendezVousControleur rdvController;

    public VueSpecialiste(Specialiste specialiste) {
        this.specialiste = specialiste;
        this.rdvController = new GestionRendezVousControleur();
        initUI();
    }

    private void initUI() {
        setTitle("Espace Spécialiste - " + specialiste.getNom());
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titre = new JLabel("Bienvenue, Dr. " + specialiste.getNom());
        titre.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnPlanning = new JButton("Voir planning");
        JButton btnQuitter = new JButton("Déconnexion");

        btnPlanning.addActionListener(e -> {
            rdvController.consulterPlanningUtilisateur(specialiste.getId());
        });

        btnQuitter.addActionListener(e -> {
            dispose();
            new VueLogin();
        });

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.add(titre);
        panel.add(btnPlanning);
        panel.add(btnQuitter);

        add(panel);
        setVisible(true);
    }
}
