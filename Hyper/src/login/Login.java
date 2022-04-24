package login;

import java.sql.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class Login extends JPanel {

    private final JFrame frameLogin;
    private final JFrame framePrincipal;
    private final JPanel panelRegister;
    private javax.swing.JPasswordField inputPass;
    private javax.swing.JTextField inputUser;
    private javax.swing.JCheckBox rememberMe;
    private final Preferences pref;

    public Login(JFrame frameLogin, JPanel panelRegister, JFrame framePrincipal) {
        this.pref = Preferences.userRoot().node("Rememberme");
        this.frameLogin = frameLogin;
        this.framePrincipal = framePrincipal;
        this.panelRegister = panelRegister;
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

        javax.swing.JButton acceder = new javax.swing.JButton();
        javax.swing.JPanel contra = new javax.swing.JPanel();
        javax.swing.JPanel extras = new javax.swing.JPanel();
        inputPass = new javax.swing.JPasswordField();
        inputUser = new javax.swing.JTextField();
        javax.swing.JLabel labelPass = new javax.swing.JLabel();
        javax.swing.JLabel labelUser = new javax.swing.JLabel();
        javax.swing.JLabel noAccount = new javax.swing.JLabel();
        rememberMe = new javax.swing.JCheckBox();
        javax.swing.JLabel titulo = new javax.swing.JLabel();
        javax.swing.JPanel usuario = new javax.swing.JPanel();
        java.awt.GridBagConstraints gridBagConstraints;

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        formulario.setLayout(new java.awt.GridBagLayout());

        titulo.setFont(lemonB.deriveFont(24f));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        titulo.setText("ACCOUNT LOGIN");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        formulario.add(titulo, gridBagConstraints);

        usuario.setLayout(new java.awt.GridLayout(2, 1));
        usuario.setBackground(new Color(240, 245, 249));

        labelUser.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelUser.setText(" USUARIO");
        labelUser.setFont(lemonR.deriveFont(12f));
        labelUser.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        usuario.add(labelUser);

        inputUser.setPreferredSize(new java.awt.Dimension(13, 35));
        usuario.add(inputUser);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);
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
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 7, 0);
        formulario.add(contra, gridBagConstraints);

        extras.setLayout(new java.awt.GridLayout(1, 2));
        extras.setBackground(new Color(240, 245, 249));

        rememberMe.setFont(new java.awt.Font("sansserif", 0, 10));
        rememberMe.setText("Remember Me");
        rememberMe.setBackground(new Color(240, 245, 249));
        extras.add(rememberMe);

        noAccount.setFont(lemonR.deriveFont(8f));
        noAccount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        noAccount.setText("No tengo cuenta");
        extras.add(noAccount);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 18, 0);
        formulario.add(extras, gridBagConstraints);

        acceder.setText("INICIAR SESION");
        acceder.setFont(lemonB.deriveFont(14f));
        acceder.setForeground(Color.WHITE);
        acceder.setBackground(new Color(255, 223, 76));
        acceder.setBorder(null);
        acceder.setPreferredSize(new java.awt.Dimension(119, 40));
        acceder.addActionListener((java.awt.event.ActionEvent evt) -> {
            loguearse();
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        formulario.add(acceder, gridBagConstraints);
        inputUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(30, 32, 34)));
        inputPass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(30, 32, 34)));

        add(formulario, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 40, 300, 400));

        noAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                registrarse();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                noAccount.setForeground(new Color(255, 223, 76));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                noAccount.setForeground(new Color(51, 51, 51));
            }
        });
    }

    private void registrarse() {
        this.setVisible(false);
        inputUser.setText("");
        inputPass.setText("");
        inputUser.setBackground(new Color(255, 255, 255));
        inputPass.setBackground(new Color(255, 255, 255));
        rememberMe.setSelected(false);
        panelRegister.setVisible(true);
    }

    private void loguearse() {
        if (comprobarDatos()) {
            checkRemember();
            frameLogin.setVisible(false);
            String user = inputUser.getText();
            pref.put("ActualUser", user);
            inputUser.setText("");
            inputPass.setText("");
            framePrincipal.setVisible(true);
        }
    }

    private boolean comprobarDatos() {
        boolean valido = true;
        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            String sql = "SELECT username FROM users WHERE username='" + inputUser.getText() + "'";
            ResultSet resul = sentencia.executeQuery(sql);
            if (!resul.next() || inputUser.getText().isBlank()) {
                valido = false;
                inputUser.setBackground(new Color(255, 77, 77));
                JOptionPane.showMessageDialog(null, "El usuario introducido no existe o no es valido", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                inputUser.setBackground(new Color(255, 255, 255));
            }
            if (valido) {
                sql = "SELECT password FROM users WHERE username='" + inputUser.getText() + "'";
                resul = sentencia.executeQuery(sql);
                resul.next();
                if (!Arrays.equals(resul.getString(1).toCharArray(), inputPass.getPassword())) {
                    valido = false;
                    inputPass.setBackground(new Color(255, 77, 77));
                    JOptionPane.showMessageDialog(null, "La contraseña introducida es incorrecta.", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    inputPass.setBackground(new Color(255, 255, 255));
                }
            }
            resul.close();
            sentencia.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valido;
    }

    private void checkRemember() {
        if (rememberMe.isSelected()) {
            String user = inputUser.getText();
            pref.put("Username", user);
            String pass = String.valueOf(inputPass.getPassword());
            pref.put("Password", pass);
        } else {
            pref.remove("Username");
            pref.remove("Password");
        }
    }
}
