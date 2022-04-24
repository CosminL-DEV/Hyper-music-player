package components;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class ElementoListaPopup extends javax.swing.JMenuItem {

    private String idPlaylist;

    public ElementoListaPopup() {
        super();
    }

    public void setIdPlaylist(String idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public String getIdPlaylist() {
        return idPlaylist;
    }

}
