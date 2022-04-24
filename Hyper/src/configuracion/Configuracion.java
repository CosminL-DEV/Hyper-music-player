package configuracion;

import components.Combobox;
import components.ImgCircleConverter;
import components.ScrollBar;
import components.TopBar;
import appManagement.Utilities;
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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import profiles.Artista;
import profiles.Perfil;
import appManagement.ColorReturner;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import songManager.BotBar;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class Configuracion extends JPanel {

    private ColorReturner CReturner = new ColorReturner();
    private java.awt.GridBagConstraints gridBagConstraints;
    private Font coolvetica = Utilities.cargarCoolvetica();
    private final ImgCircleConverter convertidor = new ImgCircleConverter();
    private String username;
    private JPanel content;
    private JScrollPane scrollPane;
    private JPanel main;
    private BotBar botBar;
    private TopBar topBar;
    private JPanel interfazPrinc;
    private JLabel home;
    private JPanel listaPlaylist;
    private JFrame window;
    private JLabel portada;
    private Popup popup;
    private File fotoDePerfil;
    private boolean tieneFoto = true;
    private boolean esArtista = false;
    private String picture;
    private javax.swing.JTextField inputName;
    private Combobox orden;
    private Preferences pref;

    public Configuracion(JPanel interfazPrinc, BotBar botBar, JScrollPane scrollPane, JPanel main, TopBar topBar, JLabel home,
            JPanel listaPlaylist, JFrame window) {
        this.window = window;
        this.content = this;
        this.interfazPrinc = interfazPrinc;
        this.botBar = botBar;
        this.topBar = topBar;
        this.scrollPane = scrollPane;
        this.home = home;
        this.main = main;
        this.listaPlaylist = listaPlaylist;
        setOpaque(true);
        setBackground(CReturner.getBackground());
        setLayout(new java.awt.GridBagLayout());
        pref = Preferences.userRoot().node("Rememberme");
        username = pref.get("ActualUser", "");
        addLateralIzq();
        addLateralDer();
        convertirArtista();
        seleccionarTema();
        seleccionarUbicacion();
        borrarCuenta();
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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weighty = 0.1;
        add(latIzq, gridBagConstraints);
    }

    private void convertirArtista() {
        JPanel conjunto = new javax.swing.JPanel();
        conjunto.setMaximumSize(new Dimension(3000, 225));
        conjunto.setOpaque(false);
        conjunto.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        portada = new javax.swing.JLabel();
        conjunto.add(portada);

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
                String sql = "SELECT artist.name "
                        + "FROM artist "
                        + "WHERE artist.username = '" + username + "'";
                ResultSet resul = sentencia.executeQuery(sql);
                if (resul.next()) {
                    esArtista = true;
                } else {
                    JPanel help1 = new javax.swing.JPanel();
                    help1.setOpaque(false);
                    help1.setLayout(new java.awt.GridLayout(2, 0, 0, 25));
                    JPanel help2 = new javax.swing.JPanel();
                    help2.setOpaque(false);
                    help2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, CReturner.getTexto()));
                    help2.setLayout(new java.awt.BorderLayout());
                    inputName = new javax.swing.JTextField("Nombre");
                    inputName.setForeground(CReturner.getTexto());
                    inputName.setBackground(CReturner.getBackground());
                    inputName.setFont(coolvetica.deriveFont(15f));
                    inputName.setPreferredSize(new Dimension(200, 20));
                    inputName.setBorder(null);
                    inputName.addKeyListener(new java.awt.event.KeyAdapter() {
                        @Override
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                            if (inputName.getText().length() > 20) {
                                inputName.setText(inputName.getText().substring(0, 20));
                            }
                        }
                    });
                    help2.add(inputName);
                    help1.add(help2);

                    conjunto.add(help1);

                    javax.swing.JButton convertir = new javax.swing.JButton();
                    convertir.setText("Convertir perfil en artista");
                    convertir.setFont(coolvetica.deriveFont(16f));
                    convertir.setForeground(CReturner.getBackground());
                    convertir.setBackground(CReturner.getTexto());
                    convertir.setBorder(null);
                    convertir.setPreferredSize(new Dimension(200, 40));
                    help1.add(convertir);
                    convertir.addActionListener((java.awt.event.ActionEvent evt) -> {
                        if (comprobarDatos()) {
                            Statement sentencia1;
                            String idArtista = null;
                            try {
                                Class.forName("com.mysql.cj.jdbc.Driver");
                                try (Connection conexion1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                                    sentencia1 = conexion1.createStatement();
                                    String sql1 = "INSERT INTO artist(name, username, profile_pic) "
                                            + "VALUES('" + inputName.getText() + "', '" + username + "', '" + picture + "')";
                                    sentencia1.executeUpdate(sql1);
                                    sql1 = "SELECT artist.artist_id "
                                            + "FROM artist "
                                            + "WHERE artist.username = '" + username + "'";
                                    ResultSet resul1 = sentencia1.executeQuery(sql1);
                                    if (resul1.next()) {
                                        idArtista = resul1.getString("artist_id");
                                    }
                                    sentencia1.close();
                                }
                            } catch (SQLException | ClassNotFoundException ex) {
                                Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            content = new Artista(idArtista, listaPlaylist, interfazPrinc, botBar, scrollPane, main, topBar, home, window);
                            cargarNuevoPanel();
                            interfazPrinc.revalidate();
                            interfazPrinc.repaint();
                            setCursor(null);
                        }
                    });
                }
                sql = "SELECT users.profile_pic "
                        + "FROM users "
                        + "WHERE users.username = '" + username + "'";
                resul = sentencia.executeQuery(sql);
                if (resul.next()) {
                    picture = resul.getString("profile_pic");
                    if (picture == null) {
                        tieneFoto = false;
                        picture = "http://localhost/hyper/wp-content/uploads/2022/04/user.png";
                    }
                    ImageIcon img = new ImageIcon(convertidor.convertirImagen(Utilities.transformarLink(picture)));
                    portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(225, 225, Image.SCALE_SMOOTH))));
                    if (username.equalsIgnoreCase(username)) {
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
                                int returnVal = inputPic.showOpenDialog(Configuracion.this);
                                if (returnVal == JFileChooser.APPROVE_OPTION) {
                                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                    fotoDePerfil = inputPic.getSelectedFile();
                                    tieneFoto = true;
                                    Statement sentencia;
                                    String nuevoLink = uploadImage();
                                    picture = nuevoLink;
                                    try {
                                        Class.forName("com.mysql.cj.jdbc.Driver");
                                        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                                            sentencia = conexion.createStatement();
                                            String sql = "UPDATE users "
                                                    + "SET users.profile_pic = '" + nuevoLink + "' "
                                                    + "WHERE users.username = '" + username + "'";
                                            sentencia.executeUpdate(sql);
                                            if (esArtista) {
                                                sql = "UPDATE artist "
                                                        + "SET artist.profile_pic = '" + nuevoLink + "' "
                                                        + "WHERE artist.username = '" + username + "'";
                                                sentencia.executeUpdate(sql);
                                            }
                                            sentencia.close();
                                        }
                                    } catch (SQLException | ClassNotFoundException ex) {
                                        Logger.getLogger(Perfil.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    ImageIcon img = new ImageIcon(convertidor.convertirImagen(Utilities.transformarLink(nuevoLink)));
                                    portada.setIcon(new ImageIcon((img.getImage().getScaledInstance(225, 225, Image.SCALE_SMOOTH))));
                                    cargarNuevoPanel();
                                    interfazPrinc.revalidate();
                                    interfazPrinc.repaint();
                                    setCursor(null);
                                }
                            }
                        });
                    }
                }
                resul.close();
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean comprobarDatos() {
        boolean valido = true;
        if (!tieneFoto) {
            valido = false;
            JOptionPane.showMessageDialog(new JFrame(), "Para convertirte en artista \n"
                    + "es obligatorio seleccionar una foto.", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT artist.name "
                        + "FROM artist "
                        + "WHERE artist.name = '" + inputName.getText() + "'";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    if (resul.next()) {
                        valido = false;
                        JOptionPane.showMessageDialog(new JFrame(), "El nombre introducido ya esta en uso.", "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valido;
    }

    private void seleccionarTema() {
        JPanel contenedor = new javax.swing.JPanel();
        contenedor.setOpaque(false);
        contenedor.setLayout(new java.awt.GridLayout(2, 0, 0, 10));
        JPanel texto1 = new javax.swing.JPanel();
        JLabel titulo1 = new javax.swing.JLabel();
        texto1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 5));
        texto1.setBackground(CReturner.getBackground());
        titulo1.setText("Selecciona el tema");
        titulo1.setFont(coolvetica.deriveFont(30f));
        titulo1.setForeground(CReturner.getTexto());
        texto1.add(titulo1);
        contenedor.add(texto1);

        JPanel contenedor2 = new javax.swing.JPanel();
        contenedor2.setOpaque(false);
        contenedor2.setLayout(new java.awt.GridLayout(0, 2, 20, 0));
        orden = new components.Combobox();
        orden.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[]{"Dark-Yellow", "Dark-Green", "Nord-Light", "Nord-Dark", "Rose-Light", "Purple", "Rojo-Atardecer"}
        ));
        orden.setLabeText("");
        orden.setSelectedItem(CReturner.getTemaActual());
        orden.setBackground(CReturner.getTexto2());
        orden.setForeground(CReturner.getTexto());
        orden.setBorder(null);
        orden.setPreferredSize(new Dimension(225, 30));
        orden.setFont(coolvetica.deriveFont(15f));
        orden.setFocusable(false);
        contenedor2.add(orden);
        javax.swing.JButton guardar = new javax.swing.JButton();
        guardar.setText("Guardar cambios");
        guardar.setFont(coolvetica.deriveFont(16f));
        guardar.setForeground(CReturner.getBackground());
        guardar.setBackground(CReturner.getTexto());
        guardar.setBorder(null);
        guardar.setPreferredSize(new Dimension(200, 40));
        guardar.addActionListener((java.awt.event.ActionEvent evt) -> {
            Utilities.cambiarTema(orden.getSelectedItem().toString());
            JOptionPane.showMessageDialog(new JFrame(), "Se ha cambiado de tema correctamente. \n"
                    + "Para ver el nuevo tema se va a reiniciar el programa", "CONFIRMADO",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        });
        contenedor2.add(guardar);
        contenedor.add(contenedor2);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 30, 0);
        add(contenedor, gridBagConstraints);
    }

    private void seleccionarUbicacion() {
        /* Realmente esta funcion no tiene mucho sentido ya que los iconos sigue
           cogiendolos de la base de datos, pero esta pensada para implentar un
           modo sin conexion mas adelante. */
        JPanel contenedor = new javax.swing.JPanel();
        contenedor.setOpaque(false);
        contenedor.setLayout(new java.awt.GridLayout(2, 0, 0, 10));
        JPanel texto1 = new javax.swing.JPanel();
        JLabel titulo1 = new javax.swing.JLabel();
        texto1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 5));
        texto1.setBackground(CReturner.getBackground());
        titulo1.setText("Ubicación de las descargas de canciones.");
        titulo1.setFont(coolvetica.deriveFont(30f));
        titulo1.setForeground(CReturner.getTexto());
        texto1.add(titulo1);
        contenedor.add(texto1);

        JPanel contenedor2 = new javax.swing.JPanel();
        contenedor2.setOpaque(false);
        contenedor2.setLayout(new java.awt.GridLayout(0, 2, 20, 0));
        JLabel ubicacion = new javax.swing.JLabel();
        ubicacion.setFont(coolvetica.deriveFont(15f));
        ubicacion.setForeground(CReturner.getTexto2());
        ubicacion.setText(CReturner.getFolderPath().toString());
        contenedor2.add(ubicacion);
        javax.swing.JButton guardar = new javax.swing.JButton();
        guardar.setText("Cambiar ubicación");
        guardar.setFont(coolvetica.deriveFont(16f));
        guardar.setForeground(CReturner.getBackground());
        guardar.setBackground(CReturner.getTexto());
        guardar.setBorder(null);
        guardar.setPreferredSize(new Dimension(200, 40));
        guardar.addActionListener((java.awt.event.ActionEvent evt) -> {
            JFileChooser chooser;
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Selecciona la ruta");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(content) == JFileChooser.APPROVE_OPTION) {
                Path rutaAntigua = CReturner.getFolderPath().getParent();
                Path rutaNueva = chooser.getSelectedFile().toPath();
                Path rutaFinal = Paths.get(rutaNueva.toString(), "Hyper");
                try {
                    Path newFinal = Paths.get(rutaFinal.toString(), "Almacenamiento");
                    Utilities.escribirRuta(newFinal.toString());
                    ubicacion.setText(newFinal.toString());
                    FileUtils.moveDirectory(rutaAntigua.toFile(), rutaFinal.toFile());
                } catch (IOException ex) {
                    Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        contenedor2.add(guardar);

        contenedor.add(contenedor2);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 30, 0);

        add(contenedor, gridBagConstraints);
    }

    private void borrarCuenta() {
        JPanel contenedor = new javax.swing.JPanel();
        contenedor.setOpaque(false);
        contenedor.setLayout(new java.awt.GridLayout(0, 1, 0, 0));
        javax.swing.JButton borrar = new javax.swing.JButton();
        borrar.setText("ELIMINAR CUENTA");
        borrar.setFont(coolvetica.deriveFont(16f));
        borrar.setForeground(CReturner.getClose());
        borrar.setBackground(CReturner.getTexto());
        borrar.setBorder(null);
        borrar.setPreferredSize(new Dimension(200, 40));
        borrar.addActionListener((java.awt.event.ActionEvent evt) -> {
            int input = JOptionPane.showConfirmDialog(null, "¿Estas seguro de que deseas realizar esta accion?\n"
                    + "Todos los datos relativos a tu cuenta seran borrados.", "CONFIRMACION", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (input == 0) {
                borrarTodo();
            }
        });
        contenedor.add(borrar);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 30, 0);

        add(contenedor, gridBagConstraints);
    }

    private void borrarTodo() {
        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String idArtista = null;
                ResultSet resul = sentencia.executeQuery("SELECT artist_id "
                        + "FROM artist "
                        + "WHERE username = '" + username + "'");
                if (resul.next()) {
                    idArtista = resul.getString("artist_id");
                }
                String sql = "DELETE FROM registro_savedlist "
                        + "WHERE registro_savedlist.user = '" + username + "'";
                sentencia.executeUpdate(sql);
                sql = "DELETE FROM registro_savedalbum "
                        + "WHERE registro_savedalbum.user = '" + username + "'";
                sentencia.executeUpdate(sql);
                sql = "DELETE FROM registro_playlist "
                        + "WHERE registro_playlist.user_added = '" + username + "'";
                sentencia.executeUpdate(sql);
                sql = "DELETE FROM registro_playlist "
                        + "WHERE registro_playlist.user_added = '" + username + "'";
                sentencia.executeUpdate(sql);
                if (idArtista != null) {
                    sql = "DELETE FROM registro_song "
                            + "WHERE registro_song.artist_id = '" + idArtista + "'";
                    sentencia.executeUpdate(sql);
                    sql = "DELETE FROM album "
                            + "WHERE album.artist_id = '" + idArtista + "'";
                    sentencia.executeUpdate(sql);
                }
                sql = "DELETE FROM playlist "
                        + "WHERE playlist.user = '" + username + "'";
                sentencia.executeUpdate(sql);
                sql = "DELETE FROM artist "
                        + "WHERE artist.username = '" + username + "'";
                sentencia.executeUpdate(sql);
                sql = "DELETE FROM users "
                        + "WHERE users.username = '" + username + "'";
                sentencia.executeUpdate(sql);
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Perfil.class.getName()).log(Level.SEVERE, null, ex);
        }
        pref.remove("Username");
        pref.remove("Password");
        pref.remove("ActualUser");
        window.setVisible(false);
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
            // tenido que hacer este feo apaño para por lo menos poder usarlo.
            Path source = Paths.get(fotoDePerfil.getPath());
            Path target = Paths.get(CReturner.getRutaXampp() + linkImagen.substring(17));
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (XmlRpcException | IOException e) {
            System.out.println("Imagen no subida.");
        }
        return linkImagen;
    }
}
