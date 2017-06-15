package item.osbuddy.table;

import item.osbuddy.OSBuddyInstance;
import tool.Gui;

import javax.swing.table.AbstractTableModel;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Alex on 6/8/2017.
 *
 */
public class AbstractOSBuddyItemsTable extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<OSBuddyInstance> list;
	private final String[] columnNames =  {"Item ID", "Item Name",
			"Buy Average", "Sell Average", "Overall Average", "Members Item", "Store Price", "Potential Profit"};

	private final boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false };

	public boolean isCellEditable(int row, int column) {
		return columnEditables[column];
	}

	public AbstractOSBuddyItemsTable(HashMap<Integer, OSBuddyInstance> items) {
		list = new ArrayList<>(items.values());
		list = list.stream().sorted(Comparator.comparingInt(OSBuddyInstance::getId)).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	public AbstractOSBuddyItemsTable(Collection collection) {
		list = new ArrayList<>(collection);
		list = list.stream().sorted(Comparator.comparingInt(OSBuddyInstance::getId)).collect(Collectors.toList());
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
		return list.size();
	}

	@Override
	public void setValueAt(Object object, int row, int column) {
	}

	@Override
	public Object getValueAt(int row, int column) {
		Object entryAttribute = null;
		OSBuddyInstance itemObject = list.get(row);
		if(itemObject==null)return null;
		switch(column) {
			case 0:
				entryAttribute = Gui.formatNumber(itemObject.getId());
				break;
			case 1:
				entryAttribute = itemObject.getName();
				break;
			case 2:
				entryAttribute = Gui.formatNumber(itemObject.getBuyAverage());
				break;
			case 3:
				entryAttribute = Gui.formatNumber(itemObject.getSellAverage());
				break;
			case 4:
				entryAttribute = Gui.formatNumber(itemObject.getOverallAverage());
				break;
			case 5:
				entryAttribute = itemObject.isMembers();
				break;
			case 6:
				entryAttribute = Gui.formatNumber(itemObject.getStorePrice());
				break;
			case 7:
				entryAttribute = itemObject.getBuyAverage() == 0 ? Gui.formatNumber(Math.subtractExact(itemObject.getSellAverage(), itemObject.getBuyAverage())) + " - (Not Accurate!)" : Gui.formatNumber(Math.subtractExact(itemObject.getSellAverage(), itemObject.getBuyAverage()));
				break;
			default:
				break;
		}
		return entryAttribute;
	}

}
