package profiles;

import components.ImgCircleConverter;
import components.ReviewPlaylist;
import components.ScrollBar;
import components.TopBar;
import appManagement.Utilities;
import playlist.Playlist;
import java.awt.Cursor;
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
import songManager.BotBar;
import appManagement.ColorReturner;
import java.util.HashMap;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class Perfil extends JPanel {

    private JPanel listaPlaylist;
    private JPanel interfazPrinc;
    private JPanel content;
    private JScrollPane scrollPane;
    private JPanel main;
    private BotBar botBar;
    private TopBar topBar;
    private JLabel home;
    private String miUsername;
    private String username;
    private final ImgCircleConverter convertidor = new ImgCircleConverter();
    private final ColorReturner CReturner = new ColorReturner();
    private java.awt.GridBagConstraints gridBagConstraints;
    private final Font coolvetica = Utilities.cargarCoolvetica();
    private JLabel titulo;
    private JLabel portada;
    private Popup popup;
    private File fotoDePerfil;
    private JFrame window;

    public Perfil(String username, JPanel listaPlaylist, JPanel interfazPrinc, BotBar botBar, JScrollPane scrollPane,
            JPanel main, TopBar topBar, JLabel home, JFrame window) {
        this.window = window;
        this.username = username;
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
        addListas();
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
        JLabel privacity = new javax.swing.JLabel("PERFIL");
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
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT users.username, users.profile_pic, COUNT(playlist.user) AS total "
                        + "FROM users, playlist "
                        + "WHERE users.username = '" + username + "' AND playlist.privacity = 'publica' AND playlist.user = '" + username + "'";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    if (resul.next()) {
                        titulo.setText(resul.getString("username"));
                        String picture = resul.getString("profile_pic");
                        if (picture == null) {
                            picture = "http://localhost/hyper/wp-content/uploads/2022/04/user.png";
                        }
                        ImageIcon img = new ImageIcon(convertidor.convertirImagen(Utilities.transformarLink(picture)));
                        portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(225, 225, Image.SCALE_SMOOTH))));
                        num.setText(resul.getString("total") + " listas p??blicas");
                        if (username.equalsIgnoreCase(miUsername)) {
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
                                    int returnVal = inputPic.showOpenDialog(Perfil.this);
                                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                                        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                        fotoDePerfil = inputPic.getSelectedFile();
                                        Statement sentencia;
                                        String nuevoLink = uploadImage();
                                        try {
                                            Class.forName("com.mysql.cj.jdbc.Driver");
                                            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                                                sentencia = conexion.createStatement();
                                                String sql = "UPDATE users "
                                                        + "SET users.profile_pic = '" + nuevoLink + "' "
                                                        + "WHERE users.username = '" + username + "'";
                                                sentencia.executeUpdate(sql);
                                                sentencia.close();
                                            }
                                        } catch (SQLException | ClassNotFoundException ex) {
                                            Logger.getLogger(Perfil.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        ImageIcon img = new ImageIcon(convertidor.convertirImagen(Utilities.transformarLink(nuevoLink)));
                                        portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(225, 225, Image.SCALE_SMOOTH))));
                                        cargarNuevoPanel();
                                        setCursor(null);
                                    }
                                }
                            });
                        }
                    }
                }

                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Perfil.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addListas() {
        JPanel deHyper = new javax.swing.JPanel();
        JPanel texto1 = new javax.swing.JPanel();
        JLabel titulo1 = new javax.swing.JLabel();
        JPanel listas1 = new javax.swing.JPanel();

        deHyper.setBackground(CReturner.getBackground());
        deHyper.setLayout(new java.awt.GridBagLayout());

        texto1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 5));
        texto1.setBackground(CReturner.getBackground());
        titulo1.setText("Listas p??blicas");
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
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT playlist_id, picture, name, user "
                        + "FROM playlist "
                        + "WHERE playlist.user = '" + username + "'";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    while (resul.next()) {
                        ReviewPlaylist elemento = new ReviewPlaylist(resul.getString("playlist_id"), resul.getString("picture"), resul.getString("name"), resul.getString("user"));
                        elemento.addMouseListener(new java.awt.event.MouseAdapter() {
                            @Override
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                content = new Playlist(elemento.getId(), listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar, home, window);
                                cargarNuevoPanel();
                                interfazPrinc.revalidate();
                                interfazPrinc.repaint();
                                setCursor(null);
                            }
                        });
                        listas1.add(elemento);
                    }
                }
                sentencia.close();
            }
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
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        add(deHyper, gridBagConstraints);
    }

    private void cargarNuevoPanel() {
        main.removeAll();
        home.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "home.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        topBar = new TopBar(listaPlaylist, interfazPrinc, botBar, scrollPane, main, home, window);
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

            Map contenido = new HashMap();
            contenido.put("name", fotoDePerfil.getName());
            contenido.put("type", "image/" + formato);
            contenido.put("bits", base64EncodedString);
            contenido.put("overwrite", false);
            Object result = rpcClient.execute("wp.uploadFile", new Object[]{
                0,
                "root",
                "root",
                contenido
            });

            int start = result.toString().indexOf("thumbnail=") + 10;
            int end = result.toString().indexOf(",", start);
            linkImagen = result.toString().substring(start, end);
            // Esta mal configurado el encoding que le llega a Wordpress y he 
            // tenido que hacer este feo apa??o para por lo menos poder usarlo.
            Path source = Paths.get(fotoDePerfil.getPath());
            Path target = Paths.get(CReturner.getRutaXampp() + linkImagen.substring(17));
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (XmlRpcException | IOException e) {
            System.out.println("Imagen no subida.");
        }
        return linkImagen;
    }
}
