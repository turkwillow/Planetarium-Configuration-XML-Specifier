package org.carnegiesciencecenter.buhl;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EditUnitSlewGUI extends JDialog implements ActionListener, FocusListener
{
	private final JPanel contentPanel = new JPanel();
	private EditBankGUI parentWindow;
	private JTextField textFieldMinPos, textFieldMaxPos, textFieldMinVal, textFieldMaxVal, textFieldTripTime;
	private JComboBox comboBoxUnitName;
	private JButton okButton, cancelButton;
	private UnitSlew toBeEdited;
	private String toBeEditedName;	// The original name of the unit to be edited

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EditUnitSlewGUI dialog = new EditUnitSlewGUI(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @wbp.parser.constructor
	 */
	public EditUnitSlewGUI(EditBankGUI parent) 
	{
		initialize(parent);
		toBeEdited = null;
	}
	
	public EditUnitSlewGUI(EditBankGUI parent, Unit toEdit)
	{
		initialize(parent);
		
		// Make sure we were given the right kind of unit
		if (toEdit.getClass() == UnitSlew.class)
			toBeEdited = (UnitSlew) toEdit;
		else
		{
			JOptionPane.showMessageDialog(this, "Unit to be edited is of wrong type", "Error", JOptionPane.ERROR_MESSAGE);
			closeWindow();
			return;
		}
		
		// Fill in parameter values
		if (toEdit != null && toEdit.getName().length() > 0)
		{
			// Move selection to correct name
			toBeEditedName = toBeEdited.getName();
			char uName = toBeEditedName.charAt(0);
			if (!(uName < 'A' || uName > 'X'))
				comboBoxUnitName.setSelectedIndex(uName - 'A');
			
			// textFieldMinPos, textFieldMaxPos, textFieldMinVal, textFieldMaxVal
			textFieldMinPos.setText(String.valueOf(toBeEdited.getMinPos()));
			textFieldMaxPos.setText(String.valueOf(toBeEdited.getMaxPos()));
			textFieldMinVal.setText(String.valueOf(toBeEdited.getMinValue()));
			textFieldMaxVal.setText(String.valueOf(toBeEdited.getMaxValue()));
			textFieldTripTime.setText(String.valueOf(toBeEdited.getTripTime()));
		}
	}
	
	private void initialize(EditBankGUI parent)
	{
		parentWindow = parent;
		setTitle("Slew Units");
		
		setBounds(100, 100, 340, 261);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel label = new JLabel("Unit Name");
		label.setToolTipText("The name of the unit");
		label.setBounds(10, 14, 82, 15);
		contentPanel.add(label);
		
		comboBoxUnitName = new JComboBox();
		comboBoxUnitName.setToolTipText("List of possible unit names (only A-X are possible in SPICE)");
		comboBoxUnitName.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X"}));
		comboBoxUnitName.setSelectedIndex(0);
		comboBoxUnitName.setBounds(99, 11, 46, 20);
		contentPanel.add(comboBoxUnitName);
		
		JLabel lblMinimumPosition = new JLabel("Minimum Position");
		lblMinimumPosition.setBounds(10, 59, 121, 14);
		contentPanel.add(lblMinimumPosition);
		
		JLabel lblMaximumPosition = new JLabel("Maximum Position");
		lblMaximumPosition.setBounds(10, 84, 121, 14);
		contentPanel.add(lblMaximumPosition);
		
		JLabel lblMinumumValue = new JLabel("Minumum Value");
		lblMinumumValue.setBounds(10, 109, 121, 14);
		contentPanel.add(lblMinumumValue);
		
		JLabel lblMaximumValue = new JLabel("Maximum Value");
		lblMaximumValue.setBounds(10, 134, 121, 14);
		contentPanel.add(lblMaximumValue);
		
		textFieldMinPos = new JTextField();
		textFieldMinPos.setText("0.0");
		textFieldMinPos.setBounds(141, 56, 63, 20);
		textFieldMinPos.addFocusListener(this);
		contentPanel.add(textFieldMinPos);
		textFieldMinPos.setColumns(10);
		
		textFieldMaxPos = new JTextField();
		textFieldMaxPos.setText("0.0");
		textFieldMaxPos.setBounds(141, 81, 63, 20);
		textFieldMaxPos.addFocusListener(this);
		contentPanel.add(textFieldMaxPos);
		textFieldMaxPos.setColumns(10);
		
		textFieldMinVal = new JTextField();
		textFieldMinVal.setText("0.0");
		textFieldMinVal.setBounds(141, 106, 63, 20);
		textFieldMinVal.addFocusListener(this);
		contentPanel.add(textFieldMinVal);
		textFieldMinVal.setColumns(10);
		
		textFieldMaxVal = new JTextField();
		textFieldMaxVal.setText("0.0");
		textFieldMaxVal.setBounds(141, 131, 63, 20);
		textFieldMaxVal.addFocusListener(this);
		contentPanel.add(textFieldMaxVal);
		textFieldMaxVal.setColumns(10);
		
		JLabel lblTripTime = new JLabel("Trip Time");
		lblTripTime.setBounds(10, 159, 121, 14);
		contentPanel.add(lblTripTime);
		
		textFieldTripTime = new JTextField();
		textFieldTripTime.setText("0.0");
		textFieldTripTime.setBounds(141, 156, 63, 20);
		textFieldTripTime.addFocusListener(this);
		contentPanel.add(textFieldTripTime);
		textFieldTripTime.setColumns(10);
		
		JLabel lblSeconds = new JLabel("seconds / 100");
		lblSeconds.setBounds(214, 159, 109, 14);
		contentPanel.add(lblSeconds);
		
		JLabel lblDegrees = new JLabel("degrees");
		lblDegrees.setBounds(214, 59, 82, 14);
		contentPanel.add(lblDegrees);
		
		JLabel label_1 = new JLabel("degrees");
		label_1.setBounds(214, 84, 82, 14);
		contentPanel.add(label_1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
	}

	/**
	 * Closes the window.
	 */
	private void closeWindow()
	{
		this.dispose();
		this.setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == cancelButton)
			closeWindow();
		else if (e.getSource() == okButton)
		{
			String name = (String) comboBoxUnitName.getSelectedItem();
			
			double minPos = EditUnitSlideProjGUI.StringToDouble(textFieldMinPos.getText());
			double maxPos = EditUnitSlideProjGUI.StringToDouble(textFieldMaxPos.getText());
			double minVal = EditUnitSlideProjGUI.StringToDouble(textFieldMinVal.getText());
			double maxVal = EditUnitSlideProjGUI.StringToDouble(textFieldMaxVal.getText());
			double tripTm = EditUnitSlideProjGUI.StringToDouble(textFieldTripTime.getText());
			
			if (   minPos == Double.MAX_VALUE
				|| maxPos == Double.MAX_VALUE
				|| minVal == Double.MAX_VALUE
				|| maxVal == Double.MAX_VALUE
				|| tripTm == Double.MAX_VALUE )
				JOptionPane.showMessageDialog(this, "Every item must have a valid number");
			else		
			{
				if (parentWindow != null)
				{
					// In case name was changed
					if (toBeEdited != null)
						parentWindow.removeUnit(toBeEditedName);
					
					// Create new Unit and add it to parent
					UnitSlew unit = new UnitSlew(name, minPos, maxPos, minVal, maxVal, tripTm);
					parentWindow.addUnit(unit);
				}
				
				closeWindow();
			}
		}
	}

	@Override
	public void focusGained(FocusEvent e) 
	{
		// When the textfield is tabbed to, highlight its text
		if (e.getComponent().getClass() == JTextField.class)
			((JTextField) e.getComponent()).selectAll();
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
