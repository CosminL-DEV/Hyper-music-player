package components;

import interfaz.Interfaz;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import profiles.Artista;
import profiles.Perfil;
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
    private final Font coolvetica = Utilities.cargarCoolvetica();
    private JLabel nombrePlaylist;
    private Popup popup;
    private JPanel desplegable;
    private String miUsername;
    private JPanel listaPlaylist;
    private JPanel interfazPrinc;
    private JPanel content;
    private JScrollPane scrollPane;
    private JPanel main;
    private JPanel botBar;
    private JPanel topBar;
    private JLabel home;
    private JFrame window;
    Preferences pref = Preferences.userRoot().node("Rememberme");

    public TopBar(JPanel listaPlaylist, JPanel interfazPrinc, JPanel botBar, JScrollPane scrollPane, JPanel main, JLabel home, JFrame window) {
        miUsername = pref.get("ActualUser", "");
        this.window = window;
        this.listaPlaylist = listaPlaylist;
        this.interfazPrinc = interfazPrinc;
        this.botBar = botBar;
        this.topBar = this;
        this.scrollPane = scrollPane;
        this.main = main;
        this.home = home;
        java.awt.GridBagConstraints gridBagConstraints;
        setMaximumSize(new Dimension(3000, 60));
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

        crearDesplegable();
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

    private void crearDesplegable() {
        desplegable = new JPanel();
        java.awt.GridBagConstraints gridBagConstraints;
        JLabel uploadSong = new javax.swing.JLabel();
        JLabel uploadAlbum = new javax.swing.JLabel();
        JLabel perfil = new javax.swing.JLabel();
        JLabel configuracion = new javax.swing.JLabel();
        JLabel cerrarSesion = new javax.swing.JLabel();

        desplegable.setLayout(new java.awt.GridBagLayout());
        desplegable.setBackground(CReturner.getPrincipal());
        desplegable.setPreferredSize(new Dimension(125, 170));
        int ejeY = 0;

        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            String sql = "SELECT artist.name "
                    + "FROM artist "
                    + "WHERE artist.username='" + miUsername + "'";
            ResultSet resul = sentencia.executeQuery(sql);
            if (!resul.next()) {
                uploadSong.setText("Subir canción");
                uploadSong.setForeground(CReturner.getBackground());
                uploadSong.setBackground(CReturner.getTexto3());
                uploadSong.setFont(coolvetica.deriveFont(18f));
                uploadSong.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Accion
                    }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = ejeY;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
                gridBagConstraints.weighty = 0.1;
                gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
                desplegable.add(uploadSong, gridBagConstraints);
                ejeY++;

                uploadAlbum.setText("Subir álbum");
                uploadAlbum.setForeground(CReturner.getBackground());
                uploadAlbum.setBackground(CReturner.getTexto3());
                uploadAlbum.setFont(coolvetica.deriveFont(18f));
                uploadAlbum.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Accion
                    }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = ejeY;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
                gridBagConstraints.weighty = 0.1;
                gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
                desplegable.add(uploadAlbum, gridBagConstraints);
                ejeY++;
            } else {
                desplegable.setPreferredSize(new Dimension(125, 100));
            }
            resul.close();
            sentencia.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(TopBar.class.getName()).log(Level.SEVERE, null, ex);
        }

        perfil.setText("Perfil");
        perfil.setForeground(CReturner.getBackground());
        perfil.setBackground(CReturner.getTexto3());
        perfil.setFont(coolvetica.deriveFont(18f));
        perfil.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                content = new Perfil(miUsername, listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar, home, window);
                cargarNuevoPanel();
                popup.hide();
                interfazPrinc.revalidate();
                interfazPrinc.repaint();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ejeY;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        desplegable.add(perfil, gridBagConstraints);
        ejeY++;

        configuracion.setText("Configuración");
        configuracion.setForeground(CReturner.getBackground());
        configuracion.setBackground(CReturner.getTexto3());
        configuracion.setFont(coolvetica.deriveFont(18f));
        configuracion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Accion
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ejeY;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        desplegable.add(configuracion, gridBagConstraints);
        ejeY++;

        cerrarSesion.setText("Cerrar sesión");
        cerrarSesion.setForeground(CReturner.getBackground());
        cerrarSesion.setBackground(CReturner.getTexto3());
        cerrarSesion.setFont(coolvetica.deriveFont(18f));
        cerrarSesion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pref.remove("Username");
                pref.remove("Password");
                pref.remove("ActualUser");
                window.setVisible(false);
                popup.hide();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = ejeY;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        desplegable.add(cerrarSesion, gridBagConstraints);
        ejeY++;
    }

    private void desplegarMenu(java.awt.event.MouseEvent evt) {
        if (popup != null) {
            popup.hide();
            popup = null;
        } else {
            popup = PopupFactory.getSharedInstance().getPopup(evt.getComponent(), desplegable, evt.getXOnScreen() - 100, evt.getYOnScreen() + 10);
            popup.show();
        }
    }
    
    private void cargarNuevoPanel(){
        main.removeAll();
        
        java.awt.GridBagConstraints gridBagConstraints;
        
        topBar = new TopBar(listaPlaylist, interfazPrinc, botBar, scrollPane, main, home, window);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        main.add(topBar, gridBagConstraints);
        
        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBar(new ScrollBar());
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(content);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        main.add(scrollPane, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        main.add(botBar, gridBagConstraints);
    }
}
