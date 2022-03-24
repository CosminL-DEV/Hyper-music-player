package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JButton;
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
public class Inicio extends JPanel {

    java.awt.GridBagConstraints gridBagConstraints;
    private final JFrame framePrincipal;
    ColorReturner CReturner = new ColorReturner();
    String username;
    Font lemonB = null;
    Font lemonR = null;
    int totalAncho;
    int totalAlto;
    
    public Inicio(JFrame framePrincipal) {
        this.framePrincipal = framePrincipal;
        obtenerUsername();
        cargarFonts();
        cargarScreenSize();
        iniciarComponentes();
        iniciarTopPage();
        iniciarBarraIzq();
        iniciarBarraDer();
        iniciarBotPage();
        iniciarPanelPrincipal();
    }
    
    private void obtenerUsername(){
        Preferences pref = Preferences.userRoot().node("Rememberme");
        username = pref.get("ActualUser", "");
    }
    
    private void cargarFonts(){
        InputStream is = Inicio.class.getResourceAsStream("/fonts/LEMONMILK-Bold.otf");
        InputStream is2 = Inicio.class.getResourceAsStream("/fonts/LEMONMILK-Regular.otf");
        try {
            lemonB = Font.createFont(Font.TRUETYPE_FONT, is);
            lemonR = Font.createFont(Font.TRUETYPE_FONT, is2);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Usar asi:
        // setFont(lemonB.deriveFont(24f));
    }
    
    private void cargarScreenSize(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        totalAncho = screenSize.width - 10;
        totalAlto = screenSize.height - 10;
    }
    
    private void iniciarComponentes(){
        setBackground(CReturner.getPrincipal());
        setLayout(new java.awt.GridBagLayout());
    }
    
    private void iniciarTopPage(){
        JPanel top = new JPanel();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        //gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(top, gridBagConstraints);
        top.setOpaque(false);
        top.setBackground(CReturner.getBackground());
        
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
    
    private void iniciarBarraIzq(){
        JPanel izq = new JPanel();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(izq, gridBagConstraints);
        izq.setOpaque(false);
        izq.setBackground(CReturner.getBackground());
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
        der.setBackground(CReturner.getBackground());

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
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        //gridBagConstraints.weightx = 0.1;
        //gridBagConstraints.weighty = 0.1;
        add(bot, gridBagConstraints);
        bot.setOpaque(true);
        bot.setBackground(CReturner.getBackground());
        JLabel fake = new JLabel(" ");
        fake.setPreferredSize(new Dimension(10, 20));
        bot.add(fake);
    }
    
    private void iniciarPanelPrincipal(){
        
    }
}
