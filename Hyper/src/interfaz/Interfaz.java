package interfaz;

import components.TopBar;
import components.RoundedPanel;
import components.ItemPlaylist;
import songManager.BotBar;
import dialogs.CreatePlaylist;
import components.ScrollBar;
import playlist.Playlist;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import appManagement.ColorReturner;
import views.Biblioteca;
import views.Busqueda;
import views.Inicio;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class Interfaz extends JPanel {

    private java.awt.GridBagConstraints gridBagConstraints;
    private final JFrame framePrincipal;
    private ColorReturner CReturner = new ColorReturner();
    private String username;
    private Font lemonB = null;
    private Font lemonR = null;
    private JPanel listaPlaylist;
    private TopBar topBar;
    private BotBar botBar;
    private JPanel content;
    private JScrollPane scrollPane;
    private JPanel main;
    private String actualLabel = "Inicio";
    private JLabel home;
    private JLabel search;
    private JLabel library;
    private JPanel esta;

    public Interfaz(JFrame framePrincipal) {
        this.framePrincipal = framePrincipal;
        esta = this;
        validadUsername();
        cargarFonts();
        iniciarComponentes();
        iniciarTopPage();
        iniciarIcono();
        iniciarBarraIzq();
        iniciarBarraDer();
        iniciarBotPage();
        iniciarPanelPrincipal();
    }

    private void validadUsername() {
        Preferences pref = Preferences.userRoot().node("Rememberme");
        username = pref.get("ActualUser", "");
        if (!pref.get("Username", "").equals("") && !pref.get("Password", "").equals("")) {
            if (!username.equals(pref.get("Username", ""))) {
                System.exit(0);
            }
            Statement sentencia;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
                sentencia = conexion.createStatement();
                String sql = "SELECT password FROM users WHERE username='" + username + "'";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    resul.next();
                    if (!pref.get("Password", "").equals(resul.getString(1))) {
                        resul.close();
                        sentencia.close();
                        System.exit(0);
                    }
                }
                sentencia.close();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void cargarFonts() {
        InputStream is = Interfaz.class.getResourceAsStream("/fonts/LEMONMILK-Bold.otf");
        InputStream is2 = Interfaz.class.getResourceAsStream("/fonts/LEMONMILK-Regular.otf");
        try {
            lemonB = Font.createFont(Font.TRUETYPE_FONT, is);
            lemonR = Font.createFont(Font.TRUETYPE_FONT, is2);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iniciarComponentes() {
        setBackground(CReturner.getPrincipal());
        setLayout(new java.awt.GridBagLayout());
    }

    private void iniciarTopPage() {
        JPanel top = new JPanel();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(top, gridBagConstraints);
        top.setOpaque(false);

        JLabel lblMin = new JLabel("-");
        lblMin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                framePrincipal.setState(1);
            }
        });
        lblMin.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMin.setForeground(CReturner.getClose());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMin.setForeground(CReturner.getTexto());
            }
        });
        lblMin.setForeground(CReturner.getTexto());
        lblMin.setFont(lemonB.deriveFont(24f));
        lblMin.setHorizontalAlignment(SwingConstants.CENTER);
        lblMin.setPreferredSize(new Dimension(35, 25));
        lblMin.setBackground(new Color(240, 245, 249));
        top.add(lblMin);
    }

    private void iniciarIcono() {
        JPanel izq = new JPanel();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(izq, gridBagConstraints);
        izq.setOpaque(false);
        JLabel icono = new JLabel(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcon() + "icon.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        icono.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                icono.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcon() + "iconFocus.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                icono.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcon() + "icon.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Credits creditos = new Credits();
                creditos.setLocationRelativeTo(null);
                creditos.setVisible(true);
            }
        });
        izq.add(icono);
    }

    private void iniciarBarraIzq() {
        JPanel izq = new JPanel();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        add(izq, gridBagConstraints);
        izq.setOpaque(false);

        JPanel columna = new JPanel();
        columna.setOpaque(false);
        columna.setLayout(new java.awt.GridBagLayout());

        home = new JLabel(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "homeSelected.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        home.setText(" INICIO");
        home.setFont(lemonB.deriveFont(20f));
        home.setForeground(CReturner.getAbsoluto());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 15, 0);
        home.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                home.setForeground(new Color(255, 36, 36));
                home.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcon() + "homeFocus.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                home.setForeground(CReturner.getAbsoluto());
                if (actualLabel.equals("Inicio")) {
                    home.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "homeSelected.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                } else {
                    home.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "home.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                }
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                content = new Inicio(esta, botBar, scrollPane, main, topBar, home, listaPlaylist, framePrincipal);
                cargarNuevoPanel();
                actualLabel = "Inicio";
                home.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "homeSelected.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                revalidate();
                repaint();
                setCursor(null);
            }
        });
        columna.add(home, gridBagConstraints);

        search = new JLabel(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "search.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        search.setText(" BUSCAR");
        search.setFont(lemonB.deriveFont(20f));
        search.setForeground(CReturner.getAbsoluto());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        search.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                search.setForeground(new Color(255, 36, 36));
                search.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcon() + "searchFocus.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                search.setForeground(CReturner.getAbsoluto());
                if (actualLabel.equals("Busqueda")) {
                    search.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "searchSelected.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                } else {
                    search.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "search.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                }
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                content = new Busqueda(listaPlaylist, esta, botBar, scrollPane, main, topBar, home, search, framePrincipal);
                cargarNuevoPanel();
                actualLabel = "Busqueda";
                search.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "searchSelected.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                revalidate();
                repaint();
                setCursor(null);
            }
        });
        columna.add(search, gridBagConstraints);

        library = new JLabel(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "library.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        library.setText(" BIBLIOTECA");
        library.setFont(lemonB.deriveFont(20f));
        library.setForeground(CReturner.getAbsoluto());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        library.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                library.setForeground(new Color(255, 36, 36));
                library.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcon() + "libraryFocus.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                library.setForeground(CReturner.getAbsoluto());
                if (actualLabel.equals("Biblioteca")) {
                    library.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "librarySelected.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                } else {
                    library.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "library.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                }
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                content = new Biblioteca(listaPlaylist, esta, botBar, scrollPane, main, topBar, home, framePrincipal);
                cargarNuevoPanel();
                actualLabel = "Biblioteca";
                library.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "librarySelected.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                revalidate();
                repaint();
                setCursor(null);
            }
        });
        columna.add(library, gridBagConstraints);

        JLabel space = new JLabel(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "space.png")).getImage()));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        columna.add(space, gridBagConstraints);

        JLabel addPlaylist = new JLabel(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "add.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        addPlaylist.setText(" CREAR PLAYLIST");
        addPlaylist.setFont(lemonB.deriveFont(14f));
        addPlaylist.setForeground(CReturner.getAbsoluto());
        addPlaylist.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addPlaylist.setForeground(new Color(255, 36, 36));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addPlaylist.setForeground(CReturner.getAbsoluto());
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CreatePlaylist cp = new CreatePlaylist();
                cp.setBounds(0, 0, 350, 270);
                cp.setLocationRelativeTo(null);
                cp.setVisible(true);
                cp.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        if (cp.getGuardado() == 1) {
                            cargarLista();
                            revalidate();
                            repaint();
                        }
                    }
                });
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        columna.add(addPlaylist, gridBagConstraints);

        listaPlaylist = new javax.swing.JPanel();
        listaPlaylist.setOpaque(false);
        listaPlaylist.setLayout(new java.awt.GridLayout(0, 1));
        cargarLista();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
        columna.add(listaPlaylist, gridBagConstraints);
        izq.add(columna);
    }

    private void cargarLista() {
        Statement sentencia;
        try {
            listaPlaylist.removeAll();
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT playlist.playlist_id ,playlist.picture, playlist.name "
                        + "FROM playlist, registro_savedlist "
                        + "WHERE playlist.playlist_id=registro_savedlist.playlist_id AND registro_savedlist.user='" + username + "'";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    while (resul.next()) {
                        ItemPlaylist elemento = new ItemPlaylist(resul.getString("playlist_id"), resul.getString("picture"), resul.getString("name"), CReturner);
                        elemento.addMouseListener(new java.awt.event.MouseAdapter() {
                            @Override
                            public void mouseEntered(java.awt.event.MouseEvent evt) {
                                elemento.setColor(new Color(255, 36, 36));
                            }

                            @Override
                            public void mouseExited(java.awt.event.MouseEvent evt) {
                                elemento.setColor(CReturner.getAbsoluto());
                            }

                            @Override
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                content = new Playlist(elemento.getId(), listaPlaylist, esta, botBar, scrollPane, main, topBar, home, framePrincipal);
                                cargarNuevoPanel();
                                revalidate();
                                repaint();
                                setCursor(null);
                            }
                        });
                        listaPlaylist.add(elemento);
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iniciarBarraDer() {
        JPanel der = new JPanel();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.weighty = 0.1;
        add(der, gridBagConstraints);
        der.setOpaque(false);

        JLabel lblExit = new JLabel("X");
        lblExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblExit.setForeground(CReturner.getClose());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblExit.setForeground(CReturner.getTexto());
            }
        });
        lblExit.setForeground(CReturner.getTexto());
        lblExit.setFont(lemonB.deriveFont(18f));
        lblExit.setHorizontalAlignment(SwingConstants.CENTER);
        lblExit.setPreferredSize(new Dimension(35, 25));
        der.add(lblExit);
    }

    private void iniciarBotPage() {
        JPanel bot = new JPanel();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.weighty = 0.1;
        add(bot, gridBagConstraints);
        bot.setOpaque(false);
        JLabel fake = new JLabel(" ");
        fake.setPreferredSize(new Dimension(10, 25));
        bot.add(fake);
    }

    private void iniciarPanelPrincipal() {

        main = new RoundedPanel(35, CReturner.getBackground());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 10;
        gridBagConstraints.weighty = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        main.setBackground(CReturner.getBackground());
        main.setOpaque(false);
        add(main, gridBagConstraints);

        main.setLayout(new javax.swing.BoxLayout(main, javax.swing.BoxLayout.PAGE_AXIS));

        scrollPane = new JScrollPane();
        botBar = new BotBar();
        topBar = new TopBar(listaPlaylist, esta, botBar, scrollPane, main, home, framePrincipal);
        content = new Inicio(this, botBar, scrollPane, main, topBar, home, listaPlaylist, framePrincipal);
        scrollPane.setVerticalScrollBar(new ScrollBar());
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(content);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        main.add(topBar, gridBagConstraints);

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

    private void cargarNuevoPanel() {
        clearIconos();
        main.removeAll();

        topBar = new TopBar(listaPlaylist, esta, botBar, scrollPane, main, home, framePrincipal);
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

    private void clearIconos() {
        home.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "home.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        search.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "search.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        library.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "library.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
    }
}
