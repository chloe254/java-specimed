package DAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgendaDAO {

    public List<LocalDateTime> getDisponibilitesPourSpecialiste(int idSpecialiste) {
        List<LocalDateTime> liste = new ArrayList<>();
        String sql = "SELECT date_heure FROM agenda WHERE id_specialiste = ? AND disponible = true ORDER BY date_heure";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSpecialiste);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                liste.add(rs.getTimestamp("date_heure").toLocalDateTime());
            }

        } catch (SQLException e) {
            System.out.println("Erreur AgendaDAO.getDisponibilites : " + e.getMessage());
        }

        return liste;
    }

    public void supprimerCreneau(int idSpecialiste, LocalDateTime dateHeure) {
        String sql = "DELETE FROM agenda WHERE id_specialiste = ? AND date_heure = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSpecialiste);
            stmt.setTimestamp(2, Timestamp.valueOf(dateHeure));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur AgendaDAO.supprimerCreneau : " + e.getMessage());
        }
    }
}
