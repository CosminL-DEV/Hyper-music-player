package tabla;

import com.sun.jdi.connect.LaunchingConnector;
import components.ImgCircleConverter;
import components.Utilities;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import themeManagement.ColorReturner;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 11-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class PlaylistCellRender implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        PlaylistFeed feed = (PlaylistFeed) value;
        ColorReturner CReturner = new ColorReturner();
        Font coolvetica = Utilities.cargarCoolvetica();
        ImgCircleConverter convertidor = new ImgCircleConverter();
        if (row == 0) {
            JPanel panel = new JPanel(new java.awt.GridLayout(1, 1));
            panel.setOpaque(false);
            panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CReturner.getTexto()));
            javax.swing.JLabel texto = new javax.swing.JLabel();
            texto.setFont(coolvetica.deriveFont(14f));
            texto.setForeground(CReturner.getTexto());
            panel.add(texto);
            switch (column) {
                case 0: {
                    texto.setText("TÍTULO");
                    return panel;
                }
                case 1: {
                    texto.setText("ÁLBUM");
                    return panel;
                }
                case 2: {
                    texto.setText("AÑADIDA POR");
                    return panel;
                }
                case 3: {
                    texto.setText("FECHA INCORPORACIÓN");
                    return panel;
                }
                case 4: {
                    texto.setText("DURACIÓN");
                    return panel;
                }
                default:
                    return null;
            }
        } else {
            switch (column) {
                case 0: {
                    CancionDisplay panel = new CancionDisplay(feed.getPlayIcon(), feed.getPortadaLink(), feed.getTituloSong(), feed.getIdCancion());
                    if (isSelected) {
                        panel.setOpaque(true);
                    } else {
                        panel.setOpaque(false);
                    }
                    return panel;
                }
                case 1: {
                    JPanel panel = new JPanel(new java.awt.GridLayout(1, 1));
                    panel.setOpaque(false);
                    panel.setBackground(CReturner.getSelected());
                    javax.swing.JLabel album = new javax.swing.JLabel();
                    album.setText(feed.getnAlbum());
                    album.setFont(coolvetica.deriveFont(14f));
                    album.setForeground(CReturner.getTexto());
                    panel.add(album);
                    if (isSelected) {
                        panel.setOpaque(true);
                    } else {
                        panel.setOpaque(false);
                    }
                    return panel;
                }
                case 2: {
                    JPanel panel = new JPanel(new java.awt.GridLayout(1, 1));
                    panel.setOpaque(false);
                    panel.setBackground(CReturner.getSelected());
                    javax.swing.JLabel user = new javax.swing.JLabel();
                    String picture = feed.getUserPicture();
                    if (picture == null) {
                        picture = "http://localhost/hyper/wp-content/uploads/2022/04/user.png";
                    }
                    ImageIcon img = new ImageIcon(convertidor.convertirImagen(Utilities.transformarLink(picture)));
                    user.setIcon(new ImageIcon(img.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
                    user.setText(feed.getUserAdded());
                    user.setFont(coolvetica.deriveFont(14f));
                    user.setForeground(CReturner.getTexto());
                    panel.add(user);
                    if (isSelected) {
                        panel.setOpaque(true);
                    } else {
                        panel.setOpaque(false);
                    }
                    return panel;
                }
                case 3: {
                    JPanel panel = new JPanel(new java.awt.GridLayout(1, 1));
                    panel.setOpaque(false);
                    panel.setBackground(CReturner.getSelected());
                    javax.swing.JLabel fecha = new javax.swing.JLabel();
                    Date now = new Date();
                    float a = Utilities.getDateDiff(feed.getDateAdded(), now, TimeUnit.MINUTES);
                    if (a < 60) {
                        fecha.setText((int) a + " mins ago");
                    } else if (a < 1440) {
                        a = a / 60;
                        if ((int) a == 1) {
                            fecha.setText((int) a + " hour ago");
                        } else {
                            fecha.setText((int) a + " hours ago");
                        }
                    } else {
                        a = a / 1440;
                        if ((int) a == 1) {
                            fecha.setText((int) a + " day ago");
                        } else {
                            fecha.setText((int) a + " days ago");
                        }
                    }
                    fecha.setFont(coolvetica.deriveFont(14f));
                    fecha.setForeground(CReturner.getTexto3());
                    panel.add(fecha);
                    if (isSelected) {
                        panel.setOpaque(true);
                    } else {
                        panel.setOpaque(false);
                    }
                    return panel;
                }
                case 4: {
                    /* Funcion no disponible, se ha usado numeros fijos para facilitar 
                    la implementacion mas adelante*/
                    JPanel panel = new JPanel(new java.awt.GridLayout(1, 1));
                    panel.setOpaque(false);
                    panel.setBackground(CReturner.getSelected());
                    javax.swing.JLabel duracion = new javax.swing.JLabel();
                    int minutos = 3;
                    int segundos = 33;
                    duracion.setText(minutos + ":" + segundos);
                    duracion.setFont(coolvetica.deriveFont(14f));
                    duracion.setForeground(CReturner.getTexto3());
                    panel.add(duracion);
                    if (isSelected) {
                        panel.setOpaque(true);
                    } else {
                        panel.setOpaque(false);
                    }
                    return panel;
                }
                default:
                    return null;
            }
        }
    }
}
