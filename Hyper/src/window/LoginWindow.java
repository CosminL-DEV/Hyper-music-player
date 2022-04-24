package window;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import login.Login;
import login.Register;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class LoginWindow extends JFrame {

    private final JFrame principal;
    private int mouseX, mouseY;

    public LoginWindow(JFrame principal) {
        this.principal = principal;
        iniciarComponentes();
        iniciarLogin();
    }

    private void iniciarComponentes() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hyper - Log in");
        getContentPane().setBackground(new Color(240, 245, 249));
        getContentPane().setLayout(null);
        setBounds(100, 100, 700, 500);
        setUndecorated(true);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(getX() + e.getX() - mouseX, getY() + e.getY() - mouseY);
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        panel.setBackground(new Color(0, 245, 249));
        panel.setBounds(0, 0, 700, 44);
        add(panel);
        panel.setLayout(null);
        panel.setOpaque(false);
        JLabel lblExit = new JLabel("X");
        lblExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblExit.setBackground(new Color(255, 223, 76));
                lblExit.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblExit.setBackground(new Color(240, 245, 249));
                lblExit.setForeground(new Color(255, 223, 76));
            }
        });
        lblExit.setForeground(new Color(255, 223, 76));
        lblExit.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblExit.setHorizontalAlignment(SwingConstants.CENTER);
        lblExit.setBounds(655, 0, 45, 44);
        lblExit.setOpaque(true);
        lblExit.setBackground(new Color(240, 245, 249));
        panel.add(lblExit);
    }

    private void iniciarLogin() {
        Register register = new Register();
        add(register);
        register.setBounds(0, 0, 700, 500);
        register.setVisible(false);

        Login login = new Login(this, register, principal);
        add(login);
        login.setBounds(0, 0, 700, 500);
        login.setVisible(true);

        register.setLogin(login);
    }

}
