package components;

import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import themeManagement.ColorReturner;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 15-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class AdderSongInAlbum extends JPanel {

    private JTextField inputNombre;
    private Combobox genre;
    private File archivoCancion;
    private boolean cancionSeleccionada = false;
    private JLabel nombreArchivo;

    public AdderSongInAlbum() {
        java.awt.GridBagConstraints gridBagConstraints;
        ColorReturner CReturner = new ColorReturner();
        final Font coolvetica = Utilities.cargarCoolvetica();
        setOpaque(false);

        inputNombre = new javax.swing.JTextField();
        JPanel contNombre = new javax.swing.JPanel();
        contNombre.setOpaque(false);
        contNombre.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CReturner.getTexto()));
        inputNombre.setText("Nombre canción");
        inputNombre.setForeground(CReturner.getTexto());
        inputNombre.setBackground(CReturner.getBackground());
        inputNombre.setBorder(null);
        inputNombre.setFont(coolvetica.deriveFont(15f));
        inputNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (inputNombre.getText().length() > 20) {
                    inputNombre.setText(inputNombre.getText().substring(0, 25));
                }
            }
        });
        contNombre.setLayout(new java.awt.GridBagLayout());
        inputNombre.setPreferredSize(new Dimension(150, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        contNombre.add(inputNombre, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        add(contNombre, gridBagConstraints);

        genre = new components.Combobox();
        genre.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[]{"trap", "reggaeton", "rap", "techno", "house"}
        ));
        genre.setLabeText("");
        genre.setSelectedItem(CReturner.getTemaActual());
        genre.setBackground(CReturner.getTexto2());
        genre.setForeground(CReturner.getTexto());
        genre.setBorder(null);
        genre.setPreferredSize(new Dimension(150, 30));
        genre.setFont(coolvetica.deriveFont(15f));
        genre.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        add(genre, gridBagConstraints);

        nombreArchivo = new JLabel("");
        nombreArchivo.setForeground(CReturner.getTexto2());
        nombreArchivo.setFont(coolvetica.deriveFont(13f));

        javax.swing.JButton subirCancion = new javax.swing.JButton();
        subirCancion.setText("Seleccionar Canción");
        subirCancion.setFont(coolvetica.deriveFont(14f));
        subirCancion.setForeground(CReturner.getBackground());
        subirCancion.setBackground(CReturner.getTexto());
        subirCancion.setBorder(null);
        subirCancion.setPreferredSize(new Dimension(150, 30));
        subirCancion.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JFileChooser inputSong = new JFileChooser();
                FileFilter songFilter = new FileNameExtensionFilter("wav files", "wav");
                inputSong.setFileFilter(songFilter);
                int returnVal = inputSong.showOpenDialog(AdderSongInAlbum.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    archivoCancion = inputSong.getSelectedFile();
                    nombreArchivo.setText(archivoCancion.getName());
                    cancionSeleccionada = true;
                } else {
                    cancionSeleccionada = false;
                }
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
        add(subirCancion, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 2);
        add(nombreArchivo, gridBagConstraints);
    }

    public JTextField getInputNombre() {
        return inputNombre;
    }

    public Combobox getGenre() {
        return genre;
    }

    public File getArchivoCancion() {
        return archivoCancion;
    }

    public boolean isCancionSeleccionada() {
        return cancionSeleccionada;
    }
}
