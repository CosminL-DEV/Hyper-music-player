package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import themeManagement.ColorReturner;

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
    java.awt.GridBagConstraints gridBagConstraints;
    ImageIcon aleatorioIcon;
    ImageIcon anteriorIcon;
    ImageIcon pararIcon;
    ImageIcon siguienteIcon;
    ImageIcon repetirIcon;
    ImageIcon colaIcon;
    ImageIcon volumenIcon;

    public BotBar() {
        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());
        setMaximumSize(new Dimension(3000,60));
        addTop();
        addIzq();
        addMid();
        addDrch();
    }

    private void addTop(){
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
    
    private void addIzq(){
        JPanel izq = new javax.swing.JPanel();
        JLabel aleatorio = new javax.swing.JLabel();
        JLabel anterior = new javax.swing.JLabel();
        JLabel parar = new javax.swing.JLabel();
        JLabel siguiente = new javax.swing.JLabel();
        JLabel repetir = new javax.swing.JLabel();
        izq.setOpaque(false);
        izq.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
        aleatorioIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "aleatorio.png"));
        aleatorio.setIcon(new ImageIcon(aleatorioIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
        izq.add(aleatorio);
        anteriorIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "anterior.png"));
        anterior.setIcon(new ImageIcon(anteriorIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
        izq.add(anterior);
        pararIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIconsSpecific()+ "play.png"));
        parar.setIcon(new ImageIcon(pararIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        izq.add(parar);
        siguienteIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "siguiente.png"));
        siguiente.setIcon(new ImageIcon(siguienteIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
        izq.add(siguiente);
        repetirIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "repetir.png"));
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
                // Accion
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
                // Modificar x en vez de y si consigo que ocupe mas el mid.
                parar.setIcon(new ImageIcon(pararIcon.getImage().getScaledInstance(61, 60, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                parar.setIcon(new ImageIcon(pararIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Accion
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
    
    private void addMid(){
        JPanel mid = new javax.swing.JPanel();
        mid.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
        JLabel jLabel6 = new javax.swing.JLabel();
        mid.setOpaque(true);
        mid.setBackground(Color.red);
        jLabel6.setText("Aqui la cancion");
        mid.add(jLabel6);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        add(mid, gridBagConstraints);
    }
    
    private void addDrch(){
        JPanel drch = new javax.swing.JPanel();
        JLabel cola = new javax.swing.JLabel();
        JPanel controlVol = new javax.swing.JPanel();
        JLabel volumen = new javax.swing.JLabel();
        JLabel barraVol = new javax.swing.JLabel();
        drch.setOpaque(false);
        drch.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));
        colaIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "cola.png"));
        cola.setIcon(new ImageIcon(colaIcon.getImage().getScaledInstance(25, 35, Image.SCALE_SMOOTH)));
        drch.add(cola);
        volumenIcon = new javax.swing.ImageIcon(getClass().getResource(CReturner.getIcons() + "volumen.png"));
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
}
