package components;

import interfaz.Interfaz;
import java.awt.Dimension;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
public class TopBar extends JPanel {

    private javax.swing.JPanel mid;
    private final ColorReturner CReturner = new ColorReturner();
    private final ImgCircleConverter convertidor = new ImgCircleConverter();
    private JLabel nombrePlaylist;

    public TopBar() {
        java.awt.GridBagConstraints gridBagConstraints;
        javax.swing.JPanel latIzq = new javax.swing.JPanel();
        mid = new javax.swing.JPanel();
        javax.swing.JPanel latDer = new javax.swing.JPanel();
        javax.swing.JLabel sep = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel icono = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());
        setOpaque(false);

        latIzq.setOpaque(false);
        sep.setText(" ");
        sep.setPreferredSize(new Dimension(70, 60));
        latIzq.add(sep);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(latIzq, gridBagConstraints);

        mid.setBackground(CReturner.getBackground());
        nombrePlaylist = new JLabel(" ");
        nombrePlaylist.setPreferredSize(new Dimension(200, 60));
        mid.add(nombrePlaylist);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 10.0;
        gridBagConstraints.weighty = 0.1;
        add(mid, gridBagConstraints);

        latDer.setBackground(new java.awt.Color(51, 51, 255));
        latDer.setLayout(new java.awt.GridBagLayout());
        latDer.setOpaque(false);
        latDer.setPreferredSize(new Dimension(70, 60));
        icono.setIcon(getIconUser());
        latDer.add(icono, new java.awt.GridBagConstraints());
        icono.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                desplegarMenu(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 15);
        add(latDer, gridBagConstraints);
    }

    private ImageIcon getIconUser() {
        Preferences pref = Preferences.userRoot().node("Rememberme");
        String user = pref.get("ActualUser", "");
        String picture = Utilities.getIconUser(user);
        ImageIcon img = new ImageIcon(convertidor.convertirImagen(Utilities.transformarLink(picture)));
        return new ImageIcon(
                (img.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
    }
 
    private void desplegarMenu(java.awt.event.MouseEvent evt){
        // Desplegar el menu y cerrarlo con otro click en el panel principal porque aqui no funcionaria.
        System.out.println("h");
    }
    
    public void activarMedio(){
        // Añadir datos de la playlist actual y el panel de la tabla.
    }
    
    public void desactivarMedio(){
        mid.removeAll();
    }
}