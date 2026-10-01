package modele;

import java.sql.*;
import java.util.*;

public class Specialiste extends Personne {
    private String specialite;
    private String numeroLicence;
    private int anneesExperience;

    private final String url = "jdbc:mysql://localhost:3306/pj_java";
    private final String user = "root";
    private final String password = "";

    public Specialiste(int id, String nom, String email, String motDePasse, String specialite,
                       String numeroLicence, int anneesExperience) {
        super(id, nom, email, motDePasse);
        this.specialite = specialite;
        this.numeroLicence = numeroLicence;
        this.anneesExperience = anneesExperience;
    }

    // === Méthodes JDBC ===

    public static boolean creerSpecialiste(Connection conn, Specialiste s) {
        String sql = "INSERT INTO specialistes (id, nom, email, motDePasse, specialite, numeroLicence, anneesExperience) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, s.getId());
            stmt.setString(2, s.getNom());
            stmt.setString(3, s.getEmail());
            stmt.setString(4, s.getMotDePasse());
            stmt.setString(5, s.getSpecialite());
            stmt.setString(6, s.getNumeroLicence());
            stmt.setInt(7, s.getAnneesExperience());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création du spécialiste : " + e.getMessage());
            return false;
        }
    }

    public static Specialiste rechercherParId(Connection conn, int id) {
        String sql = "SELECT * FROM specialistes WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
            System.out.println("Erreur lors de la recherche du spécialiste : " + e.getMessage());
        }
        return null;
    }

    /**
     * Vérifie si le spécialiste est disponible à une date donnée (dans la table `agenda`)
     */
    public boolean estDisponible(java.util.Date date) {
        String sql = "SELECT * FROM agenda WHERE id_specialiste = ? AND dateHeure = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, this.id);

            // Conversion explicite vers java.sql.Timestamp
            java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
            stmt.setTimestamp(2, timestamp);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de disponibilité : " + e.getMessage());
            return false;
        }
    }


    /**
     * Affiche le planning du spécialiste en listant les créneaux dans la table `agenda`
     */
    public void consulterPlanningSpecialiste() {
        String sql = "SELECT dateHeure FROM agenda WHERE id_specialiste = ? ORDER BY dateHeure";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, this.id);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Planning de " + this.nom + " :");
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("dateHeure");
                System.out.println(" - " + ts);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la consultation du planning : " + e.getMessage());
        }
    }

    // === Getters / setters ===

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getNumeroLicence() {
        return numeroLicence;
    }

    public void setNumeroLicence(String numeroLicence) {
        this.numeroLicence = numeroLicence;
    }

    public int getAnneesExperience() {
        return anneesExperience;
    }

    public void setAnneesExperience(int anneesExperience) {
        this.anneesExperience = anneesExperience;
    }

    @Override
    public String toString() {
        return "Specialiste{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", specialite='" + specialite + '\'' +
                ", numeroLicence='" + numeroLicence + '\'' +
                ", anneesExperience=" + anneesExperience +
                '}';
    }
}
