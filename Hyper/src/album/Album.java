package album;

import components.ImgCircleConverter;
import components.TopBar;
import appManagement.Utilities;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Popup;
import songManager.BotBar;
import appManagement.ColorReturner;
import components.ElementoListaPopup;
import components.ItemPlaylist;
import components.ScrollBar;
import dialogs.CreatePlaylist;
import dialogs.EditDialog;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.PopupFactory;
import javax.swing.table.DefaultTableCellRenderer;
import playlist.Playlist;
import profiles.Artista;
import views.Inicio;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class Album extends JPanel {

    private final ColorReturner CReturner = new ColorReturner();
    private java.awt.GridBagConstraints gridBagConstraints;
    private final Font coolvetica = Utilities.cargarCoolvetica();
    private final String albumID;
    private String artistId;
    private final ImgCircleConverter convertidor = new ImgCircleConverter();
    private ImageIcon creador;
    private ImageIcon downloaded;
    private ImageIcon saved;
    private List contenido = new ArrayList();
    private String username;
    private javax.swing.JTable tabla;
    private boolean guardada;
    private JPanel listaPlaylist;
    private JPanel interfazPrinc;
    private JPanel content;
    private JScrollPane scrollPane;
    private JPanel main;
    private BotBar botBar;
    private TopBar topBar;
    private JFrame window;
    private JLabel home;
    private Popup popup;
    private boolean descargada;
    private JLabel titulo;
    private JLabel portada;
    private ArrayList<String> lista = new ArrayList<>();
    private JMenuItem optReproducir;
    private JMenuItem optAddCola;
    private JMenu optAddPlaylist;
    private JPopupMenu pmenu;
    private Statement sentenciaExtra;

    public Album(String albumID, JPanel listaPlaylist, JPanel interfazPrinc, BotBar botBar, JScrollPane scrollPane,
            JPanel main, TopBar topBar, JLabel home, JFrame window) {
        this.albumID = albumID;
        this.content = this;
        this.listaPlaylist = listaPlaylist;
        this.interfazPrinc = interfazPrinc;
        this.botBar = botBar;
        this.topBar = topBar;
        this.scrollPane = scrollPane;
        this.main = main;
        this.home = home;
        this.window = window;
        setOpaque(true);
        Preferences pref = Preferences.userRoot().node("Rememberme");
        username = pref.get("ActualUser", "");
        setBackground(CReturner.getBackground());
        setLayout(new java.awt.GridBagLayout());
        addLateralIzq();
        addLateralDer();
        addPreviewAlbum();
        addBarraOpciones();
        addLista();
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

    private void addPreviewAlbum() {
        JPanel conjunto = new javax.swing.JPanel();
        conjunto.setMaximumSize(new Dimension(3000, 225));
        conjunto.setOpaque(false);
        conjunto.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        portada = new javax.swing.JLabel();
        conjunto.add(portada);

        JPanel details = new javax.swing.JPanel();
        details.setOpaque(false);
        details.setLayout(new javax.swing.BoxLayout(details, javax.swing.BoxLayout.PAGE_AXIS));

        JPanel help1 = new javax.swing.JPanel();
        help1.setOpaque(false);
        help1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        JLabel privacity = new javax.swing.JLabel();
        privacity.setForeground(CReturner.getTexto());
        privacity.setFont(coolvetica.deriveFont(14f));
        help1.add(privacity);
        details.add(help1);

        JPanel help2 = new javax.swing.JPanel();
        help2.setOpaque(false);
        help2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        titulo = new javax.swing.JLabel();
        titulo.setForeground(CReturner.getTexto());
        titulo.setFont(coolvetica.deriveFont(75f));
        help2.add(titulo);
        details.add(help2);

        JPanel detalles = new javax.swing.JPanel();
        detalles.setOpaque(false);
        detalles.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        JLabel usuario = new javax.swing.JLabel();
        usuario.setForeground(CReturner.getTexto2());
        usuario.setFont(coolvetica.deriveFont(15f));
        JLabel num = new javax.swing.JLabel();
        num.setForeground(CReturner.getTexto());
        num.setFont(coolvetica.deriveFont(15f));
        detalles.add(usuario);
        detalles.add(num);
        details.add(detalles);
        conjunto.add(details);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        add(conjunto, gridBagConstraints);

        Statement sentencia;
        String pictureAlbum = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT artist.artist_id "
                        + "FROM artist, album "
                        + "WHERE album.album_id = '" + albumID + "' AND album.artist_id = artist.artist_id";
                ResultSet resul = sentencia.executeQuery(sql);
                if (resul.next()) {
                    artistId = resul.getString("artist_id");
                }
                sql = "SET lc_time_names = 'es_ES'";
                sentencia.executeUpdate(sql);
                sql = "SELECT album.picture, album.name AS nAlbum, artist.name AS nArtist, artist.profile_pic, DATE_FORMAT(album.fecha,'%d - %b - %Y') AS fecha, COUNT(registro_album.album_id) AS total "
                        + "FROM album, artist, registro_album "
                        + "WHERE album.album_id = '" + albumID + "' AND album.album_id = registro_album.album_id AND album.artist_id = artist.artist_id";
                resul = sentencia.executeQuery(sql);
                if (resul.next()) {
                    pictureAlbum = resul.getString("album.picture");
                    privacity.setText("ÁLBUM");
                    titulo.setText(resul.getString("nAlbum"));
                    String picture = resul.getString("artist.profile_pic");
                    ImageIcon img = new ImageIcon(convertidor.convertirImagen(Utilities.transformarLink(picture)));
                    creador = new ImageIcon(img.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
                    usuario.setText(resul.getString("nArtist"));

                    num.setText("•  " + resul.getString("total") + " canciones  •  " + resul.getString("fecha"));
                    usuario.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            usuario.setForeground(CReturner.getPrincipal());
                        }

                        @Override
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            usuario.setForeground(CReturner.getTexto2());
                        }

                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            content = new Artista(artistId, listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar, home, window);
                            cargarNuevoPanel();
                            interfazPrinc.revalidate();
                            interfazPrinc.repaint();
                        }
                    });
                }
                resul.close();
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Album.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageIcon img = new ImageIcon(Utilities.redondearImagen(Utilities.transformarLink(pictureAlbum), 15, CReturner));
        portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(225, 225, Image.SCALE_SMOOTH))));
        usuario.setIcon(creador);
    }

    private void addBarraOpciones() {
        JPanel barra = new javax.swing.JPanel();
        barra.setMaximumSize(new Dimension(3000, 100));
        barra.setOpaque(false);
        barra.setLayout(new java.awt.GridBagLayout());

        JPanel izq = new javax.swing.JPanel();
        izq.setOpaque(false);
        izq.setLayout(new javax.swing.BoxLayout(izq, javax.swing.BoxLayout.LINE_AXIS));
        JLabel play = new javax.swing.JLabel();
        JLabel guardado = new javax.swing.JLabel();
        JLabel descargar = new javax.swing.JLabel();
        JLabel editar = new javax.swing.JLabel();
        play.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "play.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        izq.add(play);

        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT users.username "
                        + "FROM users, registro_savedalbum "
                        + "WHERE registro_savedalbum.album_id = '" + albumID + "' AND users.username = registro_savedalbum.user";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    if (resul.next()) {
                        saved = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "guardado.png"));
                        guardada = true;
                    } else {
                        saved = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "guardado.png"));
                        guardada = false;
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Album.class.getName()).log(Level.SEVERE, null, ex);
        }
        guardado.setIcon(new ImageIcon(saved.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        izq.add(guardado);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT registro_savedalbum.downloaded "
                        + "FROM registro_savedalbum "
                        + "WHERE registro_savedalbum.album_id = '" + albumID + "' AND registro_savedalbum.user = '" + username + "'";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    if (resul.next()) {
                        if (resul.getString("downloaded").equals("1")) {
                            downloaded = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "descargado.png"));
                            descargada = true;
                        } else {
                            downloaded = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "descargar.png"));
                            descargada = false;
                        }
                    } else {
                        downloaded = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "descargar.png"));
                        descargada = false;
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Album.class.getName()).log(Level.SEVERE, null, ex);
        }
        descargar.setIcon(new ImageIcon(downloaded.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        izq.add(descargar);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT album.artist_id  "
                        + "FROM album, artist "
                        + "WHERE album.album_id = '" + albumID + "' AND album.artist_id = artist.artist_id AND artist.username = '" + username + "'";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    if (resul.next()) {
                        editar.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "edit.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                        izq.add(editar);
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Album.class.getName()).log(Level.SEVERE, null, ex);
        }
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        barra.add(izq, gridBagConstraints);

        play.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                play.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "play.png")).getImage().getScaledInstance(81, 80, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                play.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "play.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ArrayList<String> lista2 = new ArrayList<>();
                lista2.addAll(lista);
                botBar.addListaDeCanciones(lista2, lista.get(0));
                botBar.revalidate();
                botBar.repaint();
            }
        });
        guardado.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                guardado.setIcon(new ImageIcon(saved.getImage().getScaledInstance(60, 61, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                guardado.setIcon(new ImageIcon(saved.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Statement sentencia;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                        sentencia = conexion.createStatement();
                        String sql;
                        if (guardada) {
                            sql = "DELETE FROM registro_savedalbum "
                                    + "WHERE registro_savedalbum.album_id = '" + albumID + "' AND registro_savedalbum.user = '" + username + "'";
                            guardada = false;
                            saved = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "guardado.png"));
                            guardado.setIcon(new ImageIcon(saved.getImage().getScaledInstance(60, 61, Image.SCALE_SMOOTH)));
                        } else {
                            sql = "INSERT INTO registro_savedalbum "
                                    + "VALUES (" + albumID + ",'" + username + "',false)";
                            guardada = true;
                            saved = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "guardado.png"));
                            guardado.setIcon(new ImageIcon(saved.getImage().getScaledInstance(60, 61, Image.SCALE_SMOOTH)));
                        }
                        sentencia.executeUpdate(sql);
                        sentencia.close();
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(Album.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        descargar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                descargar.setIcon(new ImageIcon(downloaded.getImage().getScaledInstance(60, 61, Image.SCALE_SMOOTH)));
                if (!guardada) {
                    if (popup != null) {
                        popup.hide();
                    }
                    JLabel text = new JLabel("Guarda el album para poder descargarla");
                    text.setBackground(CReturner.getBackground());
                    text.setOpaque(true);
                    text.setForeground(CReturner.getTexto());
                    text.setFont(coolvetica.deriveFont(14f));
                    popup = PopupFactory.getSharedInstance().getPopup(evt.getComponent(), text, evt.getXOnScreen(), evt.getYOnScreen());
                    popup.show();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                descargar.setIcon(new ImageIcon(downloaded.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                if (!guardada) {
                    popup.hide();
                }
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (guardada) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    if (descargada) {
                        downloaded = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "descargar.png"));
                        descargar.setIcon(new ImageIcon(downloaded.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                        descargada = false;
                    } else {
                        downloaded = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "descargado.png"));
                        descargar.setIcon(new ImageIcon(downloaded.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                        descargada = true;
                        lista.forEach(elem -> {
                            Utilities.descargarCanciones(elem);
                        });
                    }
                    Statement sentencia;
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                            sentencia = conexion.createStatement();
                            String sql;
                            if (descargada) {
                                sql = "UPDATE registro_savedalbum "
                                        + "SET registro_savedalbum.downloaded = 1 "
                                        + "WHERE registro_savedalbum.album_id = '" + albumID + "' AND registro_savedalbum.user = '" + username + "'";
                            } else {
                                sql = "UPDATE registro_savedalbum "
                                        + "SET registro_savedalbum.downloaded = 0 "
                                        + "WHERE registro_savedalbum.album_id = '" + albumID + "' AND registro_savedalbum.user = '" + username + "'";
                            }
                            sentencia.executeUpdate(sql);
                            sentencia.close();
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(Album.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    setCursor(null);
                }
            }
        });
        editar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                editar.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "edit.png")).getImage().getScaledInstance(60, 61, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                editar.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "edit.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EditDialog ed = new EditDialog(false, albumID);
                ed.setBounds(0, 0, 350, 270);
                ed.setLocationRelativeTo(null);
                ed.setVisible(true);
                ed.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        if (ed.getGuardado() == 1) {
                            titulo.setText(ed.nuevoNombre());
                            if (ed.getFotoSelected()) {
                                ImageIcon img = new ImageIcon(Utilities.redondearImagen(Utilities.transformarLink(ed.nuevoLink()), 15, CReturner));
                                portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(225, 225, Image.SCALE_SMOOTH))));
                            }
                        } else if (ed.getGuardado() == -1) {
                            content = new Inicio(interfazPrinc, botBar, scrollPane, main, topBar, home, listaPlaylist, window);
                            cargarNuevoPanel();
                            interfazPrinc.revalidate();
                            interfazPrinc.repaint();
                        }
                    }
                });
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        add(barra, gridBagConstraints);
    }

    private void addLista() {
        JPanel contenedor = new javax.swing.JPanel();
        contenedor.setOpaque(false);
        contenedor.setLayout(new java.awt.GridLayout(0, 1, 0, 10));

        addDatos();
        tabla = new javax.swing.JTable(new AlbumTableModel(contenido));
        tabla.setFont(coolvetica.deriveFont(15f));
        tabla.setForeground(CReturner.getTexto());
        tabla.setOpaque(false);
        ((DefaultTableCellRenderer) tabla.getDefaultRenderer(Object.class)).setOpaque(false);
        tabla.setShowGrid(false);
        tabla.setDefaultRenderer(AlbumFeed.class, new AlbumCellRender());
        tabla.setRowHeight(60);
        tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        contenedor.add(tabla);

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
            ArrayList<String> lista2 = new ArrayList<>();
            lista2.addAll(lista);
            botBar.addListaDeCanciones(lista2, ((AlbumFeed) tabla.getValueAt(tabla.getSelectedRow(), 0)).getIdCancion());
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
            botBar.addToCola(((AlbumFeed) tabla.getValueAt(tabla.getSelectedRow(), 0)).getIdCancion());
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
                                String sql = "INSERT INTO registro_playlist(playlist_id,song_id,user_added) "
                                        + "VALUES('" + cp.getIdPlaylist() + "','" + ((AlbumFeed) tabla.getValueAt(tabla.getSelectedRow(), 0)).getIdCancion() + "','" + username + "')";
                                sentencia.executeUpdate(sql);
                                sentencia.close();
                            }
                        } catch (SQLException | ClassNotFoundException ex) {
                            Logger.getLogger(Album.class.getName()).log(Level.SEVERE, null, ex);
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
                String sql = "SELECT playlist.playlist_id, playlist.name "
                        + "FROM playlist "
                        + "WHERE user = '" + username + "'";
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
                                    String sql2 = "SELECT registro_playlist.song_id "
                                            + "FROM registro_playlist "
                                            + "WHERE registro_playlist.playlist_id = '" + elemLista.getIdPlaylist() + "' AND registro_playlist.song_id = '" + ((AlbumFeed) tabla.getValueAt(tabla.getSelectedRow(), 0)).getIdCancion() + "'";
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
                                                + "WHERE registro_savedlist.playlist_id = '" + elemLista.getIdPlaylist() + "' AND registro_savedlist.user = '" + username + "'";
                                        resul1 = sentenciaExtra.executeQuery(sql2);
                                        boolean nuevaDownloaded = false;
                                        if (resul1.next()) {
                                            nuevaDownloaded = resul1.getString("downloaded").equals("1");
                                        }
                                        sql2 = "INSERT INTO registro_playlist(playlist_id,song_id,user_added) "
                                                + "VALUES('" + elemLista.getIdPlaylist() + "','" + ((AlbumFeed) tabla.getValueAt(tabla.getSelectedRow(), 0)).getIdCancion() + "','" + username + "')";
                                        sentenciaExtra.executeUpdate(sql2);
                                        if (nuevaDownloaded) {
                                            Utilities.descargarCanciones(((AlbumFeed) tabla.getValueAt(tabla.getSelectedRow(), 0)).getIdCancion());
                                        }
                                    }
                                    sentenciaExtra.close();
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(Album.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            setCursor(null);
                        });
                        optAddPlaylist.add(elemLista);
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Album.class.getName()).log(Level.SEVERE, null, ex);
        }

        pmenu.add(optAddPlaylist);

        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getButton() == 3) {
                    int r = tabla.rowAtPoint(e.getPoint());
                    if (r >= 0 && r < tabla.getRowCount()) {
                        tabla.setRowSelectionInterval(r, r);
                    } else {
                        tabla.clearSelection();
                    }
                    pmenu.show(interfazPrinc, e.getXOnScreen(), e.getYOnScreen());
                } else if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    ArrayList<String> lista2 = new ArrayList<>();
                    lista2.addAll(lista);
                    botBar.addListaDeCanciones(lista2, ((AlbumFeed) tabla.getValueAt(tabla.getSelectedRow(), 0)).getIdCancion());
                    botBar.revalidate();
                    botBar.repaint();
                }
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 30, 0);

        add(contenedor, gridBagConstraints);
    }

    private void addDatos() {
        Statement sentencia;
        contenido.clear();
        contenido.add(null);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT song.picture, song.name AS nCancion, song.song_id "
                        + "FROM song, registro_album "
                        + "WHERE registro_album.album_id = '" + albumID + "' AND registro_album.song_id = song.song_id";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    while (resul.next()) {
                        contenido.add(new AlbumFeed(resul.getString("picture"), resul.getString("nCancion"), resul.getString("song_id")));
                        lista.add(resul.getString("song_id"));
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Album.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarNuevoPanel() {
        main.removeAll();

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

    private void recargarListaPlaylist() {
        Statement sentencia;
        try {
            listaPlaylist.removeAll();
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT playlist.playlist_id ,playlist.picture, playlist.name "
                        + "FROM playlist, registro_savedlist "
                        + "WHERE playlist.playlist_id=registro_savedlist.playlist_id AND registro_savedlist.user='" + username + "'";
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
            Logger.getLogger(Album.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
