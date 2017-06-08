package tool;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import entry.EntryInstance;
import entry.table.AbstractCalculationTable;
import entry.table.AbstractEntryTable;
import entry.table.edit.EditEntry;
import item.osbuddy.OSBuddyInstance;
import item.osbuddy.table.AbstractOSBuddyItemsTable;
import item.osbuddy.table.detailed.ItemInstanceConstants;
import item.osbuddy.table.detailed.runescape.RunescapeItemInstance;
import item.osbuddy.table.detailed.runescape.graph.RunescapeGraphInstance;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
	private final JTextField textField_8;
	private JTable table;

	private JCheckBox checkBox = new JCheckBox("Enable logging");

	private ArrayList<EntryInstance> entries;
	private HashMap<Integer, OSBuddyInstance> items;

	private AbstractEntryTable entryTable;
	private AbstractCalculationTable calculationTable;
	private AbstractOSBuddyItemsTable itemTable;

	public AbstractCalculationTable getCalculationTable() {
		return calculationTable;
	}

	private int rowAtPoint;
	private int rowAtPoint_1;

	private JPanel jpanel_1;

	private RunescapeGraphInstance runescapeGraph;

	private RunescapeItemInstance runescapeItem;
	private String runescapeItemPath;

	private JsonParser parser = new JsonParser();
	private JsonElement object;

	private BufferedImage image;
	private JLabel picLabel;
	private JLabel nameLabel;
	private JLabel idLabel;
	private JLabel descriptionLabel;
	private JLabel membersLabel;
	private JLabel currentTrendLabel;
	private JLabel todaysTrendLabel;
	private JLabel thirtyDayTrendLabel;
	private JLabel ninetyDayTrendLabel;
	private JLabel oneHundredEightyDayTrendLabel;
	private JLabel typeLabel;

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
				SwingUtilities.invokeLater(() -> {
					rowAtPoint = table
							.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), table));
					if (rowAtPoint > -1) {
						table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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

		editItem.addActionListener(e -> EventQueue.invokeLater(() -> {
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
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}));

		deleteItem.addActionListener(e -> {

			if (rowAtPoint > -1) {
				entries.remove(rowAtPoint);
			}

			Gui.this.saveAll();

		});

		clearList.addActionListener(e -> {

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

		/*
		 * Instances a new JPanel
		 */
		JPanel graphPanel = new JPanel();

		graphPanel.setLayout(null);

		/*
		 * Adds {@link tablePanel} to {@tabbedPane}
		 */
		//tabbedPane.addTab("Graphs", null, graphPanel, null);

		Gson gson_3 = new Gson();
		this.items = gson_3.fromJson(new BufferedReader(new InputStreamReader(new URL("https://rsbuddy.com/exchange/summary.json").openStream())), new TypeToken<HashMap<Integer, OSBuddyInstance>>() {
		}.getType());

		/*
		 * Instances a new JPanel
		 */
		JPanel osbuddyItems = new JPanel();

		osbuddyItems.setLayout(null);

		/*
		 * Adds {@link tablePanel} to {@tabbedPane}
		 */
		tabbedPane.addTab("OSBuddy Items", null, osbuddyItems, null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane_2.setBounds(5, 5, 778, 450);
		osbuddyItems.add(scrollPane_2);
		itemTable = new AbstractOSBuddyItemsTable(this.items) {
		};
		JTable table_2 = new JTable(itemTable) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				Object value = getModel().getValueAt(row, column);
				comp.setForeground(Color.BLACK);
				if (column == 7) {
					if (String.valueOf(value).contains("(Not Accurate!)")) {
						comp.setForeground(Color.YELLOW);
					} else if (Integer.parseInt(Gui.unformatNumber(String.valueOf(value))) > 0) {
						comp.setForeground(Color.GREEN);
					} else if (Integer.parseInt(Gui.unformatNumber(String.valueOf(value))) < 0) {
						comp.setForeground(Color.RED);
					} else {
						comp.setForeground(Color.BLACK);
					}
				} else {
					comp.setForeground(Color.BLACK);
				}
				return comp;
			}
		};

		table_2.setCellSelectionEnabled(true);
		table_2.getTableHeader().setReorderingAllowed(false);
		table_2.getTableHeader().setUpdateTableInRealTime(true);
		table_2.getTableHeader().setResizingAllowed(true);

		table_2.setModel(itemTable);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);

		table_2.getColumnModel().getColumn(0).setPreferredWidth(55);
		table_2.getColumnModel().getColumn(1).setPreferredWidth(110);
		table_2.getColumnModel().getColumn(2).setPreferredWidth(75);
		table_2.getColumnModel().getColumn(3).setPreferredWidth(75);
		table_2.getColumnModel().getColumn(4).setPreferredWidth(75);
		table_2.getColumnModel().getColumn(5).setPreferredWidth(70);
		table_2.getColumnModel().getColumn(6).setPreferredWidth(55);
		table_2.setRowHeight(22);
		scrollPane_2.setViewportView(table_2);

		final JPopupMenu popupMenu_1 = new JPopupMenu();

		JMenuItem searchJagex = new JMenuItem("Runescape Details");

		popupMenu_1.add(searchJagex);

		popupMenu_1.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						rowAtPoint_1 = table_2
								.rowAtPoint(SwingUtilities.convertPoint(popupMenu_1, new Point(0, 0), table_2));
						if (rowAtPoint_1 > -1) {
							table_2.setRowSelectionInterval(rowAtPoint_1, rowAtPoint_1);
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

		searchJagex.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							if (picLabel != null || nameLabel != null || idLabel != null || descriptionLabel != null || membersLabel != null
									|| currentTrendLabel != null || todaysTrendLabel != null || thirtyDayTrendLabel != null || ninetyDayTrendLabel != null
									|| oneHundredEightyDayTrendLabel != null || typeLabel != null) {
								jpanel_1.remove(picLabel);
								jpanel_1.remove(nameLabel);
								jpanel_1.remove(idLabel);
								jpanel_1.remove(descriptionLabel);
								jpanel_1.remove(membersLabel);
								jpanel_1.remove(typeLabel);
								jpanel_1.remove(currentTrendLabel);
								jpanel_1.remove(todaysTrendLabel);
								jpanel_1.remove(thirtyDayTrendLabel);
								jpanel_1.remove(ninetyDayTrendLabel);
								jpanel_1.remove(oneHundredEightyDayTrendLabel);
							}
							object = parser.parse(new BufferedReader(new InputStreamReader(new URL(ItemInstanceConstants.getRunescapeItemInstanceDetails(Integer.parseInt(Gui.unformatNumber(String.valueOf(itemTable.getValueAt(rowAtPoint_1, 0)))))).openStream())));
							runescapeItem = gson_3.fromJson(object.getAsJsonObject().get("item"), RunescapeItemInstance.class);

							object = parser.parse(new BufferedReader(new InputStreamReader(new URL(ItemInstanceConstants.getRunescapeItemInstanceGraphDetails(Integer.parseInt(Gui.unformatNumber(String.valueOf(itemTable.getValueAt(rowAtPoint_1, 0)))))).openStream())));
							runescapeGraph = gson_3.fromJson(object, RunescapeGraphInstance.class);

							image = ImageIO.read(new URL(runescapeItem.getLargeIcon()));
							picLabel = new JLabel(new ImageIcon(image));
							jpanel_1.add(picLabel).setBounds(5, 5, 96, 96);

							nameLabel = new JLabel(runescapeItem.getName());
							nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
							jpanel_1.add(nameLabel).setBounds(195, 5, nameLabel.getText().length() * 13, 20);

							idLabel = new JLabel(String.valueOf(runescapeItem.getId()));
							idLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
							jpanel_1.add(idLabel).setBounds(40, 106, nameLabel.getText().length() * 13, 20);

							descriptionLabel = new JLabel(runescapeItem.getDescription());
							descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
							jpanel_1.add(descriptionLabel).setBounds(232, 30, descriptionLabel.getText().length() * 13, 20);

							membersLabel = new JLabel(String.valueOf(runescapeItem.isMembersItem()));
							membersLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
							jpanel_1.add(membersLabel).setBounds(252, 55, membersLabel.getText().length() * 13, 20);

							typeLabel = new JLabel(runescapeItem.getType());
							typeLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
							jpanel_1.add(typeLabel).setBounds(190, 80, typeLabel.getText().length() * 13, 20);

							currentTrendLabel = new JLabel(runescapeItem.getCurrentTrend().getTrend() + ", " + runescapeItem.getCurrentTrend().getPrice());
							currentTrendLabel.setForeground(runescapeItem.getCurrentTrend().getTrend().contains("negative") ? Color.RED :
									runescapeItem.getCurrentTrend().getTrend().contains("positive") ? Color.GREEN : Color.BLACK);
							currentTrendLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
							jpanel_1.add(currentTrendLabel).setBounds(120, 150, currentTrendLabel.getText().length() * 16, 20);

							todaysTrendLabel = new JLabel(runescapeItem.getTodaysTrend().getTrend() + ", " + runescapeItem.getTodaysTrend().getPrice());
							todaysTrendLabel.setForeground(runescapeItem.getTodaysTrend().getTrend().contains("negative") ? Color.RED :
									runescapeItem.getTodaysTrend().getTrend().contains("positive") ? Color.GREEN : Color.BLACK);
							todaysTrendLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
							jpanel_1.add(todaysTrendLabel).setBounds(510, 150, todaysTrendLabel.getText().length() * 16, 20);

							thirtyDayTrendLabel = new JLabel(runescapeItem.getThirtyDayTrend().getTrend() + ", " + runescapeItem.getThirtyDayTrend().getChange());
							thirtyDayTrendLabel.setForeground(runescapeItem.getThirtyDayTrend().getTrend().contains("negative") ? Color.RED :
									runescapeItem.getThirtyDayTrend().getTrend().contains("positive") ? Color.GREEN : Color.BLACK);
							thirtyDayTrendLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
							jpanel_1.add(thirtyDayTrendLabel).setBounds(60, 235, thirtyDayTrendLabel.getText().length() * 16, 20);

							ninetyDayTrendLabel = new JLabel(runescapeItem.getNinetyDayTrend().getTrend() + ", " + runescapeItem.getNinetyDayTrend().getChange());
							ninetyDayTrendLabel.setForeground(runescapeItem.getNinetyDayTrend().getTrend().contains("negative") ? Color.RED :
									runescapeItem.getNinetyDayTrend().getTrend().contains("positive") ? Color.GREEN : Color.BLACK);
							ninetyDayTrendLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
							jpanel_1.add(ninetyDayTrendLabel).setBounds(310, 235, ninetyDayTrendLabel.getText().length() * 16, 20);

							oneHundredEightyDayTrendLabel = new JLabel(runescapeItem.getOneHundredEigthyDayTrend().getTrend() + ", " + runescapeItem.getOneHundredEigthyDayTrend().getChange());
							oneHundredEightyDayTrendLabel.setForeground(runescapeItem.getOneHundredEigthyDayTrend().getTrend().contains("negative") ? Color.RED :
									runescapeItem.getOneHundredEigthyDayTrend().getTrend().contains("positive") ? Color.GREEN : Color.BLACK);
							oneHundredEightyDayTrendLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
							jpanel_1.add(oneHundredEightyDayTrendLabel).setBounds(550, 235, oneHundredEightyDayTrendLabel.getText().length() * 16, 20);

							jpanel_1.repaint();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
			}

		});

		table_2.setComponentPopupMenu(popupMenu_1);

		textField_8 = new JTextField();
		textField_8.setBounds(5, 462, 100, 20);

		osbuddyItems.add(textField_8);

		textField_8.setColumns(10);

		checkBox = new JCheckBox("Enable logging");
		checkBox.setText("Balls");
		checkBox.setToolTipText("Select to search for potential profits under the value of your parameters.");
		checkBox.setBounds(110, 462, 20, 20);

		osbuddyItems.add(checkBox);

		JComboBox<String> searchParameters = new JComboBox<String>(new String[]{"Item ID", "Item Name", "Potential Profit"});
		searchParameters.setBounds(140, 462, 120, 20);
		osbuddyItems.add(searchParameters);

		List<OSBuddyInstance> searchIndex = new ArrayList<OSBuddyInstance>();

		AbstractOSBuddyItemsTable searchTable;

		JButton button_3 = new JButton("Search");
		button_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (searchParameters.getSelectedItem().equals("Item ID")) {
					if (!textField_8.getText().matches("\\d+")) {
						JOptionPane.showMessageDialog(null, "You cannot search for Items via IDs while having letters within the search parameters!",
								"An Error Occured!", 1, null);
						return;
					}
					for (OSBuddyInstance item : items.values()) {
						if (item.getId() == Integer.parseInt(textField_8.getText())) {
							searchIndex.add(item);
						}
					}
					itemTable = new AbstractOSBuddyItemsTable(searchIndex);
					table_2.setModel(itemTable);
				}
				if (searchParameters.getSelectedItem().equals("Item Name")) {
					for (OSBuddyInstance item : items.values()) {
						if (item.getName().toLowerCase().contains(textField_8.getText().toLowerCase())) {
							searchIndex.add(item);
						}
					}
					itemTable = new AbstractOSBuddyItemsTable(searchIndex);
					table_2.setModel(itemTable);
				}
				if (searchParameters.getSelectedItem().equals("Potential Profit"))
					if (checkBox.isSelected()) {
						if (!textField_8.getText().toString().matches("\\d+")) {
							JOptionPane.showMessageDialog(null, "You cannot search for items via potential while having letters within the search parameters!",
									"An Error Occured!", 1, null);
							return;
						}
						for (OSBuddyInstance item : items.values()) {
							if (Math.subtractExact(item.getSellAverage(), item.getBuyAverage()) < Integer.parseInt(textField_8.getText())) {
								searchIndex.add(item);
							}
						}
						itemTable = new AbstractOSBuddyItemsTable(searchIndex);
						table_2.setModel(itemTable);
					} else {
						if (!textField_8.getText().toString().matches("\\d+")) {
							JOptionPane.showMessageDialog(null, "You cannot search for prices via potential while having letters within the search parameters!",
									"An Error Occured!", 1, null);
							return;
						}
						for (OSBuddyInstance item : items.values()) {
							if (Math.subtractExact(item.getSellAverage(), item.getBuyAverage()) > Integer.parseInt(textField_8.getText())) {
								searchIndex.add(item);
							}
						}
						itemTable = new AbstractOSBuddyItemsTable(searchIndex);
						table_2.setModel(itemTable);
					}
				searchIndex.clear();
				itemTable.fireTableDataChanged();
			}
		});
		button_3.setBounds(270, 462, 130, 20);
		osbuddyItems.add(button_3);

		JButton button_4 = new JButton("Main Table");
		button_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		button_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				itemTable = new AbstractOSBuddyItemsTable(items);
				table_2.setModel(itemTable);
				itemTable.fireTableDataChanged();
			}
		});

		button_4.setBounds(410, 462, 130, 20);
		osbuddyItems.add(button_4);

		JButton button_5 = new JButton("Refresh Table");
		button_5.setFont(new Font("Tahoma", Font.BOLD, 13));
		button_5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					items = gson_3.fromJson(new BufferedReader(new InputStreamReader(new URL("https://rsbuddy.com/exchange/summary.json").openStream())), new TypeToken<HashMap<Integer, OSBuddyInstance>>() {
					}.getType());
					itemTable = new AbstractOSBuddyItemsTable(items);
					table_2.setModel(itemTable);
					itemTable.fireTableDataChanged();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		button_5.setBounds(650, 462, 130, 20);
		osbuddyItems.add(button_5);

		JPanel itemPanel = new JPanel();

		itemPanel.setLayout(null);

		/*
		 * Adds {@code tablePanel} to {@tabbedPane}
		 */
		tabbedPane.addTab("Item Panel", null, itemPanel, null);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane_3.setBounds(5, 5, 778, 470);
		scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		itemPanel.add(scrollPane_3);

		jpanel_1 = new JPanel();
		jpanel_1.setLayout(null);
		jpanel_1.setBounds(0, 0, scrollPane_3.getWidth(), 600);
		scrollPane_3.getViewport().add(jpanel_1);

		JLabel idLabel = new JLabel("ID:");
		idLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		idLabel.setBounds(10, 106, idLabel.getText().length() * 10, 20);
		jpanel_1.add(idLabel);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		nameLabel.setBounds(150, 5, 50, 20);
		jpanel_1.add(nameLabel);

		JLabel descriptionLabel = new JLabel("Description:");
		descriptionLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		descriptionLabel.setBounds(150, 30, descriptionLabel.getText().length() * 10, 20);
		jpanel_1.add(descriptionLabel);

		JLabel membersLabel = new JLabel("Members Item:");
		membersLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		membersLabel.setBounds(150, 55, membersLabel.getText().length() * 10, 20);
		jpanel_1.add(membersLabel);

		JLabel typeLabel = new JLabel("Type:");
		typeLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		typeLabel.setBounds(150, 80, typeLabel.getText().length() * 10, 20);
		jpanel_1.add(typeLabel);

		JLabel currentTrendLabel = new JLabel("Current Trend:");
		currentTrendLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		currentTrendLabel.setBounds(120, 130, currentTrendLabel.getText().length() * 16, 20);
		jpanel_1.add(currentTrendLabel);

		JLabel todaysTrendLabel = new JLabel("Today's Trend:");
		todaysTrendLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		todaysTrendLabel.setBounds(510, 130, todaysTrendLabel.getText().length() * 16, 20);
		jpanel_1.add(todaysTrendLabel);

		JLabel thirtyDayTrendLabel = new JLabel("30 Day Trend:");
		thirtyDayTrendLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		thirtyDayTrendLabel.setBounds(60, 210, thirtyDayTrendLabel.getText().length() * 16, 20);
		jpanel_1.add(thirtyDayTrendLabel);

		JLabel ninetyDayTrendLabel = new JLabel("90 Day Trend:");
		ninetyDayTrendLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		ninetyDayTrendLabel.setBounds(310, 210, ninetyDayTrendLabel.getText().length() * 16, 20);
		jpanel_1.add(ninetyDayTrendLabel);

		JLabel oneHundredEightyDayTrendLabel = new JLabel("180 Day Trend:");
		oneHundredEightyDayTrendLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		oneHundredEightyDayTrendLabel.setBounds(550, 210, oneHundredEightyDayTrendLabel.getText().length() * 16, 20);
		jpanel_1.add(oneHundredEightyDayTrendLabel);

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

	public static Object formatNumber(int id) {
		return null;
	}
}
