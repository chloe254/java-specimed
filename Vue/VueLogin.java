package Vue;

import controleur.AuthentificationControleur;
import modele.Patient;
import modele.Specialiste;
import DAO.SpecialisteDAO;

import javax.swing.*;
import java.awt.*;

public class VueLogin extends JFrame {

    private JTextField champIdentifiant;
    private JPasswordField champMotDePasse;
    private JButton boutonConnexion;
    private JButton boutonInscription;
    private JComboBox<String> comboRole;

    private final AuthentificationControleur authController = new AuthentificationControleur();

    public VueLogin() {
        initUI();
    }

    private void initUI() {
        setTitle("Connexion");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 5));

        JLabel labelRole = new JLabel("Je suis :");
        comboRole = new JComboBox<>(new String[]{"Patient", "Spécialiste"});

        JLabel labelIdentifiant = new JLabel("Email (patient) ou ID (spécialiste) :");
        champIdentifiant = new JTextField();

        JLabel labelMotDePasse = new JLabel("Mot de passe :");
        champMotDePasse = new JPasswordField();

        boutonConnexion = new JButton("Se connecter");
        boutonInscription = new JButton("Créer un compte patient");

        boutonConnexion.addActionListener(e -> seConnecter());
        boutonInscription.addActionListener(e -> ouvrirInscription());

        add(labelRole);
        add(comboRole);
        add(labelIdentifiant);
        add(champIdentifiant);
        add(labelMotDePasse);
        add(champMotDePasse);
        add(new JLabel());
        add(boutonConnexion);
        add(new JLabel());
        add(boutonInscription);

        setVisible(true);
    }

    private void seConnecter() {
        String role = (String) comboRole.getSelectedItem();
        String identifiant = champIdentifiant.getText().trim();
        String mdp = new String(champMotDePasse.getPassword()).trim();

        if (role.equals("Patient")) {
            Patient patient = authController.seConnecter(identifiant, mdp);
            if (patient != null) {
                dispose();
                new VuePatient(patient);
            } else {
                JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect.");
            }
        } else if (role.equals("Spécialiste")) {
            try {
                int id = Integer.parseInt(identifiant);
                SpecialisteDAO dao = new SpecialisteDAO();
                Specialiste specialiste = dao.getSpecialisteParId(id);

                if (specialiste != null && mdp.equals(specialiste.getMotDePasse())) {
                    dispose();
                    new VueSpecialiste(specialiste);
                } else {
                    JOptionPane.showMessageDialog(this, "ID ou mot de passe incorrect.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "L’ID spécialiste doit être un nombre.");
            }
        }
    }

    private void ouvrirInscription() {
        dispose();
        new VueInscriptionPatient();
    }
}
