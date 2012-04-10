package org.carnegiesciencecenter.buhl;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;

import org.carnegiesciencecenter.buhl.Bank.CommandAction;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class EditCommandGUI extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox comboBox;
	private JButton okButton, cancelButton;
	private JRadioButton rdbtnRunViaSpice, rdbtnDiscard, rdbtnConvertToDigitalsky;
	private EditBankGUI parentWindow;	// The window that called this one
	private SpiceCommand toBeEdited;
	private String toBeEditedName;	// Since the name may change

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EditCommandGUI dialog = new EditCommandGUI(null);
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
	public EditCommandGUI(EditBankGUI parentWin) 
	{
		setTitle("SPICE Commands");
		initialize(parentWin);
		
		toBeEdited = null;
	}
	
	/**
	 * Create the dialog, supplying it with a Unit to start with and edit.
	 * @param parent	The window that request this one be created
	 * @param toEdit	The Unit to edit
	 */
	public EditCommandGUI(EditBankGUI parentWin, SpiceCommand toEdit) 
	{
		initialize(parentWin);
		
		toBeEdited = toEdit;
		
		if (toBeEdited != null)
		{
			toBeEditedName = toBeEdited.getName();	// Save old name
			
			// Set the selected item to be the one with the name of the 
			// command to be edited
			for (int i = 0; i < comboBox.getItemCount(); i++)
				if (toBeEditedName.equals(comboBox.getItemAt(i)))
				{
					comboBox.setSelectedIndex(i);
					break;
				}
			
			switch (toEdit.getAction())
			{
			// Ignore case of Run Via Spice, as this is set by default
			case DISCARD:
				rdbtnDiscard.doClick();
				break;
			case CONVERT_TO_DS:
				rdbtnConvertToDigitalsky.doClick();
				break;
			}
		}
	}
	
	/**
	 * Initializes the GUI.
	 * @param parent	The Window that created this one
	 */
	private void initialize(EditBankGUI parentWin)
	{
		parentWindow = parentWin;	// Set the window's parent
		
		setBounds(100, 100, 345, 266);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblCommandName = new JLabel("Command Name");
		lblCommandName.setToolTipText("Name of the command to get special treatment");
		lblCommandName.setBounds(19, 28, 124, 15);
		contentPanel.add(lblCommandName);
		
		rdbtnRunViaSpice = new JRadioButton("Run via SPICE");
		rdbtnRunViaSpice.setToolTipText("Run this command via SPICE (e.g., using DigitalSky's \"Spice Cue Exec\" command)");
		rdbtnRunViaSpice.setSelected(true);
		buttonGroup.add(rdbtnRunViaSpice);
		rdbtnRunViaSpice.setBounds(20, 77, 149, 23);
		contentPanel.add(rdbtnRunViaSpice);
		
		rdbtnDiscard = new JRadioButton("Discard");
		rdbtnDiscard.setToolTipText("Ignore the command");
		buttonGroup.add(rdbtnDiscard);
		rdbtnDiscard.setBounds(20, 104, 149, 23);
		contentPanel.add(rdbtnDiscard);
		
		rdbtnConvertToDigitalsky = new JRadioButton("Convert to DigitalSky");
		rdbtnConvertToDigitalsky.setToolTipText("Automatically convert to a corresponding DigitalSky command");
		buttonGroup.add(rdbtnConvertToDigitalsky);
		rdbtnConvertToDigitalsky.setBounds(20, 131, 174, 23);
		contentPanel.add(rdbtnConvertToDigitalsky);
		
		comboBox = new JComboBox();
		comboBox.setMaximumRowCount(11);
		comboBox.setToolTipText("List of possible commands");
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"AccFwd", "AccRev", "Alt", "AuDiofade", "BLackout", "ButtonBlinkofF", "ButtonBlinkoN", "ChapterNext", "ChapterPrev", "CHase", "CoNtrast", "DigistarBeamoFf", "DigistarBeamoN", "DigistarCOntinue", "DigistarENd", "DigistarLensClose", "DigistarLensOpen", "DigistarModeLive", "DigistarPAuse", "DigistarPLay", "DigistarREset", "DigistarSTop", "DisplayofF", "DisplayoN", "Dissolve", "DutyCycle", "EJect", "Fade", "FanofF", "FanoN", "FForWard", "FlashofF", "FLashoN", "FLashRandom", "FLashTime", "FlicKerTime", "ForWard", "FRameFwd", "FRameRev", "FwdMotor", "FwdReSet", "GenerateTime", "Home", "HotSelectInput", "HotSelectSource", "InterAdvance", "InterLoaD", "InterLock", "InterMode", "InterPage", "InterRun", "InterSTop", "InterVote", "JamSync", "LineDoubleoFf", "LineDoubleoN", "LoadMedia", "LoCate", "LongFade", "ManDisTime", "MotorofF", "MotoroN", "MPulse", "OFf", "ON", "PaRk", "PaTch", "PLay", "PlayerStop", "PUlse", "RadiooFf", "RadioOn", "RadioPulse", "ReLayofF", "ReLayoN", "Repeat", "RepeatPlayoFf", "RepeatPlayoN", "RepStart", "ResetClock", "ReVerse", "RevMotor", "RevReSet", "ReWind", "RPulse", "RSrchFwd", "RSrchRev", "RUN", "RunFwd", "RunRev", "SEarch", "SelectDisk", "SelectInput", "SelectMode", "SelectOutput", "SelectProgram", "SelectSource", "SetBrightness", "SetContrast", "SLew", "SMpteofF", "SMpteoN", "STAtofF", "STAtoN", "STIll", "STop", "ThymePAuse", "ThymeStart", "ThymeStoP", "TPLay", "TSEarch", "VariFwd", "VariRev", "VariSpeed", "VaxLogOut", "VirtualTime", "ZeissCOntinue", "ZeissLoad", "ZeissofFLine", "ZeissoNLine", "ZeissPAuse", "ZeissREset", "ZeissSmpteofF", "ZeissSmpteoN", "ZeissSTop", "Zero"}));
		comboBox.setBounds(140, 28, 149, 24);
		contentPanel.add(comboBox);
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
		{
			closeWindow();
		}
		else if (e.getSource() == okButton)
		{
			// Get command details
			String chosenCommandName = (String) comboBox.getSelectedItem();
			
			CommandAction chosenCommandAction;
			if (rdbtnRunViaSpice.isSelected())
				chosenCommandAction = Bank.CommandAction.KEEP;
			else if (rdbtnDiscard.isSelected())
				chosenCommandAction = Bank.CommandAction.DISCARD;
			else	// Convert to DS
				chosenCommandAction = Bank.CommandAction.CONVERT_TO_DS;
			
			// Hand off command details to parent
			if (parentWindow != null)
			{
				parentWindow.removeSpecialCaseCommand(toBeEditedName);
				parentWindow.addSpecialCaseCommand(new SpiceCommand(chosenCommandName, chosenCommandAction));
			}
			
			// Close
			closeWindow();
		}
		
	}
}
