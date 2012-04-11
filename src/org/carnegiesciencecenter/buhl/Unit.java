package org.carnegiesciencecenter.buhl;

import org.jdom.Element;

public class Unit implements Comparable
{
	protected String name;	// The name of the Unit (e.g., "E")
	protected String deviceType;	// The type of device (e.g., "Video Projector")
	// For referring to things in XML:
	public static final String NAME_ATTR = "name";
	public static final String OTHER = "Other";
	
	/**
	 * Public constructor, taking the Unit's name and device type
	 * @param Name		The name of the Unit (e.g., "E")
	 * @param devType	The type of device (e.g., "Video Projector")
	 */
	public Unit(String Name, String devType)
	{
		name = Name;
		setDeviceType(devType);
	}
	
	/**
	 * Public constructor, taking the Unit's name
	 * and assigning the device type a default value of "Other"
	 * @param Name	The name of the Unit (e.g., "E")
	 */
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
	 * Gets the JDOM XML Element that represents this Unit.
	 * @return	The JDOM XML Element that describes this Unit
	 */
	public Element getXML()
	{
		Element unitElement = new Element("unit");	// The root of the bank definition
		unitElement.setAttribute(NAME_ATTR, name);
		unitElement.setAttribute(Bank.DEVICE_TYPE_ATTR, deviceType);
		
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
