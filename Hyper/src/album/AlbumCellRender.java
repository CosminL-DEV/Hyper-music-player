package album;

import appManagement.ColorReturner;
import appManagement.Utilities;
import components.CancionDisplay;
import java.awt.Component;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class AlbumCellRender implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        AlbumFeed feed = (AlbumFeed) value;
        ColorReturner CReturner = new ColorReturner();
        Font coolvetica = Utilities.cargarCoolvetica();
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
                    texto.setText("REPRODUCCIONES");
                    return panel;
                }
                case 2: {
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
                    /* Funcion no disponible, se ha usado numeros aleatorios para facilitar 
                    la implementacion mas adelante*/
                    JPanel panel = new JPanel(new java.awt.GridLayout(1, 1));
                    panel.setOpaque(false);
                    panel.setBackground(CReturner.getSelected());
                    javax.swing.JLabel repros = new javax.swing.JLabel();
                    String formatted = NumberFormat.getNumberInstance(Locale.GERMAN).format(new Random().nextInt(10000000));
                    repros.setText(formatted);
                    repros.setFont(coolvetica.deriveFont(14f));
                    repros.setForeground(CReturner.getTexto());
                    panel.add(repros);
                    if (isSelected) {
                        panel.setOpaque(true);
                    } else {
                        panel.setOpaque(false);
                    }
                    return panel;
                }
                case 2: {
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
