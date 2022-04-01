package login;

import java.sql.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 23-03-2022
 * @version 1.0
 *
 * ************************************
 */
public class Register extends JPanel {

    private JPanel panelLogin;
    private javax.swing.JPasswordField inputPass;
    private javax.swing.JPasswordField inputPass2;
    private javax.swing.JTextField inputUser;
    private javax.swing.JTextField inputNombre;
    private javax.swing.JCheckBox isArtist;
    boolean fotoSelected = false;
    File fotoDePerfil;

    public Register() {
        iniciarComponentes();
        iniciarFormulario();
    }

    private void iniciarComponentes() {
        setBackground(new Color(240, 245, 249));
        setLayout(null);
        JPanel panel2 = new JPanel();
        panel2.setBounds(0, 0, 350, 500);
        JLabel lblImage = new JLabel(new ImageIcon(getClass().getResource("/resources/login.png")));
        lblImage.setBounds(0, 0, 350, 500);
        panel2.add(lblImage);
        add(panel2);
        panel2.setLayout(null);
    }

    private void iniciarFormulario() {
        JPanel formulario = new JPanel();
        formulario.setBackground(new Color(240, 245, 249));
        formulario.setBounds(350, 44, 350, 456);
        InputStream is = Login.class.getResourceAsStream("/fonts/LEMONMILK-Bold.otf");
        InputStream is2 = Login.class.getResourceAsStream("/fonts/LEMONMILK-Regular.otf");
        Font lemonB = null;
        Font lemonR = null;
        try {
            lemonB = Font.createFont(Font.TRUETYPE_FONT, is);
            lemonR = Font.createFont(Font.TRUETYPE_FONT, is2);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.GridBagConstraints gridBagConstraints;

        javax.swing.JButton acceder = new javax.swing.JButton();
        javax.swing.JButton seleccionar = new javax.swing.JButton();
        javax.swing.JPanel contra = new javax.swing.JPanel();
        javax.swing.JPanel extras = new javax.swing.JPanel();
        javax.swing.JPanel extras2 = new javax.swing.JPanel();
        javax.swing.JPanel extras3 = new javax.swing.JPanel();
        inputPass2 = new javax.swing.JPasswordField();
        javax.swing.JLabel labelPass = new javax.swing.JLabel();
        javax.swing.JLabel labelPass2 = new javax.swing.JLabel();
        javax.swing.JLabel labelUser = new javax.swing.JLabel();
        javax.swing.JLabel labelNombre = new javax.swing.JLabel();
        javax.swing.JLabel titulo = new javax.swing.JLabel();
        javax.swing.JPanel usuario = new javax.swing.JPanel();
        inputUser = new javax.swing.JTextField();
        inputNombre = new javax.swing.JTextField();
        inputPass = new javax.swing.JPasswordField();
        isArtist = new javax.swing.JCheckBox();

        setLayout(
                new org.netbeans.lib.awtextra.AbsoluteLayout());

        formulario.setLayout(
                new java.awt.GridBagLayout());

        titulo.setFont(lemonB.deriveFont(24f));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        titulo.setText(
                "ACCOUNT REGISTER");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);

        formulario.add(titulo, gridBagConstraints);

        usuario.setLayout(
                new java.awt.GridLayout(2, 1));

        labelUser.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelUser.setText(" USUARIO");
        labelUser.setFont(lemonR.deriveFont(12f));
        labelUser.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        usuario.setLayout(new java.awt.GridLayout(2, 1));
        usuario.setBackground(new Color(240, 245, 249));

        usuario.add(labelUser);

        inputUser.setPreferredSize(new java.awt.Dimension(13, 35));
        usuario.add(inputUser);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
        formulario.add(usuario, gridBagConstraints);

        contra.setLayout(new java.awt.GridLayout(2, 1));
        contra.setBackground(new Color(240, 245, 249));

        labelPass.setText(" CONTRASEÑA");
        labelPass.setFont(lemonR.deriveFont(12f));
        labelPass.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        contra.add(labelPass);

        inputPass.setPreferredSize(new java.awt.Dimension(13, 35));
        contra.add(inputPass);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
        formulario.add(contra, gridBagConstraints);

        extras.setLayout(new java.awt.GridLayout(2, 1));
        extras.setBackground(new Color(240, 245, 249));

        labelPass2.setText(" REPITA CONTRASEÑA");
        labelPass.setFont(lemonR.deriveFont(12f));
        labelPass2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        extras.add(labelPass2);

        inputPass2.setPreferredSize(new java.awt.Dimension(13, 35));
        extras.add(inputPass2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        formulario.add(extras, gridBagConstraints);

        extras3.setBackground(new Color(240, 245, 249));
        extras3.setLayout(
                new java.awt.GridLayout(2, 1));
        labelNombre.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelNombre.setText(" NOMBRE");
        labelNombre.setFont(lemonR.deriveFont(12f));
        labelNombre.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        usuario.setLayout(new java.awt.GridLayout(2, 1));
        usuario.setBackground(new Color(240, 245, 249));
        extras3.add(labelNombre);
        inputNombre.setPreferredSize(new java.awt.Dimension(13, 35));
        inputNombre.setText("(Solo rellenar si eres artista)");
        inputNombre.setEnabled(false);
        extras3.add(inputNombre);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        formulario.add(extras3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
        extras2.setLayout(new javax.swing.BoxLayout(extras2, javax.swing.BoxLayout.LINE_AXIS));
        extras2.setBackground(new Color(240, 245, 249));
        isArtist.setFont(new java.awt.Font("sansserif", 0, 10));
        isArtist.setText("¿Eres artista?");
        isArtist.setBackground(new Color(240, 245, 249));
        extras2.add(isArtist);
        isArtist.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent arg0) {
                if (isArtist.isSelected()) {
                    inputNombre.setEnabled(true);
                    inputNombre.setText("");
                } else {
                    inputNombre.setEnabled(false);
                    inputNombre.setText("(Solo rellenar si eres artista)");
                }
            }
        });
        JLabel separar = new JLabel("             ");
        extras2.add(separar);
        seleccionar.setText("Seleccionar Imagen de Perfil");
        seleccionar.setFont(lemonR.deriveFont(8f));
        seleccionar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        seleccionar.setPreferredSize(new java.awt.Dimension(25, 20));
        extras2.add(seleccionar);
        seleccionar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JFileChooser inputPic = new JFileChooser();
                FileFilter imageFilter = new FileNameExtensionFilter(
                        "Image files", ImageIO.getReaderFileSuffixes());
                inputPic.setFileFilter(imageFilter);
                int returnVal = inputPic.showOpenDialog(Register.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    fotoDePerfil = inputPic.getSelectedFile();
                    fotoSelected = true;
                } else {
                    fotoSelected = false;
                }
            }
        });
        formulario.add(extras2, gridBagConstraints);

        acceder.setText("REGISTRARME");
        acceder.setFont(lemonB.deriveFont(14f));
        acceder.setForeground(Color.WHITE);
        acceder.setBackground(new Color(255, 223, 76));
        acceder.setBorder(null);
        acceder.setPreferredSize(new java.awt.Dimension(119, 40));
        acceder.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrarse();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        formulario.add(acceder, gridBagConstraints);
        inputUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(30, 32, 34)));
        inputPass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(30, 32, 34)));
        inputPass2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(30, 32, 34)));

        add(formulario, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 35, 300, 450));
    }

    public void setLogin(JPanel panelLogin) {
        this.panelLogin = panelLogin;
    }

    private void registrarse() {
        if (comprobarDatos()) {
            PreparedStatement stmt;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
                String linkImagen = null;
                if (fotoSelected) {
                    linkImagen = uploadImage();
                    stmt = conexion.prepareStatement("INSERT INTO users(username,password,profile_pic) VALUES (?, ?, ?)");
                    stmt.setString(1, inputUser.getText());
                    stmt.setString(2, String.valueOf(inputPass.getPassword()));
                    stmt.setString(3, linkImagen);
                } else {
                    stmt = conexion.prepareStatement("INSERT INTO users(username,password) VALUES (?, ?)");
                    stmt.setString(1, inputUser.getText());
                    stmt.setString(2, String.valueOf(inputPass.getPassword()));
                }
                stmt.executeUpdate();
                if (isArtist.isSelected()) {
                    stmt = conexion.prepareStatement("INSERT INTO artist(name,username,profile_pic) VALUES (?, ?, ?)");
                    stmt.setString(1, inputNombre.getText());
                    stmt.setString(2, inputUser.getText());
                    stmt.setString(3, linkImagen);
                    stmt.executeUpdate();
                }

                setVisible(false);
                inputUser.setText("");
                inputPass.setText("");
                inputPass2.setText("");
                inputNombre.setText("");
                isArtist.setSelected(false);
                panelLogin.setVisible(true);
                conexion.close();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

    private boolean comprobarDatos() {
        boolean valido = true;
        Statement sentencia;
        PreparedStatement stmt;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            String sql = "SELECT username FROM users WHERE username='" + inputUser.getText() + "'";
            ResultSet resul = sentencia.executeQuery(sql);
            if (resul.next()) {
                valido = false;
                inputUser.setBackground(new Color(255, 77, 77));
                JOptionPane.showMessageDialog(null, "El usuario ya existe.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                if (inputUser.getText().isBlank()) {
                    valido = false;
                    inputUser.setBackground(new Color(255, 77, 77));
                    JOptionPane.showMessageDialog(null, "El usuario no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else if (inputUser.getText().contains(" ")) {
                    valido = false;
                    inputUser.setBackground(new Color(255, 77, 77));
                    JOptionPane.showMessageDialog(null, "El usuario no puede tener espacios", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else if (inputUser.getText().length() > 16) {
                    valido = false;
                    inputUser.setBackground(new Color(255, 77, 77));
                    JOptionPane.showMessageDialog(null, "El usuario no puede tener mas de 16 caracteres", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    inputUser.setBackground(new Color(255, 255, 255));
                    if (valido) {
                        if (!Arrays.equals(inputPass.getPassword(), inputPass2.getPassword()) || inputPass.getPassword().length <= 0) {
                            valido = false;
                            inputPass.setBackground(new Color(255, 77, 77));
                            inputPass2.setBackground(new Color(255, 77, 77));
                            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "ERROR", JOptionPane.ERROR_MESSAGE);
                        } else if (inputPass.getPassword().length > 16) {
                            valido = false;
                            inputPass.setBackground(new Color(255, 77, 77));
                            inputPass2.setBackground(new Color(255, 77, 77));
                            JOptionPane.showMessageDialog(null, "La contraseña no puede tener mas de 16 caracteres", "ERROR", JOptionPane.ERROR_MESSAGE);
                        } else {
                            inputPass.setBackground(new Color(255, 255, 255));
                            inputPass2.setBackground(new Color(255, 255, 255));
                            if (isArtist.isSelected()) {
                                if (!fotoSelected) {
                                    valido = false;
                                    JOptionPane.showMessageDialog(null, "Es obligatorio seleccionar foto de perfil en artistas", "ERROR", JOptionPane.ERROR_MESSAGE);
                                } else if (!fotoDePerfil.toString().endsWith("png") && !fotoDePerfil.toString().endsWith("jpg") && !fotoDePerfil.toString().endsWith("jpeg")) {
                                    valido = false;
                                    JOptionPane.showMessageDialog(null, "La imagen solo puede ser .png o .jpg o .jpeg", "ERROR", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    sql = "SELECT name FROM artist WHERE name='" + inputNombre.getText() + "'";
                                    resul = sentencia.executeQuery(sql);
                                    if (resul.next()) {
                                        valido = false;
                                        inputNombre.setBackground(new Color(255, 77, 77));
                                        JOptionPane.showMessageDialog(null, "El nombre ya esta siendo utilizado.", "ERROR", JOptionPane.ERROR_MESSAGE);
                                    } else if (inputNombre.getText().isBlank() || inputNombre.getText().charAt(0) == ' ') {
                                        valido = false;
                                        inputNombre.setBackground(new Color(255, 77, 77));
                                        JOptionPane.showMessageDialog(null, "El nombre no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);
                                    } else if (inputNombre.getText().length() > 20) {
                                        valido = false;
                                        inputNombre.setBackground(new Color(255, 77, 77));
                                        JOptionPane.showMessageDialog(null, "El nombre no puede tener mas de 16 caracteres", "ERROR", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        inputNombre.setBackground(new Color(255, 255, 255));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            resul.close();
            sentencia.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Register.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return valido;
    }
}
