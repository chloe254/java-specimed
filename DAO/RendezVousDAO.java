package DAO;

import modele.Patient;
import modele.Specialiste;
import modele.RendezVous;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DAO pour la gestion des rendez-vous dans la base de données.
 */
public class RendezVousDAO {

    private final PatientDAO patientDAO;

    public RendezVousDAO(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    /**
     * Crée un nouveau rendez-vous.
     */
    public boolean create(RendezVous rdv) {
        String sql = "INSERT INTO rendezvous (id, dateHeure, lieu, id_patient, id_specialiste, statut, note) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rdv.getId());
            stmt.setTimestamp(2, new java.sql.Timestamp(rdv.getDateHeure().getTime()));
            stmt.setString(3, rdv.getLieu());
            stmt.setInt(4, rdv.getPatient().getId());
            stmt.setInt(5, rdv.getSpecialiste().getId());
            stmt.setString(6, rdv.getStatut());
            stmt.setString(7, rdv.getNote());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erreur lors de la création du rendez-vous : " + e.getMessage());
            return false;
        }
    }

    /**
     * Récupère un rendez-vous via son ID.
     */
    public RendezVous read(int id) {
        String sql = "SELECT * FROM rendezvous WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToRendezVous(rs, conn);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lecture rendez-vous : " + e.getMessage());
        }
        return null;
    }

    /**
     * Annule un rendez-vous (modifie son statut).
     */
    public boolean annuler(int id) {
        String sql = "UPDATE rendezvous SET statut = 'Annulé' WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'annulation : " + e.getMessage());
            return false;
        }
    }

    /**
     * Renvoie la liste des rendez-vous à venir pour un patient.
     */
    public List<RendezVous> findFutursByPatient(int patientId) {
        List<RendezVous> list = new ArrayList<>();
        String sql = "SELECT * FROM rendezvous WHERE id_patient = ? AND dateHeure > NOW() AND statut != 'Annulé'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToRendezVous(rs, conn));
            }

        } catch (SQLException e) {
            System.out.println("Erreur RDV futur patient : " + e.getMessage());
        }
        return list;
    }

    /**
     * Renvoie la liste des rendez-vous à venir pour un spécialiste.
     */
    public List<RendezVous> findFutursBySpecialiste(int specialisteId) {
        List<RendezVous> list = new ArrayList<>();
        String sql = "SELECT * FROM rendezvous WHERE id_specialiste = ? AND dateHeure > NOW() AND statut != 'Annulé'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, specialisteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToRendezVous(rs, conn));
            }

        } catch (SQLException e) {
            System.out.println("Erreur RDV futur spécialiste : " + e.getMessage());
        }
        return list;
    }

    /**
     * Renvoie l'historique complet des rendez-vous d’un patient.
     */
    public List<RendezVous> findHistoriqueByPatient(int patientId) {
        List<RendezVous> list = new ArrayList<>();
        String sql = "SELECT * FROM rendezvous WHERE id_patient = ? ORDER BY dateHeure DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToRendezVous(rs, conn));
            }

        } catch (SQLException e) {
            System.out.println("Erreur historique patient : " + e.getMessage());
        }

        return list;
    }

    /**
     * Convertit un ResultSet en objet RendezVous complet.
     */
    private RendezVous mapResultSetToRendezVous(ResultSet rs, Connection conn) throws SQLException {
        int id = rs.getInt("id");
        Date dateHeure = new Date(rs.getTimestamp("dateHeure").getTime());
        String lieu = rs.getString("lieu");
        String statut = rs.getString("statut");
        String note = rs.getString("note");

        int idPatient = rs.getInt("id_patient");
        int idSpecialiste = rs.getInt("id_specialiste");

        Patient patient = patientDAO.read(idPatient);
        Specialiste specialiste = new SpecialisteDAO().getSpecialisteParId(idSpecialiste);

        RendezVous rdv = new RendezVous(id, dateHeure, lieu, patient, specialiste);
        rdv.setStatut(statut);
        rdv.setNote(note);

        return rdv;
    }
}
