package songManager;

import components.ScrollBar;
import appManagement.Utilities;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import appManagement.ColorReturner;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class QueueViewer extends JDialog {

    private final ColorReturner CReturner = new ColorReturner();
    private java.awt.GridBagConstraints gridBagConstraints;
    private final Font coolvetica = Utilities.cargarCoolvetica();
    private final JScrollPane scrollPane = new JScrollPane();
    private final JPanel content = new JPanel();
    private QueueManager qm;
    private QueueViewer este;
    private Popup popup;
    private JPanel contActual;
    private JPanel contSiguientes;

    public QueueViewer(QueueManager qm) {
        this.qm = qm;
        este = this;
        setBounds(0, 0, 300, 400);
        setLocationRelativeTo(null);
        setBackground(CReturner.getBackground());
        setUndecorated(true);
        setResizable(false);
        content.setBackground(CReturner.getBackground());
        content.setLayout(new java.awt.GridBagLayout());
        addPrimeraLinea();
        addActual();
        addSiguientes();
        scrollPane.setVerticalScrollBar(new ScrollBar());
        scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(CReturner.getTexto(), 2));
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(content);
        getContentPane().setLayout(new BorderLayout(0, 0));
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
        setCursor(null);
    }

    private void addPrimeraLinea() {
        javax.swing.JPanel linea1 = new javax.swing.JPanel();
        linea1.setOpaque(false);
        linea1.setLayout(new java.awt.GridBagLayout());
        javax.swing.JLabel texto = new javax.swing.JLabel();
        javax.swing.JLabel recargar = new javax.swing.JLabel();
        javax.swing.JLabel close = new javax.swing.JLabel();

        texto.setText("Cola");
        texto.setForeground(CReturner.getTexto());
        texto.setFont(coolvetica.deriveFont(22f));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        linea1.add(texto, gridBagConstraints);

        ImageIcon recargarIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsOpuestos() + "repetir.png"));
        recargar.setIcon(new ImageIcon(recargarIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        recargar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                content.remove(contActual);
                content.remove(contSiguientes);
                addActual();
                addSiguientes();
                este.revalidate();
                este.repaint();
                setCursor(null);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (popup != null) {
                    popup.hide();
                }
                JLabel text = new JLabel("Click para refrescar la cola");
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
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 0.1;
        linea1.add(recargar, gridBagConstraints);

        close.setText("X");
        close.setForeground(CReturner.getClose());
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
                close.setFont(coolvetica.deriveFont(23f));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                close.setFont(coolvetica.deriveFont(22f));
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        content.add(linea1, gridBagConstraints);
    }

    private void addActual() {
        contActual = new JPanel();
        contActual.setOpaque(false);
        contActual.setLayout(new java.awt.GridBagLayout());

        javax.swing.JLabel texto = new javax.swing.JLabel("Sonando");
        texto.setForeground(CReturner.getTexto2());
        texto.setFont(coolvetica.deriveFont(16f));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        contActual.add(texto, gridBagConstraints);

        SongDisplay sd;
        if (qm.getActual().getId().equals("null")) {
            sd = new SongDisplay(null);
        } else {
            sd = new SongDisplay(qm.getActual().getId());
        }
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        contActual.add(sd, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        content.add(contActual, gridBagConstraints);
    }

    private void addSiguientes() {
        contSiguientes = new JPanel();
        contSiguientes.setOpaque(false);
        contSiguientes.setLayout(new java.awt.GridBagLayout());

        javax.swing.JLabel texto = new javax.swing.JLabel("Siguientes");
        texto.setForeground(CReturner.getTexto2());
        texto.setFont(coolvetica.deriveFont(16f));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        contSiguientes.add(texto, gridBagConstraints);

        SongDisplay sd;
        String[] nextSongs = qm.toString().split(",");
        int contador = 0;
        for (String nextSong : nextSongs) {
            contador++;
            sd = new SongDisplay(nextSong);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridy = contador;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
            gridBagConstraints.weightx = 0.1;
            gridBagConstraints.weighty = 0.1;
            gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
            contSiguientes.add(sd, gridBagConstraints);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        content.add(contSiguientes, gridBagConstraints);
    }
}
