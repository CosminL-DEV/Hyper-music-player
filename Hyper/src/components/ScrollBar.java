package components;

import java.awt.Dimension;
import javax.swing.JScrollBar;
import themeManagement.ColorReturner;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 08-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class ScrollBar extends JScrollBar {

    private final ColorReturner CReturner = new ColorReturner();

    public ScrollBar() {
        setUI(new ModernScrollBar());
        setPreferredSize(new Dimension(5, 5));
        setBackground(CReturner.getBackground());
        setUnitIncrement(25);

    }
}
