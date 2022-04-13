package profiles;

import components.ImgCircleConverter;
import components.ReviewPlaylist;
import components.ScrollBar;
import components.TopBar;
import components.Utilities;
import details.Album;
import details.Playlist;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import themeManagement.ColorReturner;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 13-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class Artista extends JPanel {

    private JPanel listaPlaylist;
    private JPanel interfazPrinc;
    private JPanel content;
    private JScrollPane scrollPane;
    private JPanel main;
    private JPanel botBar;
    private JPanel topBar;
    private JLabel home;
    private String miUsername;
    private String idArtista;
    private final ImgCircleConverter convertidor = new ImgCircleConverter();
    private final ColorReturner CReturner = new ColorReturner();
    private java.awt.GridBagConstraints gridBagConstraints;
    private final Font coolvetica = Utilities.cargarCoolvetica();
    private JLabel titulo;
    private JLabel portada;
    private Popup popup;
    private File fotoDePerfil;

    public Artista(String idArtista, JPanel listaPlaylist, JPanel interfazPrinc, JPanel botBar, JScrollPane scrollPane, JPanel main, JPanel topBar, JLabel home) {
        this.idArtista = idArtista;
        this.content = this;
        this.listaPlaylist = listaPlaylist;
        this.interfazPrinc = interfazPrinc;
        this.botBar = botBar;
        this.topBar = topBar;
        this.scrollPane = scrollPane;
        this.main = main;
        this.home = home;
        setOpaque(true);
        Preferences pref = Preferences.userRoot().node("Rememberme");
        miUsername = pref.get("ActualUser", "");
        setBackground(CReturner.getBackground());
        setLayout(new java.awt.GridBagLayout());
        addLateralIzq();
        addLateralDer();
        addPreviewUsername();
        addAlbums();
        addCanciones();
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

    private void addPreviewUsername() {
        JPanel conjunto = new javax.swing.JPanel();
        conjunto.setMaximumSize(new Dimension(3000, 225));
        conjunto.setOpaque(false);
        conjunto.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        portada = new javax.swing.JLabel();
        conjunto.add(portada);

        JPanel details = new javax.swing.JPanel();
        details.setOpaque(false);
        details.setLayout(new javax.swing.BoxLayout(details, javax.swing.BoxLayout.PAGE_AXIS));

        JPanel help1 = new javax.swing.JPanel();
        help1.setOpaque(false);
        help1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        JLabel privacity = new javax.swing.JLabel("ARTISTA");
        privacity.setForeground(CReturner.getTexto());
        privacity.setFont(coolvetica.deriveFont(14f));
        help1.add(privacity);
        details.add(help1);

        JPanel help2 = new javax.swing.JPanel();
        help2.setOpaque(false);
        help2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        titulo = new javax.swing.JLabel();
        titulo.setForeground(CReturner.getTexto());
        titulo.setFont(coolvetica.deriveFont(75f));
        help2.add(titulo);
        details.add(help2);

        JPanel help3 = new javax.swing.JPanel();
        help3.setOpaque(false);
        help3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        JLabel num = new javax.swing.JLabel();
        num.setForeground(CReturner.getTexto());
        num.setFont(coolvetica.deriveFont(15f));
        help3.add(num);
        details.add(help3);

        conjunto.add(details);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 30, 0);
        add(conjunto, gridBagConstraints);

        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            String sql = "SELECT artist.username, artist.name, artist.profile_pic, COUNT(registro_song.artist_id) AS total "
                    + "FROM users, artist, registro_song "
                    + "WHERE artist.artist_id = '" + idArtista + "' AND artist.username = users.username AND registro_song.artist_id=artist.artist_id";
            ResultSet resul = sentencia.executeQuery(sql);
            if (resul.next()) {
                titulo.setText(resul.getString("name"));
                String picture = resul.getString("profile_pic");
                if (picture == null) {
                    picture = "http://localhost/hyper/wp-content/uploads/2022/04/user.png";
                }
                ImageIcon img = new ImageIcon(convertidor.convertirImagen(Utilities.transformarLink(picture)));
                portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(225, 225, Image.SCALE_SMOOTH))));
                num.setText(resul.getString("total") + " canciones");
                if (resul.getString("username").equalsIgnoreCase(miUsername)) {
                    portada.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            if (popup != null) {
                                popup.hide();
                            }
                            JLabel text = new JLabel("Click aqui para seleccionar la nueva imagen");
                            text.setBackground(CReturner.getBackground());
                            text.setOpaque(true);
                            text.setForeground(CReturner.getTexto());
                            text.setFont(coolvetica.deriveFont(14f));
                            popup = PopupFactory.getSharedInstance().getPopup(evt.getComponent(), text, evt.getXOnScreen(), evt.getYOnScreen());
                            popup.show();
                        }

                        @Override
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            popup.hide();
                        }

                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            JFileChooser inputPic = new JFileChooser();
                            FileFilter imageFilter = new FileNameExtensionFilter(
                                    "Image files", ImageIO.getReaderFileSuffixes());
                            inputPic.setFileFilter(imageFilter);
                            int returnVal = inputPic.showOpenDialog(Artista.this);
                            if (returnVal == JFileChooser.APPROVE_OPTION) {
                                fotoDePerfil = inputPic.getSelectedFile();
                                Statement sentencia;
                                String nuevoLink = uploadImage();
                                try {
                                    Class.forName("com.mysql.cj.jdbc.Driver");
                                    Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
                                    sentencia = conexion.createStatement();
                                    String sql = "UPDATE artist "
                                            + "SET artist.profile_pic = '" + nuevoLink + "' "
                                            + "WHERE artist.artist_id = '" + idArtista + "'";
                                    sentencia.executeUpdate(sql);
                                    sql = "UPDATE users "
                                            + "SET users.profile_pic = '" + nuevoLink + "' "
                                            + "WHERE users.username = '" + miUsername + "'";
                                    sentencia.executeUpdate(sql);
                                    sentencia.close();
                                    conexion.close();
                                } catch (SQLException | ClassNotFoundException ex) {
                                    Logger.getLogger(Perfil.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                ImageIcon img = new ImageIcon(convertidor.convertirImagen(Utilities.transformarLink(nuevoLink)));
                                portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(225, 225, Image.SCALE_SMOOTH))));
                                cargarNuevoPanel();
                            }
                        }
                    });
                }
            }

            resul.close();

            sentencia.close();

            conexion.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Artista.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addAlbums() {
        JPanel deHyper = new javax.swing.JPanel();
        JPanel texto1 = new javax.swing.JPanel();
        JLabel titulo1 = new javax.swing.JLabel();
        JPanel listas1 = new javax.swing.JPanel();

        deHyper.setBackground(CReturner.getBackground());
        deHyper.setLayout(new java.awt.GridBagLayout());

        texto1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 5));
        texto1.setBackground(CReturner.getBackground());
        titulo1.setText("ALBUMS");
        titulo1.setFont(coolvetica.deriveFont(25f));
        titulo1.setForeground(CReturner.getTexto());
        texto1.add(titulo1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        deHyper.add(texto1, gridBagConstraints);

        listas1.setLayout(new java.awt.GridLayout(0, 5, 0, 10));
        listas1.setBackground(CReturner.getBackground());

        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            String sql = "SELECT album.album_id, album.picture, album.name AS nombre "
                    + "FROM album, artist "
                    + "WHERE album.artist_id = artist.artist_id AND artist.artist_id = '" + idArtista + "' "
                    + "ORDER BY album.fecha DESC";
            ResultSet resul = sentencia.executeQuery(sql);
            while (resul.next()) {
                ReviewPlaylist elemento = new ReviewPlaylist(resul.getString("album_id"), resul.getString("picture"), resul.getString("nombre"), null);
                elemento.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        content = new Album(elemento.getId(), listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar);
                        cargarNuevoPanel();
                        interfazPrinc.revalidate();
                        interfazPrinc.repaint();
                    }
                });
                listas1.add(elemento);
            }
            resul.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Perfil.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.weightx = 0.1;
        deHyper.add(listas1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 60, 0);
        add(deHyper, gridBagConstraints);
    }

    private void addCanciones() {
        JPanel deHyper = new javax.swing.JPanel();
        JPanel texto1 = new javax.swing.JPanel();
        JLabel titulo1 = new javax.swing.JLabel();

        deHyper.setBackground(CReturner.getBackground());
        deHyper.setLayout(new java.awt.GridBagLayout());

        texto1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 5));
        texto1.setBackground(CReturner.getBackground());
        titulo1.setText("TODAS LAS CANCIONES");
        titulo1.setFont(coolvetica.deriveFont(25f));
        titulo1.setForeground(CReturner.getTexto());
        texto1.add(titulo1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        deHyper.add(texto1, gridBagConstraints);
        
        JPanel resultados = new JPanel();
        resultados.setBackground(CReturner.getBackground());
        ImageIcon playIcon = new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "play.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        resultados.setLayout(new java.awt.GridLayout(0, 1, 0, 5));
        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            String sql = "SELECT song.song_id, song.name "
                    + "FROM song, registro_song "
                    + "WHERE song.song_id = registro_song.song_id AND registro_song.artist_id = '"+idArtista+"'";
            ResultSet resul = sentencia.executeQuery(sql);
            System.out.println(idArtista);
            while (resul.next()) {
                JLabel elemento = new JLabel(resul.getString("name"));
                elemento.setForeground(CReturner.getTexto());
                elemento.setIcon(playIcon);
                elemento.setFont(coolvetica.deriveFont(16f));
                resultados.add(elemento);
                // Add listener de canciones
                
            }
            resul.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Artista.class.getName()).log(Level.SEVERE, null, ex);
        }
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weighty = 0.1;
        deHyper.add(resultados, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        add(deHyper, gridBagConstraints);
    }

    private void cargarNuevoPanel() {
        main.removeAll();
        home.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "home.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        topBar = new TopBar();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        main.add(topBar, gridBagConstraints);

        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBar(new ScrollBar());
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(content);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        main.add(scrollPane, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        main.add(botBar, gridBagConstraints);
    }

    private String uploadImage() {
        String linkImagen = null;
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL("http://localhost/hyper/" + "/xmlrpc.php"));
            XmlRpcClient rpcClient = new XmlRpcClient();
            rpcClient.setConfig(config);

            BufferedImage bImage = ImageIO.read(fotoDePerfil);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            String formato = fotoDePerfil.getName().substring(fotoDePerfil.getName().indexOf(".") + 1);
            ImageIO.write(bImage, formato, bos);
            byte[] outputByteArray = bos.toByteArray();
            String base64EncodedString = Base64.getEncoder().encodeToString(outputByteArray);

            Map content = new Hashtable();
            content.put("name", fotoDePerfil.getName());
            content.put("type", "image/" + formato);
            content.put("bits", base64EncodedString);
            content.put("overwrite", false);
            Object result = rpcClient.execute("wp.uploadFile", new Object[]{
                0,
                "root",
                "root",
                content
            });

            int start = result.toString().indexOf("thumbnail=") + 10;
            int end = result.toString().indexOf(",", start);
            linkImagen = result.toString().substring(start, end);
            // Esta mal configurado el encoding que le llega a Wordpress y he 
            // tenido que hacer este feo apaño para por lo menos poder usarlo.
            Path source = Paths.get(fotoDePerfil.getPath());
            Path target = Paths.get("E:/xampp/htdocs/" + linkImagen.substring(17));
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (XmlRpcException | IOException e) {
            System.out.println("Imagen no subida.");
        }
        return linkImagen;
    }
}
