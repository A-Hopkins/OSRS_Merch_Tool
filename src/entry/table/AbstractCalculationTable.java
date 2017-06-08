package entry.table;

import entry.EntryInstance;
import tool.Gui;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Alex on 6/8/2017.
 *
 */
public class AbstractCalculationTable extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private final ArrayList<EntryInstance> entryData;
	private String[] columnNames = { "Total Transactions", "Total Invested Amount", "Total Invested Payback",
			"Total Invested In", "Total Individual Potential", "Total Made" };

	boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };

	public boolean isCellEditable(int row, int column) {
		return columnEditables[column];
	}

	public AbstractCalculationTable(ArrayList<EntryInstance> entryData) {
		this.entryData = entryData;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return entryData.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Object entryAttribute = null;
		switch (column) {
			case 0:
				entryAttribute = Gui.formatNumber(entryData.size());
				break;
			case 1:
				long total = 0;
				for (EntryInstance entry : entryData) {
					total += entry.getBought();
				}
				entryAttribute = Gui.formatNumber(total);
				break;
			case 2:
				long total2 = 0;
				for (EntryInstance entry : entryData) {
					total2 += entry.getSold();
				}
				entryAttribute = Gui.formatNumber(total2);
				break;
			case 3:
				long total3 = 0;
				for (EntryInstance entry : entryData) {
					total3 += entry.getAmount();
				}
				entryAttribute = Gui.formatNumber(total3);
				break;
			case 4:
				long total4 = 0;
				for (EntryInstance entry : entryData) {
					total4 += Math.subtractExact(entry.getSold(), entry.getBought());
				}
				entryAttribute = Gui.formatNumber(total4);
				break;
			case 5:
				long total5 = 0;
				for (EntryInstance entry : entryData) {
					total5 += Math.subtractExact(entry.getSold(), entry.getBought()) * entry.getAmount();
				}
				entryAttribute = Gui.formatNumber(total5);
				break;
			default:
				break;
		}
		return entryAttribute;
	}

}
