package DAO;

import modele.Specialiste;
import java.util.List;
import java.util.ArrayList;


import java.sql.*;

/**
 * DAO pour la gestion des spécialistes dans la base de données.
 */
public class SpecialisteDAO {

    /**
     * Ajoute un nouveau spécialiste à la base de données.
     */
    public boolean ajouterSpecialiste(Specialiste s) {
        String sql = "INSERT INTO specialiste (id, nom, email, motDePasse, specialite, numeroLicence, anneesExperience) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, s.getId());
            stmt.setString(2, s.getNom());
            stmt.setString(3, s.getEmail());
            stmt.setString(4, s.getMotDePasse());
            stmt.setString(5, s.getSpecialite());
            stmt.setString(6, s.getNumeroLicence());
            stmt.setInt(7, s.getAnneesExperience());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du spécialiste : " + e.getMessage());
            return false;
        }
    }

    /**
     * Récupère un spécialiste via son ID.
     */
    public Specialiste getSpecialisteParId(int id) {
        String sql = "SELECT * FROM specialiste WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Specialiste(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("motDePasse"),
                        rs.getString("specialite"),
                        rs.getString("numeroLicence"),
                        rs.getInt("anneesExperience")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du spécialiste : " + e.getMessage());
        }

        return null;
    }
    public List<Specialiste> findAll() {
        List<Specialiste> liste = new ArrayList<>();
        String sql = "SELECT * FROM specialiste";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Specialiste s = new Specialiste(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("motDePasse"),
                        rs.getString("specialite"),
                        rs.getString("numeroLicence"),
                        rs.getInt("anneesExperience")
                );
                liste.add(s);
            }

        } catch (SQLException e) {
            System.out.println("Erreur findAll() dans SpecialisteDAO : " + e.getMessage());
        }

        return liste;
    }

}
