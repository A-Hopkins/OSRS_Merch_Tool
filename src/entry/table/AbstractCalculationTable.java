package entry.table;

import entry.EntryInstance;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Alex on 6/8/2017.
 *
 */
public class AbstractCalculationTable extends AbstractTableModel {

	public AbstractCalculationTable(ArrayList<EntryInstance> entries) {

	}

	@Override
	public int getRowCount() {
		return 0;
	}

	@Override
	public int getColumnCount() {
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return null;
	}
}
