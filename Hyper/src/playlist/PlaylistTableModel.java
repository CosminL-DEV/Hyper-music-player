package playlist;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class PlaylistTableModel extends AbstractTableModel {

    private List contenido;

    public PlaylistTableModel(List contenido) {
        this.contenido = contenido;
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return PlaylistFeed.class;
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return "TÍTULO";
    }

    @Override
    public int getRowCount() {
        return (contenido == null) ? 0 : contenido.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return (contenido == null) ? null : contenido.get(rowIndex);
    }

    @Override
    public boolean isCellEditable(int columnIndex, int rowIndex) {
        return false;
    }
}
