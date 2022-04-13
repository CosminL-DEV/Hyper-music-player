package components;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import themeManagement.ColorReturner;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 08-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class ReviewPlaylist extends JPanel {

    ColorReturner CReturner = new ColorReturner();
    private String idPlaylist;

    public ReviewPlaylist(String idPlaylist, String picture, String nombre, String creador) {
        setLayout(new java.awt.GridBagLayout());
        this.idPlaylist = idPlaylist;
        setOpaque(false);

        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        if (picture == null) {
            picture = "http://localhost/hyper/wp-content/uploads/2022/03/playlist.png";
        }
        ImageIcon img = new ImageIcon(Utilities.redondearImagen(Utilities.transformarLink(picture), 35, CReturner));
        JLabel imagen = new javax.swing.JLabel(new ImageIcon((img.getImage().getScaledInstance(165, 165, Image.SCALE_SMOOTH))));
        add(imagen, gridBagConstraints);

        Font coolvetica = Utilities.cargarCoolvetica();

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        if (nombre.length() > 23) {
            nombre = nombre.substring(0, 21) + "...";
        }
        JLabel titulo = new javax.swing.JLabel(nombre);
        titulo.setForeground(CReturner.getTexto());
        titulo.setFont(coolvetica.deriveFont(15f));
        add(titulo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        JLabel owner = new javax.swing.JLabel("De " + creador);
        owner.setForeground(CReturner.getTexto2());
        owner.setFont(coolvetica.deriveFont(12f));
        if (creador!=null)
            add(owner, gridBagConstraints);
    }
    
    public String getId(){
        return idPlaylist;
    }
}
