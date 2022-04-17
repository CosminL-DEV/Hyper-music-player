package details;

import components.ImgCircleConverter;
import components.Utilities;
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
import songManager.QueueManager;
import songManager.QueueManager.Cancion;
import themeManagement.ColorReturner;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 11-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class Album extends JPanel {

    private final ColorReturner CReturner = new ColorReturner();
    private java.awt.GridBagConstraints gridBagConstraints;
    private final Font coolvetica = Utilities.cargarCoolvetica();
    private final String albumID;
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
    private JFrame window;
    private JLabel home;
    private Popup popup;

    public Album(String albumID, JPanel listaPlaylist, JPanel interfazPrinc, JPanel botBar, JScrollPane scrollPane, 
            JPanel main, JPanel topBar, JLabel home, JFrame window) {
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
        sentenciaSQL = "SELECT song.picture, song.name AS nCancion, album.name AS nAlbum, registro_playlist.user_added, "
                + "registro_playlist.fecha_added, song.song_id, users.profile_pic, registro_playlist.fecha_added "
                + "FROM song, registro_playlist, registro_album, album, users "
                + "WHERE registro_playlist.playlist_id='" + albumID + "' AND song.song_id = registro_album.song_id AND album.album_id = registro_album.album_id "
                + "AND registro_playlist.song_id = song.song_id AND users.username = registro_playlist.user_added";
        sentenciaEjecutable = sentenciaSQL;
        setOpaque(true);
        Preferences pref = Preferences.userRoot().node("Rememberme");
        username = pref.get("ActualUser", "");
        setBackground(CReturner.getBackground());
        setLayout(new java.awt.GridBagLayout());
        addLateralIzq();
        addLateralDer();
        addPreviewAlbum();
        //addBarraOpciones();
        //addLista();
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

        JLabel portada = new javax.swing.JLabel();
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
        JLabel titulo = new javax.swing.JLabel();
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
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            String sql = "SELECT album.picture, album.name AS nAlbum, artist.name AS nArtist, artist.profile_pic, COUNT(registro_album.album_id) AS total "
                    + "FROM album, artist, registro_album "
                    + "WHERE album.album_id = '" + albumID + "' AND album.album_id = registro_album.album_id AND album.artist_id = artist.artist_id";
            ResultSet resul = sentencia.executeQuery(sql);
            if (resul.next()) {
                pictureAlbum = resul.getString("album.picture");
                privacity.setText("ÁLBUM");
                titulo.setText(resul.getString("nAlbum"));
                String picture = resul.getString("artist.profile_pic");
                ImageIcon img = new ImageIcon(convertidor.convertirImagen(Utilities.transformarLink(picture)));
                creador = new ImageIcon(img.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
                usuario.setText(resul.getString("nArtist"));
                num.setText("•  " + resul.getString("total") + " canciones");
            }
            resul.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Album.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageIcon img = new ImageIcon(Utilities.redondearImagen(Utilities.transformarLink(pictureAlbum), 15, CReturner));
        portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(225, 225, Image.SCALE_SMOOTH))));
        usuario.setIcon(creador);
    }
}
