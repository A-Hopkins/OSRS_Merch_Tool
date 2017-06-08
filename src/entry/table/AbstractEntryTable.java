package entry.table;

import entry.EntryInstance;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Alex on 6/8/2017.
 *
 */
public class AbstractEntryTable extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private ArrayList<EntryInstance> entryData;
	private String[] columnNames =  {"Date", "Item Name", "Price Bought",
			"Price Sold", "Amount", "Individual Profit Margin", "Total Margin"};

	public AbstractEntryTable(ArrayList<EntryInstance> entries) {
		this.entryData = entries;
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
