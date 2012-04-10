package org.carnegiesciencecenter.buhl;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import java.awt.Insets;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileFilter;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditConfigMainGUI implements ActionListener
{
	public static final String FILE_EXTENSION   = "xml"; // XML file extension
	public static final String IMPORT_EXTENSION = "ini"; // Extension for imported settings files
	private String filename = "configFile";
	private JFrame frmSpiceDeviceConfiguration;
	private JList list;
	private JButton btnAdd, btnEdit, btnRemove;
	private JMenuItem mntmNew, mntmOpen, mntmImportBankList, mntmSave, mntmSaveAs, mntmExit;
	private JScrollPane scrollPane;
	private HashMap<String, Bank> banks;
	private File saveFile;	// The File that we will save to
	private Boolean hasMadeChanges;	// Keeps track of whether or not unsaved file changes exist

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditConfigMainGUI window = new EditConfigMainGUI();
					window.frmSpiceDeviceConfiguration.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EditConfigMainGUI() 
	{
		initialize();
	}

	/**
	 * Adds the given bank to the list of Banks.
	 * @param bank	The Bank to be added
	 */
	public void addBank(Bank bank)
	{
		banks.put(bank.toString(), bank);
		
		refreshBankList();
	}
	
	/**
	 * Removes the Bank with the given name from the list.
	 * @param bank	The name of the Bank to remove
	 */
	public void removeBank(String bank)
	{
		banks.remove(bank);
		
		refreshBankList();
	}
	
	/**
	 * Refreshes the list of Banks.
	 */
	private void refreshBankList()
	{
		// Refresh the list
		String[] bankNames = Arrays.copyOf(banks.keySet().toArray(), banks.size(), String[].class);
		Arrays.sort(bankNames);	// Alphabetize
		list.setListData(bankNames);
		
		hasMadeChanges = true;	// Changes have most likely been made
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		banks = new HashMap<String, Bank>();
		saveFile = null;
		hasMadeChanges = false;
		
		frmSpiceDeviceConfiguration = new JFrame();
		frmSpiceDeviceConfiguration.setBounds(100, 100, 364, 235);
		frmSpiceDeviceConfiguration.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSpiceDeviceConfiguration.getContentPane().setLayout(null);
		frmSpiceDeviceConfiguration.setTitle("SPICE Device Configuration");
		
		JLabel lblBanks = new JLabel("Banks");
		lblBanks.setToolTipText("The list of all banks (e.g., ANIM, SRC2)");
		lblBanks.setBounds(12, 28, 43, 15);
		frmSpiceDeviceConfiguration.getContentPane().add(lblBanks);
		
		btnAdd = new JButton("Add");
		btnAdd.setToolTipText("Add a new bank");
		btnAdd.addActionListener(this);
		btnAdd.setBounds(226, 33, 117, 25);
		frmSpiceDeviceConfiguration.getContentPane().add(btnAdd);
		
		btnEdit = new JButton("Edit");
		btnEdit.setToolTipText("Make changes to the selected bank");
		btnEdit.addActionListener(this);
		btnEdit.setBounds(226, 70, 117, 25);
		frmSpiceDeviceConfiguration.getContentPane().add(btnEdit);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(62, 28, 154, 136);
		frmSpiceDeviceConfiguration.getContentPane().add(scrollPane);
		
		list = new JList();
		scrollPane.setViewportView(list);
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btnRemove = new JButton("Remove");
		btnRemove.setToolTipText("Remove the selected bank from the list");
		btnRemove.addActionListener(this);
		btnRemove.setBounds(226, 107, 117, 25);
		frmSpiceDeviceConfiguration.getContentPane().add(btnRemove);
		
		JMenuBar menuBar = new JMenuBar();
		frmSpiceDeviceConfiguration.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(this);
		mntmNew.setToolTipText("Create a new Planetarium configuration file");
		mnFile.add(mntmNew);
		
		mntmOpen = new JMenuItem("Open Config File...");
		mntmOpen.setToolTipText("Open an existing Planetarium configuration file");
		mntmOpen.addActionListener(this);
		mnFile.add(mntmOpen);
		
		mntmImportBankList = new JMenuItem("Import Spice Config File...");
		mntmImportBankList.setToolTipText("Use a SPICE configuration file"
										+ "  (e.g., C:\\Program Files\\Sky-Skan\\SPICE Automation\\Spice-skyvision.ini)");
		mntmImportBankList.addActionListener(this);
		mnFile.add(mntmImportBankList);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		mntmSave = new JMenuItem("Save");
		mntmSave.setToolTipText("Save the current Planetarium configuration");
		mntmSave.addActionListener(this);
		mnFile.add(mntmSave);
		
		mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.setToolTipText("Save the current Planetarium configuration to a new file");
		mntmSaveAs.addActionListener(this);
		mnFile.add(mntmSaveAs);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.setToolTipText("Exit this editor");
		mntmExit.addActionListener(this);
		mnFile.add(mntmExit);
	}
	
	/**
	 * Opens a file dialog, allowing the use to choose a file or not. 
	 * If use chooses a file, it will be returned. Otherwise, null is returned.
	 * @param fileFilter	Specifies what kind of files to filter out. Pass
	 * 						null if this does not matter.
	 * @return	The chosen file, or null if there wasn't one.
	 */
	private File getFileViaDialog(FileFilter fileFilter)
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		chooser.setFileFilter(fileFilter);
		if (chooser.showOpenDialog(frmSpiceDeviceConfiguration) == JFileChooser.APPROVE_OPTION)
			return chooser.getSelectedFile();
		return null;
	}
	
	/**
	 * Shows a dialog that allows the user to select where they want to save
	 * their file and sets saveFile to that file.
	 * @return	JFileChooser.APPROVE_OPTION if the user selected a file
	 */
	private int setSaveAsFile()
	{
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		jfc.setFileFilter(new SPDSFileFilter());
		
		int ret = jfc.showSaveDialog(frmSpiceDeviceConfiguration);
		if (ret == JFileChooser.APPROVE_OPTION)
		{
			saveFile = jfc.getSelectedFile();
			// Make sure the file has a .xml extension
			if (saveFile != null)
			{
				String fName = saveFile.getAbsolutePath();
				if (!fName.endsWith(".xml") && !fName.endsWith(".XML"))
					saveFile = new File(fName + ".xml");
			}
		}
		
		return ret;
	}
	
	/**
	 * Saves the file to the most recently chosen save location,
	 * or prompts the user to select a location if there isn't one 
	 * chosen and then saves it there.
	 */
	private void saveFile()
	{
		if (saveFile == null)
			if (setSaveAsFile() != JFileChooser.APPROVE_OPTION)
				return;
		
		XMLOutputter xout = new XMLOutputter();
		xout.setFormat(Format.getPrettyFormat());
		String outString = xout.outputString(getXML());
		PrintWriter fout = null;
		
		try 
		{
			fout = new PrintWriter(new FileWriter(saveFile));
			fout.println("<?xml version='1.0' encoding='UTF-8'?>");
			fout.print(outString);
			
			hasMadeChanges = false;	// All changes have just been saved
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		if (fout != null)
			fout.close();
	}
	
	/**
	 * Imports the given Spice Windows Startup File and adds its
	 * banks to the list of Banks.
	 * @param spiceFile The config file from which to gather bank names.
	 */
	private void importSpiceConfigFile(File spiceFile)
	{
		if (spiceFile == null)
			return;
		
		StringBuilder sb = new StringBuilder();
		BufferedReader fin = null;
		try {
			fin = new BufferedReader(new FileReader(spiceFile));
			String line;
			// Get all the lines of the file
			while ((line = fin.readLine()) != null)
				sb.append(line + "\n");
			String result = sb.toString();
			
			// Look for all devices within, e.g., DMX in <PAGE:DMX (01)>
			String regex = "<PAGE:(\\S+) \\(\\d+\\)>";
			Pattern pattern = Pattern.compile(regex);
			Matcher m = pattern.matcher(result);
			
			while (m.find())
			{
				String bankName = m.group().substring(6, m.group().indexOf(' '));
				addBank(new Bank(bankName, "Other", Bank.CommandAction.KEEP));
			}
			
		} 
		catch (FileNotFoundException e) 
		{
			JOptionPane.showMessageDialog(frmSpiceDeviceConfiguration, "Error finding file");
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(frmSpiceDeviceConfiguration, "Error reading file");
		}
		
		if (fin != null)
			try 
			{
				fin.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**
	 * Closes the window.
	 */
	private void closeWindow()
	{
		frmSpiceDeviceConfiguration.dispose();
		frmSpiceDeviceConfiguration.setVisible(false);
	}
	
	/**
	 * Opens an XML file representing the Planetarium's configuration
	 * @param file An XML file representing the Planetarium's configuration
	 */
	private void openXMLConfigFile(File file)
	{
		SAXBuilder builder = new SAXBuilder();
		
		try 
		{
			Document doc = builder.build(file);
			
			Element root = doc.getRootElement();
			List elements = root.getChildren();
			Iterator iter = elements.iterator();
			
			banks = new HashMap<String, Bank>();	// Reset banks
			
			while (iter.hasNext())	// For each Bank
			{
				// Read Bank
				Element elem = (Element) iter.next();
				String sAction = elem.getAttributeValue(Bank.ACTION);
				Bank.CommandAction cmdAction;
				if (sAction.equals(Bank.KEEPING_MOST))
					cmdAction = Bank.CommandAction.KEEP;
				else if (sAction.equals(Bank.DISCARD_MOST))
					cmdAction = Bank.CommandAction.DISCARD;
				else	// sAction.equals(Bank.CONVERT_MOST)
					cmdAction = Bank.CommandAction.CONVERT_TO_DS;
				
				String deviceType = elem.getAttributeValue(Bank.DEVICE_TYPE);
				Bank bank = new Bank(elem.getAttributeValue(Bank.NAME), deviceType, cmdAction);
				// Read any special-case commands or units
				List commands = elem.getChildren();
				Iterator cIter = commands.iterator();
				while (cIter.hasNext())
				{
					Element cElem = (Element) cIter.next();
					// Find out what to do with the action
					String elementType = cElem.getName();	// Get name of thing
					if (elementType.equals("command"))
					{
						Bank.CommandAction ca = null;
						String actionName = cElem.getAttributeValue(Bank.ACTION);
						if (actionName.equals(Bank.DISCARD))
							ca = Bank.CommandAction.DISCARD;
						else if (actionName.equals(Bank.CONVERT))
							ca = Bank.CommandAction.CONVERT_TO_DS;
						else	// Keep
							ca = Bank.CommandAction.KEEP;
						// Add the new action to the bank
						bank.setCommand(cElem.getAttributeValue(Bank.NAME), ca);
					}
					else if (elementType.equals("unit"))
					{
						Unit unit;
						String unitName = cElem.getAttributeValue(Unit.NAME);
						
						if (deviceType.equals(UnitMotor.MOTOR))
						{
							unit = new UnitMotor(
									unitName,
									cElem.getAttributeValue(UnitMotor.MIN_WIDTH),
									cElem.getAttributeValue(UnitMotor.MAX_WIDTH),
									cElem.getAttributeValue(UnitMotor.MIN_HEIGHT),
									cElem.getAttributeValue(UnitMotor.MAX_HEIGHT),
									cElem.getAttributeValue(UnitMotor.MIN_POS),
									cElem.getAttributeValue(UnitMotor.MAX_POS));
						}
						else if (deviceType.equals(UnitSlew.SLEW))
						{
							// String name, double MinPos, double MaxPos, double MinValue, double MaxValue
							unit = new UnitSlew(
									unitName,
									cElem.getAttributeValue(UnitSlew.MIN_POS),
									cElem.getAttributeValue(UnitSlew.MAX_POS),
									cElem.getAttributeValue(UnitSlew.MIN_VAL),
									cElem.getAttributeValue(UnitSlew.MAX_VAL),
									cElem.getAttributeValue(UnitSlew.TRIP_TIME));
						}
						else if (deviceType.equals(UnitSlideProjector.SLIDE_PROJ))
						{
							unit = new UnitSlideProjector(
									unitName,
									cElem.getAttributeValue(UnitSlideProjector.AZIMUTH),
									cElem.getAttributeValue(UnitSlideProjector.ELEVATION),
									cElem.getAttributeValue(UnitSlideProjector.ROTATION),
									cElem.getAttributeValue(UnitSlideProjector.WIDTH),
									cElem.getAttributeValue(UnitSlideProjector.HEIGHT));
						}
						else if (deviceType.equals(UnitVideoProjector.VIDEO_PROJ))
						{
							unit = new UnitVideoProjector(
									unitName,
									cElem.getAttributeValue(UnitSlideProjector.AZIMUTH),
									cElem.getAttributeValue(UnitSlideProjector.ELEVATION),
									cElem.getAttributeValue(UnitSlideProjector.ROTATION),
									cElem.getAttributeValue(UnitSlideProjector.WIDTH),
									cElem.getAttributeValue(UnitSlideProjector.HEIGHT));
						}
						else
							unit = new Unit(unitName, deviceType);
						bank.setUnit(unit);
					}
				}
				addBank(bank);
			}
			
		} 
		catch (JDOMException e) 
		{
			JOptionPane.showMessageDialog(frmSpiceDeviceConfiguration, "File is not well-formed");
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(frmSpiceDeviceConfiguration, "Couldn't read file");
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(frmSpiceDeviceConfiguration, "Couldn't read file");
		}
	}
	
	/**
	 * Returns the XML representation of the Planetarium configuration.
	 * @return	The XML representation of the Planetarium configuration.
	 */
	private Element getXML()
	{
		Element root = new Element("planetarium");	// The root of the bank definition
		Bank[] bankArray = Arrays.copyOf(banks.values().toArray(), banks.size(), Bank[].class);
		Arrays.sort(bankArray);
		for (Bank b : bankArray)
			root.addContent(b.getXML());
		
		return root;
	}

	/**
	 * Used to select only files of the type suggested in FILE_EXTENSION
	 * when using dialogs.
	 */
	private class SPDSFileFilter extends FileFilter
	{

		@Override
		public boolean accept(File f) {
			if (f.isDirectory())
				return true;
			return f.getName().toLowerCase().endsWith("." + FILE_EXTENSION);
		}

		@Override
		public String getDescription() {
			return "XML file";
		}
		
	}

	/**
	 * Used to select only files of the type .ini
	 * when using dialogs.
	 */
	private class IniFileFilter extends FileFilter
	{

		@Override
		public boolean accept(File f) {
			if (f.isDirectory())
				return true;
			return f.getName().toLowerCase().endsWith(".ini");
		}

		@Override
		public String getDescription() {
			return "ini file";
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == btnAdd)
		{
			EditBankGUI ebg = new EditBankGUI(this);
			ebg.setVisible(true);
		}
		else if (e.getSource() == btnEdit)
		{
			if (list.getSelectedIndex() > -1)
			{
				String bankName = (String) list.getSelectedValue();
				
				EditBankGUI ebg = new EditBankGUI(this, banks.get(bankName));
				ebg.setVisible(true);
			}
		}
		else if (e.getSource() == btnRemove)
		{
			if (list.getSelectedIndex() > -1)
				removeBank((String) list.getSelectedValue());
		}
		else if (e.getSource() == mntmNew)
		{
			saveFile = null;	// Set it so that we have no current/working file we're saving to
			hasMadeChanges = true;
		}
		else if (e.getSource() == mntmOpen)
		{
			File config = getFileViaDialog(new SPDSFileFilter());
			
			if (config != null)
			{
				openXMLConfigFile(config);
				saveFile = config;
			}
		}
		else if (e.getSource() == mntmImportBankList)
		{
			File config = getFileViaDialog(new IniFileFilter());
			importSpiceConfigFile(config);
		}
		else if (e.getSource() == mntmSave)
		{
			saveFile();
		}
		else if (e.getSource() == mntmSaveAs)
		{
			if (setSaveAsFile() == JFileChooser.APPROVE_OPTION)	// Check for cancel
				saveFile();
		}
		else if (e.getSource() == mntmExit)
		{
			// If user has made changes, see if they would like to save them
			if (hasMadeChanges)
			{
				int choice = JOptionPane.showConfirmDialog(frmSpiceDeviceConfiguration, 
						"There are unsaved changes.\nWould you like to save before exiting?", 
						"Save?", 
						JOptionPane.YES_NO_CANCEL_OPTION, 
						JOptionPane.WARNING_MESSAGE);
				switch (choice)
				{
					case JOptionPane.YES_OPTION:
						saveFile();
						closeWindow();
						break;
					case JOptionPane.NO_OPTION:
						closeWindow();
						break;
					case JOptionPane.CANCEL_OPTION:
						break;
				}
			}
			else
				closeWindow();			
		}
	}
}
