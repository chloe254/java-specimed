

package Vue;
import javax.swing.*;
import java.awt.*;
import DAO.PatientDAO;
import modele.Patient;

public class VueRechercheP extends JFrame {

    public VueRechercheP() {
        JTextField emailField = new JTextField();
        JComboBox<String> resultCombo = new JComboBox<>(new String[]{
                "Aucun résultat pour le moment"
        });
        setTitle("Recherche Spécialiste");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        header.setBackground(Color.WHITE);

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setOpaque(false);
        JLabel logo = new JLabel(new ImageIcon("logomedic.png"));
        JLabel title = new JLabel("Spécimed");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(10, 28, 103));
        logoPanel.add(logo);
        logoPanel.add(Box.createHorizontalStrut(10));
        logoPanel.add(title);

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navPanel.setOpaque(false);

        JLabel accueilLink = new JLabel("Accueil");
        accueilLink.setFont(new Font("SansSerif", Font.PLAIN, 16));
        accueilLink.setForeground(new Color(10, 28, 103));
        accueilLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        accueilLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose(); // ferme cette fenêtre
                new VueAccueilAdmin().setVisible(true); // ou new AccueilVue() selon ton projet
            }
        });
        navPanel.add(Box.createHorizontalStrut(20));
        navPanel.add(accueilLink);
        navPanel.add(Box.createHorizontalStrut(20));



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

        JLabel rechercheLabel = new JLabel("Recherche");
        rechercheLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        rechercheLabel.setForeground(new Color(10, 28, 103));
        rechercheLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(rechercheLabel);
        contentPanel.add(Box.createVerticalStrut(30));

        // Panel pour les champs de recherche
        // Panel pour la recherche + résultat alignés horizontalement
        JPanel recherchePanel = new JPanel(new GridBagLayout());
        recherchePanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;

// === Ligne 1 : Email + champ + loupe ===
        gbc.gridx = 0;
        gbc.gridy = 0;
        recherchePanel.add(new JLabel("email :"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;

        recherchePanel.add(emailField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        JButton btnSearch = new JButton("Rechercher");
        btnSearch.setFocusable(false);
        recherchePanel.add(btnSearch, gbc);
        PatientDAO dao = new PatientDAO();

        btnSearch.addActionListener(e -> {
            String email = emailField.getText().trim();
            resultCombo.removeAllItems();

            if (email.isEmpty()) {
                resultCombo.addItem("Email vide.");
                return;
            }

            Patient p = dao.findByEmail(email);
            if (p != null) {
                resultCombo.addItem(p.getId() + " - " + p.getNom() + " (" + p.getEmail() + ")");
                resultCombo.putClientProperty("patient", p); // pour le bouton consulter
            } else {
                resultCombo.addItem("Aucun patient trouvé.");
            }
        });


// === Ligne 2 : Résultat + combo + bouton consulter ===
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0;
        recherchePanel.add(new JLabel("Patient trouvé :"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;

        recherchePanel.add(resultCombo, gbc);




        // Label Nom du médecin


        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(recherchePanel);

        // Bouton "Voir les disponibilités" en bas à droite
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        JButton dispoBtn = new JButton("Consulter le profil ");
        dispoBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        dispoBtn.setBackground(new Color(10, 28, 103));
        dispoBtn.setForeground(Color.WHITE);
        dispoBtn.setFocusPainted(false);
        dispoBtn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        buttonPanel.add(dispoBtn,gbc);
        dispoBtn.addActionListener(e -> {
            Patient p = (Patient) resultCombo.getClientProperty("patient");
            if (p != null) {
                JOptionPane.showMessageDialog(this,
                        "Patient trouvé :\n\n" +
                                "ID : " + p.getId() + "\n" +
                                "Nom : " + p.getNom() + "\n" +
                                "Email : " + p.getEmail() + "\n" +
                                "Type : " + p.getType(),
                        "Détails du patient",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Aucun patient sélectionné.",
                        "Erreur",
                        JOptionPane.WARNING_MESSAGE);
            }
        });



        contentPanel.add(buttonPanel);

        add(contentPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VueRechercheP().setVisible(true));
    }
}

