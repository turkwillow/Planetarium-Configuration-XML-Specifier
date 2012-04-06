package org.carnegiesciencecenter.buhl;

import org.jdom.Element;

public class Unit implements Comparable
{
	protected String name;
	protected String deviceType;
	public static final String NAME = "name";
	public static final String OTHER = "Other";
	
	public Unit(String Name, String devType)
	{
		name = Name;
		setDeviceType(devType);
	}
	
	public Unit(String Name)
	{
		name = Name;
		deviceType = OTHER;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String Name)
	{
		name = Name;
	}
	
	public void setDeviceType(String devType)
	{
		deviceType = devType.trim();
	}
	
	public String getDeviceType()
	{
		return deviceType;
	}
	
	/**
	 * If the given number equals Double.MAX_VALUE, returns zero.
	 * Otherwise, the original number is returned.
	 * (Purpose of this function is to turn an unusable error value
	 *  into something practical, rather than just showing the user
	 *  something nasty)
	 * @param num	The number to check
	 * @return	The original number given if it does not equal Double.MAX_VALUE,
	 * 			0.0 otherwise
	 */
	protected double maxToZeroOrOriginal(double num)
	{
		if (num == Double.MAX_VALUE)
			return 0.0;
		else
			return num;
	}
	
	/**
	 * Gets the JDOM XML Element that represents this Unit.
	 * @return	The JDOM XML Element that describes this Unit
	 */
	public Element getXML()
	{
		Element unitElement = new Element("unit");	// The root of the bank definition
		unitElement.setAttribute(NAME, name);
		unitElement.setAttribute(Bank.DEVICE_TYPE, deviceType);
		
		return unitElement;
	}

	@Override
	public int compareTo(Object obj) 
	{
		if (this == obj)
			return 0;	// Equal
		if (!(obj instanceof Unit))
			return -1;	// Arbitrary for non-Unit
		
		return name.compareTo( ((Unit)obj).getName() ); 	// Sort by name
	}
}
