package org.carnegiesciencecenter.buhl;

import org.carnegiesciencecenter.buhl.Bank.CommandAction;
import org.jdom.Element;

/**
 * Lightweight class for keeping track of commands
 * @author D Turka
 */
public class SpiceCommand 
{
	private String name;
	private CommandAction action;
	
	/**
	 * Public constructor for SpiceCommand
	 * @param cmdName	The name of the command (e.g., "Dissolve")
	 * @param cmdAction	The action to be taken when the conversion 
	 * 					process encounters this command
	 */
	public SpiceCommand(String cmdName, CommandAction cmdAction)
	{
		name = cmdName;
		action = cmdAction;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String cmdName)
	{
		name = cmdName;
	}
	
	public CommandAction getAction()
	{
		return action;
	}
	
	public void setAction(CommandAction cmdAction)
	{
		action = cmdAction;
	}

	/**
	 * Gets the JDOM XML Element that represents this Unit.
	 * @return	The JDOM XML Element that describes this Unit
	 */
	public Element getXML()
	{
		Element element = new Element("command");
		
		element.setAttribute(Bank.NAME_ATTR, name);
		switch (action)
		{
		case KEEP:
			element.setAttribute(Bank.ACTION_ATTR, Bank.KEEP);
			break;
		case DISCARD:
			element.setAttribute(Bank.ACTION_ATTR, Bank.DISCARD);
			break;
		case CONVERT_TO_DS:
			element.setAttribute(Bank.ACTION_ATTR, Bank.CONVERT);
			break;
		default:
			element.setAttribute(Bank.ACTION_ATTR, Bank.KEEP);	// If all else fails, don't throw stuff out
			break;
		}
		
		return element;
	}
}
