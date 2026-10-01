package Vue;

import DAO.SpecialisteDAO;
import controleur.GestionRendezVousControleur;
import modele.Patient;
import modele.Specialiste;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class VuePriseRendezVousParSpecialite extends JFrame {

    private final Patient patient;
    private final SpecialisteDAO specialisteDAO = new SpecialisteDAO();
    private final GestionRendezVousControleur rdvController = new GestionRendezVousControleur();

    private JTextField champSpecialite;
    private JTextField champNom;
    private JComboBox<String> comboSpecialistes;
    private JButton boutonVoirAgenda;

    private List<Specialiste> specialistesFiltres;

    public VuePriseRendezVousParSpecialite(Patient patient) {
        this.patient = patient;
        initUI();
    }

    private void initUI() {
        setTitle("Prendre un rendez-vous par spécialité");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 5));

        champSpecialite = new JTextField();
        champNom = new JTextField();
        comboSpecialistes = new JComboBox<>();
        boutonVoirAgenda = new JButton("Voir les disponibilités");

        JButton boutonRecherche = new JButton("Rechercher");
        boutonRecherche.addActionListener(e -> rechercherSpecialistes());

        boutonVoirAgenda.addActionListener(e -> ouvrirAgenda());

        add(new JLabel("Spécialité :"));
        add(champSpecialite);
        add(new JLabel("Nom du médecin :"));
        add(champNom);
        add(boutonRecherche);
        add(new JLabel());
        add(new JLabel("Spécialistes trouvés :"));
        add(comboSpecialistes);
        add(new JLabel());
        add(boutonVoirAgenda);

        setVisible(true);
    }

    private void rechercherSpecialistes() {
        String specialite = champSpecialite.getText().trim().toLowerCase();
        String nom = champNom.getText().trim().toLowerCase();

        specialistesFiltres = specialisteDAO.findAll().stream()
                .filter(s -> s.getSpecialite().toLowerCase().contains(specialite))
                .filter(s -> s.getNom().toLowerCase().contains(nom))
                .collect(Collectors.toList());

        comboSpecialistes.removeAllItems();
        for (Specialiste s : specialistesFiltres) {
            comboSpecialistes.addItem(s.getId() + " - " + s.getNom());
        }
    }

    private void ouvrirAgenda() {
        int index = comboSpecialistes.getSelectedIndex();
        if (index == -1 || specialistesFiltres.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un spécialiste.");
            return;
        }

        Specialiste s = specialistesFiltres.get(index);
        new VueAgendaSpecialiste(patient, s);
        dispose();
    }
}
