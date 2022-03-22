package window;

import java.awt.Color;
import java.awt.Font;
import static java.awt.Frame.MAXIMIZED_BOTH;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
public class LoginWindow extends JFrame {

    private int mouseX, mouseY;

    public LoginWindow() {
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hyper - Log in");
        getContentPane().setBackground(new Color(240, 245, 249));
        getContentPane().setLayout(null);
        setBounds(100, 100, 700, 500);
        setUndecorated(true);
        setLocationRelativeTo(null);

        JPanel panel2 = new JPanel();
        panel2.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mouseX <= 350 && mouseY <=44)
                    setLocation(getX() + e.getX() - mouseX, getY() + e.getY() - mouseY);
            }
        });
        panel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        panel2.setBounds(0, 0, 350, 500);
        JLabel lblImage = new JLabel(new ImageIcon(getClass().getResource("/resources/login.png")));
        lblImage.setBounds(0, 0, 350, 500);
        panel2.add(lblImage);
        getContentPane().add(panel2);
        panel2.setLayout(null);
        
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
        panel.setBackground(new Color(240, 245, 249));
        panel.setBounds(350, 0, 350, 44);
        getContentPane().add(panel);
        panel.setLayout(null);

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
        lblExit.setBounds(305, 0, 45, 44);
        lblExit.setOpaque(true);
        lblExit.setBackground(new Color(240, 245, 249));
        panel.add(lblExit);
        
        // Panel 3 con login
    }
}
