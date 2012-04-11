package org.carnegiesciencecenter.buhl;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;
import javax.swing.JTextField;

public class EditUnitSlewProjGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldSlew1, textFieldSlew2, textFieldMotor;
	private JComboBox comboBoxName;
	private JButton okButton, cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EditUnitSlewProjGUI dialog = new EditUnitSlewProjGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EditUnitSlewProjGUI() {
		setBounds(100, 100, 414, 264);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel label = new JLabel("Unit Name");
		label.setToolTipText("The name of the unit");
		label.setBounds(10, 14, 82, 15);
		contentPanel.add(label);
		
		comboBoxName = new JComboBox();
		comboBoxName.setToolTipText("List of possible unit names (only A-X are possible in SPICE)");
		comboBoxName.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X"}));
		comboBoxName.setSelectedIndex(0);
		comboBoxName.setBounds(99, 11, 46, 20);
		contentPanel.add(comboBoxName);
		
		JLabel lblNewLabel = new JLabel("Each slew projector has 2 slews \r\nand 1 motor associated with it.");
		lblNewLabel.setBounds(10, 40, 370, 47);
		contentPanel.add(lblNewLabel);
		
		JLabel lblSlew = new JLabel("Slew 1");
		lblSlew.setBounds(10, 98, 82, 14);
		contentPanel.add(lblSlew);
		
		JLabel lblSlew_1 = new JLabel("Slew 2");
		lblSlew_1.setBounds(10, 123, 82, 14);
		contentPanel.add(lblSlew_1);
		
		JLabel lblMotor = new JLabel("Motor");
		lblMotor.setBounds(10, 148, 82, 14);
		contentPanel.add(lblMotor);
		
		textFieldSlew1 = new JTextField();
		textFieldSlew1.setBounds(99, 95, 86, 20);
		contentPanel.add(textFieldSlew1);
		textFieldSlew1.setColumns(10);
		
		textFieldSlew2 = new JTextField();
		textFieldSlew2.setBounds(99, 120, 86, 20);
		contentPanel.add(textFieldSlew2);
		textFieldSlew2.setColumns(10);
		
		textFieldMotor = new JTextField();
		textFieldMotor.setBounds(99, 145, 86, 20);
		contentPanel.add(textFieldMotor);
		textFieldMotor.setColumns(10);
		
		JLabel lblegSlewA = new JLabel("(e.g., SLEW C)");
		lblegSlewA.setBounds(195, 98, 103, 14);
		contentPanel.add(lblegSlewA);
		
		JLabel lblegSlewD = new JLabel("(e.g., SLEW D)");
		lblegSlewD.setBounds(195, 123, 103, 14);
		contentPanel.add(lblegSlewD);
		
		JLabel lblegMotrB = new JLabel("(e.g., MOTR B)");
		lblegMotrB.setBounds(195, 148, 103, 14);
		contentPanel.add(lblegMotrB);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
