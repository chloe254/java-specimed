package Vue;

import DAO.AdminDAO;
import DAO.SpecialisteDAO;
import modele.Specialiste;

import javax.swing.*;
import java.awt.*;

public class Supp_Spe extends JFrame {

    private JTextField idField;
    private JTextField specialiteField;
    private JComboBox<String> resultCombo;
    private Specialiste specialistResult;

    public Supp_Spe() {
        setTitle("Suppression Spécialiste");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color bleu = new Color(10, 28, 103);

        // ===== NAVIGATION =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        header.setBackground(Color.WHITE);

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setOpaque(false);
        JLabel logo = new JLabel(new ImageIcon("logomedic.png"));
        JLabel title = new JLabel("Spécimed");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(bleu);
        logoPanel.add(logo);
        logoPanel.add(Box.createHorizontalStrut(10));
        logoPanel.add(title);

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navPanel.setOpaque(false);

        JLabel accueilLink = new JLabel("Accueil");
        accueilLink.setFont(new Font("SansSerif", Font.PLAIN, 16));
        accueilLink.setForeground(bleu);
        accueilLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        accueilLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new VueAccueilAdmin().setVisible(true);
            }
        });

        JLabel comptelink = new JLabel("Déconnexion");
        comptelink.setFont(new Font("SansSerif", Font.PLAIN, 16));
        comptelink.setForeground(new Color(10, 28, 103));
        comptelink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        comptelink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new VueLogin().setVisible(true);
            }
        });

        navPanel.add(accueilLink);

        navPanel.add(Box.createHorizontalStrut(30));
        navPanel.add(comptelink);

        header.add(logoPanel, BorderLayout.WEST);
        header.add(navPanel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // ===== CONTENU =====
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        JLabel titre = new JLabel("Recherche Spécialiste");
        titre.setFont(new Font("SansSerif", Font.BOLD, 36));
        titre.setForeground(bleu);
        titre.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(titre);
        contentPanel.add(Box.createVerticalStrut(30));

        // PANEL RECHERCHE
        JPanel recherchePanel = new JPanel(new GridBagLayout());
        recherchePanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        recherchePanel.add(new JLabel("ID :"), gbc);

        gbc.gridx = 1;
        idField = new JTextField();
        idField.setPreferredSize(new Dimension(200, 30));
        recherchePanel.add(idField, gbc);

        gbc.gridx = 2;
        JButton btnSearchId = new JButton("Rechercher");
        recherchePanel.add(btnSearchId, gbc);

        // Spécialité
        gbc.gridx = 0;
        gbc.gridy = 1;
        recherchePanel.add(new JLabel("Spécialité :"), gbc);

        gbc.gridx = 1;
        specialiteField = new JTextField();
        specialiteField.setPreferredSize(new Dimension(200, 30));
        recherchePanel.add(specialiteField, gbc);

        gbc.gridx = 2;
        JButton btnSearchSpe = new JButton("Rechercher");
        recherchePanel.add(btnSearchSpe, gbc);

        // Résultat
        gbc.gridx = 0;
        gbc.gridy = 2;
        recherchePanel.add(new JLabel("Spécialiste trouvé :"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        resultCombo = new JComboBox<>();
        recherchePanel.add(resultCombo, gbc);

        contentPanel.add(recherchePanel);

        // BOUTON SUPPRIMER
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomButtonPanel.setBackground(Color.WHITE);

        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.setBackground(bleu);
        btnSupprimer.setForeground(Color.WHITE);
        btnSupprimer.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSupprimer.setFocusPainted(false);
        bottomButtonPanel.add(btnSupprimer);

        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(bottomButtonPanel);
        add(contentPanel, BorderLayout.CENTER);

        // === LOGIQUE ===
        SpecialisteDAO dao = new SpecialisteDAO();

        btnSearchId.addActionListener(e -> {
            resultCombo.removeAllItems();
            try {
                int id = Integer.parseInt(idField.getText().trim());
                Specialiste s = dao.getSpecialisteParId(id);
                if (s != null) {
                    specialistResult = s;
                    resultCombo.addItem(s.getId() + " - " + s.getNom() + " (" + s.getSpecialite() + ")");
                } else {
                    resultCombo.addItem("Aucun spécialiste trouvé.");
                }
            } catch (NumberFormatException ex) {
                resultCombo.addItem("ID invalide.");
            }
        });

        btnSearchSpe.addActionListener(e -> {
            resultCombo.removeAllItems();
            String spe = specialiteField.getText().trim();
            // Implémenter un dao.findBySpecialite si tu veux aller plus loin
            resultCombo.addItem("Recherche par spécialité à implémenter.");
        });

        btnSupprimer.addActionListener(e -> {
            if (specialistResult != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Supprimer le spécialiste : " + specialistResult.getNom() + " ?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean deleted = new AdminDAO().supprimerSpecialiste(specialistResult.getId());
                    if (deleted) {
                        JOptionPane.showMessageDialog(this, "Spécialiste supprimé.");
                        resultCombo.removeAllItems();
                        specialistResult = null;
                    } else {
                        JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Aucun spécialiste sélectionné.");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Supp_Spe().setVisible(true));
    }
}
