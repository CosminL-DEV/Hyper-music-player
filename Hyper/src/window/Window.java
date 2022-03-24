package window;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.util.prefs.Preferences;

import themeManagement.ColorReturner;
import views.Inicio;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 22-03-2022
 * @version 1.0
 *
 * ************************************
 */
public class Window extends JFrame {

    ColorReturner CReturner = new ColorReturner();
    private final Preferences pref;

    public Window() {
        this.pref = Preferences.userRoot().node("Rememberme");
        LoginWindow ventana = new LoginWindow(this);
        iniciarComponentes();
        iniciarHome();
        if (pref.get("Username", "").equals("") && pref.get("Password", "").equals("")) {
            ventana.setVisible(true);
        } else {
            setVisible(true);
        }
    }

    private void iniciarComponentes() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hyper");
        this.setExtendedState(MAXIMIZED_BOTH);
        getContentPane().setBackground(CReturner.getBackground());
        setUndecorated(true);
        setResizable(false);
        pack();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
    }
    
    private void iniciarHome(){
        Inicio inicio = new Inicio(this);
        add(inicio);
        inicio.setVisible(true);
    }
}
