package details;

import components.ImgCircleConverter;
import components.ReviewPlaylist;
import components.Utilities;
import interfaz.Interfaz;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;
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
public class Playlist extends JPanel {

    private final ColorReturner CReturner = new ColorReturner();
    private java.awt.GridBagConstraints gridBagConstraints;
    private Font coolvetica = null;
    private final String playlistID;
    private String picturePlaylist;
    private String tituloPlaylist;
    private final ImgCircleConverter convertidor = new ImgCircleConverter();
    private ImageIcon creador;

    public Playlist(String playlistID) {
        this.playlistID = playlistID;
        setOpaque(true);
        setBackground(CReturner.getBackground());
        setLayout(new java.awt.GridBagLayout());
        cargarFont();
        addLateralIzq();
        addLateralDer();
        addPreviewPlaylist();
        addBarraOpciones();
        addLista();
    }

    private void cargarFont() {
        InputStream is = Interfaz.class.getResourceAsStream("/fonts/coolvetica rg.otf");
        try {
            coolvetica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 40, 0);
        add(conjunto, gridBagConstraints);

        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            String sql = "SELECT playlist.picture, playlist.privacity, playlist.name, playlist.user, users.profile_pic, COUNT(registro_playlist.playlist_id) AS total "
                    + "FROM playlist, users, registro_playlist "
                    + "WHERE playlist.playlist_id = '1' AND playlist.user = users.username";
            ResultSet resul = sentencia.executeQuery(sql);
            if (resul.next()) {
                picturePlaylist = resul.getString("playlist.picture");
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
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageIcon img = new ImageIcon(Utilities.redondearImagen(Utilities.transformarLink(picturePlaylist), 15, CReturner));
        portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(225, 225, Image.SCALE_SMOOTH))));
        usuario.setIcon(creador);
    }

    private void addBarraOpciones() {
        JPanel barra = new javax.swing.JPanel();
        barra.setOpaque(false);
        barra.setLayout(new java.awt.GridBagLayout());
        
        JPanel izq = new javax.swing.JPanel();
        izq.setOpaque(false);
        izq.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        JLabel play = new javax.swing.JLabel();
        JLabel descargar = new javax.swing.JLabel();
        JLabel editar = new javax.swing.JLabel();
        play.setText("jLabel1");
        izq.add(play);
        descargar.setText("jLabel2");
        izq.add(descargar);
        editar.setText("jLabel1");
        izq.add(editar);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        barra.add(izq, gridBagConstraints);
        
        JPanel drch = new javax.swing.JPanel();
        drch.setOpaque(false);
        drch.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 0));
        JLabel buscador = new javax.swing.JLabel();
        javax.swing.JComboBox<String> orden = new javax.swing.JComboBox<>();
        buscador.setText("jLabel1");
        drch.add(buscador);
        orden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
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
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 30, 0);
        add(barra, gridBagConstraints);
    }

    private void addLista() {
        JPanel contenedor = new javax.swing.JPanel();
        javax.swing.JTable tabla = new javax.swing.JTable();

       contenedor.setOpaque(false);
       contenedor.setLayout(new java.awt.BorderLayout());

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        //tabla.setOpaque(false);
        //((DefaultTableCellRenderer)tabla.getDefaultRenderer(Object.class)).setOpaque(false);
        //tabla.setShowGrid(false);
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
}
