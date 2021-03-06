package window;

import appManagement.Utilities;
import appManagement.ColorReturner;
import interfaz.Interfaz;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.prefs.Preferences;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class Window extends JFrame {

    private ColorReturner CReturner = new ColorReturner();
    private JFrame esta;
    private Interfaz inicio;

    public Window() {
        esta = this;
        Preferences pref;
        pref = Preferences.userRoot().node("Rememberme");
        LoginWindow ventana = new LoginWindow(this);
        iniciarComponentes();
        crearCarpeta();
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

    private void crearCarpeta() {
        File directorio = CReturner.getFolderPath().toFile();
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                Utilities.escribirRuta(directorio.getAbsolutePath());
            }
        }
    }
}
