package album;

import appManagement.ColorReturner;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class AlbumFeed {

    private final ColorReturner CReturner = new ColorReturner();
    private ImageIcon playIcon;
    private String portadaLink;
    private String tituloSong;
    private String idCancion;

    public AlbumFeed(String portadaLink, String tituloSong, String idCancion) {
        this.playIcon = new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "play.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        this.portadaLink = portadaLink;
        this.tituloSong = tituloSong;
        this.idCancion = idCancion;
    }

    public ImageIcon getPlayIcon() {
        return playIcon;
    }

    public void setPlayIcon(ImageIcon playIcon) {
        this.playIcon = playIcon;
    }

    public String getPortadaLink() {
        return portadaLink;
    }

    public void setPortadaLink(String portadaLink) {
        this.portadaLink = portadaLink;
    }

    public String getTituloSong() {
        return tituloSong;
    }

    public void setTituloSong(String tituloSong) {
        this.tituloSong = tituloSong;
    }

    public String getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(String idCancion) {
        this.idCancion = idCancion;
    }

}
