package components;

import details.Album;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FileUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import songManager.QueueManager;
import songManager.QueueManager.Cancion;
import themeManagement.ColorReturner;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 15-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class UploadAlbum extends JPanel {

    private java.awt.GridBagConstraints gridBagConstraints;
    private ColorReturner CReturner = new ColorReturner();
    private final Font coolvetica = Utilities.cargarCoolvetica();
    private JPanel listaPlaylist;
    private JPanel interfazPrinc;
    private JPanel content;
    private JScrollPane scrollPane;
    private JPanel main;
    private JPanel botBar;
    private JPanel topBar;
    private JLabel home;
    private JFrame window;
    private Popup popup;
    private String idArtista;
    private File archivoFoto;
    private boolean fotoSubida = false;
    private String linkPicture = null;
    private JTextField nombreAlbum;
    private JPanel addMore;
    private int contador = 3;
    private ArrayList<AdderSongInAlbum> canciones;
    private JPanel botones;
    private String albumId;

    public UploadAlbum(String idArtista, JPanel listaPlaylist, JPanel interfazPrinc, JPanel botBar, JScrollPane scrollPane, 
            JPanel main, JPanel topBar, JLabel home, JFrame window) {
        this.idArtista = idArtista;
        this.content = this;
        this.listaPlaylist = listaPlaylist;
        this.interfazPrinc = interfazPrinc;
        this.botBar = botBar;
        this.topBar = topBar;
        this.scrollPane = scrollPane;
        this.main = main;
        this.home = home;
        this.window = window;
        setBackground(CReturner.getBackground());
        setLayout(new java.awt.GridBagLayout());
        addLateralIzq();
        addLateralDer();
        addPrimeraLinea();
        addListaCanciones();
        addBoton();
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

    private void addPrimeraLinea() {
        JPanel conjunto = new javax.swing.JPanel();
        conjunto.setMaximumSize(new Dimension(3000, 225));
        conjunto.setOpaque(false);
        conjunto.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 0));

        JLabel portada = new javax.swing.JLabel();
        String pictureAlbum = "http://localhost/hyper/wp-content/uploads/2022/03/playlist.png";
        ImageIcon img = new ImageIcon(Utilities.redondearImagen(Utilities.transformarLink(pictureAlbum), 15, CReturner));
        portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(225, 225, Image.SCALE_SMOOTH))));
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
                int returnVal = inputPic.showOpenDialog(UploadAlbum.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    archivoFoto = inputPic.getSelectedFile();
                    BufferedImage img2 = null;
                    try {
                        img2 = ImageIO.read(archivoFoto);
                    } catch (IOException ex) {
                        Logger.getLogger(UploadAlbum.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ImageIcon img = new ImageIcon(Utilities.redondearImagen(img2, 15, CReturner));
                    portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(225, 225, Image.SCALE_SMOOTH))));
                    fotoSubida = true;
                } else {
                    fotoSubida = false;
                }
            }
        });
        conjunto.add(portada);

        nombreAlbum = new javax.swing.JTextField();
        JPanel contNombre = new javax.swing.JPanel();
        contNombre.setOpaque(false);
        contNombre.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CReturner.getTexto()));
        nombreAlbum.setText("Nombre album");
        nombreAlbum.setForeground(CReturner.getTexto());
        nombreAlbum.setBackground(CReturner.getBackground());
        nombreAlbum.setBorder(null);
        nombreAlbum.setFont(coolvetica.deriveFont(15f));
        nombreAlbum.setPreferredSize(new Dimension(150, 25));
        nombreAlbum.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (nombreAlbum.getText().length() > 20) {
                    nombreAlbum.setText(nombreAlbum.getText().substring(0, 20));
                }
            }
        });
        contNombre.setLayout(new java.awt.GridBagLayout());
        nombreAlbum.setMaximumSize(new Dimension(150, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        contNombre.add(nombreAlbum, gridBagConstraints);
        conjunto.add(contNombre);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        add(conjunto, gridBagConstraints);
    }

    private void addListaCanciones() {
        canciones = new ArrayList<>();
        addMore = new JPanel();
        addMore.setOpaque(false);
        JLabel addOther = new JLabel("+");
        addOther.setForeground(CReturner.getTexto());
        addOther.setFont(coolvetica.deriveFont(50f));
        addMore.add(addOther, java.awt.BorderLayout.CENTER);

        AdderSongInAlbum asia = new AdderSongInAlbum();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        add(asia, gridBagConstraints);
        canciones.add(asia);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = contador;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        add(addMore, gridBagConstraints);
        addOther.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                remove(addMore);
                AdderSongInAlbum asia = new AdderSongInAlbum();
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = contador;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
                gridBagConstraints.weightx = 0.1;
                gridBagConstraints.weighty = 0.1;
                gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
                add(asia, gridBagConstraints);
                canciones.add(asia);
                contador++;
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = contador;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
                gridBagConstraints.weightx = 0.1;
                gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
                add(addMore, gridBagConstraints);
                remove(botones);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = contador + 1;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
                gridBagConstraints.weightx = 0.1;
                gridBagConstraints.weighty = 0.1;
                gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
                add(botones, gridBagConstraints);
                revalidate();
                repaint();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addOther.setForeground(CReturner.getPrincipal());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addOther.setForeground(CReturner.getTexto());
            }
        });
    }

    private void addBoton() {
        botones = new javax.swing.JPanel();
        botones.setOpaque(false);
        botones.setLayout(new java.awt.GridBagLayout());
        javax.swing.JButton subir = new javax.swing.JButton();

        subir.setText("S U B I R");
        subir.setFont(coolvetica.deriveFont(24f));
        subir.setForeground(CReturner.getBackground());
        subir.setBackground(CReturner.getTexto());
        subir.setBorder(null);
        subir.setPreferredSize(new Dimension(300, 50));
        subir.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (comprobarDatos()) {
                    Statement sentencia;
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
                        sentencia = conexion.createStatement();
                        linkPicture = uploadImage();
                        String sql = "INSERT INTO album(name, artist_id, picture) "
                                + "VALUES ('" + nombreAlbum.getText() + "','" + idArtista + "','" + linkPicture + "')";
                        sentencia.executeUpdate(sql);
                        albumId = null;
                        sql = "SELECT album.album_id "
                                + "FROM album "
                                + "WHERE album.picture = '" + linkPicture + "'";
                        ResultSet resul = sentencia.executeQuery(sql);
                        if (resul.next()) {
                            albumId = resul.getString("album_id");
                        }
                        for (AdderSongInAlbum elem : canciones) {
                            String linkCancion = uploadSong(elem.getArchivoCancion());
                            sql = "INSERT INTO song(name,genre,url,picture) "
                                    + "VALUES ('" + elem.getInputNombre().getText() + "','" + elem.getGenre().getSelectedItem().toString() + "',"
                                    + "'" + linkCancion + "','" + linkPicture + "')";
                            sentencia.executeUpdate(sql);
                            String songId = null;
                            sql = "SELECT song.song_id "
                                    + "FROM song "
                                    + "WHERE song.url = '" + linkCancion + "'";
                            resul = sentencia.executeQuery(sql);
                            if (resul.next()) {
                                songId = resul.getString("song_id");
                            }
                            sql = "INSERT INTO registro_song "
                                    + "VALUES ('" + songId + "','" + idArtista + "')";
                            sentencia.executeUpdate(sql);
                            sql = "INSERT INTO registro_album "
                                    + "VALUES ('" + albumId + "','" + songId + "')";
                            sentencia.executeUpdate(sql);
                        }
                        sentencia.close();
                        conexion.close();
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(EditDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    setCursor(null);
                    content = new Album(albumId, listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar, home, window);
                    cargarNuevoPanel();
                    interfazPrinc.revalidate();
                    interfazPrinc.repaint();

                }
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        botones.add(subir, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        add(botones, gridBagConstraints);
    }

    private boolean comprobarDatos() {
        boolean valido = true;
        if (!fotoSubida) {
            valido = false;
            JOptionPane.showMessageDialog(new JFrame(), "Tienes que seleccionar una foto", "ERROR", JOptionPane.ERROR_MESSAGE);
            return valido;
        } else if (nombreAlbum.getText().isBlank()) {
            valido = false;
            JOptionPane.showMessageDialog(new JFrame(), "Selecciona un nombre valido para el album", "ERROR", JOptionPane.ERROR_MESSAGE);
            return valido;
        }
        for (AdderSongInAlbum elem : canciones) {
            if (elem.getInputNombre().getText().isBlank()) {
                valido = false;
                break;
            } else if (!elem.isCancionSeleccionada()) {
                valido = false;
                break;
            }
        }
        if (!valido) {
            JOptionPane.showMessageDialog(new JFrame(), "Revisa los datos de las canciones.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return valido;
    }

    private void cargarNuevoPanel() {
        main.removeAll();

        java.awt.GridBagConstraints gridBagConstraints;

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

            BufferedImage bImage = ImageIO.read(archivoFoto);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            String formato = archivoFoto.getName().substring(archivoFoto.getName().indexOf(".") + 1);
            ImageIO.write(bImage, formato, bos);
            byte[] outputByteArray = bos.toByteArray();
            String base64EncodedString = Base64.getEncoder().encodeToString(outputByteArray);

            Map content = new Hashtable();
            content.put("name", archivoFoto.getName());
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
            Path source = Paths.get(archivoFoto.getPath());
            Path target = Paths.get("E:/xampp/htdocs/" + linkImagen.substring(17));
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (XmlRpcException | IOException e) {
            System.out.println("Imagen no subida.");
        }
        return linkImagen;
    }

    private String uploadSong(File archivoCancion) {
        String songLink = null;
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL("http://localhost/hyper/" + "/xmlrpc.php"));
            XmlRpcClient rpcClient = new XmlRpcClient();
            rpcClient.setConfig(config);
            byte[] bytes = FileUtils.readFileToByteArray(archivoCancion);
            String base64EncodedString = Base64.getEncoder().encodeToString(bytes);
            Map content = new Hashtable();
            content.put("name", archivoCancion.getName());
            content.put("type", "audio/wav");
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
            songLink = result.toString().substring(start, end);
            // Esta mal configurado el encoding que le llega a Wordpress y he 
            // tenido que hacer este feo apaño para por lo menos poder usarlo.
            Path source = Paths.get(archivoCancion.getPath());
            Path target = Paths.get("E:/xampp/htdocs/" + songLink.substring(17));
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (XmlRpcException | IOException e) {
            System.out.println("Canción no subida.");
        }
        return songLink;
    }
}
