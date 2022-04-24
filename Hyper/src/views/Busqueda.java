package views;

import dialogs.CreatePlaylist;
import components.ElementoListaPopup;
import components.ItemPlaylist;
import components.ReviewArtist;
import components.ReviewPlaylist;
import components.ScrollBar;
import components.TabPanel;
import components.TopBar;
import appManagement.Utilities;
import album.Album;
import playlist.Playlist;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import profiles.Artista;
import songManager.BotBar;
import components.CancionDisplay;
import appManagement.ColorReturner;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class Busqueda extends JPanel {

    private JPanel listaPlaylist;
    private JPanel interfazPrinc;
    private JPanel content;
    private JScrollPane scrollPane;
    private JPanel main;
    private BotBar botBar;
    private TopBar topBar;
    private JLabel home;
    private String miUsername;
    private final ColorReturner CReturner = new ColorReturner();
    private java.awt.GridBagConstraints gridBagConstraints;
    private final Font coolvetica = Utilities.cargarCoolvetica();
    private JPanel busqSong = new JPanel();
    private JPanel busqPlaylist = new JPanel();
    private JPanel busqAlbum = new JPanel();
    private JPanel busqArtist = new JPanel();
    private ImageIcon playIcon;
    private String sql;
    private boolean primera = true;
    private JLabel search;
    private JFrame window;
    private JMenuItem optReproducir;
    private JMenuItem optAddCola;
    private JMenu optAddPlaylist;
    private JPopupMenu pmenu;
    private Statement sentenciaExtra;
    private String idSelected;

    public Busqueda(JPanel listaPlaylist, JPanel interfazPrinc, BotBar botBar, JScrollPane scrollPane,
            JPanel main, TopBar topBar, JLabel home, JLabel search, JFrame window) {
        this.playIcon = new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "play.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        this.window = window;
        this.content = this;
        this.listaPlaylist = listaPlaylist;
        this.interfazPrinc = interfazPrinc;
        this.botBar = botBar;
        this.topBar = topBar;
        this.scrollPane = scrollPane;
        this.main = main;
        this.home = home;
        this.search = search;
        setOpaque(true);
        Preferences pref = Preferences.userRoot().node("Rememberme");
        miUsername = pref.get("ActualUser", "");
        setBackground(CReturner.getBackground());
        setLayout(new java.awt.GridBagLayout());
        addLateralIzq();
        addLateralDer();
        addTopPage();
        setupPopup();
        devSongTab();
        devPlaylistTab();
        devAlbumTab();
        devArtistTab();
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
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weighty = 0.1;
        add(latIzq, gridBagConstraints);
    }

    private void addTopPage() {
        TabPanel tabP = new TabPanel();
        tabP.setFont(coolvetica.deriveFont(20f));
        tabP.setTabPlacement(JTabbedPane.TOP);
        tabP.addTab("Canciones", busqSong);
        tabP.addTab("Playlist", busqPlaylist);
        tabP.addTab("Albums", busqAlbum);
        tabP.addTab("Artistas", busqArtist);
        tabP.addChangeListener((ChangeEvent e) -> {
            if (e.getSource() instanceof JTabbedPane) {
                primera = true;
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
        add(tabP, gridBagConstraints);
    }

    private void setupPopup() {
        pmenu = new JPopupMenu();
        pmenu.setLayout(new java.awt.GridLayout(0, 1, 10, 5));
        pmenu.setBorder(javax.swing.BorderFactory.createLineBorder(CReturner.getTexto(), 2));
        pmenu.setBackground(CReturner.getBackground());
        optReproducir = new javax.swing.JMenuItem();
        optReproducir.setText("Reproducir");
        optReproducir.setForeground(CReturner.getTexto());
        optReproducir.setFont(coolvetica.deriveFont(15f));
        optReproducir.setOpaque(true);
        optReproducir.setBackground(CReturner.getBackground());
        optReproducir.addActionListener((java.awt.event.ActionEvent evt) -> {
            botBar.addCancion(idSelected);
            botBar.revalidate();
            botBar.repaint();
        });
        pmenu.add(optReproducir);

        optAddCola = new javax.swing.JMenuItem();
        optAddCola.setText("Añadir a la cola");
        optAddCola.setForeground(CReturner.getTexto());
        optAddCola.setFont(coolvetica.deriveFont(15f));
        optAddCola.setOpaque(true);
        optAddCola.setBackground(CReturner.getBackground());
        optAddCola.addActionListener((java.awt.event.ActionEvent evt) -> {
            botBar.addToCola(idSelected);
        });
        pmenu.add(optAddCola);

        optAddPlaylist = new javax.swing.JMenu();
        optAddPlaylist.setText("Añadir a la playlist");
        optAddPlaylist.setForeground(CReturner.getTexto());
        optAddPlaylist.setFont(coolvetica.deriveFont(15f));
        optAddPlaylist.setOpaque(true);
        optAddPlaylist.setBackground(CReturner.getBackground());

        JMenuItem optAddNewLista = new javax.swing.JMenuItem();
        optAddNewLista.setText("Añadir a una lista nueva");
        optAddNewLista.setForeground(CReturner.getTexto());
        optAddNewLista.setFont(coolvetica.deriveFont(15f));
        optAddNewLista.setOpaque(true);
        optAddNewLista.setBackground(CReturner.getBackground());
        optAddNewLista.addActionListener((java.awt.event.ActionEvent evt) -> {
            CreatePlaylist cp = new CreatePlaylist();
            cp.setBounds(0, 0, 350, 270);
            cp.setLocationRelativeTo(null);
            cp.setVisible(true);
            cp.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if (cp.getGuardado() == 1) {
                        recargarListaPlaylist();
                        interfazPrinc.revalidate();
                        interfazPrinc.repaint();
                        Statement sentencia;
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                                sentencia = conexion.createStatement();
                                sql = "INSERT INTO registro_playlist(playlist_id,song_id,user_added) "
                                        + "VALUES('" + cp.getIdPlaylist() + "','" + idSelected + "','" + miUsername + "')";
                                sentencia.executeUpdate(sql);
                                sentencia.close();
                            }
                        } catch (SQLException | ClassNotFoundException ex) {
                            Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
        });
        optAddPlaylist.add(optAddNewLista);
        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                sql = "SELECT playlist.playlist_id, playlist.name "
                        + "FROM playlist "
                        + "WHERE user = '" + miUsername + "'";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    while (resul.next()) {
                        ElementoListaPopup elemLista = new ElementoListaPopup();
                        elemLista.setIdPlaylist(resul.getString("playlist_id"));
                        elemLista.setText(resul.getString("name"));
                        elemLista.setForeground(CReturner.getTexto());
                        elemLista.setFont(coolvetica.deriveFont(15f));
                        elemLista.setOpaque(true);
                        elemLista.setBackground(CReturner.getBackground());
                        elemLista.addActionListener((java.awt.event.ActionEvent evt) -> {
                            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            try {
                                try (Connection conexion1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                                    sentenciaExtra = conexion1.createStatement();
                                    String sql2 = "SELECT registro_playlist.song_id "
                                            + "FROM registro_playlist "
                                            + "WHERE registro_playlist.playlist_id = '" + elemLista.getIdPlaylist() + "' AND registro_playlist.song_id = '" + idSelected + "'";
                                    sentenciaExtra = conexion1.createStatement();
                                    ResultSet resul1 = sentenciaExtra.executeQuery(sql2);
                                    boolean hacer = true;
                                    if (resul1.next()) {
                                        hacer = false;
                                    }
                                    resul1.close();
                                    if (hacer) {
                                        sql2 = "SELECT registro_savedlist.downloaded "
                                                + "FROM registro_savedlist "
                                                + "WHERE registro_savedlist.playlist_id = '" + elemLista.getIdPlaylist() + "' AND registro_savedlist.user = '" + miUsername + "'";
                                        resul1 = sentenciaExtra.executeQuery(sql2);
                                        boolean nuevaDownloaded = false;
                                        if (resul1.next()) {
                                            nuevaDownloaded = resul1.getString("downloaded").equals("1");
                                        }
                                        sql2 = "INSERT INTO registro_playlist(playlist_id,song_id,user_added) "
                                                + "VALUES('" + elemLista.getIdPlaylist() + "','" + idSelected + "','" + miUsername + "')";
                                        sentenciaExtra.executeUpdate(sql2);
                                        if (nuevaDownloaded) {
                                            Utilities.descargarCanciones(idSelected);
                                        }
                                    }
                                    sentenciaExtra.close();
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            setCursor(null);
                        });
                        optAddPlaylist.add(elemLista);
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, null, ex);
        }

        pmenu.add(optAddPlaylist);
    }

    private void devSongTab() {
        busqSong.setOpaque(false);
        busqSong.setLayout(new java.awt.GridBagLayout());
        JPanel contenedor = new JPanel();
        JPanel resultados = new JPanel();
        JLabel iconoBusq = new javax.swing.JLabel();
        JTextField inputTexto = new JTextField();
        contenedor.setLayout(new java.awt.GridBagLayout());
        contenedor.setOpaque(false);
        contenedor.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CReturner.getTexto()));
        iconoBusq.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "search.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        contenedor.add(iconoBusq, gridBagConstraints);
        inputTexto.setText("Buscar en la lista");
        inputTexto.setForeground(CReturner.getTexto2());
        inputTexto.setBackground(CReturner.getBackground());
        inputTexto.setBorder(null);
        inputTexto.setFont(coolvetica.deriveFont(15f));
        inputTexto.setPreferredSize(new Dimension(200, 25));
        inputTexto.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (primera) {
                    inputTexto.setText("");
                    inputTexto.setForeground(CReturner.getTexto());
                    primera = false;
                }
            }
        });
        inputTexto.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sql = "SELECT song.song_id, song.picture, song.name "
                        + "FROM song "
                        + "WHERE song.name LIKE '" + inputTexto.getText() + "%' "
                        + "ORDER BY song.name "
                        + "LIMIT 15";
                resultados.removeAll();
                buscarCanciones(resultados);
                revalidate();
                repaint();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        contenedor.add(inputTexto, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 10);
        busqSong.add(contenedor, gridBagConstraints);

        resultados.setBackground(CReturner.getBackground());
        resultados.setOpaque(false);
        resultados.setLayout(new java.awt.GridLayout(0, 1, 0, 10));
        sql = "SELECT song.song_id, song.picture, song.name "
                + "FROM song "
                + "ORDER BY song.name "
                + "LIMIT 15";
        buscarCanciones(resultados);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        busqSong.add(resultados, gridBagConstraints);
    }

    private void buscarCanciones(JPanel resultados) {
        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    while (resul.next()) {
                        CancionDisplay panel = new CancionDisplay(playIcon, resul.getString("picture"), resul.getString("name"), resul.getString("song_id"));
                        panel.setBackground(CReturner.getBackground());
                        panel.addMouseListener(new java.awt.event.MouseAdapter() {
                            @Override
                            public void mouseClicked(java.awt.event.MouseEvent e) {
                                if (e.getButton() == 3) {
                                    idSelected = panel.getIdCancion();
                                    pmenu.show(interfazPrinc, e.getXOnScreen(), e.getYOnScreen());
                                } else if (e.getClickCount() == 2 && !e.isConsumed()) {
                                    e.consume();
                                    idSelected = panel.getIdCancion();
                                    botBar.addCancion(idSelected);
                                    botBar.revalidate();
                                    botBar.repaint();
                                }
                            }

                            @Override
                            public void mouseEntered(java.awt.event.MouseEvent e) {
                                panel.setBackground(CReturner.getSelected());
                            }

                            @Override
                            public void mouseExited(java.awt.event.MouseEvent e) {
                                panel.setBackground(CReturner.getBackground());
                            }
                        });
                        resultados.add(panel);
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void devPlaylistTab() {
        busqPlaylist.setOpaque(false);
        busqPlaylist.setLayout(new java.awt.GridBagLayout());
        JPanel contenedor = new JPanel();
        JPanel resultados = new JPanel();
        JLabel iconoBusq = new javax.swing.JLabel();
        JTextField inputTexto = new JTextField();
        contenedor.setLayout(new java.awt.GridBagLayout());
        contenedor.setOpaque(false);
        contenedor.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CReturner.getTexto()));
        iconoBusq.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "search.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        contenedor.add(iconoBusq, gridBagConstraints);
        inputTexto.setText("Buscar en la lista");
        inputTexto.setForeground(CReturner.getTexto2());
        inputTexto.setBackground(CReturner.getBackground());
        inputTexto.setBorder(null);
        inputTexto.setFont(coolvetica.deriveFont(15f));
        inputTexto.setPreferredSize(new Dimension(200, 25));
        inputTexto.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (primera) {
                    inputTexto.setText("");
                    inputTexto.setForeground(CReturner.getTexto());
                    primera = false;
                }
            }
        });
        inputTexto.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sql = "SELECT playlist.playlist_id, playlist.picture, playlist.name, playlist.user "
                        + "FROM playlist "
                        + "WHERE playlist.name LIKE '" + inputTexto.getText() + "%' "
                        + "ORDER BY playlist.name "
                        + "LIMIT 20";
                resultados.removeAll();
                buscarPlaylists(resultados);
                revalidate();
                repaint();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        contenedor.add(inputTexto, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 10);
        busqPlaylist.add(contenedor, gridBagConstraints);

        resultados.setBackground(CReturner.getBackground());
        resultados.setLayout(new java.awt.GridLayout(0, 4, 10, 20));
        sql = "SELECT playlist.playlist_id, playlist.picture, playlist.name, playlist.user "
                + "FROM playlist "
                + "ORDER BY playlist.name "
                + "LIMIT 20";
        buscarPlaylists(resultados);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        busqPlaylist.add(resultados, gridBagConstraints);
    }

    private void buscarPlaylists(JPanel resultados) {
        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
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
                        resultados.add(elemento);
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void devAlbumTab() {
        busqAlbum.setOpaque(false);
        busqAlbum.setLayout(new java.awt.GridBagLayout());
        JPanel contenedor = new JPanel();
        JPanel resultados = new JPanel();
        JLabel iconoBusq = new javax.swing.JLabel();
        JTextField inputTexto = new JTextField();
        contenedor.setLayout(new java.awt.GridBagLayout());
        contenedor.setOpaque(false);
        contenedor.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CReturner.getTexto()));
        iconoBusq.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "search.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        contenedor.add(iconoBusq, gridBagConstraints);
        inputTexto.setText("Buscar en la lista");
        inputTexto.setForeground(CReturner.getTexto2());
        inputTexto.setBackground(CReturner.getBackground());
        inputTexto.setBorder(null);
        inputTexto.setFont(coolvetica.deriveFont(15f));
        inputTexto.setPreferredSize(new Dimension(200, 25));
        inputTexto.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (primera) {
                    inputTexto.setText("");
                    inputTexto.setForeground(CReturner.getTexto());
                    primera = false;
                }
            }
        });
        inputTexto.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sql = "SELECT album.album_id, album.picture, album.name AS nombre, artist.name AS artista "
                        + "FROM album, artist "
                        + "WHERE album.artist_id = artist.artist_id AND album.name LIKE '" + inputTexto.getText() + "%' "
                        + "ORDER BY album.name "
                        + "LIMIT 20";
                resultados.removeAll();
                buscarAlbums(resultados);
                revalidate();
                repaint();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        contenedor.add(inputTexto, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 10);
        busqAlbum.add(contenedor, gridBagConstraints);

        resultados.setBackground(CReturner.getBackground());
        resultados.setLayout(new java.awt.GridLayout(0, 4, 10, 20));
        sql = "SELECT album.album_id, album.picture, album.name AS nombre, artist.name AS artista "
                + "FROM album, artist "
                + "WHERE album.artist_id = artist.artist_id "
                + "ORDER BY album.name "
                + "LIMIT 20";
        buscarAlbums(resultados);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        busqAlbum.add(resultados, gridBagConstraints);
    }

    private void buscarAlbums(JPanel resultados) {
        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
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
                        resultados.add(elemento);
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void devArtistTab() {
        busqArtist.setOpaque(false);
        busqArtist.setLayout(new java.awt.GridBagLayout());
        JPanel contenedor = new JPanel();
        JPanel resultados = new JPanel();
        JLabel iconoBusq = new javax.swing.JLabel();
        JTextField inputTexto = new JTextField();
        contenedor.setLayout(new java.awt.GridBagLayout());
        contenedor.setOpaque(false);
        contenedor.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CReturner.getTexto()));
        iconoBusq.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "search.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        contenedor.add(iconoBusq, gridBagConstraints);
        inputTexto.setText("Buscar en la lista");
        inputTexto.setForeground(CReturner.getTexto2());
        inputTexto.setBackground(CReturner.getBackground());
        inputTexto.setBorder(null);
        inputTexto.setFont(coolvetica.deriveFont(15f));
        inputTexto.setPreferredSize(new Dimension(200, 25));
        inputTexto.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (primera) {
                    inputTexto.setText("");
                    inputTexto.setForeground(CReturner.getTexto());
                    primera = false;
                }
            }
        });
        inputTexto.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sql = "SELECT artist.artist_id, artist.profile_pic, artist.name "
                        + "FROM artist "
                        + "WHERE artist.name LIKE '" + inputTexto.getText() + "%' "
                        + "ORDER BY artist.name "
                        + "LIMIT 20";
                resultados.removeAll();
                buscarArtistas(resultados);
                revalidate();
                repaint();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        contenedor.add(inputTexto, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 10);
        busqArtist.add(contenedor, gridBagConstraints);

        resultados.setBackground(CReturner.getBackground());
        resultados.setLayout(new java.awt.GridLayout(0, 4, 10, 25));
        sql = "SELECT artist.artist_id, artist.profile_pic, artist.name "
                + "FROM artist "
                + "ORDER BY artist.name "
                + "LIMIT 20";
        buscarArtistas(resultados);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        busqArtist.add(resultados, gridBagConstraints);
    }

    private void buscarArtistas(JPanel resultados) {
        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    while (resul.next()) {
                        ReviewArtist elemento = new ReviewArtist(resul.getString("artist_id"), resul.getString("profile_pic"), resul.getString("name"));
                        elemento.addMouseListener(new java.awt.event.MouseAdapter() {
                            @Override
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                content = new Artista(elemento.getId(), listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar, home, window);
                                cargarNuevoPanel();
                                interfazPrinc.revalidate();
                                interfazPrinc.repaint();
                                setCursor(null);
                            }
                        });
                        resultados.add(elemento);
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void recargarListaPlaylist() {
        Statement sentencia;
        try {
            listaPlaylist.removeAll();
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                sql = "SELECT playlist.playlist_id ,playlist.picture, playlist.name "
                        + "FROM playlist, registro_savedlist "
                        + "WHERE playlist.playlist_id=registro_savedlist.playlist_id AND registro_savedlist.user='" + miUsername + "'";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    while (resul.next()) {
                        ItemPlaylist elemento = new ItemPlaylist(resul.getString("playlist_id"), resul.getString("picture"), resul.getString("name"), CReturner);
                        elemento.addMouseListener(new java.awt.event.MouseAdapter() {
                            @Override
                            public void mouseEntered(java.awt.event.MouseEvent evt) {
                                elemento.setColor(new Color(255, 36, 36));
                            }

                            @Override
                            public void mouseExited(java.awt.event.MouseEvent evt) {
                                elemento.setColor(CReturner.getAbsoluto());
                            }

                            @Override
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                content = new Playlist(elemento.getId(), listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar, home, window);
                                cargarNuevoPanel();
                                interfazPrinc.revalidate();
                                interfazPrinc.repaint();
                            }
                        });
                        listaPlaylist.add(elemento);
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarNuevoPanel() {
        main.removeAll();
        search.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "search.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
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
