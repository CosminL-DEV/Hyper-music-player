package views;

import interfaz.Interfaz;
import components.ReviewPlaylist;
import components.ScrollBar;
import components.TopBar;
import appManagement.Utilities;
import appManagement.ColorReturner;
import songManager.BotBar;
import album.Album;
import playlist.Playlist;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class Inicio extends JPanel {

    private ColorReturner CReturner = new ColorReturner();
    private java.awt.GridBagConstraints gridBagConstraints;
    private Font coolvetica = Utilities.cargarCoolvetica();
    private String username;
    private JFrame window;
    private BotBar botBar;
    private TopBar topBar;
    private JScrollPane scrollPane;
    private JPanel interfazPrinc;
    private JPanel content;
    private JPanel main;
    private JPanel listaPlaylist;
    private JLabel home;

    public Inicio(JPanel interfazPrinc, BotBar botBar, JScrollPane scrollPane, JPanel main, TopBar topBar,
            JLabel home, JPanel listaPlaylist, JFrame window) {
        this.window = window;
        this.content = this;
        this.interfazPrinc = interfazPrinc;
        this.botBar = botBar;
        this.topBar = topBar;
        this.scrollPane = scrollPane;
        this.home = home;
        this.main = main;
        this.listaPlaylist = listaPlaylist;
        setOpaque(true);
        setBackground(CReturner.getBackground());
        setLayout(new java.awt.GridBagLayout());
        Preferences pref = Preferences.userRoot().node("Rememberme");
        username = pref.get("ActualUser", "");
        addLateralIzq();
        addLateralDer();
        addListasHyper();
        addListasPropias();
        addAlbumNoved();
        addGeneroFav();
        addDescubrirListas();
    }

    private void addLateralIzq() {
        javax.swing.JPanel latIzq = new javax.swing.JPanel();
        javax.swing.JLabel sep = new javax.swing.JLabel();
        latIzq.setOpaque(false);
        setBackground(CReturner.getBackground());
        sep.setText(" ");
        sep.setPreferredSize(new Dimension(70, 60));
        latIzq.add(sep);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weighty = 0.1;
        add(latIzq, gridBagConstraints);
    }

    private void addLateralDer() {
        javax.swing.JPanel latIzq = new javax.swing.JPanel();
        javax.swing.JLabel sep = new javax.swing.JLabel();
        latIzq.setOpaque(false);
        setBackground(CReturner.getBackground());
        sep.setText(" ");
        sep.setPreferredSize(new Dimension(70, 60));
        latIzq.add(sep);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weighty = 0.1;
        add(latIzq, gridBagConstraints);
    }

    private void addListasHyper() {
        JPanel deHyper = new javax.swing.JPanel();
        JPanel texto1 = new javax.swing.JPanel();
        JLabel titulo1 = new javax.swing.JLabel();
        JPanel listas1 = new javax.swing.JPanel();

        deHyper.setBackground(CReturner.getBackground());
        deHyper.setLayout(new java.awt.GridBagLayout());

        texto1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 5));
        texto1.setBackground(CReturner.getBackground());
        titulo1.setText("De Hyper para Ti");
        titulo1.setFont(coolvetica.deriveFont(22f));
        titulo1.setForeground(CReturner.getTexto());
        texto1.add(titulo1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        deHyper.add(texto1, gridBagConstraints);

        listas1.setPreferredSize(new java.awt.Dimension(150, 200));
        listas1.setLayout(new java.awt.GridLayout(1, 5, 0, 0));
        listas1.setBackground(CReturner.getBackground());

        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT playlist_id, picture, name, user "
                        + "FROM playlist "
                        + "LIMIT 5";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    while (resul.next()) {
                        ReviewPlaylist elemento = new ReviewPlaylist(resul.getString("playlist_id"), resul.getString("picture"), resul.getString("name"), resul.getString("user"));
                        elemento.addMouseListener(new java.awt.event.MouseAdapter() {
                            @Override
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                content = new Playlist(elemento.getId(), listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar, home, window);
                                cargarNuevoPanel();
                                interfazPrinc.revalidate();
                                interfazPrinc.repaint();
                                setCursor(null);
                            }
                        });
                        listas1.add(elemento);
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        deHyper.add(listas1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 50, 0);
        add(deHyper, gridBagConstraints);
    }

    private void addAlbumNoved() {
        JPanel albums = new javax.swing.JPanel();
        JPanel texto1 = new javax.swing.JPanel();
        JLabel titulo1 = new javax.swing.JLabel();
        JPanel listas1 = new javax.swing.JPanel();

        albums.setBackground(CReturner.getBackground());
        albums.setLayout(new java.awt.GridBagLayout());

        texto1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 5));
        texto1.setBackground(CReturner.getBackground());
        titulo1.setText("Echa un vistazo a los nuevos albums");
        titulo1.setFont(coolvetica.deriveFont(22f));
        titulo1.setForeground(CReturner.getTexto());
        texto1.add(titulo1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        albums.add(texto1, gridBagConstraints);

        listas1.setPreferredSize(new java.awt.Dimension(150, 200));
        listas1.setLayout(new java.awt.GridLayout(1, 5, 0, 0));
        listas1.setBackground(CReturner.getBackground());

        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT album.album_id, album.picture, album.name AS nombre, artist.name AS artista "
                        + "FROM album, artist "
                        + "WHERE album.artist_id = artist.artist_id "
                        + "ORDER BY album.fecha DESC "
                        + "LIMIT 5";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    while (resul.next()) {
                        ReviewPlaylist elemento = new ReviewPlaylist(resul.getString("album_id"), resul.getString("picture"), resul.getString("nombre"), resul.getString("artista"));
                        elemento.addMouseListener(new java.awt.event.MouseAdapter() {
                            @Override
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                content = new Album(elemento.getId(), listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar, home, window);
                                cargarNuevoPanel();
                                interfazPrinc.revalidate();
                                interfazPrinc.repaint();
                                setCursor(null);
                            }
                        });
                        listas1.add(elemento);
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        albums.add(listas1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 50, 0);
        add(albums, gridBagConstraints);
    }

    private void addGeneroFav() {
        JPanel generos = new javax.swing.JPanel();
        JPanel texto1 = new javax.swing.JPanel();
        JLabel titulo1 = new javax.swing.JLabel();
        JPanel listas1 = new javax.swing.JPanel();

        generos.setBackground(CReturner.getBackground());
        generos.setLayout(new java.awt.GridBagLayout());

        texto1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 5));
        texto1.setBackground(CReturner.getBackground());
        titulo1.setText("Aumenta tu repertorio de tu genero favorito");
        titulo1.setFont(coolvetica.deriveFont(22f));
        titulo1.setForeground(CReturner.getTexto());
        texto1.add(titulo1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        generos.add(texto1, gridBagConstraints);

        listas1.setPreferredSize(new java.awt.Dimension(150, 200));
        listas1.setLayout(new java.awt.GridLayout(1, 5, 0, 0));
        listas1.setBackground(CReturner.getBackground());

        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT playlist_id, picture, name, user "
                        + "FROM playlist "
                        + "WHERE user = 'Hyper' AND playlist_id > 5 "
                        + "LIMIT 5";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    while (resul.next()) {
                        ReviewPlaylist elemento = new ReviewPlaylist(resul.getString("playlist_id"), resul.getString("picture"), resul.getString("name"), resul.getString("user"));
                        elemento.addMouseListener(new java.awt.event.MouseAdapter() {
                            @Override
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                content = new Playlist(elemento.getId(), listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar, home, window);
                                cargarNuevoPanel();
                                interfazPrinc.revalidate();
                                interfazPrinc.repaint();
                                setCursor(null);
                            }
                        });
                        listas1.add(elemento);
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        generos.add(listas1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 50, 0);
        add(generos, gridBagConstraints);
    }

    private void addDescubrirListas() {
        JPanel albums = new javax.swing.JPanel();
        JPanel texto1 = new javax.swing.JPanel();
        JLabel titulo1 = new javax.swing.JLabel();
        JPanel listas1 = new javax.swing.JPanel();

        albums.setBackground(CReturner.getBackground());
        albums.setLayout(new java.awt.GridBagLayout());

        texto1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 5));
        texto1.setBackground(CReturner.getBackground());
        titulo1.setText("Descubre las playlist de la comunidad");
        titulo1.setFont(coolvetica.deriveFont(22f));
        titulo1.setForeground(CReturner.getTexto());
        texto1.add(titulo1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        albums.add(texto1, gridBagConstraints);

        listas1.setPreferredSize(new java.awt.Dimension(150, 200));
        listas1.setLayout(new java.awt.GridLayout(1, 5, 0, 0));
        listas1.setBackground(CReturner.getBackground());

        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT * FROM "
                        + "	(SELECT playlist.playlist_id ,playlist.picture, playlist.name, playlist.user "
                        + "	FROM playlist "
                        + "	WHERE playlist.user!='" + username + "' AND playlist.user !='Hyper' AND playlist.privacity='publica') AS temp "
                        + "ORDER BY RAND() LIMIT 5";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    while (resul.next()) {
                        ReviewPlaylist elemento = new ReviewPlaylist(resul.getString("playlist_id"), resul.getString("picture"), resul.getString("name"), resul.getString("user"));
                        elemento.addMouseListener(new java.awt.event.MouseAdapter() {
                            @Override
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                content = new Playlist(elemento.getId(), listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar, home, window);
                                cargarNuevoPanel();
                                interfazPrinc.revalidate();
                                interfazPrinc.repaint();
                                setCursor(null);
                            }
                        });
                        listas1.add(elemento);
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        albums.add(listas1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        add(albums, gridBagConstraints);
    }

    private void addListasPropias() {
        JPanel tusListas = new javax.swing.JPanel();
        JPanel texto1 = new javax.swing.JPanel();
        JLabel titulo1 = new javax.swing.JLabel();
        JPanel listas1 = new javax.swing.JPanel();

        tusListas.setBackground(CReturner.getBackground());
        tusListas.setLayout(new java.awt.GridBagLayout());

        texto1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 5));
        texto1.setBackground(CReturner.getBackground());
        titulo1.setText("Sum??rgete de nuevo en tu m??sica");
        titulo1.setFont(coolvetica.deriveFont(22f));
        titulo1.setForeground(CReturner.getTexto());
        texto1.add(titulo1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        tusListas.add(texto1, gridBagConstraints);

        listas1.setPreferredSize(new java.awt.Dimension(150, 200));
        listas1.setLayout(new java.awt.GridLayout(1, 5, 0, 0));
        listas1.setBackground(CReturner.getBackground());

        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT * FROM "
                        + "(SELECT playlist.playlist_id ,playlist.picture, playlist.name, playlist.user "
                        + "FROM playlist, registro_savedlist "
                        + "WHERE playlist.playlist_id=registro_savedlist.playlist_id AND registro_savedlist.user='" + username + "') AS temp "
                        + "ORDER BY RAND() LIMIT 5";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    while (resul.next()) {
                        ReviewPlaylist elemento = new ReviewPlaylist(resul.getString("playlist_id"), resul.getString("picture"), resul.getString("name"), resul.getString("user"));
                        elemento.addMouseListener(new java.awt.event.MouseAdapter() {
                            @Override
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                content = new Playlist(elemento.getId(), listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar, home, window);
                                cargarNuevoPanel();
                                interfazPrinc.revalidate();
                                interfazPrinc.repaint();
                                setCursor(null);
                            }
                        });
                        listas1.add(elemento);
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        tusListas.add(listas1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 50, 0);
        add(tusListas, gridBagConstraints);
    }

    private void cargarNuevoPanel() {
        main.removeAll();
        home.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "home.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
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
