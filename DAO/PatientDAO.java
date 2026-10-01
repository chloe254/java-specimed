package DAO;

import modele.Patient;
import modele.Patient.TypePatient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des patients dans la base de données.
 */
public class PatientDAO {

    /**
     * Insère un nouveau patient dans la BDD.
     */
    public void create(Patient p) {
        String sql = "INSERT INTO patient (id, nom, email, mot_de_passe, type_patient) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getId());                        // id
            stmt.setString(2, p.getNom());                   // nom
            stmt.setString(3, p.getEmail());                 // email
            stmt.setString(4, p.getMotDePasse());            // mot de passe
            stmt.setString(5, p.getType().name());           // type

            stmt.executeUpdate();
            System.out.println("Patient ajouté avec succès.");

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du patient : " + e.getMessage());
        }
    }



    /**
     * Récupère un patient via son ID.
     */
    public Patient read(int id) {
        String sql = "SELECT * FROM patient WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Patient(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        TypePatient.valueOf(rs.getString("type_patient"))
                );
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la lecture du patient : " + e.getMessage());
        }
        return null;
    }

    /**
     * Met à jour les infos d’un patient.
     */
    public void update(Patient p) {
        String sql = "UPDATE patient SET nom = ?, email = ?, mot_de_passe = ?, type_patient = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNom());
            stmt.setString(2, p.getEmail());
            stmt.setString(3, p.getMotDePasse());
            stmt.setString(4, p.getType().name());
            stmt.setInt(5, p.getId());

            stmt.executeUpdate();
            System.out.println("Patient mis à jour.");

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    /**
     * Supprime un patient via son ID.
     */
    public void delete(int id) {
        String sql = "DELETE FROM patient WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Patient supprimé.");

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
    public List<Patient> findAll() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patient";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        Patient.TypePatient.valueOf(rs.getString("type_patient"))
                );
                list.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erreur findAll() patients : " + e.getMessage());
        }

        return list;
    }


    /**
     * Recherche un patient par email.
     */
    public Patient findByEmail(String email) {
        String sql = "SELECT * FROM patient WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Respecte bien l'ordre des paramètres du constructeur Patient
                return new Patient(
                        rs.getInt("id"),                            // ID
                        rs.getString("nom"),                        // NOM
                        rs.getString("email"),                      // EMAIL
                        rs.getString("mot_de_passe"),               // MDP
                        Patient.TypePatient.valueOf(rs.getString("type_patient")) // TYPE
                );
            }

        } catch (SQLException e) {
            System.out.println("Erreur dans findByEmail : " + e.getMessage());
        }

        return null;
    }

}
