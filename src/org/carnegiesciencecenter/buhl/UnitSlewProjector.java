package org.carnegiesciencecenter.buhl;

import org.jdom.Element;

public class UnitSlewProjector extends Unit
{
	// Each slew projector is associated with 2 slews and 1 motor
	private String associatedSlew1, associatedSlew2;
	private String associatedMotor;
	
	public static final String SLEW_PROJECTOR = "Slew Projector";
	public static final String ASSOCIATED_SLEW_1_ATTR = "slew1";
	public static final String ASSOCIATED_SLEW_2_ATTR  = "slew2";
	public static final String ASSOCIATED_MOTOR_ATTR   = "motor";
	
	/**
	 * Constructor, taking the associated slews and motor in String form 
	 * (e.g., "SLEW A", "MOTR C")
	 * @param Name	The name of the slew projector
	 * @param slew1	String representation of the first associated slew (e.g., "SLEW A")
	 * @param slew2	String representation of the other associated slew (e.g., "SLEW B")
	 * @param motor String representation of the associated motor (e.g., "MOTR A")
	 */
	public UnitSlewProjector(String Name, String slew1, String slew2, String motor)
	{
		super(Name, SLEW_PROJECTOR);
		setSlew1(slew1);
		setSlew2(slew2);
		setMotor(motor);
	}
	
	public void setSlew1(String slew)
	{
		associatedSlew1 = slew;
	}
	
	public void setSlew2(String slew)
	{
		associatedSlew2 = slew;
	}
	
	public void setMotor(String motor)
	{
		associatedMotor = motor;
	}
	
	public String getSlew1()
	{
		return associatedSlew1;
	}
	
	public String getSlew2()
	{
		return associatedSlew2;
	}
	
	public String getMotor()
	{
		return associatedMotor;
	}
	
	/**
	 * Gets the JDOM XML Element that represents this Unit.
	 * @return	The JDOM XML Element that describes this Unit
	 */
	public Element getXML()
	{
		Element unitElement = super.getXML();
		
		unitElement.setAttribute(ASSOCIATED_SLEW_1_ATTR, associatedSlew1);
		unitElement.setAttribute(ASSOCIATED_SLEW_2_ATTR, associatedSlew2);
		unitElement.setAttribute(ASSOCIATED_MOTOR_ATTR,  associatedMotor);
		
		return unitElement;
	}
}
