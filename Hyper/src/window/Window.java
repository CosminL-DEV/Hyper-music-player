package window;

import javax.swing.JFrame;
import java.awt.Toolkit;

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

    ColorReturner CReturner = new ColorReturner();

    public Window() {
        LoginWindow ventana = new LoginWindow(this);
        ventana.setVisible(true);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hyper");
        this.setExtendedState(MAXIMIZED_BOTH);
        getContentPane().setBackground(CReturner.getBackground());
        pack();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
    }

}
