package window;

import interfaz.Interfaz;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.prefs.Preferences;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;

import themeManagement.ColorReturner;

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

    private ColorReturner CReturner = new ColorReturner();
    private final Preferences pref;
    private JFrame esta;
    private Interfaz inicio;

    public Window() {
        esta = this;
        this.pref = Preferences.userRoot().node("Rememberme");
        LoginWindow ventana = new LoginWindow(this);
        iniciarComponentes();
        if (pref.get("Username", "").equals("") && pref.get("Password", "").equals("")) {
            ventana.setVisible(true);
        } else {
            inicio = iniciarHome();
            add(inicio);
            inicio.setVisible(true);
            setVisible(true);
        }
        ventana.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                inicio = iniciarHome();
                add(inicio);
                inicio.setVisible(true);
                setVisible(true);
            }
        });
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                ventana.setVisible(true);
                esta.remove(inicio);
            }
        });
    }

    private void iniciarComponentes() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hyper");
        this.setExtendedState(MAXIMIZED_BOTH);
        getContentPane().setBackground(CReturner.getBackground());
        setUndecorated(true);
        setResizable(false);
        setDefaultLookAndFeelDecorated(true);
        pack();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
    }

    private Interfaz iniciarHome() {
        inicio = new Interfaz(this);
        return inicio;
    }
}
