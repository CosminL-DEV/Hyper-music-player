package components;

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
import java.util.Hashtable;
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
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
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
public class CreatePlaylist extends javax.swing.JDialog {

    private java.awt.GridBagConstraints gridBagConstraints;
    private ColorReturner CReturner = new ColorReturner();
    private final Font coolvetica = Utilities.cargarCoolvetica();
    private JPanel contenedor;
    private javax.swing.JTextField inputNombre;
    private javax.swing.JRadioButton radioPublica;
    private javax.swing.JRadioButton radioPrivada;
    private File fotoDePerfil;
    private Popup popup;
    private boolean fotoSelected = false;
    private int guardado = 0;
    private String linkImagen = null;
    private String username;

    public CreatePlaylist() {
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

        texto.setText("Crear playlist");
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
        inputNombre = new javax.swing.JTextField();
        javax.swing.ButtonGroup grupoPrivacidad = new javax.swing.ButtonGroup();
        radioPublica = new javax.swing.JRadioButton();
        radioPrivada = new javax.swing.JRadioButton();
        datos.setLayout(new java.awt.GridBagLayout());

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
                int returnVal = inputPic.showOpenDialog(CreatePlaylist.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    fotoDePerfil = inputPic.getSelectedFile();
                    ImageIcon img = new ImageIcon(fotoDePerfil.getPath());
                    portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(175, 175, Image.SCALE_SMOOTH))));
                    fotoSelected = true;
                } else {
                    fotoSelected = false;
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

        JPanel contNombre = new javax.swing.JPanel();
        contNombre.setOpaque(false);
        contNombre.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CReturner.getTexto()));
        inputNombre.setText("Nombre playlist");
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
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        datos.add(contNombre, gridBagConstraints);

        grupoPrivacidad.add(radioPublica);
        radioPublica.setText("Publica");
        radioPublica.setBackground(CReturner.getBackground());
        radioPublica.setForeground(CReturner.getTexto());
        radioPublica.setFont(coolvetica.deriveFont(15f));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        datos.add(radioPublica, gridBagConstraints);

        grupoPrivacidad.add(radioPrivada);
        radioPrivada.setText("Privada");
        radioPrivada.setBackground(CReturner.getBackground());
        radioPrivada.setForeground(CReturner.getTexto());
        radioPrivada.setFont(coolvetica.deriveFont(15f));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        datos.add(radioPrivada, gridBagConstraints);
        String picture = "http://localhost/hyper/wp-content/uploads/2022/03/playlist.png";
        ImageIcon img = new ImageIcon(Utilities.transformarLink(picture));
        portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(175, 175, Image.SCALE_SMOOTH))));
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
        javax.swing.JButton guardar = new javax.swing.JButton();

        guardar.setText("CREAR");
        guardar.setFont(coolvetica.deriveFont(16f));
        guardar.setForeground(CReturner.getBackground());
        guardar.setBackground(CReturner.getTexto());
        guardar.setBorder(null);
        guardar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (comprobarDatos()) {
                    guardado = 1;
                    Statement sentencia;
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
                        sentencia = conexion.createStatement();
                        String sql = null;
                        if (fotoSelected) {
                            linkImagen = uploadImage();
                        }
                        String privacidad;
                        if (radioPublica.isSelected()) {
                            privacidad = "publica";
                        } else {
                            privacidad = "privada";
                        }
                        if (fotoSelected) {
                            sql = "INSERT INTO playlist(name,privacity,user,picture) "
                                    + "VALUES ('" + inputNombre.getText() + "','" + privacidad + "','" + username + "','" + linkImagen + "')";
                            sentencia.executeUpdate(sql);
                        } else {
                            sql = "INSERT INTO playlist(name,privacity,user) "
                                    + "VALUES ('" + inputNombre.getText() + "','" + privacidad + "','" + username + "')";
                            sentencia.executeUpdate(sql);
                        }
                        String idPlaylist = null;
                        sql = "SELECT playlist.playlist_id "
                                + "FROM playlist "
                                + "WHERE playlist.name = '" + inputNombre.getText() + "' AND playlist.user = '" + username + "'";
                        ResultSet resul = sentencia.executeQuery(sql);
                        if (resul.next()) {
                            idPlaylist = resul.getString("playlist_id");
                        }
                        sql = "INSERT INTO registro_savedlist "
                                + "VALUES('" + idPlaylist + "','" + username + "',false)";
                        sentencia.executeUpdate(sql);
                        sentencia.close();
                        conexion.close();
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(EditDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    dispose();
                }
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        botones.add(guardar, gridBagConstraints);

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
        if (inputNombre.getText().isBlank()) {
            valido = false;
            JOptionPane.showMessageDialog(new JFrame(), "Introduce un nombre valido.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        if (!radioPublica.isSelected() && !radioPrivada.isSelected()) {
            valido = false;
            JOptionPane.showMessageDialog(new JFrame(), "Selecciona la privacidad de la lista.", "ERROR", JOptionPane.ERROR_MESSAGE);
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

    public int getGuardado() {
        return guardado;
    }
}
