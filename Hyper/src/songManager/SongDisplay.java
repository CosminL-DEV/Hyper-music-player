package songManager;

import appManagement.Utilities;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
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
public class SongDisplay extends javax.swing.JPanel {

    private String linkCancion;

    public SongDisplay(String idCancion) {
        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gridBagConstraints;
        ColorReturner CReturner = new ColorReturner();
        Font coolvetica = Utilities.cargarCoolvetica();
        javax.swing.JLabel portada;
        javax.swing.JLabel nombre;
        javax.swing.JLabel artistas;
        if (idCancion == null) {
            portada = new javax.swing.JLabel(new ImageIcon(Utilities.transformarLink("http://localhost/hyper/wp-content/uploads/2022/03/playlist.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            nombre = new javax.swing.JLabel("Ninguna cancion");
            artistas = new javax.swing.JLabel("Ningun artista");
        } else {
            Statement sentencia;
            String[] artistasArray = new String[5];
            int contador = 0;
            String nombreCancion = null;
            String picture = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                    sentencia = conexion.createStatement();
                    String sql = "SELECT song.name, song.picture, song.url "
                            + "FROM song "
                            + "WHERE song.song_id = '" + idCancion + "'";
                    ResultSet resul = sentencia.executeQuery(sql);
                    if (resul.next()) {
                        nombreCancion = resul.getString("name");
                        picture = resul.getString("picture");
                        linkCancion = resul.getString("url");
                    }
                    sql = "SELECT artist.name "
                            + "FROM artist, registro_song "
                            + "WHERE artist.artist_id = registro_song.artist_id AND registro_song.song_id = '" + idCancion + "'";
                    resul = sentencia.executeQuery(sql);
                    while (resul.next()) {
                        artistasArray[contador] = resul.getString("name");
                        contador++;
                    }
                    resul.close();
                    sentencia.close();
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SongDisplay.class.getName()).log(Level.SEVERE, null, ex);
            }
            String artistasFinal = artistasArray[0];
            for (int i = 1; i < artistasArray.length - 1; i++) {
                if (artistasArray[i] != null) {
                    artistasFinal += ", " + artistasArray[i];
                }
            }
            portada = new javax.swing.JLabel(new ImageIcon(Utilities.transformarLink(picture).getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            nombre = new javax.swing.JLabel(nombreCancion);
            artistas = new javax.swing.JLabel(artistasFinal);
        }
        nombre.setForeground(CReturner.getTexto());
        nombre.setFont(coolvetica.deriveFont(16f));
        artistas.setForeground(CReturner.getTexto());
        artistas.setFont(coolvetica.deriveFont(12f));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        add(portada, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        gridBagConstraints.weighty = 0.1;
        add(nombre, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weighty = 0.1;
        add(artistas, gridBagConstraints);
    }

    public String getLinkCancion() {
        return linkCancion;
    }
}
