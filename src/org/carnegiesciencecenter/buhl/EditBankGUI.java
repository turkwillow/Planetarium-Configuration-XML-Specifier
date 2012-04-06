package org.carnegiesciencecenter.buhl;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;

import org.carnegiesciencecenter.buhl.Bank.CommandAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;

public class EditBankGUI extends JDialog implements ActionListener
{

	private final JPanel contentPanel = new JPanel();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField bankNameField;
	private JList commandListing, unitListing;
	private JButton okButton, cancelButton, 
					btnAddCommand, btnEditCommand, btnRemoveCommand,
					btnAddUnit, btnEditUnit, btnRemoveUnit;
	private JRadioButton rdbtnKeepCommands, rdbtnDiscardCommands, rdbtnAutoconvertToDigitalsky;
	private HashMap<String, SpiceCommand> commands;
	private HashMap<String, Unit> units;
	private JScrollPane scrollPane;
	JComboBox comboBoxDeviceType;
	private EditConfigMainGUI parentWindow;	// The calling/parent window
	private Bank toBeEdited;	// For when the "Edit" button was clicked to open this window
	private int oldDeviceSelectionIndex = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		try {
			EditBankGUI dialog = new EditBankGUI(null);
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
	public EditBankGUI(EditConfigMainGUI parentWin) 
	{
		setTitle("SPICE Banks");
		initialize(parentWin);
		disableUnitButtons();	// Start off disabled, since we start on device type "Other"
		toBeEdited = null;
	}
	
	/**
	 * Ctor for when the Edit button has been pressed.
	 * @param parentWin	The calling/parent window
	 * @param toEdit	The Bank to be edited
	 */
	public EditBankGUI(EditConfigMainGUI parentWin, Bank toEdit)
	{
		initialize(parentWin);
		
		toBeEdited = toEdit;
		bankNameField.setText(toBeEdited.toString());
		String devType = toEdit.getDeviceType();
		
		// Set radio button
		Bank.CommandAction cmdAction = toBeEdited.checkIfKeepingMost();
		switch (cmdAction)
		{
		case DISCARD:
			rdbtnDiscardCommands.doClick();
			break;
		case CONVERT_TO_DS:
			rdbtnAutoconvertToDigitalsky.doClick();
			break;
		}
		
		// Set the selected bank type to be the one with the name of the 
		// bank to be edited
		for (int i = 0; i < comboBoxDeviceType.getItemCount(); i++)
			if (devType.equals(comboBoxDeviceType.getItemAt(i)))
			{
				comboBoxDeviceType.setSelectedIndex(i);
				oldDeviceSelectionIndex = i;
				break;
			}
		
		enableOrDisableUnitButtons();
		
		// Populate Commands list
		SpiceCommand[] cmds = toBeEdited.getSpecialCommands();
		for (SpiceCommand cmd : cmds)
			commands.put(cmd.getName(), cmd);
		refreshCommandList();
		
		// Populate Units list
		Unit[] unts = toBeEdited.getUnits();
		for (Unit u : unts)
			units.put(u.getName(), u);
		refreshUnitList();
	}
	
	private void initialize(EditConfigMainGUI parentWin)
	{
		parentWindow = parentWin;	// Set the parent window
		commands = new HashMap<String, SpiceCommand>();
		units = new HashMap<String, Unit>();
		
		setBounds(100, 100, 368, 556);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblBankName = new JLabel("Bank Name");
		lblBankName.setToolTipText("The name of the bank (e.g., VSRC)");
		lblBankName.setBounds(12, 12, 88, 15);
		contentPanel.add(lblBankName);
		
		JLabel lblDefaultAction = new JLabel("Default Action");
		lblDefaultAction.setToolTipText("Whether to keep or discard commands for this bank. Choose \"keep\" if unsure.");
		lblDefaultAction.setBounds(12, 82, 110, 15);
		contentPanel.add(lblDefaultAction);
		
		rdbtnKeepCommands = new JRadioButton("Keep Related Commands");
		rdbtnKeepCommands.setToolTipText("Keeps commands related to this bank");
		rdbtnKeepCommands.setSelected(true);
		buttonGroup.add(rdbtnKeepCommands);
		rdbtnKeepCommands.setBounds(118, 76, 201, 23);
		contentPanel.add(rdbtnKeepCommands);
		
		rdbtnDiscardCommands = new JRadioButton("Discard Related Commands");
		rdbtnDiscardCommands.setToolTipText("Discards commands related to this bank");
		buttonGroup.add(rdbtnDiscardCommands);
		rdbtnDiscardCommands.setBounds(118, 103, 226, 23);
		contentPanel.add(rdbtnDiscardCommands);
		
		rdbtnAutoconvertToDigitalsky = new JRadioButton("Auto-convert to DigitalSky");
		rdbtnAutoconvertToDigitalsky.setToolTipText("Commands will be specially modified to run without SPICE");
		rdbtnAutoconvertToDigitalsky.setBounds(118, 129, 201, 23);
		buttonGroup.add(rdbtnAutoconvertToDigitalsky);
		contentPanel.add(rdbtnAutoconvertToDigitalsky);
		
		JLabel lblCommandsTreatedDifferently = new JLabel("Commands Treated Differently");
		lblCommandsTreatedDifferently.setToolTipText("List of commands that get non-default treatment");
		lblCommandsTreatedDifferently.setBounds(12, 174, 238, 15);
		contentPanel.add(lblCommandsTreatedDifferently);
		
		btnAddCommand = new JButton("Add");
		btnAddCommand.setToolTipText("Add a special-case command");
		btnAddCommand.addActionListener(this);
		btnAddCommand.setBounds(220, 201, 117, 25);
		contentPanel.add(btnAddCommand);
		
		btnEditCommand = new JButton("Edit");
		btnEditCommand.setToolTipText("Edit the highlighted special-case command");
		btnEditCommand.addActionListener(this);
		btnEditCommand.setBounds(220, 238, 117, 25);
		contentPanel.add(btnEditCommand);
		
		btnRemoveCommand = new JButton("Remove");
		btnRemoveCommand.setToolTipText("Remove the highlighted command from the list");
		btnRemoveCommand.addActionListener(this);
		btnRemoveCommand.setBounds(220, 275, 117, 25);
		contentPanel.add(btnRemoveCommand);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 201, 201, 103);
		contentPanel.add(scrollPane);
		
		commandListing = new JList();
		scrollPane.setViewportView(commandListing);
		commandListing.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		commandListing.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		bankNameField = new JTextField();
		bankNameField.setBounds(118, 10, 114, 19);
		contentPanel.add(bankNameField);
		bankNameField.setColumns(10);
		
		// Unit stuff
		JLabel lblUnitsAndTheir = new JLabel("Units and their device-dependent information (optional)");
		lblUnitsAndTheir.setToolTipText("Specifies information for individual units in this bank (e.g., projection position information for slide projectors)");
		lblUnitsAndTheir.setBounds(12, 316, 325, 15);
		contentPanel.add(lblUnitsAndTheir);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 343, 201, 103);
		contentPanel.add(scrollPane_1);
		
		unitListing = new JList();
		scrollPane_1.setViewportView(unitListing);
		
		// Unit buttons
		btnAddUnit = new JButton("Add");
		btnAddUnit.setToolTipText("Add a unit (e.g., B) and its projection information");
		btnAddUnit.setBounds(220, 343, 117, 25);
		btnAddUnit.addActionListener(this);
		contentPanel.add(btnAddUnit);
		
		btnEditUnit = new JButton("Edit");
		btnEditUnit.setToolTipText("Edit the projection information for the highlighted unit");
		btnEditUnit.setBounds(220, 380, 117, 25);
		btnEditUnit.addActionListener(this);
		contentPanel.add(btnEditUnit);
		
		btnRemoveUnit = new JButton("Remove");
		btnRemoveUnit.setToolTipText("Remove the selected unit from the list");
		btnRemoveUnit.setBounds(220, 417, 117, 25);
		btnRemoveUnit.addActionListener(this);
		contentPanel.add(btnRemoveUnit);
		
		comboBoxDeviceType = new JComboBox();
		comboBoxDeviceType.setToolTipText("The category of devices in this bank");
		comboBoxDeviceType.setModel(new DefaultComboBoxModel(new String[] {"Other", "Interactive", "Motor", "Player", "Slew", "Slew Projector", "Slide Projector", "Video Projector"}));
		comboBoxDeviceType.setSelectedIndex(0);
		comboBoxDeviceType.setBounds(116, 40, 147, 20);
		comboBoxDeviceType.addActionListener(this);
		contentPanel.add(comboBoxDeviceType);
		
		JLabel lblBankType = new JLabel("Bank Type");
		lblBankType.setBounds(10, 43, 64, 14);
		contentPanel.add(lblBankType);
		
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
				cancelButton.addActionListener(this);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	/**
	 * Adds a command to the list of those getting special treatment
	 * @param command	The command to get special consideration
	 */
	public void addSpecialCaseCommand(SpiceCommand command)
	{
		// Add the new command
		commands.put(command.getName(), command);
		
		refreshCommandList();
	}
	
	/**
	 * Adds a unit to the list of units with projection position information
	 * @param unit	The unit to add
	 */
	public void addUnit(Unit unit)
	{
		// Add the new unit
		units.put(unit.getName(), unit);
		
		refreshUnitList();
	}
	
	/**
	 * Removes the command of the given name.
	 * @param command	The name of the command to be removed
	 */
	public void removeSpecialCaseCommand(String command)
	{
		commands.remove(command);
		
		refreshCommandList();
	}
	
	/**
	 * Removes the unit of the given name.
	 * @param unit	The name of the unit to be removed
	 */
	public void removeUnit(String unit)
	{
		units.remove(unit);
		
		refreshUnitList();
	}
	
	/**
	 * Refreshes the list of commands.
	 */
	public void refreshCommandList()
	{
		// Refresh the list
		Object[] commandNames = commands.keySet().toArray();
		Arrays.sort(commandNames);	// Alphabetize
		commandListing.setListData(commandNames);
	}
	
	/**
	 * Refreshes the list of units.
	 */
	public void refreshUnitList()
	{
		// Refresh the list
		Object[] unitNames = units.keySet().toArray();
		Arrays.sort(unitNames);	// Alphabetize
		unitListing.setListData(unitNames);
	}
	
	/**
	 * Closes the window.
	 */
	private void closeWindow()
	{
		this.dispose();
		this.setVisible(false);
	}
	
	/**
	 * Enables or disables buttons associated with units, 
	 * depending on the type of device associated with the bank.
	 */
	private void enableOrDisableUnitButtons()
	{
		// Update whether unit buttons are enabled
		String selection = (String) comboBoxDeviceType.getSelectedItem();
		if (		selection.equals(UnitMotor.MOTOR)
				|| selection.equals(UnitSlew.SLEW)
				|| selection.equals(UnitSlideProjector.SLIDE_PROJ)
				|| selection.equals(UnitVideoProjector.VIDEO_PROJ))
			enableUnitButtons();
		else
			disableUnitButtons();
	}
	
	/**
	 * Turns off the buttons associated with units
	 */
	private void enableUnitButtons()
	{
		btnAddUnit.setEnabled(true);
		btnEditUnit.setEnabled(true);
		btnRemoveUnit.setEnabled(true);
	}
	
	/**
	 * Turns off the buttons associated with units
	 */
	private void disableUnitButtons()
	{
		btnAddUnit.setEnabled(false);
		btnEditUnit.setEnabled(false);
		btnRemoveUnit.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == cancelButton)
			closeWindow();
		else if (e.getSource() == okButton)
		{
			String name = bankNameField.getText();
			
			if (name.length() > 0)
			{	
				Bank.CommandAction cmdAction;
				if (rdbtnKeepCommands.isSelected())
					cmdAction = Bank.CommandAction.KEEP;
				else if (rdbtnDiscardCommands.isSelected())
					cmdAction = Bank.CommandAction.DISCARD;
				else	// rdbtnAutoconvertToDigitalsky
					cmdAction = Bank.CommandAction.CONVERT_TO_DS;
				Boolean isKeepingMost = rdbtnKeepCommands.isSelected();
				Bank bank = new Bank(	name, 
										(String)comboBoxDeviceType.getSelectedItem(), 
										cmdAction);
				
				// Add any special-case commands
				SpiceCommand[] cmds = Arrays.copyOf(commands.values().toArray(), commands.size(), SpiceCommand[].class);
				bank.setSpecialCommands(cmds);
				
				// Add any units
				Unit[] unts = Arrays.copyOf(units.values().toArray(), units.size(), Unit[].class);
				bank.setUnits(unts);
				
				// Hand off the Bank to the parent window
				if (parentWindow != null)
				{
					if (toBeEdited != null)	// If we were editing, remove old before adding changed
						parentWindow.removeBank(toBeEdited.toString());
					parentWindow.addBank(bank);
				}
				
				closeWindow();
			}
			else	// No name entered
				JOptionPane.showMessageDialog(this, "You must enter a name for the bank");
		}
		else if (e.getSource() == comboBoxDeviceType)
		{
			if (units.size() > 0)	// If there are previously defined units
			{
				// Ask user if they're sure they want change device type and discard units
				if (JOptionPane.showConfirmDialog(this, 
						"Are you sure you want to change device types? This will remove all previously defined units") 
						== JOptionPane.OK_OPTION)
				{
					// Discard units (since they're of a different type)
					units = new HashMap<String, Unit>();
					refreshUnitList();
				}
				else
					// Restore old selection
					comboBoxDeviceType.setSelectedIndex(oldDeviceSelectionIndex);
			}
			
			// Enable or disable buttons, depending on device type
			enableOrDisableUnitButtons();
			// Update device selection index
			oldDeviceSelectionIndex = comboBoxDeviceType.getSelectedIndex();
		}
		else if (e.getSource() == btnAddCommand)
		{
			EditCommandGUI ecg = new EditCommandGUI(this);
			ecg.setVisible(true);
		}
		else if (e.getSource() == btnEditCommand)
		{
			if (commandListing.getSelectedIndex() > -1)	// Make sure something is selected
			{
				EditCommandGUI ecg = new EditCommandGUI(this, commands.get( (String) commandListing.getSelectedValue() ));
				ecg.setVisible(true);
			}
			else
				JOptionPane.showMessageDialog(this, "No command selected");
		}
		else if (e.getSource() == btnRemoveCommand)
		{
			if (commandListing.getSelectedIndex() > -1)	// Make sure something is selected
				this.removeSpecialCaseCommand((String) commandListing.getSelectedValue());
			else
				JOptionPane.showMessageDialog(this, "No command selected");
		}
		else if (e.getSource() == btnAddUnit)
		{
			// See what kind of unit we should be adding
			String selection = (String) comboBoxDeviceType.getSelectedItem();
			if (selection.equals(UnitMotor.MOTOR))
			{
				EditUnitMotorGUI eug = new EditUnitMotorGUI(this);
				eug.setVisible(true);
			}
			else if (selection.equals(UnitSlew.SLEW))
			{
				EditUnitSlewGUI eug = new EditUnitSlewGUI(this);
				eug.setVisible(true);
			}
			else if (selection.equals(UnitSlideProjector.SLIDE_PROJ))
			{
				EditUnitSlideProjGUI eug = new EditUnitSlideProjGUI(this);
				eug.setVisible(true);
			}
			else if (selection.equals(UnitVideoProjector.VIDEO_PROJ))
			{
				EditUnitVideoProjGUI eug = new EditUnitVideoProjGUI(this);
				eug.setVisible(true);
			}
		}
		else if (e.getSource() == btnEditUnit)
		{
			if (unitListing.getSelectedIndex() > -1)	// Make sure something is selected
			{
				Unit unitToEdit = units.get( (String) unitListing.getSelectedValue() );
				
				// See what kind of unit we should be adding
				String selection = (String) comboBoxDeviceType.getSelectedItem();
				if (selection.equals(UnitMotor.MOTOR))
				{
					EditUnitMotorGUI eug = new EditUnitMotorGUI(this, unitToEdit);
					eug.setVisible(true);
				}
				else if (selection.equals(UnitSlew.SLEW))
				{
					EditUnitSlewGUI eug = new EditUnitSlewGUI(this, unitToEdit);
					eug.setVisible(true);
				}
				else if (selection.equals(UnitSlideProjector.SLIDE_PROJ))
				{
					EditUnitSlideProjGUI eug = new EditUnitSlideProjGUI(this, unitToEdit);
					eug.setVisible(true);
				}
				else if (selection.equals(UnitVideoProjector.VIDEO_PROJ))
				{
					EditUnitVideoProjGUI eug = new EditUnitVideoProjGUI(this, unitToEdit);
					eug.setVisible(true);
				}
			}
			else
				JOptionPane.showMessageDialog(this, "No unit selected");
		}
		else if (e.getSource() == btnRemoveUnit)
		{
			if (unitListing.getSelectedIndex() > -1)	// Make sure something is selected
				this.removeUnit((String) unitListing.getSelectedValue());
			else
				JOptionPane.showMessageDialog(this, "No command selected");
		}
	}
}
