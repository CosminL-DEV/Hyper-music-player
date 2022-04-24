package songManager;

import components.BarraDeProgreso;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import appManagement.ColorReturner;
import java.io.File;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class BotBar extends JPanel {

    private final ColorReturner CReturner = new ColorReturner();
    private java.awt.GridBagConstraints gridBagConstraints;
    private ImageIcon aleatorioIcon;
    private ImageIcon aleatorioSelectIcon;
    private ImageIcon anteriorIcon;
    private ImageIcon playIcon;
    private ImageIcon resumeIcon;
    private ImageIcon siguienteIcon;
    private ImageIcon repetirIcon;
    private ImageIcon repetirSelectIcon;
    private ImageIcon colaIcon;
    private ImageIcon volumenIcon;
    private ImageIcon volumenMutedIcon;
    private QueueManager qm = null;
    private SongDisplay sd;
    private JPanel mid;
    private JLabel parar;
    private JLabel aleatorio;
    private JLabel anterior;
    private JLabel siguiente;
    private JLabel repetir;
    private JSlider barraVol;
    private JSlider progreso;
    private boolean muted = false;
    private Timer timer;
    private ArrayList<String> lista = new ArrayList<>();
    private boolean modoRepeticion = false;
    private boolean modoAleatorio = false;
    private boolean descargada = false;
    private File archivoCancion;
    private boolean reproduciendo = false;
    private Long currentFrame;
    private Clip clip;
    private String linkCancionActual;
    private float volumenSelected = 50;

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
        progress.setLayout(new java.awt.BorderLayout());
        progreso = new JSlider();
        progreso.setOpaque(true);
        progreso.setUI(new BarraDeProgreso(barraVol));
        progreso.setMinimum(0);
        progreso.setMaximum(100);
        progreso.setValue(0);
        progreso.setBackground(CReturner.getPrincipal());
        progreso.setForeground(CReturner.getTexto());
        progreso.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (reproduciendo) {
                    currentFrame = (long) (progreso.getValue() * 1000000);
                    if (currentFrame > 0 && currentFrame < clip.getMicrosecondLength()) {
                        clip.stop();
                        clip.close();
                        resetAudioStream();
                        clip.setMicrosecondPosition(currentFrame);
                        clip.start();
                    }
                }
            }
        }
        );
        progress.add(progreso, java.awt.BorderLayout.CENTER);
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
        aleatorioSelectIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "aleatorioSelected.png"));
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
        repetirSelectIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific() + "repetirSelected.png"));
        repetir.setIcon(new ImageIcon(repetirIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
        izq.add(repetir);
        aleatorio.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (modoAleatorio) {
                    aleatorio.setIcon(new ImageIcon(aleatorioSelectIcon.getImage().getScaledInstance(35, 36, Image.SCALE_SMOOTH)));
                } else {
                    aleatorio.setIcon(new ImageIcon(aleatorioIcon.getImage().getScaledInstance(35, 36, Image.SCALE_SMOOTH)));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (modoAleatorio) {
                    aleatorio.setIcon(new ImageIcon(aleatorioSelectIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
                } else {
                    aleatorio.setIcon(new ImageIcon(aleatorioIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
                }
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (modoAleatorio) {
                    aleatorio.setIcon(new ImageIcon(aleatorioIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
                    modoAleatorio = false;
                    ArrayList<String> lista2 = new ArrayList<>();
                    boolean comenzar = false;
                    for (String elem : lista) {
                        if (comenzar) {
                            lista2.add(elem);
                        }
                        if (elem.equals(qm.getActual().getId())) {
                            comenzar = true;
                        }
                    }
                    qm.setListaNormal(lista2);
                } else {
                    aleatorio.setIcon(new ImageIcon(aleatorioSelectIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
                    modoAleatorio = true;
                    ArrayList<String> lista2 = new ArrayList<>();
                    lista2.addAll(lista);
                    qm.setListaRandom(lista2);
                }
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
                if (reproduciendo) {
                    qm.anteriorCancion();
                    String cancion = qm.getActual().getId();
                    if (!cancion.equals("null")) {
                        clip.stop();
                        timer.stop();
                        clip.close();
                        mid.remove(sd);
                        sd = new SongDisplay(cancion);
                        mid.add(sd);
                        parar.setIcon(new ImageIcon(playIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                        if (cancion != null) {
                            buscarDescarga(cancion);
                            if (!descargada) {
                                getLinkCancion(cancion);
                            }
                            reproduciendo = true;
                            parar.setIcon(new ImageIcon(resumeIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                            reproducirCancion();
                            addListenerClip();
                        }
                        revalidate();
                        repaint();
                    } else {
                        qm.reiniciarActual();
                    }
                }
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
                if (reproduciendo) {
                    currentFrame = clip.getMicrosecondPosition();
                    clip.stop();
                    reproduciendo = false;
                    timer.stop();
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
                if (reproduciendo) {
                    clip.stop();
                    timer.stop();
                    clip.close();
                    qm.nextCancion();
                    String cancion = qm.getActual().getId();
                    if (cancion.equals("null")) {
                        cancion = null;
                        reproduciendo = false;
                        parar.setIcon(new ImageIcon(playIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                    }
                    mid.remove(sd);
                    sd = new SongDisplay(cancion);
                    mid.add(sd);
                    parar.setIcon(new ImageIcon(playIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                    if (cancion != null) {
                        buscarDescarga(cancion);
                        if (!descargada) {
                            getLinkCancion(cancion);
                        }
                        reproduciendo = true;
                        parar.setIcon(new ImageIcon(resumeIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                        reproducirCancion();
                        addListenerClip();
                    }
                    revalidate();
                    repaint();
                }
            }
        });
        repetir.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (modoRepeticion) {
                    repetir.setIcon(new ImageIcon(repetirSelectIcon.getImage().getScaledInstance(35, 36, Image.SCALE_SMOOTH)));
                } else {
                    repetir.setIcon(new ImageIcon(repetirIcon.getImage().getScaledInstance(35, 36, Image.SCALE_SMOOTH)));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (modoRepeticion) {
                    repetir.setIcon(new ImageIcon(repetirSelectIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
                } else {
                    repetir.setIcon(new ImageIcon(repetirIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
                }
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (reproduciendo) {
                    if (modoRepeticion) {
                        modoRepeticion = false;
                        repetir.setIcon(new ImageIcon(repetirIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
                    } else {
                        modoRepeticion = true;
                        repetir.setIcon(new ImageIcon(repetirSelectIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
                    }
                }
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
        drch.setOpaque(false);
        drch.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));
        colaIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "cola.png"));
        cola.setIcon(new ImageIcon(colaIcon.getImage().getScaledInstance(25, 35, Image.SCALE_SMOOTH)));
        drch.add(cola);
        volumenIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "volumen.png"));
        volumenMutedIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "volumenMuted.png"));
        volumen.setIcon(new ImageIcon(volumenIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
        barraVol = new JSlider();
        barraVol.setOpaque(false);
        barraVol.setUI(new BarraDeProgreso(barraVol));
        barraVol.setMinimum(0);
        barraVol.setMaximum(100);
        barraVol.setValue(50);
        barraVol.setBackground(CReturner.getPrincipal());
        barraVol.setForeground(CReturner.getTexto());
        barraVol.addChangeListener((ChangeEvent e) -> {
            if (reproduciendo) {
                volumenSelected = barraVol.getValue();
                setVolume(volumenSelected);
                if (volumenSelected == 0) {
                    volumen.setIcon(new ImageIcon(volumenMutedIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
                    muted = true;
                } else {
                    volumen.setIcon(new ImageIcon(volumenIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
                    muted = false;
                }
            }
        });
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
                QueueViewer qv;
                if (qm != null) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    qv = new QueueViewer(qm);
                    setCursor(null);
                }
            }
        });
        volumen.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (reproduciendo) {
                    if (muted) {
                        setVolume(30);
                        barraVol.setValue(30);
                        volumen.setIcon(new ImageIcon(volumenIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
                    } else {
                        setVolume(0);
                        barraVol.setValue(0);
                        volumen.setIcon(new ImageIcon(volumenMutedIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
                    }
                }
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

    private void setVolume(float volume) {
        volume /= 100;
        if (volume < 0f || volume > 1f) {
            throw new IllegalArgumentException("Volume not valid: " + volume);
        }
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    private void getLinkCancion(String id) {
        Statement sentencia;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hyper", "root", "root")) {
                sentencia = conexion.createStatement();
                String sql = "SELECT song.url "
                        + "FROM song "
                        + "WHERE song.song_id = '" + id + "'";
                try (ResultSet resul = sentencia.executeQuery(sql)) {
                    if (resul.next()) {
                        linkCancionActual = resul.getString("url");
                    }
                }
                sentencia.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(BotBar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addToCola(String cancion) {
        if (qm == null) {
            addCancion(cancion);
        } else {
            qm.addCola(cancion);
        }
    }

    public void addListaDeCanciones(ArrayList<String> lista, String cancionSelected) {
        qm = new QueueManager();
        this.lista.removeAll(this.lista);
        if (clip != null) {
            if (clip.isActive()) {
                clip.stop();
                if (clip.isOpen()) {
                    clip.close();
                }
            }
        }
        this.lista = lista;
        ArrayList<String> lista2 = new ArrayList<>();
        boolean comenzar = false;
        for (String elem : lista) {
            if (elem.equals(cancionSelected)) {
                comenzar = true;
            }
            if (comenzar) {
                lista2.add(elem);
            }
        }
        qm.addLista(lista2);
        buscarDescarga(qm.getActual().getId());
        if (!descargada) {
            getLinkCancion(qm.getActual().getId());
        }
        mid.remove(sd);
        sd = new SongDisplay(qm.getActual().getId());
        mid.add(sd);
        reproduciendo = true;
        parar.setIcon(new ImageIcon(resumeIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        reproducirCancion();
        addListenerClip();
    }

    public void addCancion(String cancion) {
        qm = new QueueManager();
        lista.removeAll(lista);
        lista.add(cancion);
        qm.addLista(lista);
        buscarDescarga(cancion);
        if (!descargada) {
            getLinkCancion(cancion);
        }
        mid.remove(sd);
        sd = new SongDisplay(cancion);
        mid.add(sd);
        reproduciendo = true;
        parar.setIcon(new ImageIcon(resumeIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        reproducirCancion();
        addListenerClip();
    }

    private void addListenerClip() {
        clip.addLineListener((LineEvent e) -> {
            if (e.getType() == LineEvent.Type.STOP) {
                if (clip.getMicrosecondLength() == clip.getMicrosecondPosition()) {
                    currentFrame = (long) 1;
                    if (modoRepeticion) {
                        clip.close();
                        resetAudioStream();
                        clip.setMicrosecondPosition(currentFrame);
                        clip.start();
                    } else {
                        clip.stop();
                        clip.close();
                        qm.nextCancion();
                        String cancion2 = qm.getActual().getId();
                        if (cancion2.equals("null")) {
                            cancion2 = null;
                            reproduciendo = false;
                            parar.setIcon(new ImageIcon(playIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                        }
                        mid.remove(sd);
                        sd = new SongDisplay(cancion2);
                        mid.add(sd);
                        parar.setIcon(new ImageIcon(playIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                        if (cancion2 != null) {
                            getLinkCancion(cancion2);
                            reproduciendo = true;
                            parar.setIcon(new ImageIcon(resumeIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                            reproducirCancion();
                        }
                        revalidate();
                        repaint();
                        addListenerClip();
                    }
                }
            }
        });
    }

    public void reproducirCancion() {
        try {
            AudioInputStream stream = null;
            try {
                if (descargada) {
                    stream = AudioSystem.getAudioInputStream(archivoCancion);
                } else {
                    stream = AudioSystem.getAudioInputStream(new URL(linkCancionActual));
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(BotBar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException | IOException ex) {
                Logger.getLogger(BotBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.loop(0);
            setVolume(volumenSelected);
            progreso.setMaximum((int) (clip.getMicrosecondLength() / 1000000));
        } catch (LineUnavailableException | IOException ex) {
            Logger.getLogger(BotBar.class.getName()).log(Level.SEVERE, null, ex);
        }
        timer = new javax.swing.Timer(500, (ActionEvent e) -> {
            if (reproduciendo) {
                progreso.setValue((int) (clip.getMicrosecondPosition() / 1000000));
            }
        });
        timer.start();
    }

    private void buscarDescarga(String elem) {
        archivoCancion = new File(CReturner.getFolderPath().toString() + "/" + elem + ".wav");
        descargada = archivoCancion.exists();
    }

    private void resetAudioStream() {
        try {
            AudioInputStream stream;
            if (descargada) {
                stream = AudioSystem.getAudioInputStream(archivoCancion);
            } else {
                stream = AudioSystem.getAudioInputStream(new URL(linkCancionActual));
            }
            clip.open(stream);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(BotBar.class.getName()).log(Level.SEVERE, null, ex);
        }
        timer.start();
    }
}
