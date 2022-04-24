package album;

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
public class AlbumTableModel extends AbstractTableModel {

    private List contenido;

    public AlbumTableModel(List contenido) {
        this.contenido = contenido;
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return AlbumFeed.class;
    }

    @Override
    public int getColumnCount() {
        return 3;
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
