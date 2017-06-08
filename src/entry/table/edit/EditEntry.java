package entry.table.edit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import tool.Gui;
import entry.table.AbstractEntryTable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alex on 6/8/2017.
 *
 */
public class EditEntry extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public JTextField textField;
	public JTextField textField_1;
	public JTextField textField_2;
	public JTextField textField_3;

	/**
	 * Create the dialog.
	 */
	public EditEntry(AbstractEntryTable entryTable, JTable table, Gui gui) {
		setBounds(100, 100, 230, 263);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Item Name:");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setBounds(60, 11, 105, 14);
			contentPanel.add(lblNewLabel);

			textField = new JTextField();
			textField.setBounds(56, 28, 109, 20);
			contentPanel.add(textField);
			textField.setColumns(10);

			JLabel lblNewLabel_1 = new JLabel("Bought For:");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_1.setBounds(56, 54, 109, 18);
			contentPanel.add(lblNewLabel_1);

			textField_1 = new JTextField();
			textField_1.setBounds(56, 73, 109, 20);
			contentPanel.add(textField_1);
			textField_1.setColumns(10);

			JLabel lblNewLabel_2 = new JLabel("Sold For:");
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_2.setBounds(56, 97, 105, 14);
			contentPanel.add(lblNewLabel_2);

			textField_2 = new JTextField();
			textField_2.setBounds(56, 116, 109, 20);
			contentPanel.add(textField_2);
			textField_2.setColumns(10);

			JLabel lblNewLabel_3 = new JLabel("Amount Sold:");
			lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_3.setBounds(56, 141, 109, 14);
			contentPanel.add(lblNewLabel_3);

			textField_3 = new JTextField();
			textField_3.setBounds(56, 158, 109, 20);
			contentPanel.add(textField_3);
			textField_3.setColumns(10);

		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(e -> {
					String itemName = textField.getText();
					int boughtAmount = Integer.parseInt(textField_1.getText());
					int soldAmount = Integer.parseInt(textField_2.getText());
					int itemAmount = Integer.parseInt(textField_3.getText());
					entryTable.setValueAt(itemName, table.getSelectedRow(), 1);
					entryTable.setValueAt(boughtAmount, table.getSelectedRow(), 2);
					entryTable.setValueAt(soldAmount, table.getSelectedRow(), 3);
					entryTable.setValueAt(itemAmount, table.getSelectedRow(), 4);
					gui.saveAll();
					entryTable.fireTableDataChanged();
					gui.getCalculationTable().fireTableDataChanged();
					EditEntry.this.dispose();

				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						EditEntry.this.dispose();

					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
