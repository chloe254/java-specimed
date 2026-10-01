package DAO;


import modele.Admin;
import modele.Patient;
import modele.Specialiste;
import modele.Patient.TypePatient;

import java.sql.*;

/**
 * DAO pour les actions spécifiques d'administration (ajout/suppression)
 */
public class AdminDAO {

    /**
     * Ajoute un nouveau spécialiste dans la base.
     */
    public boolean ajouterSpecialiste(Specialiste s) {
        String sql = "INSERT INTO specialiste (nom, email, motDePasse, specialite, numeroLicence, anneesExperience) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, s.getNom());
            stmt.setString(2, s.getEmail());
            stmt.setString(3, s.getMotDePasse());
            stmt.setString(4, s.getSpecialite());
            stmt.setString(5, s.getNumeroLicence());
            stmt.setInt(6, s.getAnneesExperience());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenere = rs.getInt(1);
                    s.setId(idGenere);
                    System.out.println("Spécialiste ajouté avec ID : " + idGenere);
                }
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Erreur ajout spécialiste : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprime un spécialiste de la base par son ID.
     */
    public boolean supprimerSpecialiste(int id) {
        String sql = "DELETE FROM specialiste WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Spécialiste supprimé.");
                return true;
            } else {
                System.out.println("Aucun spécialiste trouvé avec cet ID.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Erreur suppression spécialiste : " + e.getMessage());
            return false;
        }
    }

    /**
     * Récupère un patient par son ID.
     */
    public Patient getPatientParId(int id) {
        String sql = "SELECT * FROM patient WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Patient patient = new Patient(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        TypePatient.valueOf(rs.getString("type_patient"))
                );
                return patient;
            }

        } catch (SQLException e) {
            System.out.println("Erreur récupération patient : " + e.getMessage());
        }

        System.out.println("Patient introuvable.");
        return null;
    }
    public Admin findByEmail(String email) {
        String sql = "SELECT * FROM admin WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erreur lecture admin : " + e.getMessage());
        }

        return null;
    }
}
