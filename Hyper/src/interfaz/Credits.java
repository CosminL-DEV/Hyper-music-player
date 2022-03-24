package interfaz;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import themeManagement.ColorReturner;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-03-2022
 * @version 1.0
 *
 * ************************************
 */
public class Credits extends JDialog {

    ColorReturner CReturner = new ColorReturner();

    public Credits() {
        InputStream is = Interfaz.class.getResourceAsStream("/fonts/LEMONMILK-Bold.otf");
        Font lemonB = null;
        try {
            lemonB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(300, 500));
        setTitle("About Hyper");
        getContentPane().setLayout(new java.awt.GridBagLayout());
        getContentPane().setBackground(CReturner.getBackground());
        java.awt.GridBagConstraints gridBagConstraints;

        JLabel icono = new JLabel(new javax.swing.ImageIcon(getClass().getResource("/resources/icon.png")));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(icono, gridBagConstraints);

        JPanel credits = new javax.swing.JPanel();
        JLabel ver = new javax.swing.JLabel();
        JLabel dev = new javax.swing.JLabel();
        JPanel definicion = new javax.swing.JPanel();
        JLabel titulo = new javax.swing.JLabel();
        JTextArea resumen = new javax.swing.JTextArea();

        definicion.setLayout(new java.awt.GridBagLayout());
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("HYPER");
        titulo.setFont(lemonB.deriveFont(18f));
        titulo.setForeground(CReturner.getTexto());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        definicion.add(titulo, gridBagConstraints);
        resumen.setText(" Hyper Music Player es una aplicacion para la\n reproduccion y organizacion de musica\n "
                + "permitiendo el acceso a ella tanto localmente\n como a traves de la base de datos.");
        resumen.setForeground(CReturner.getTexto());
        resumen.setOpaque(false);
        resumen.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        definicion.add(resumen, gridBagConstraints);
        definicion.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(definicion, gridBagConstraints);

        credits.setLayout(new java.awt.BorderLayout(5, 7));
        ver.setText("   Version: 1.0");
        ver.setForeground(CReturner.getTexto());
        credits.add(ver, java.awt.BorderLayout.CENTER);
        dev.setText("   Desarrollado por: Cosmin Ionut Lungu");
        dev.setForeground(CReturner.getTexto());
        credits.add(dev, java.awt.BorderLayout.PAGE_START);
        credits.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(credits, gridBagConstraints);
        pack();
    }
}
