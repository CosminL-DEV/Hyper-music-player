package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import themeManagement.ColorReturner;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 23-03-2022
 * @version 1.0
 *
 * ************************************
 */
public class Interfaz extends JPanel {

    java.awt.GridBagConstraints gridBagConstraints;
    private final JFrame framePrincipal;
    ColorReturner CReturner = new ColorReturner();
    String username;
    Font lemonB = null;
    Font lemonR = null;
    int totalAncho;
    int totalAlto;

    public Interfaz(JFrame framePrincipal) {
        this.framePrincipal = framePrincipal;
        obtenerUsername();
        cargarFonts();
        cargarScreenSize();
        iniciarComponentes();
        iniciarTopPage();
        iniciarIcono();
        iniciarBarraIzq();
        iniciarBarraDer();
        iniciarBotPage();
        iniciarPanelPrincipal();
    }

    private void obtenerUsername() {
        Preferences pref = Preferences.userRoot().node("Rememberme");
        username = pref.get("ActualUser", "");
    }

    private void cargarFonts() {
        InputStream is = Interfaz.class.getResourceAsStream("/fonts/LEMONMILK-Bold.otf");
        InputStream is2 = Interfaz.class.getResourceAsStream("/fonts/LEMONMILK-Regular.otf");
        try {
            lemonB = Font.createFont(Font.TRUETYPE_FONT, is);
            lemonR = Font.createFont(Font.TRUETYPE_FONT, is2);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Usar asi:
        // setFont(lemonB.deriveFont(24f));
    }

    private void cargarScreenSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        totalAncho = screenSize.width - 10;
        totalAlto = screenSize.height - 10;
    }

    private void iniciarComponentes() {
        setBackground(CReturner.getPrincipal());
        setLayout(new java.awt.GridBagLayout());
    }

    private void iniciarTopPage() {
        JPanel top = new JPanel();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(top, gridBagConstraints);
        top.setOpaque(false);

        JLabel lblMin = new JLabel("-");
        lblMin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                framePrincipal.setState(1);
            }
        });
        lblMin.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMin.setForeground(CReturner.getClose());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMin.setForeground(CReturner.getTexto());
            }
        });
        lblMin.setForeground(CReturner.getTexto());
        lblMin.setFont(lemonB.deriveFont(24f));
        lblMin.setHorizontalAlignment(SwingConstants.CENTER);
        lblMin.setPreferredSize(new Dimension(35, 25));
        lblMin.setBackground(new Color(240, 245, 249));
        top.add(lblMin);
    }

    private void iniciarIcono() {
        JPanel izq = new JPanel();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(izq, gridBagConstraints);
        izq.setOpaque(false);
        JLabel icono = new JLabel(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcon()+"icon.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        icono.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                icono.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcon()+"iconFocus.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                icono.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcon()+"icon.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
            }
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Desplegar creditos de la app.
            }
        });
        
        izq.add(icono);
    }

    private void iniciarBarraIzq() {
        JPanel izq = new JPanel();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(izq, gridBagConstraints);
        izq.setOpaque(false);
        
        JPanel columna = new JPanel();
        columna.setOpaque(false);
        columna.setLayout(new java.awt.GridBagLayout());

        JLabel home = new JLabel(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons()+"home.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        home.setText(" INICIO");
        home.setFont(lemonB.deriveFont(20f));
        home.setForeground(CReturner.getAbsoluto());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 15, 0);
        columna.add(home, gridBagConstraints);

        JLabel search = new JLabel(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons()+"search.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        search.setText(" BUSCAR");
        search.setFont(lemonB.deriveFont(20f));
        search.setForeground(CReturner.getAbsoluto());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        columna.add(search, gridBagConstraints);

        JLabel library = new JLabel(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons()+"library.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        library.setText(" BIBLIOTECA");
        library.setFont(lemonB.deriveFont(20f));
        library.setForeground(CReturner.getAbsoluto());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        columna.add(library, gridBagConstraints);
        
        izq.add(columna);
    }

    private void iniciarBarraDer() {
        JPanel der = new JPanel();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.weighty = 0.1;
        add(der, gridBagConstraints);
        der.setOpaque(false);

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
                lblExit.setForeground(CReturner.getClose());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblExit.setForeground(CReturner.getTexto());
            }
        });
        lblExit.setForeground(CReturner.getTexto());
        lblExit.setFont(lemonB.deriveFont(18f));
        lblExit.setHorizontalAlignment(SwingConstants.CENTER);
        lblExit.setPreferredSize(new Dimension(35, 25));
        der.add(lblExit);
    }

    private void iniciarBotPage() {
        JPanel bot = new JPanel();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.weighty = 0.1;
        add(bot, gridBagConstraints);
        bot.setOpaque(false);
        JLabel fake = new JLabel(" ");
        fake.setPreferredSize(new Dimension(10, 25));
        bot.add(fake);
    }

    private void iniciarPanelPrincipal() {
        
        JPanel main = new RoundedPanel(50, CReturner.getBackground());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 10;
        gridBagConstraints.weighty = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        main.setBackground(CReturner.getBackground());
        //main.setBounds(10,10,500,500);
        main.setOpaque(false);
        
        add(main, gridBagConstraints);
    }
}
