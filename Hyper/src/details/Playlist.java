package details;

import components.EditDialog;
import components.ImgCircleConverter;
import components.ItemPlaylist;
import components.ScrollBar;
import components.TopBar;
import tabla.PlaylistFeed;
import components.Utilities;
import interfaz.Interfaz;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.table.DefaultTableCellRenderer;
import tabla.PlaylistCellRender;
import tabla.PlaylistTableModel;
import themeManagement.ColorReturner;
import views.Inicio;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 11-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class Playlist extends JPanel {

    private final ColorReturner CReturner = new ColorReturner();
    private java.awt.GridBagConstraints gridBagConstraints;
    private final Font coolvetica = Utilities.cargarCoolvetica();
    private final String playlistID;
    private final ImgCircleConverter convertidor = new ImgCircleConverter();
    private ImageIcon creador;
    private ImageIcon downloaded;
    private ImageIcon saved;
    private boolean primera = false;
    private List contenido = new ArrayList();
    private final String sentenciaSQL;
    private String sentenciaEjecutable;
    private String username;
    private components.Combobox orden;
    private javax.swing.JTextField inputBusq;
    private javax.swing.JTable tabla;
    private boolean guardada;
    private JPanel listaPlaylist;
    private JPanel interfazPrinc;
    private JPanel content;
    private JScrollPane scrollPane;
    private JPanel main;
    private JPanel botBar;
    private JPanel topBar;
    private JLabel home;
    private Popup popup;
    private JLabel privacity;
    private JLabel titulo;
    private JLabel portada;

    public Playlist(String playlistID, JPanel listaPlaylist, JPanel interfazPrinc, JPanel botBar, JScrollPane scrollPane, JPanel main, JPanel topBar, JLabel home) {
        this.playlistID = playlistID;
        this.content = this;
        this.listaPlaylist = listaPlaylist;
        this.interfazPrinc = interfazPrinc;
        this.botBar = botBar;
        this.topBar = topBar;
        this.scrollPane = scrollPane;
        this.main = main;
        this.home = home;
        sentenciaSQL = "SELECT song.picture, song.name AS nCancion, album.name AS nAlbum, registro_playlist.user_added, "
                + "registro_playlist.fecha_added, song.song_id, users.profile_pic, registro_playlist.fecha_added "
                + "FROM song, registro_playlist, registro_album, album, users "
                + "WHERE registro_playlist.playlist_id='" + playlistID + "' AND song.song_id = registro_album.song_id AND album.album_id = registro_album.album_id "
                + "AND registro_playlist.song_id = song.song_id AND users.username = registro_playlist.user_added";
        sentenciaEjecutable = sentenciaSQL;
        setOpaque(true);
        Preferences pref = Preferences.userRoot().node("Rememberme");
        username = pref.get("ActualUser", "");
        setBackground(CReturner.getBackground());
        setLayout(new java.awt.GridBagLayout());
        addLateralIzq();
        addLateralDer();
        addPreviewPlaylist();
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

    private void addPreviewPlaylist() {
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
        privacity = new javax.swing.JLabel();
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
        String picturePlaylist = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            String sql = "SELECT playlist.picture, playlist.privacity, playlist.name, playlist.user, users.profile_pic, COUNT(registro_playlist.playlist_id) AS total "
                    + "FROM playlist, users, registro_playlist "
                    + "WHERE playlist.playlist_id = '" + playlistID + "' AND playlist.user = users.username AND playlist.playlist_id = registro_playlist.playlist_id";
            ResultSet resul = sentencia.executeQuery(sql);
            if (resul.next()) {
                picturePlaylist = resul.getString("playlist.picture");
                if (picturePlaylist == null) {
                    picturePlaylist = "http://localhost/hyper/wp-content/uploads/2022/03/playlist.png";
                }
                privacity.setText("LISTA " + resul.getString("playlist.privacity").toUpperCase());
                titulo.setText(resul.getString("playlist.name"));
                String picture = resul.getString("users.profile_pic");
                if (picture == null) {
                    picture = "http://localhost/hyper/wp-content/uploads/2022/04/user.png";
                }
                ImageIcon img = new ImageIcon(convertidor.convertirImagen(Utilities.transformarLink(picture)));
                creador = new ImageIcon(img.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
                usuario.setText(resul.getString("playlist.user"));
                num.setText("•  " + resul.getString("total") + " canciones");
            }
            resul.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageIcon img = new ImageIcon(Utilities.redondearImagen(Utilities.transformarLink(picturePlaylist), 15, CReturner));
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
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            String sql = "SELECT users.username "
                    + "FROM users, registro_savedlist "
                    + "WHERE registro_savedlist.playlist_id = '" + playlistID + "' AND users.username = registro_savedlist.user";
            ResultSet resul = sentencia.executeQuery(sql);
            if (resul.next()) {
                saved = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "guardado.png"));
                guardada = true;
            } else {
                saved = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "guardado.png"));
                guardada = false;
            }
            resul.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, null, ex);
        }
        guardado.setIcon(new ImageIcon(saved.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        izq.add(guardado);

        downloaded = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "descargar.png"));
        descargar.setIcon(new ImageIcon(downloaded.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        izq.add(descargar);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            String sql = "SELECT playlist.user "
                    + "FROM playlist "
                    + "WHERE playlist.playlist_id = '" + playlistID + "' AND playlist.user = '" + username + "'";
            ResultSet resul = sentencia.executeQuery(sql);
            if (resul.next()) {
                editar.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "edit.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                izq.add(editar);
            }
            resul.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, null, ex);
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
                // Accion
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
                    Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
                    sentencia = conexion.createStatement();
                    String sql = null;
                    if (guardada) {
                        sql = "DELETE FROM registro_savedlist "
                                + "WHERE registro_savedlist.playlist_id = '5' AND registro_savedlist.user = 'cosmin'";
                        guardada = false;
                        saved = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "guardado.png"));
                        guardado.setIcon(new ImageIcon(saved.getImage().getScaledInstance(60, 61, Image.SCALE_SMOOTH)));
                    } else {
                        sql = "INSERT INTO registro_savedlist "
                                + "VALUES (" + playlistID + ",'" + username + "',false)";
                        guardada = true;
                        saved = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "guardado.png"));
                        guardado.setIcon(new ImageIcon(saved.getImage().getScaledInstance(60, 61, Image.SCALE_SMOOTH)));
                    }
                    sentencia.executeUpdate(sql);
                    sentencia.close();
                    conexion.close();
                    recargarListaPlaylist();
                    interfazPrinc.revalidate();
                    interfazPrinc.repaint();
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, null, ex);
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
                    JLabel text = new JLabel("Guarda la lista para poder descargarla");
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
                // Accion
            }
        });
        editar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                editar.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "edit.png")).getImage().getScaledInstance(60, 61, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                editar.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "edit.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EditDialog ed = new EditDialog(true, playlistID);
                ed.setBounds(0, 0, 350, 270);
                ed.setLocationRelativeTo(null);
                ed.setVisible(true);
                ed.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        if (ed.getGuardado() == 1) {
                            if (ed.nuevoIsPublica()) {
                                privacity.setText("LISTA PUBLICA");
                            } else {
                                privacity.setText("LISTA PRIVADA");
                            }
                            titulo.setText(ed.nuevoNombre());
                            ImageIcon img = new ImageIcon(Utilities.redondearImagen(Utilities.transformarLink(ed.nuevoLink()), 15, CReturner));
                            portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(225, 225, Image.SCALE_SMOOTH))));
                        } else if (ed.getGuardado() == -1) {
                            content = new Inicio(interfazPrinc, botBar, scrollPane, main, topBar, home, listaPlaylist);
                            cargarNuevoPanel();
                            recargarListaPlaylist();
                            interfazPrinc.revalidate();
                            interfazPrinc.repaint();
                        }
                    }
                });
            }
        });

        JPanel drch = new javax.swing.JPanel();
        drch.setOpaque(false);
        drch.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 0));
        JPanel buscador = new javax.swing.JPanel();
        JLabel iconoBusq = new javax.swing.JLabel();
        inputBusq = new javax.swing.JTextField();
        buscador.setLayout(new java.awt.GridBagLayout());
        buscador.setOpaque(false);
        buscador.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CReturner.getTexto()));
        iconoBusq.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "search.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        buscador.add(iconoBusq, gridBagConstraints);
        inputBusq.setText("Buscar en la lista              ");
        inputBusq.setForeground(CReturner.getTexto2());
        inputBusq.setBackground(CReturner.getBackground());
        inputBusq.setBorder(null);
        inputBusq.setFont(coolvetica.deriveFont(15f));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        buscador.add(inputBusq, gridBagConstraints);
        inputBusq.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (!primera) {
                    inputBusq.setText("");
                    inputBusq.setForeground(CReturner.getTexto());
                    primera = true;
                }
            }
        });
        inputBusq.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                reformularSentencia();
            }
        });
        drch.add(buscador);

        orden = new components.Combobox();
        orden.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[]{"Orden Personalizado", "Titulo ASC", "Titulo DESC", "Album", "Fecha de incorporación ASC", "Fecha de incorporación DESC"}
        ));
        orden.setLabeText("");
        orden.setBackground(CReturner.getTexto2());
        orden.setForeground(CReturner.getTexto());
        orden.setBorder(null);
        orden.setPreferredSize(new Dimension(225, 30));
        orden.setFont(coolvetica.deriveFont(15f));
        orden.setFocusable(false);
        orden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reformularSentencia();
            }
        });
        drch.add(orden);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        barra.add(drch, gridBagConstraints);

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
        tabla = new javax.swing.JTable(new PlaylistTableModel(contenido));
        tabla.setFont(coolvetica.deriveFont(15f));
        tabla.setForeground(CReturner.getTexto());
        tabla.setOpaque(false);
        ((DefaultTableCellRenderer) tabla.getDefaultRenderer(Object.class)).setOpaque(false);
        tabla.setShowGrid(false);
        tabla.setDefaultRenderer(PlaylistFeed.class, new PlaylistCellRender());
        tabla.setRowHeight(60);
        tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        contenedor.add(tabla);

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
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            ResultSet resul = sentencia.executeQuery(sentenciaEjecutable);
            while (resul.next()) {
                Date fecha = new Date(resul.getTimestamp("fecha_added").getTime());
                contenido.add(new PlaylistFeed(resul.getString("picture"), resul.getString("nCancion"), resul.getString("song_id"), resul.getString("nAlbum"), resul.getString("profile_pic"), resul.getString("user_added"), fecha));
            }
            resul.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void reformularSentencia() {
        sentenciaEjecutable = sentenciaSQL;
        if (!inputBusq.getText().isBlank() && !inputBusq.getText().startsWith("Buscar en la lista")) {
            sentenciaEjecutable += " AND song.name LIKE '%" + inputBusq.getText() + "%'";
        }
        switch (orden.getSelectedIndex()) {
            case 1:
                sentenciaEjecutable += " ORDER BY song.name";
                break;
            case 2:
                sentenciaEjecutable += " ORDER BY song.name DESC";
                break;
            case 3:
                sentenciaEjecutable += " ORDER BY album.name";
                break;
            case 4:
                sentenciaEjecutable += " ORDER BY fecha_added";
                break;
            case 5:
                sentenciaEjecutable += " ORDER BY fecha_added DESC";
                break;
        }
        addDatos();
        tabla = new javax.swing.JTable(new PlaylistTableModel(contenido));
        revalidate();
        repaint();
    }

    private void recargarListaPlaylist() {
        Statement sentencia;
        try {
            listaPlaylist.removeAll();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            String sql = "SELECT playlist.playlist_id ,playlist.picture, playlist.name "
                    + "FROM playlist, registro_savedlist "
                    + "WHERE playlist.playlist_id=registro_savedlist.playlist_id AND registro_savedlist.user='" + username + "'";
            ResultSet resul = sentencia.executeQuery(sql);
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
                        content = new Playlist(elemento.getId(), listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar, home);
                        cargarNuevoPanel();
                        interfazPrinc.revalidate();
                        interfazPrinc.repaint();
                    }
                });
                listaPlaylist.add(elemento);
            }
            resul.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarNuevoPanel() {
        main.removeAll();

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
