import javax.swing.SwingUtilities;
import Vue.VueLogin;

public class Main {
    public static void main(String[] args) {
        // Lancement de l'application Swing dans le thread dédié
        SwingUtilities.invokeLater(() -> {
            new VueLogin(); // Lance la fenêtre de connexion
        });
    }
}
