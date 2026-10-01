package Vue;

import controleur.AuthentificationControleur;

import javax.swing.*;
import java.awt.*;

public class VueInscriptionPatient extends JFrame {

    private JTextField nomField;
    private JTextField emailField;
    private JPasswordField mdpField;
    private JButton inscrireBtn;
    private JButton retourBtn;

    private final AuthentificationControleur authController = new AuthentificationControleur();

    public VueInscriptionPatient() {
        initUI();
    }

    private void initUI() {
        setTitle("Inscription Patient");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 5));

        nomField = new JTextField();
        emailField = new JTextField();
        mdpField = new JPasswordField();
        inscrireBtn = new JButton("S'inscrire");
        retourBtn = new JButton("Retour");

        inscrireBtn.addActionListener(e -> inscrire());
        retourBtn.addActionListener(e -> {
            dispose();
            new VueLogin();
        });

        add(new JLabel("Nom :"));
        add(nomField);
        add(new JLabel("Email :"));
        add(emailField);
        add(new JLabel("Mot de passe :"));
        add(mdpField);
        add(retourBtn);
        add(inscrireBtn);

        setVisible(true);
    }

    private void inscrire() {
        String nom = nomField.getText().trim();
        String email = emailField.getText().trim();
        String mdp = new String(mdpField.getPassword()).trim();

        if (nom.isEmpty() || email.isEmpty() || mdp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }

        authController.inscrirePatient(nom, email, mdp);
        JOptionPane.showMessageDialog(this, "Inscription réussie !");
        dispose();
        new VueLogin();
    }
}
