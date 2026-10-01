package Vue;
import javax.swing.*;
import java.awt.*;

public class VueAccueilAdmin extends JFrame {

    private JButton btnSupprimerSpecialiste;
    private JButton btnVoirPatient;
    private JButton btnNouveauSpecialiste;

    public VueAccueilAdmin() {
        setTitle("Spécimed - Accueil");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Barre de navigation ===
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Color.WHITE);
        navBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        ImageIcon icon = new ImageIcon(getClass().getResource("logomedic.png"));
        Image image = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(image);

        JLabel logoLabel = new JLabel(resizedIcon);
        JLabel textLabel = new JLabel("Spécimed");
        textLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        textLabel.setForeground(new Color(14, 25, 119));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        logoPanel.setBackground(Color.WHITE);
        logoPanel.add(logoLabel);
        logoPanel.add(textLabel);

        JPanel navLinks = new JPanel(new FlowLayout(FlowLayout.RIGHT, 40, 5));
        navLinks.setBackground(Color.WHITE);
        String[] links = {"Accueil", "Mes RDV", "Déconnexion"};
        for (String link : links) {
            JLabel navItem = new JLabel(link);
            navItem.setFont(new Font("SansSerif", Font.PLAIN, 18));
            navItem.setForeground(new Color(14, 25, 119));
            navLinks.add(navItem);
        }

        navBar.add(logoPanel, BorderLayout.WEST);
        navBar.add(navLinks, BorderLayout.EAST);
        add(navBar, BorderLayout.NORTH);

        // === Corps central ===
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        JLabel accueilLabel = new JLabel("Accueil");
        accueilLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        accueilLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        accueilLabel.setForeground(new Color(14, 25, 119));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(accueilLabel);

        JLabel welcomeLabel = new JLabel("Bonjour  bienvenue sur spécimed, le site pour prendre vos rendez-vous.");
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(welcomeLabel);

        JLabel adminLabel = new JLabel("<html><div style='text-align: center;'>En tant que administrateur du site vous pouvez ajouter un patient ou un spécialiste<br>mais aussi en supprimer.</div></html>");
        adminLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
        adminLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(adminLabel);

        // === Boutons ===
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 40));

        btnSupprimerSpecialiste = new JButton("Supprimer un Spécialiste");
        btnVoirPatient = new JButton("Voir un patient");
        btnNouveauSpecialiste = new JButton("Nouveau Spécialiste");

        for (JButton btn : new JButton[]{btnSupprimerSpecialiste, btnVoirPatient, btnNouveauSpecialiste}) {
            btn.setFont(new Font("SansSerif", Font.PLAIN, 20));
            btn.setBackground(new Color(14, 25, 119));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setPreferredSize(new Dimension(260, 60));
            btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buttonPanel.add(btn);
        }

        // === Actions des boutons ===
        btnNouveauSpecialiste.addActionListener(e -> {
            dispose();
            new VueAjoutSpecialiste().setVisible(true);
        });

        btnSupprimerSpecialiste.addActionListener(e -> {
            dispose();
            new Supp_Spe().setVisible(true);
        });

        btnVoirPatient.addActionListener(e -> {
            dispose();
            new VueRechercheP().setVisible(true);

        });

        centerPanel.add(buttonPanel);
        add(centerPanel, BorderLayout.CENTER);
    }


}


