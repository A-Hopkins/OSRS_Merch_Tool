import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;

/**
 * Created by Alex on 6/7/2017.
 *
 * Gui Implementation class
 */
public class Gui extends JFrame {

	private final JPanel pane;
	private final JTextField buyingTextField;
	private final JTextField sellingTextField;
	private final JTextField amountTextField;
	private final JTextField outputTextField;

	/**
	 *  Builds the UI of the Application and handles most of the processes.
	 *
	 */
	Gui() {

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
		sellingTextField.setBounds(50, 331, 132, 20);
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
		 */
		JButton clearButton = new JButton("Clear");
		clearButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		clearButton.setBounds(198, 423, 160, 22);

		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buyingTextField.setText("");
				sellingTextField.setText("");
				amountTextField.setText("");
				outputTextField.setText("");
			}
		});

		tablePanel.add(clearButton);

		/*
		 * Instance of JLabel Potential profit made
		 */
		JLabel potentialProfitMadeLabel = new JLabel("Potential Profit Made");
		potentialProfitMadeLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		potentialProfitMadeLabel.setBounds(211, 304, 143, 14);

		tablePanel.add(potentialProfitMadeLabel);

	}
}
