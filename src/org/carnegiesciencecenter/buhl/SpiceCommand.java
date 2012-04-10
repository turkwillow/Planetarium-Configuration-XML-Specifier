package org.carnegiesciencecenter.buhl;

import org.carnegiesciencecenter.buhl.Bank.CommandAction;

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
}
