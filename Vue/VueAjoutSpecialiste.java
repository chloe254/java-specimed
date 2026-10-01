package Vue;

import javax.swing.*;
import java.awt.*;
import DAO.AdminDAO;
import modele.Specialiste;

public class VueAjoutSpecialiste extends JFrame {

    private JTextField champNom, champPrenom, champEmail, champMotDePasse;
    private JTextField champSpecialite, champLicence, champExperience, champDateNaissance;
    private JRadioButton rbHomme, rbFemme, rbAutre;
    private JButton btnSuivant;

    public VueAjoutSpecialiste() {
        setTitle("Spécimed - Nouveau Spécialiste");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color bleuFonce = new Color(14, 25, 119);

        // ===== Titre + logo à gauche =====
        JPanel gauche = new JPanel();
        gauche.setLayout(new BoxLayout(gauche, BoxLayout.Y_AXIS));
        gauche.setBackground(Color.WHITE);
        gauche.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("logomedic.png"));
        Image scaled = logoIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(scaled));
        JLabel titre = new JLabel("Spécimed");
        titre.setFont(new Font("SansSerif", Font.BOLD, 20));
        titre.setForeground(bleuFonce);

        JLabel sousTitre = new JLabel("Nouveau Spécialiste");
        sousTitre.setFont(new Font("SansSerif", Font.BOLD, 32));
        sousTitre.setForeground(bleuFonce);

        JLabel labelSpecialite = new JLabel("Spécialité");
        champSpecialite = new JTextField();

        JLabel labelLicence = new JLabel("Numéro de licence");
        champLicence = new JTextField();

        JLabel labelExperience = new JLabel("Année d’expérience");
        champExperience = new JTextField();

        JLabel labelNom = new JLabel("Nom");
        champNom = new JTextField();

        for (JComponent c : new JComponent[]{labelSpecialite, champSpecialite, labelLicence, champLicence,
                labelExperience, champExperience, labelNom, champNom}) {
            c.setAlignmentX(Component.LEFT_ALIGNMENT);
            if (c instanceof JTextField) {
                ((JTextField) c).setBackground(bleuFonce);
                ((JTextField) c).setForeground(Color.WHITE);
                ((JTextField) c).setFont(new Font("SansSerif", Font.PLAIN, 16));
                ((JTextField) c).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            }
        }

        gauche.add(logo);
        gauche.add(titre);
        gauche.add(Box.createRigidArea(new Dimension(0, 20)));
        gauche.add(sousTitre);
        gauche.add(Box.createRigidArea(new Dimension(0, 30)));
        gauche.add(labelSpecialite);
        gauche.add(champSpecialite);
        gauche.add(Box.createRigidArea(new Dimension(0, 10)));
        gauche.add(labelLicence);
        gauche.add(champLicence);
        gauche.add(Box.createRigidArea(new Dimension(0, 10)));
        gauche.add(labelExperience);
        gauche.add(champExperience);
        gauche.add(Box.createRigidArea(new Dimension(0, 10)));
        gauche.add(labelNom);
        gauche.add(champNom);

        // ===== Droite : infos complémentaires =====
        JPanel droite = new JPanel();
        droite.setLayout(new BoxLayout(droite, BoxLayout.Y_AXIS));
        droite.setBackground(Color.WHITE);
        droite.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel labelPrenom = new JLabel("Prénom");
        champPrenom = new JTextField();

        JLabel labelEmail = new JLabel("email");
        champEmail = new JTextField();

        JLabel labelMotDePasse = new JLabel("Mot de passe");
        champMotDePasse = new JTextField();

        JLabel labelGenre = new JLabel("Genre");

        rbHomme = new JRadioButton("Homme");
        rbFemme = new JRadioButton("Femme");
        rbAutre = new JRadioButton("Autre");
        ButtonGroup genreGroup = new ButtonGroup();
        genreGroup.add(rbHomme);
        genreGroup.add(rbFemme);
        genreGroup.add(rbAutre);

        JPanel genrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genrePanel.setBackground(Color.WHITE);
        genrePanel.add(rbHomme);
        genrePanel.add(rbFemme);
        genrePanel.add(rbAutre);

        JLabel labelNaissance = new JLabel("Date de naissance");
        champDateNaissance = new JTextField();

        btnSuivant = new JButton("Suivant");
        btnSuivant.setBackground(bleuFonce);
        btnSuivant.setForeground(Color.WHITE);
        btnSuivant.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnSuivant.setFocusPainted(false);

        for (JComponent c : new JComponent[]{champPrenom, champEmail, champMotDePasse, champDateNaissance}) {
            c.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            if (c instanceof JTextField) {
                ((JTextField) c).setBackground(bleuFonce);
                ((JTextField) c).setForeground(Color.WHITE);
                ((JTextField) c).setFont(new Font("SansSerif", Font.PLAIN, 16));
                ((JTextField) c).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            }
        }

        droite.add(labelPrenom);
        droite.add(champPrenom);
        droite.add(Box.createRigidArea(new Dimension(0, 10)));
        droite.add(labelEmail);
        droite.add(champEmail);
        droite.add(Box.createRigidArea(new Dimension(0, 10)));
        droite.add(labelMotDePasse);
        droite.add(champMotDePasse);
        droite.add(Box.createRigidArea(new Dimension(0, 10)));
        droite.add(labelGenre);
        droite.add(genrePanel);
        droite.add(Box.createRigidArea(new Dimension(0, 10)));
        droite.add(labelNaissance);
        droite.add(champDateNaissance);
        droite.add(Box.createRigidArea(new Dimension(0, 20)));
        droite.add(btnSuivant);

        // Panel principal
        JPanel global = new JPanel(new GridLayout(1, 2));
        global.add(gauche);
        global.add(droite);

        add(global, BorderLayout.CENTER);
        btnSuivant.addActionListener(e -> {
            try {
                String nom = champNom.getText().trim();
                String prenom = champPrenom.getText().trim();
                String email = champEmail.getText().trim();
                String motDePasse = champMotDePasse.getText().trim();
                String specialite = champSpecialite.getText().trim();
                String licence = champLicence.getText().trim();
                int experience = Integer.parseInt(champExperience.getText().trim());




                // Crée un objet spécialiste
                Specialiste spe = new Specialiste(0, nom, email, motDePasse, specialite, licence, experience);

                // Appel DAO
                AdminDAO dao = new AdminDAO();
                boolean success = dao.ajouterSpecialiste(spe);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Spécialiste ajouté !");
                    dispose(); // Ferme la fenêtre
                } else {
                    JOptionPane.showMessageDialog(this, "Échec de l'ajout.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
            dispose();
            new VueAccueilAdmin().setVisible(true);
        });

    }


}
