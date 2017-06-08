package tool;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entry.EntryInstance;
import entry.table.AbstractCalculationTable;
import entry.table.AbstractEntryTable;
import entry.table.edit.EditEntry;
import item.osbuddy.table.AbstractOSBuddyItemsTable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 6/7/2017.
 *
 * tool.Gui Implementation class
 */
public class Gui extends JFrame {

	private final JPanel pane;
	private final JTextField buyingTextField;
	private final JTextField sellingTextField;
	private final JTextField amountTextField;
	private final JTextField outputTextField;
	private final JTextField logEntryTextField;
	private final JTextField amountBoughtTextField;
	private final JTextField soldForTextField;
	private final JTextField amountSoldTextField;
	private JTable table;

	private ArrayList<EntryInstance> entries;
	private AbstractEntryTable entryTable;
	private AbstractCalculationTable calculationTable;
	private AbstractOSBuddyItemsTable itemTable;
	private int rowAtPoint;

	/**
	 *  Builds the UI of the Application and handles most of the processes.
	 *
	 */
	public Gui() throws IOException {

		/*
		 * General Application settings like size and title
		 */
		this.setBounds(100, 100, 800, 560);
		this.setTitle("OSRS Merch Tool | V.01");
		this.getContentPane().setLayout(null);
		this.setResizable(false);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		/*
		 * Instancing a JPanel
		 */
		pane = new JPanel();
		pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		pane.setLayout(new BorderLayout(0, 0));

		this.setContentPane(pane);

		/*
		 * Instancing a new tabbed pane at the top right of pane
		 */
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		pane.add(tabbedPane, BorderLayout.CENTER);

		/*
		 * Instancing a new JPanel
		 */
		JPanel tablePanel = new JPanel();

		tabbedPane.addTab("Logs and Calculations", null, tablePanel, null);

		tablePanel.setLayout(null);

		/*
		 * Loads data from a .json file into an ArrayList instanced by EntryInstance
		 */
		Gson gson = new Gson();
		ArrayList<EntryInstance> _entries = gson.fromJson(Files.newBufferedReader(Paths.get("data","items.json")), new TypeToken<List<EntryInstance>>() {
		}.getType());

		/*
		 * Set our global ArrayList to the local one loaded from the .json file.
		 */
		this.entries = _entries;

		/*
		 * Instance of a horizontal separator on the UI
		 */
		JSeparator horizontalSeperator = new JSeparator();
		horizontalSeperator.setForeground(Color.BLACK);
		horizontalSeperator.setBounds(10, 218, 760, 2);

		tablePanel.add(horizontalSeperator);

		/*
		 * Instance of a vertical separator on the UI
		 */
		JSeparator verticalSeperator = new JSeparator();
		verticalSeperator.setForeground(Color.BLACK);
		verticalSeperator.setOrientation(SwingConstants.VERTICAL);
		verticalSeperator.setBounds(390, 221, 2, 267);

		tablePanel.add(verticalSeperator);

		/*
		 * Instance of a JLabel Calculate Profit label
		 */
		JLabel calculateProfitLabel = new JLabel("Calculate Profit Margin");
		calculateProfitLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		calculateProfitLabel.setBounds(117, 225, 174, 18);

		tablePanel.add(calculateProfitLabel);

		/*
		 * Instance of a JLabel Log Entry label
		 */
		JLabel logEntryLabel = new JLabel("Log Entry");
		logEntryLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		logEntryLabel.setBounds(554, 222, 179, 20);

		tablePanel.add(logEntryLabel);

		/*
		 * Instance of a JLabel Buying Price label
		 */
		JLabel BuyingPriceLabel = new JLabel("Buying Price");
		BuyingPriceLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		BuyingPriceLabel.setBounds(50, 250, 79, 14);

		tablePanel.add(BuyingPriceLabel);

		/*
		 * Instance of JTextField Buying Text Field
		 * With key listener to only include digits
		 */
		buyingTextField = new JTextField();
		buyingTextField.setBounds(60, 269, 132, 20);
		buyingTextField.setColumns(10);

		buyingTextField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});

		tablePanel.add(buyingTextField);

		/*
		 * Instance of JLabel Selling Price Label
		 */
		JLabel SellingPriceLabel = new JLabel("Selling Price");
		SellingPriceLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		SellingPriceLabel.setBounds(50, 300, 79, 20);

		tablePanel.add(SellingPriceLabel);

		/*
		 * Instance of JTextField Selling Text Field
		 * With Key listener to only include digits
		 */
		sellingTextField = new JTextField();
		sellingTextField.setBounds(60, 331, 132, 20);
		sellingTextField.setColumns(10);

		sellingTextField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});

		tablePanel.add(sellingTextField);

		/*
		 * Instance of JLabel amountLabel
		 */
		JLabel amountLabel = new JLabel("Amount");
		amountLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		amountLabel.setBounds(50, 362, 105, 14);

		tablePanel.add(amountLabel);

		/*
		 * Instance of JTextField amountTextField
		 * With key listener to consume non digit inputs
		 */
		amountTextField = new JTextField();
		amountTextField.setBounds(60, 387, 132, 20);

		amountTextField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});

		tablePanel.add(amountTextField);

		/*
		 * Instance of JTextField outputTextField
		 * Not editable
		 */
		outputTextField = new JTextField();
		outputTextField.setEditable(false);
		outputTextField.setBounds(222, 331, 132, 20);
		outputTextField.setColumns(10);

		tablePanel.add(outputTextField);

		/*
		 * Instance of JButton Calculate margin
		 * Calculations created from Buying text field and selling text field and displayed in Output text field
		 */
		JButton marginsButton = new JButton("Calculate Margins");
		marginsButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		marginsButton.setBounds(50, 423, 142, 22);

		marginsButton.addActionListener(e -> {

			if (buyingTextField.toString() != null || buyingTextField.toString() != null) {
				if (!buyingTextField.getText().matches("\\d+")) {
					JOptionPane.showMessageDialog(null, "Buying Price Input contains invalid characters!",
							"An Error Occured!", JOptionPane.INFORMATION_MESSAGE, null);
					return;
				}
				if (!sellingTextField.getText().matches("\\d+")) {
					JOptionPane.showMessageDialog(null, "Selling Price Input contains invalid characters!",
							"An Error Occured!", JOptionPane.INFORMATION_MESSAGE, null);
					return;
				}
			}

			int buyInput = Integer.parseInt(buyingTextField.getText());
			int sellInput = Integer.parseInt(sellingTextField.getText());
			int amountInput = amountTextField.getText().equals("") ? 1
					: Integer.parseInt(amountTextField.getText());

			// TODO Break prevention -- stop calculations bigger than MAX INT
			if (amountInput > 1) {
				outputTextField.setText(String.valueOf(
						NumberFormat.getInstance().format(Math.subtractExact(buyInput, sellInput) * amountInput)));
			} else {
				outputTextField.setText(
						String.valueOf(NumberFormat.getInstance().format(Math.subtractExact(buyInput, sellInput))));
			}
		});

		tablePanel.add(marginsButton);

		/*
		 * Instance of JButton Clear button
		 *  Action listener for clearing text in buyingTextField, sellingTextField, amountTextField, and outputTextField
		 */
		JButton clearButton = new JButton("Clear");
		clearButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		clearButton.setBounds(198, 423, 160, 22);

		clearButton.addActionListener(e -> {
			buyingTextField.setText("");
			sellingTextField.setText("");
			amountTextField.setText("");
			outputTextField.setText("");
		});

		tablePanel.add(clearButton);

		/*
		 * Instance of JLabel Potential profit made
		 */
		JLabel potentialProfitMadeLabel = new JLabel("Potential Profit");
		potentialProfitMadeLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		potentialProfitMadeLabel.setBounds(211, 304, 143, 14);

		tablePanel.add(potentialProfitMadeLabel);

		/*
		 * Instance of JLabel Item Name
		 */
		JLabel itemNameLabel = new JLabel("Item Name");
		itemNameLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		itemNameLabel.setBounds(456, 251, 105, 14);

		tablePanel.add(itemNameLabel);

		/*
		 * Instnace of JTextField for log Entry Text field
		 */
		logEntryTextField = new JTextField();
		logEntryTextField.setBounds(466, 271, 277, 20);
		logEntryTextField.setColumns(10);

		tablePanel.add(logEntryTextField);

		/*
		 * Instance of JLabel Bought For
		 */
		JLabel boughtItemLabel = new JLabel("Bought For");
		boughtItemLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		boughtItemLabel.setBounds(456, 301, 92, 20);

		tablePanel.add(boughtItemLabel);

		/*
		 * Instance of textField for amount bought
		 * With key listener that consumes non digit characters
		 */
		amountBoughtTextField = new JTextField();
		amountBoughtTextField.setBounds(466, 321, 277, 20);
		amountBoughtTextField.setColumns(10);

		amountBoughtTextField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});

		tablePanel.add(amountBoughtTextField);

		/*
		 * Instance of JLabel Sold For
		 */
		JLabel soldForLabel = new JLabel("Sold For");
		soldForLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		soldForLabel.setBounds(456, 351, 105, 14);

		tablePanel.add(soldForLabel);

		/*
		 * Instance of JTextField soldForTextField
		 * With action listener that consumes non digit input
		 */
		soldForTextField = new JTextField();
		soldForTextField.setBounds(466, 371, 277, 20);
		soldForTextField.setColumns(10);

		soldForTextField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});

		tablePanel.add(soldForTextField);

		/*
		 * Instance of JLabel amountSoldLabel
		 */
		JLabel amountSoldLabel = new JLabel("Amount Sold");
		amountSoldLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		amountSoldLabel.setBounds(456, 401, 277, 14);

		tablePanel.add(amountSoldLabel);

		/*
		 * Instance of amountSoldTextField
		 * With key listener to consume non digit input
		 */
		amountSoldTextField = new JTextField();
		amountSoldTextField.setBounds(466, 421, 277, 20);
		amountSoldTextField.setColumns(10);

		amountSoldTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});

		tablePanel.add(amountSoldTextField);

		/*
		 * JButton to insert item flipped data/information
		 * Uses DateFormat to track dates
		 * With actionlistener to add to table
		 */
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		JButton tableInsertButton = new JButton("Insert to Table");
		tableInsertButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		tableInsertButton.setBounds(552, 447, 130, 22);

		tableInsertButton.addActionListener(arg0 -> {
			entries.add(new EntryInstance(dateFormat.format(date), logEntryLabel.getText(),
					Integer.parseInt(amountBoughtTextField.getText()),
					soldForTextField.getText().equals("") ? 1 : Integer.parseInt(soldForTextField.getText()),
					Integer.parseInt(amountSoldTextField.getText())));

			Gui.this.saveAll();

			logEntryLabel.setText("");
			amountBoughtTextField.setText("");
			soldForTextField.setText("");
			amountSoldTextField.setText("");
		});

		tablePanel.add(tableInsertButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 760, 144);
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tablePanel.add(scrollPane);

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
		entryTable = new AbstractEntryTable(this.entries);
		table = new JTable(entryTable) {
			
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				Object value = getModel().getValueAt(row, column);
				comp.setForeground(Color.BLACK);
				return comp;
			}
		};
		table.getTableHeader().setReorderingAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		table.setModel(entryTable);

		final JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem editItem = new JMenuItem("Edit Entry", new Icon() {
			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {

			}

			@Override
			public int getIconWidth() {
				return 0;
			}

			@Override
			public int getIconHeight() {
				return 0;
			}
		});
		JMenuItem deleteItem = new JMenuItem("Delete Entry");
		JMenuItem clearList = new JMenuItem("Clear Table");

		popupMenu.add(editItem);
		popupMenu.add(deleteItem);
		popupMenu.add(clearList);

		popupMenu.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						rowAtPoint = table
								.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), table));
						if (rowAtPoint > -1) {
							table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
						}
					}
				});
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {

			}
		});

		editItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Object itemName = entryTable.getValueAt(table.getSelectedRow(), 1);
							Object boughtAmount = entryTable.getValueAt(table.getSelectedRow(), 2);
							Object soldAmount = entryTable.getValueAt(table.getSelectedRow(), 3);
							Object itemAmount = entryTable.getValueAt(table.getSelectedRow(), 4);
							EditEntry edit = new EditEntry(entryTable, table, Gui.this);
							edit.textField.setText(itemName.toString());
							edit.textField_1.setText(Gui.unformatNumber(boughtAmount.toString()));
							edit.textField_2.setText(Gui.unformatNumber(soldAmount.toString()));
							edit.textField_3.setText(Gui.unformatNumber(itemAmount.toString()));

							edit.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							edit.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}

		});

		deleteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (rowAtPoint > -1) {
					entries.remove(rowAtPoint);
				}

				Gui.this.saveAll();

			}
		});

		clearList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int selectedOption = JOptionPane.showConfirmDialog(null,
						"By deleting the table, you will be\n" +
								"irreversably deleting all data and \n" +
								"will not be recoverable.",
						"Are you sure?",
						JOptionPane.YES_NO_OPTION);
				if (selectedOption == JOptionPane.YES_OPTION) {
					entries.clear();
					Gui.this.saveAll();
				}

			}
		});

		table.setComponentPopupMenu(popupMenu);
		table.setModel(entryTable);

		table.getColumnModel().getColumn(0).setPreferredWidth(85);
		table.getColumnModel().getColumn(1).setPreferredWidth(62);
		table.getColumnModel().getColumn(2).setPreferredWidth(70);
		table.getColumnModel().getColumn(3).setPreferredWidth(70);
		table.getColumnModel().getColumn(4).setPreferredWidth(70);
		table.getColumnModel().getColumn(5).setPreferredWidth(90);
		scrollPane.setViewportView(table);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane_1.setBounds(10, 160, 760, 52);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		tablePanel.add(scrollPane_1);
		calculationTable = new AbstractCalculationTable(this.entries);
		JTable table_1 = new JTable(calculationTable) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				Object value = getModel().getValueAt(row, column);
				comp.setForeground(Color.BLACK);
				return comp;
			}
		};

		table_1.getTableHeader().setReorderingAllowed(false);
		table_1.getTableHeader().setResizingAllowed(false);
		table_1.setRowSelectionAllowed(false);
		table_1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		table_1.setModel(calculationTable);

		table_1.getColumnModel().getColumn(0).setPreferredWidth(99);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(120);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(127);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(93);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(81);
		table_1.setRowHeight(22);
		scrollPane_1.setViewportView(table_1);
	}

	private static String unformatNumber(String s) {
		return s.replaceAll(",", "");
	}

	private void saveAll() {
		try {
			this.save(this);
			this.reload(this);
			entryTable.fireTableDataChanged();
			calculationTable.fireTableDataChanged();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void reload(Gui gui) throws IOException {
		Gson gson = new Gson();

		ArrayList<EntryInstance> entries = gson.fromJson(Files.newBufferedReader(Paths.get("data", "items.json")),
				new TypeToken<List<EntryInstance>>(){}.getType());
	}

	private void save(Gui gui) throws IOException {
		Gson gson = new Gson();

		Files.write(Paths.get("data", "items.json"), gson.toJson(gui.entries).getBytes(StandardCharsets.UTF_8),
				StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}
}
