package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import themeManagement.ColorReturner;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 01-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class ItemPlaylist extends JPanel {

    private final ImgCircleConverter convertidor = new ImgCircleConverter();
    private javax.swing.JLabel principal;
    private String idPlaylist;

    public ItemPlaylist(String idPlaylist, String picture, String name, ColorReturner CReturner) {
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        setOpaque(false);
        this.idPlaylist = idPlaylist;
        setPreferredSize(new Dimension(200, 60));
        if (picture == null) {
            picture = "http://localhost/hyper/wp-content/uploads/2022/03/playlist.png";
        }
        ImageIcon img = new ImageIcon(convertidor.convertirImagen(Utilities.transformarLink(picture)));
        principal = new javax.swing.JLabel(new ImageIcon((img.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH))));
        principal.setText("  " + name);
        principal.setForeground(CReturner.getAbsoluto());
        Font coolvetica = Utilities.cargarCoolvetica();
        principal.setFont(coolvetica.deriveFont(15f));
        if (name.length() > 17) {
            principal.setText("  " + name.substring(0, 18) + "...");
        }
        add(principal);
    }
    
    public void setColor(Color color){
        principal.setForeground(color);
    }
    
    public String getId(){
        return idPlaylist;
    }
}
