package login;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 22-03-2022
 * @version 1.0
 *
 * ************************************
 */
public class Register extends JPanel {

    private JPanel panelLogin;
    javax.swing.JPasswordField inputPass;
    javax.swing.JTextField inputUser;

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
        javax.swing.JPanel contra = new javax.swing.JPanel();
        javax.swing.JPanel extras = new javax.swing.JPanel();
        javax.swing.JPasswordField inputPass2 = new javax.swing.JPasswordField();
        javax.swing.JLabel labelPass = new javax.swing.JLabel();
        javax.swing.JLabel labelPass2 = new javax.swing.JLabel();
        javax.swing.JLabel labelUser = new javax.swing.JLabel();
        javax.swing.JLabel titulo = new javax.swing.JLabel();
        javax.swing.JPanel usuario = new javax.swing.JPanel();
        inputUser = new javax.swing.JTextField();
        inputPass = new javax.swing.JPasswordField();

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

        acceder.setText("REGISTRARME");
        acceder.setFont(lemonB.deriveFont(14f));
        acceder.setForeground(Color.WHITE);
        acceder.setBackground(new Color(255, 223, 76));
        acceder.setBorder(null);
        acceder.setPreferredSize(new java.awt.Dimension(119, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        formulario.add(acceder, gridBagConstraints);

        add(formulario, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 60, 300, 400));
    }

    public void setLogin(JPanel panelLogin) {
        this.panelLogin = panelLogin;
    }
}
