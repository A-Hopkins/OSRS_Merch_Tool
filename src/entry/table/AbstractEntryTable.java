package entry.table;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import entry.EntryInstance;
import tool.Gui;

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

	boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false };

	public boolean isCellEditable(int row, int column) {
		return columnEditables[column];
	}

	public AbstractEntryTable(ArrayList<EntryInstance> entries) {
		this.entryData = entries;
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
	public void setValueAt(Object object, int row, int column) {
		try {
			EntryInstance entryObject = entryData.get(row);
			if(entryObject == null) return;
			switch(column) {
				case 0:
					entryObject.setDate(object.toString());
					entryData.get(row).setDate(object.toString());
					break;
				case 1:
					entryObject.setName(object.toString());
					entryData.get(row).setName(object.toString());
					break;
				case 2:
					entryObject.setBought((int) object);
					entryData.get(row).setBought((int) object);
					break;
				case 3:
					entryObject.setSold((int) object);
					entryData.get(row).setSold((int) object);
					break;
				case 4:
					entryObject.setAmount((int) object);
					entryData.get(row).setAmount((int) object);
					break;
				default:
					break;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		Object entryAttribute = null;
		EntryInstance entryInstance = entryData.get(row);
		if(entryInstance==null)return null;
		switch(column) {
			case 0:
				entryAttribute = entryInstance.getDate();
				break;
			case 1:
				entryAttribute = entryInstance.getName();
				break;
			case 2:
				entryAttribute = Gui.formatNumber(entryInstance.getBought());
				break;
			case 3:
				entryAttribute = Gui.formatNumber(entryInstance.getSold());
				break;
			case 4:
				entryAttribute = Gui.formatNumber(entryInstance.getAmount());
				break;
			case 5:
				entryAttribute = Gui.formatNumber(Math.subtractExact(entryInstance.getSold(), entryInstance.getBought()));
				break;
			case 6:
				entryAttribute = Gui.formatNumber(Math.subtractExact(entryInstance.getSold(), entryInstance.getBought()) * entryInstance.getAmount());
				break;
			default:
				break;
		}
		return entryAttribute;
	}

	public void removeEntry(int entry) {
		entryData.remove(entry);
		fireTableDataChanged();
	}
}
