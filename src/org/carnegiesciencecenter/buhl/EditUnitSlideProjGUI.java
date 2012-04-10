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

public class EditUnitSlideProjGUI extends JDialog implements ActionListener, FocusListener
{
	private final JPanel contentPanel = new JPanel();
	private JButton okButton, cancelButton;
	private JTextField 	textFieldAzimuth, textFieldElevation, 
						textFieldRotation, textFieldWidth, 
						textFieldHeight;
	private JComboBox comboBoxUnitName;
	private EditBankGUI parentWindow;
	private UnitSlideProjector toBeEdited;
	private String toBeEditedName;	// Since the name may change
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EditUnitSlideProjGUI dialog = new EditUnitSlideProjGUI(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @param parent	The window that requested this one be created
	 * @wbp.parser.constructor
	 */
	public EditUnitSlideProjGUI(EditBankGUI parent)
	{
		initialize(parent);
		toBeEdited = null;
	}
	
	/**
	 * Create the dialog, supplying it with a Unit to start with and edit.
	 * @param parent	The window that request this one be created
	 * @param toEdit	The Unit to edit
	 */
	public EditUnitSlideProjGUI(EditBankGUI parent, Unit toEdit)
	{
		initialize(parent);
		
		// Make sure we were given the right kind of unit
		if (toEdit.getClass() == UnitSlideProjector.class)
			toBeEdited = (UnitSlideProjector) toEdit;
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
			
			textFieldAzimuth.setText(String.valueOf(toBeEdited.getAzimuth()));
			textFieldElevation.setText(String.valueOf(toBeEdited.getElevation()));
			textFieldHeight.setText(String.valueOf(toBeEdited.getHeight()));
			textFieldRotation.setText(String.valueOf(toBeEdited.getRotation()));
			textFieldWidth.setText(String.valueOf(toBeEdited.getWidth()));
		}
	}
	
	/**
	 * Initializes the GUI.
	 * @param parent	The Window that created this one
	 */
	private void initialize(EditBankGUI parent)
	{
		parentWindow = parent;
		setTitle("Slide Projector Units");
		
		setBounds(100, 100, 270, 286);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblUnitName = new JLabel("Unit Name");
			lblUnitName.setToolTipText("The name of the unit");
			lblUnitName.setBounds(12, 15, 82, 15);
			contentPanel.add(lblUnitName);
		}
		
		comboBoxUnitName = new JComboBox();
		comboBoxUnitName.setToolTipText("List of possible unit names (only A-X are possible in SPICE)");
		comboBoxUnitName.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X"}));
		comboBoxUnitName.setSelectedIndex(0);
		comboBoxUnitName.setBounds(101, 12, 46, 20);
		contentPanel.add(comboBoxUnitName);
		
		JLabel lblAzimuth = new JLabel("Azimuth");
		lblAzimuth.setBounds(12, 61, 70, 15);
		contentPanel.add(lblAzimuth);
		
		JLabel lblElevation = new JLabel("Elevation");
		lblElevation.setBounds(12, 88, 70, 15);
		contentPanel.add(lblElevation);
		
		JLabel lblRotation = new JLabel("Rotation");
		lblRotation.setBounds(12, 115, 70, 15);
		contentPanel.add(lblRotation);
		
		JLabel lblWidth = new JLabel("Width");
		lblWidth.setBounds(12, 142, 70, 15);
		contentPanel.add(lblWidth);
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setBounds(12, 169, 70, 15);
		contentPanel.add(lblHeight);
		
		textFieldAzimuth = new JTextField();
		textFieldAzimuth.setText("0.0");
		textFieldAzimuth.setBounds(101, 59, 70, 19);
		textFieldAzimuth.addFocusListener(this);
		contentPanel.add(textFieldAzimuth);
		textFieldAzimuth.setColumns(10);
		
		textFieldElevation = new JTextField();
		textFieldElevation.setText("0.0");
		textFieldElevation.setBounds(101, 86, 70, 19);
		textFieldElevation.addFocusListener(this);
		contentPanel.add(textFieldElevation);
		textFieldElevation.setColumns(10);
		
		textFieldRotation = new JTextField();
		textFieldRotation.setText("0.0");
		textFieldRotation.setBounds(101, 113, 70, 19);
		textFieldRotation.addFocusListener(this);
		contentPanel.add(textFieldRotation);
		textFieldRotation.setColumns(10);
		
		textFieldWidth = new JTextField();
		textFieldWidth.setText("0.0");
		textFieldWidth.setBounds(101, 140, 70, 19);
		textFieldWidth.addFocusListener(this);
		contentPanel.add(textFieldWidth);
		textFieldWidth.setColumns(10);
		
		textFieldHeight = new JTextField();
		textFieldHeight.setText("0.0");
		textFieldHeight.setBounds(101, 166, 70, 19);
		textFieldHeight.addFocusListener(this);
		contentPanel.add(textFieldHeight);
		textFieldHeight.setColumns(10);
		
		label = new JLabel("degrees");
		label.setBounds(181, 61, 70, 14);
		contentPanel.add(label);
		
		label_1 = new JLabel("degrees");
		label_1.setBounds(181, 88, 70, 14);
		contentPanel.add(label_1);
		
		label_2 = new JLabel("degrees");
		label_2.setBounds(181, 115, 70, 14);
		contentPanel.add(label_2);
		
		label_3 = new JLabel("degrees");
		label_3.setBounds(181, 142, 70, 14);
		contentPanel.add(label_3);
		
		label_4 = new JLabel("degrees");
		label_4.setBounds(181, 169, 70, 14);
		contentPanel.add(label_4);
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
	
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == cancelButton)
			closeWindow();
		else if (e.getSource() == okButton)
		{
			String name = (String) comboBoxUnitName.getSelectedItem();
			
			double azimuth = Utility.stringToDouble(textFieldAzimuth.getText());
			double elevation = Utility.stringToDouble(textFieldElevation.getText());
			double rotation = Utility.stringToDouble(textFieldRotation.getText());
			double width = Utility.stringToDouble(textFieldWidth.getText());
			double height = Utility.stringToDouble(textFieldHeight.getText());
			
			if (   azimuth 		== Double.MAX_VALUE
				|| elevation 	== Double.MAX_VALUE
				|| rotation 	== Double.MAX_VALUE
				|| width 		== Double.MAX_VALUE
				|| height 		== Double.MAX_VALUE )
				JOptionPane.showMessageDialog(this, "Every item must have a valid number");
			else		
			{
				if (parentWindow != null)
				{
					// In case name was changed
					if (toBeEdited != null)
						parentWindow.removeUnit(toBeEditedName);
					
					// Create new Unit and add it to parent
					UnitSlideProjector unit = new UnitSlideProjector(name, azimuth, elevation, rotation, width, height);
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
