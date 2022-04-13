package views;

import components.ReviewArtist;
import components.ReviewPlaylist;
import components.ScrollBar;
import components.TabPanel;
import components.TopBar;
import components.Utilities;
import details.Album;
import details.Playlist;
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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import profiles.Artista;
import tabla.CancionDisplay;
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
public class Busqueda extends JPanel {

    private JPanel listaPlaylist;
    private JPanel interfazPrinc;
    private JPanel content;
    private JScrollPane scrollPane;
    private JPanel main;
    private JPanel botBar;
    private JPanel topBar;
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

    public Busqueda(JPanel listaPlaylist, JPanel interfazPrinc, JPanel botBar, JScrollPane scrollPane, JPanel main, JPanel topBar, JLabel home, JLabel search) {
        this.playIcon = new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "play.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
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
        tabP.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof JTabbedPane) {
                    primera = true;
                }
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
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            ResultSet resul = sentencia.executeQuery(sql);
            while (resul.next()) {
                CancionDisplay panel = new CancionDisplay(playIcon, resul.getString("picture"), resul.getString("name"), resul.getString("song_id"));
                resultados.add(panel);
                // Añadir listener a estos
            }
            resul.close();
            sentencia.close();
            conexion.close();
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
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            ResultSet resul = sentencia.executeQuery(sql);
            while (resul.next()) {
                ReviewPlaylist elemento = new ReviewPlaylist(resul.getString("playlist_id"), resul.getString("picture"), resul.getString("name"), resul.getString("user"));
                elemento.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        content = new Playlist(elemento.getId(), listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar, home);
                        cargarNuevoPanel();
                        interfazPrinc.revalidate();
                        interfazPrinc.repaint();
                    }
                });
                resultados.add(elemento);
            }
            resul.close();
            sentencia.close();
            conexion.close();
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
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            ResultSet resul = sentencia.executeQuery(sql);
            while (resul.next()) {
                ReviewPlaylist elemento = new ReviewPlaylist(resul.getString("album_id"), resul.getString("picture"), resul.getString("nombre"), resul.getString("artista"));
                elemento.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        content = new Album(elemento.getId(), listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar);
                        cargarNuevoPanel();
                        interfazPrinc.revalidate();
                        interfazPrinc.repaint();
                    }
                });
                resultados.add(elemento);
            }
            resul.close();
            sentencia.close();
            conexion.close();
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
                        + "WHERE artist.name LIKE '"+inputTexto.getText()+"%' "
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
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            ResultSet resul = sentencia.executeQuery(sql);
            while (resul.next()) {
                ReviewArtist elemento = new ReviewArtist(resul.getString("artist_id"), resul.getString("profile_pic"), resul.getString("name"));
                elemento.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        content = new Artista(elemento.getId(), listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar, home);
                        cargarNuevoPanel();
                        interfazPrinc.revalidate();
                        interfazPrinc.repaint();
                    }
                });
                resultados.add(elemento);
            }
            resul.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarNuevoPanel() {
        main.removeAll();
        search.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "search.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        topBar = new TopBar();
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
