package dialogs;

import appManagement.Utilities;
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
import java.util.Base64;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FileUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
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
public class UploadSong extends javax.swing.JDialog {

    private java.awt.GridBagConstraints gridBagConstraints;
    private ColorReturner CReturner = new ColorReturner();
    private final Font coolvetica = Utilities.cargarCoolvetica();
    private JPanel contenedor;
    private javax.swing.JTextField inputNombre;
    private components.Combobox selectGenero;
    private File archivoCancion;
    private boolean cancionSubida = false;
    private String linkCancion = null;
    private File archivoFoto;
    private boolean fotoSubida = false;
    private String linkPicture = null;
    private String username;
    private Popup popup;

    public UploadSong() {
        setBackground(CReturner.getBackground());
        setUndecorated(true);
        setResizable(false);
        Preferences pref = Preferences.userRoot().node("Rememberme");
        username = pref.get("ActualUser", "");
        contenedor = new JPanel();
        contenedor.setBackground(CReturner.getBackground());
        contenedor.setBorder(javax.swing.BorderFactory.createLineBorder(CReturner.getTexto(), 2));
        contenedor.setLayout(new java.awt.GridBagLayout());
        addPrimeraLinea();
        addDetalles();
        addBotones();
        getContentPane().add(contenedor, java.awt.BorderLayout.CENTER);
    }

    private void addPrimeraLinea() {
        javax.swing.JPanel linea1 = new javax.swing.JPanel();
        linea1.setOpaque(false);
        linea1.setLayout(new java.awt.GridBagLayout());
        javax.swing.JLabel texto = new javax.swing.JLabel();
        javax.swing.JLabel close = new javax.swing.JLabel();

        texto.setText("Subir Canción");
        texto.setForeground(CReturner.getTexto());
        texto.setFont(coolvetica.deriveFont(22f));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        linea1.add(texto, gridBagConstraints);

        close.setText("X");
        close.setForeground(CReturner.getTexto2());
        close.setFont(coolvetica.deriveFont(22f));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        linea1.add(close, gridBagConstraints);
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                close.setForeground(CReturner.getClose());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                close.setForeground(CReturner.getTexto2());
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        contenedor.add(linea1, gridBagConstraints);
    }

    private void addDetalles() {
        javax.swing.JPanel datos = new javax.swing.JPanel();
        datos.setOpaque(false);
        datos.setLayout(new java.awt.GridBagLayout());

        javax.swing.JLabel portada = new javax.swing.JLabel();
        linkPicture = "http://localhost/hyper/wp-content/uploads/2022/03/playlist.png";
        ImageIcon img = new ImageIcon(Utilities.transformarLink(linkPicture));
        portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(175, 175, Image.SCALE_SMOOTH))));
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
                int returnVal = inputPic.showOpenDialog(UploadSong.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    archivoFoto = inputPic.getSelectedFile();
                    ImageIcon img = new ImageIcon(archivoFoto.getPath());
                    portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(175, 175, Image.SCALE_SMOOTH))));
                    fotoSubida = true;
                } else {
                    fotoSubida = false;
                }
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        datos.add(portada, gridBagConstraints);

        inputNombre = new javax.swing.JTextField();
        JPanel contNombre = new javax.swing.JPanel();
        contNombre.setOpaque(false);
        contNombre.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CReturner.getTexto()));
        inputNombre.setText("Nombre canción");
        inputNombre.setForeground(CReturner.getTexto());
        inputNombre.setBackground(CReturner.getBackground());
        inputNombre.setBorder(null);
        inputNombre.setFont(coolvetica.deriveFont(15f));
        inputNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (inputNombre.getText().length() > 25) {
                    inputNombre.setText(inputNombre.getText().substring(0, 25));
                }
                if (inputNombre.getText().length() > 18) {
                    inputNombre.setFont(coolvetica.deriveFont(11f));
                } else {
                    inputNombre.setFont(coolvetica.deriveFont(15f));
                }
            }
        });
        contNombre.setLayout(new java.awt.GridBagLayout());
        inputNombre.setMaximumSize(new Dimension(150, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        contNombre.add(inputNombre, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        datos.add(contNombre, gridBagConstraints);

        selectGenero = new components.Combobox();
        selectGenero.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[]{"trap", "reggaeton", "rap", "techno", "house"}
        ));
        selectGenero.setLabeText("");
        selectGenero.setSelectedItem(CReturner.getTemaActual());
        selectGenero.setBackground(CReturner.getTexto2());
        selectGenero.setForeground(CReturner.getTexto());
        selectGenero.setBorder(null);
        selectGenero.setPreferredSize(new Dimension(100, 30));
        selectGenero.setFont(coolvetica.deriveFont(15f));
        selectGenero.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        datos.add(selectGenero, gridBagConstraints);

        javax.swing.JButton subirCancion = new javax.swing.JButton();
        subirCancion.setText("Seleccionar Canción");
        subirCancion.setFont(coolvetica.deriveFont(14f));
        subirCancion.setForeground(CReturner.getBackground());
        subirCancion.setBackground(CReturner.getTexto());
        subirCancion.setBorder(null);
        subirCancion.addActionListener((java.awt.event.ActionEvent evt) -> {
            JFileChooser inputSong = new JFileChooser();
            FileFilter songFilter = new FileNameExtensionFilter("wav files", "wav");
            inputSong.setFileFilter(songFilter);
            int returnVal = inputSong.showOpenDialog(UploadSong.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                archivoCancion = inputSong.getSelectedFile();
                cancionSubida = true;
            } else {
                cancionSubida = false;
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        datos.add(subirCancion, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        contenedor.add(datos, gridBagConstraints);
    }

    private void addBotones() {
        javax.swing.JPanel botones = new javax.swing.JPanel();
        botones.setOpaque(false);
        botones.setLayout(new java.awt.GridBagLayout());
        javax.swing.JButton subir = new javax.swing.JButton();

        subir.setText("SUBIR");
        subir.setFont(coolvetica.deriveFont(16f));
        subir.setForeground(CReturner.getBackground());
        subir.setBackground(CReturner.getTexto());
        subir.setBorder(null);
        subir.addActionListener((java.awt.event.ActionEvent evt) -> {
            if (comprobarDatos()) {
                Statement sentencia;
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                        sentencia = conexion.createStatement();
                        String idArtista = null;
                        String sql = "SELECT artist.artist_id "
                                + "FROM artist "
                                + "WHERE artist.username = '" + username + "'";
                        ResultSet resul = sentencia.executeQuery(sql);
                        if (resul.next()) {
                            idArtista = resul.getString("artist_id");
                        }
                        linkPicture = uploadImage();
                        linkCancion = uploadSong();
                        sql = "INSERT INTO song(name,genre,url,picture) "
                                + "VALUES('" + inputNombre.getText() + "','" + selectGenero.getSelectedItem().toString() + "',"
                                + "'" + linkCancion + "','" + linkPicture + "')";
                        sentencia.executeUpdate(sql);
                        String songId = null;
                        sql = "SELECT song.song_id "
                                + "FROM song "
                                + "WHERE song.name = '" + inputNombre.getText() + "' AND song.url = '" + linkCancion + "'";
                        resul = sentencia.executeQuery(sql);
                        if (resul.next()) {
                            songId = resul.getString("song_id");
                        }
                        sql = "INSERT INTO registro_song(song_id, artist_id) "
                                + "VALUES ('" + songId + "','" + idArtista + "')";
                        sentencia.executeUpdate(sql);
                        sql = "INSERT INTO registro_album(album_id, song_id) "
                                + "VALUES ('35','" + songId + "')";
                        sentencia.executeUpdate(sql);
                        sentencia.close();
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(EditDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                setCursor(null);
                dispose();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        botones.add(subir, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        contenedor.add(botones, gridBagConstraints);
    }

    private boolean comprobarDatos() {
        boolean valido = true;
        if (!fotoSubida) {
            valido = false;
            JOptionPane.showMessageDialog(new JFrame(), "Tienes que seleccionar una foto", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (!cancionSubida) {
            valido = false;
            JOptionPane.showMessageDialog(new JFrame(), "Tienes que subir la canción", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (inputNombre.getText().isBlank()) {
            valido = false;
            JOptionPane.showMessageDialog(new JFrame(), "Elige un nombre valido para tu canción", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return valido;
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

            Map contenido = new HashMap();
            contenido.put("name", archivoFoto.getName());
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
            // tenido que hacer este feo apaño para por lo menos poder usarlo.
            Path source = Paths.get(archivoFoto.getPath());
            Path target = Paths.get(CReturner.getRutaXampp() + linkImagen.substring(17));
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (XmlRpcException | IOException e) {
            System.out.println("Imagen no subida.");
        }
        return linkImagen;
    }

    private String uploadSong() {
        String songLink = null;
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL("http://localhost/hyper/" + "/xmlrpc.php"));
            XmlRpcClient rpcClient = new XmlRpcClient();
            rpcClient.setConfig(config);
            byte[] bytes = FileUtils.readFileToByteArray(archivoCancion);
            String base64EncodedString = Base64.getEncoder().encodeToString(bytes);
            Map contenido = new HashMap();
            contenido.put("name", archivoCancion.getName());
            contenido.put("type", "audio/wav");
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
            songLink = result.toString().substring(start, end);
            // Esta mal configurado el encoding que le llega a Wordpress y he 
            // tenido que hacer este feo apaño para por lo menos poder usarlo.
            Path source = Paths.get(archivoCancion.getPath());
            Path target = Paths.get(CReturner.getRutaXampp() + songLink.substring(17));
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (XmlRpcException | IOException e) {
            System.out.println("Canción no subida.");
        }
        return songLink;
    }

}
