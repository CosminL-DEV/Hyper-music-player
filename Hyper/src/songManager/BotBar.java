package songManager;

import interfaz.Interfaz;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pruebas.FramePrueba;
import songManager.QueueManager.Cancion;
import themeManagement.ColorReturner;
import javax.sound.sampled.Clip;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 22-03-2022
 * @version 1.0
 *
 * ************************************
 */
public class BotBar extends JPanel {

    private final ColorReturner CReturner = new ColorReturner();
    private java.awt.GridBagConstraints gridBagConstraints;
    private ImageIcon aleatorioIcon;
    private ImageIcon anteriorIcon;
    private ImageIcon playIcon;
    private ImageIcon resumeIcon;
    private ImageIcon siguienteIcon;
    private ImageIcon repetirIcon;
    private ImageIcon colaIcon;
    private ImageIcon volumenIcon;
    private QueueManager qm;
    private SongDisplay sd;
    private JPanel mid;
    private JLabel parar;
    private JLabel aleatorio;
    private JLabel anterior;
    private JLabel siguiente;
    private JLabel repetir;

    //Para reproduccion de canciones:
    ArrayList<String> lista = new ArrayList<String>();
    private boolean modoRepeticion = false;
    private boolean descargada = false;

    private boolean reproduciendo = false;
    private boolean activo = false;
    private Long currentFrame;
    private Clip clip;
    private String linkCancionActual = "http://localhost/hyper/wp-content/uploads/2022/03/JC-REYES-FT-EL-DADDY-TO-O-NA.wav";

    public BotBar() {
        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());
        setMaximumSize(new Dimension(3000, 60));
        addTop();
        addIzq();
        addMid();
        addDrch();
    }

    private void addTop() {
        JPanel progress = new javax.swing.JPanel();
        JLabel jLabel7 = new javax.swing.JLabel();
        progress.setOpaque(true);
        jLabel7.setText("Progreso de la cancion");
        progress.add(jLabel7);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(progress, gridBagConstraints);
    }

    private void addIzq() {
        JPanel izq = new javax.swing.JPanel();
        aleatorio = new javax.swing.JLabel();
        anterior = new javax.swing.JLabel();
        parar = new javax.swing.JLabel();
        siguiente = new javax.swing.JLabel();
        repetir = new javax.swing.JLabel();
        izq.setOpaque(false);
        izq.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
        aleatorioIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "aleatorio.png"));
        aleatorio.setIcon(new ImageIcon(aleatorioIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
        izq.add(aleatorio);
        anteriorIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "anterior.png"));
        anterior.setIcon(new ImageIcon(anteriorIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
        izq.add(anterior);
        playIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "play.png"));
        resumeIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "parar.png"));
        parar.setIcon(new ImageIcon(playIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        izq.add(parar);
        siguienteIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "siguiente.png"));
        siguiente.setIcon(new ImageIcon(siguienteIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
        izq.add(siguiente);
        repetirIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "repetir.png"));
        repetir.setIcon(new ImageIcon(repetirIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
        izq.add(repetir);
        aleatorio.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                aleatorio.setIcon(new ImageIcon(aleatorioIcon.getImage().getScaledInstance(35, 36, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                aleatorio.setIcon(new ImageIcon(aleatorioIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println(qm.getActual());
            }
        });
        anterior.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                anterior.setIcon(new ImageIcon(anteriorIcon.getImage().getScaledInstance(35, 36, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                anterior.setIcon(new ImageIcon(anteriorIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Accion
            }
        });
        parar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (reproduciendo) {
                    parar.setIcon(new ImageIcon(resumeIcon.getImage().getScaledInstance(61, 60, Image.SCALE_SMOOTH)));
                } else {
                    parar.setIcon(new ImageIcon(playIcon.getImage().getScaledInstance(61, 60, Image.SCALE_SMOOTH)));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (reproduciendo) {
                    parar.setIcon(new ImageIcon(resumeIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                } else {
                    parar.setIcon(new ImageIcon(playIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                }
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (activo) {
                    if (reproduciendo) {
                        currentFrame = clip.getMicrosecondPosition();
                        clip.stop();
                        reproduciendo = false;
                        parar.setIcon(new ImageIcon(playIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                    } else {
                        clip.close();
                        resetAudioStream();
                        clip.setMicrosecondPosition(currentFrame);
                        clip.start();
                        reproduciendo = true;
                        parar.setIcon(new ImageIcon(resumeIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                    }
                }
            }
        });
        siguiente.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                siguiente.setIcon(new ImageIcon(siguienteIcon.getImage().getScaledInstance(35, 36, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                siguiente.setIcon(new ImageIcon(siguienteIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Accion
            }
        });
        repetir.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                repetir.setIcon(new ImageIcon(repetirIcon.getImage().getScaledInstance(35, 36, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                repetir.setIcon(new ImageIcon(repetirIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Accion
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(izq, gridBagConstraints);
    }

    private void addMid() {
        mid = new javax.swing.JPanel();
        mid.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
        mid.setOpaque(false);
        sd = new SongDisplay(null);
        mid.add(sd);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        add(mid, gridBagConstraints);
    }

    private void addDrch() {
        JPanel drch = new javax.swing.JPanel();
        JLabel cola = new javax.swing.JLabel();
        JPanel controlVol = new javax.swing.JPanel();
        JLabel volumen = new javax.swing.JLabel();
        JLabel barraVol = new javax.swing.JLabel();
        drch.setOpaque(false);
        drch.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));
        colaIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "cola.png"));
        cola.setIcon(new ImageIcon(colaIcon.getImage().getScaledInstance(25, 35, Image.SCALE_SMOOTH)));
        drch.add(cola);
        volumenIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "volumen.png"));
        volumen.setIcon(new ImageIcon(volumenIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
        barraVol.setText("Barra volumen");
        controlVol.setOpaque(false);
        controlVol.add(volumen);
        controlVol.add(barraVol);
        drch.add(controlVol);
        cola.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cola.setIcon(new ImageIcon(colaIcon.getImage().getScaledInstance(25, 36, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cola.setIcon(new ImageIcon(colaIcon.getImage().getScaledInstance(25, 35, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Accion
            }
        });
        volumen.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Accion
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(drch, gridBagConstraints);
    }

    private void getLinkCancion(String id) {
        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root");
            sentencia = conexion.createStatement();
            String sql = "SELECT song.url "
                    + "FROM song "
                    + "WHERE song.song_id = '" + id + "'";
            ResultSet resul = sentencia.executeQuery(sql);
            if (resul.next()) {
                linkCancionActual = resul.getString("url");
            }
            resul.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(BotBar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addListaDeCanciones(ArrayList<String> lista) {
        qm = new QueueManager();
        this.lista = lista;
        qm.addLista(lista);
    }

    public void addCancion(String cancion) {
        qm = new QueueManager();
        lista.add(cancion);
        qm.addLista(lista);
        getLinkCancion(cancion);
        mid.remove(sd);
        sd = new SongDisplay(cancion);
        mid.add(sd);
        activarReproduccion();
        parar.setIcon(new ImageIcon(resumeIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        reproducirCancion();
    }

    private void activarReproduccion() {
        reproduciendo = true;
        activo = true;
    }

    private void desactivarReproduccion() {
        reproduciendo = false;
        activo = false;
    }

    public boolean comprobarRepeticion() {
        return true;
    }

    // Implementar en playlist y album.
    public void setDescargada(boolean descargada) {
        this.descargada = descargada;
    }

    public void reproducirCancion() {
        try {
            //AudioInputStream stream = AudioSystem.getAudioInputStream(new File("audiofile"));
            AudioInputStream stream = null;
            try {
                stream = AudioSystem.getAudioInputStream(new URL(linkCancionActual));
            } catch (MalformedURLException ex) {
                Logger.getLogger(BotBar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException | IOException ex) {
                Logger.getLogger(BotBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            clip = AudioSystem.getClip();
            clip.open(stream);
            // No loop, con loop es Clip.LOOP_CONTINUOUSLY
            clip.loop(0);

        } catch (LineUnavailableException | IOException ex) {
            Logger.getLogger(BotBar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void resetAudioStream() {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new URL(linkCancionActual));
            clip.open(stream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(BotBar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
