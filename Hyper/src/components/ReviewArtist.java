package components;

import appManagement.Utilities;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
public class ReviewArtist extends JPanel {

    private ColorReturner CReturner = new ColorReturner();
    private String idArtist;
    private final ImgCircleConverter convertidor = new ImgCircleConverter();

    public ReviewArtist(String idArtist, String picture, String nombre) {
        this.idArtist = idArtist;
        java.awt.GridBagConstraints gridBagConstraints;
        setLayout(new java.awt.GridBagLayout());
        setOpaque(false);
        Font coolvetica = Utilities.cargarCoolvetica();

        if (picture == null) {
            picture = "http://localhost/hyper/wp-content/uploads/2022/04/user.png";
        }
        ImageIcon img = new ImageIcon(convertidor.convertirImagen(Utilities.transformarLink(picture)));
        JLabel imagen = new javax.swing.JLabel(new ImageIcon((img.getImage().getScaledInstance(165, 165, Image.SCALE_SMOOTH))));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(imagen, gridBagConstraints);

        JLabel titulo = new javax.swing.JLabel(nombre);
        titulo.setForeground(CReturner.getTexto());
        titulo.setFont(coolvetica.deriveFont(15f));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        add(titulo, gridBagConstraints);
    }

    public String getId() {
        return idArtist;
    }
}
