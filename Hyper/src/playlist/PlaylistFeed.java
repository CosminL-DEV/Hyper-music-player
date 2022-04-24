package playlist;

import java.awt.Image;
import java.util.Date;
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
public class PlaylistFeed {

    private final ColorReturner CReturner = new ColorReturner();
    private ImageIcon playIcon;
    private String portadaLink;
    private String tituloSong;
    private String idCancion;
    private String nAlbum;
    private String userPicture;
    private String userAdded;
    private Date dateAdded;

    public PlaylistFeed(String portadaLink, String tituloSong, String idCancion, String nAlbum, String userPicture, String userAdded, Date dateAdded) {
        this.playIcon = new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "play.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        this.portadaLink = portadaLink;
        this.tituloSong = tituloSong;
        this.idCancion = idCancion;
        this.nAlbum = nAlbum;
        this.userPicture = userPicture;
        this.userAdded = userAdded;
        this.dateAdded = dateAdded;
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

    public String getnAlbum() {
        return nAlbum;
    }

    public void setnAlbum(String nAlbum) {
        this.nAlbum = nAlbum;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getUserAdded() {
        return userAdded;
    }

    public void setUserAdded(String userAdded) {
        this.userAdded = userAdded;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

}
