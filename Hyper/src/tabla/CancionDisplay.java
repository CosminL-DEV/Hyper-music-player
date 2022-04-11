package tabla;

import components.Utilities;
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
public class CancionDisplay extends JPanel {

    public CancionDisplay(ImageIcon playIcon, String portadaLink, String tituloSong, String idCancion) {
        java.awt.GridBagConstraints gridBagConstraints;
        Font coolvetica = Utilities.cargarCoolvetica();
        ColorReturner CReturner = new ColorReturner();
        setOpaque(false);
        setBackground(CReturner.getSelected());

        JLabel play = new javax.swing.JLabel();
        JLabel portada = new javax.swing.JLabel();
        JLabel titulo = new javax.swing.JLabel();
        JLabel artistas = new javax.swing.JLabel();
        setLayout(new java.awt.GridBagLayout());

        play.setIcon(playIcon);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(play, gridBagConstraints);

        portada.setIcon(new ImageIcon(Utilities.transformarLink(portadaLink).getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 10);
        add(portada, gridBagConstraints);

        titulo.setText(tituloSong);
        titulo.setFont(coolvetica.deriveFont(16f));
        titulo.setForeground(CReturner.getTexto());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(titulo, gridBagConstraints);

        Statement sentencia;
        String[] artistasArray = new String[5];
        int contador = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            String sql = "SELECT artist.name "
                    + "FROM artist, registro_song "
                    + "WHERE artist.artist_id = registro_song.artist_id AND registro_song.song_id = '" + idCancion + "'";
            ResultSet resul = sentencia.executeQuery(sql);
            while (resul.next()) {
                artistasArray[contador] = resul.getString("name");
                contador++;
            }
            resul.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CancionDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
        String artistasFinal = artistasArray[0];
        for (int i = 1; i < artistasArray.length - 1; i++) {
            if (artistasArray[i] != null) {
                artistasFinal += ", " + artistasArray[i];
            }
        }
        artistas.setText(artistasFinal);
        artistas.setFont(coolvetica.deriveFont(13f));
        artistas.setForeground(CReturner.getTexto());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(artistas, gridBagConstraints);
    }

}
