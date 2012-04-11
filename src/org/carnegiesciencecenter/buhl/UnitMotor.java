package org.carnegiesciencecenter.buhl;

import org.jdom.Element;

public class UnitMotor extends Unit
{
	private double minWidth, maxWidth;
	private double minHeight, maxHeight;
	private double minPosition, maxPosition;
	// For referring to things in XML:
	public static final String MOTOR = "Motor";
	public static final String MIN_WIDTH_ATTR	= "minwidth";
	public static final String MAX_WIDTH_ATTR	= "maxwidth";
	public static final String MIN_HEIGHT_ATTR	= "minheight";
	public static final String MAX_HEIGHT_ATTR	= "maxheight";
	public static final String MIN_POS_ATTR		= "minpos";
	public static final String MAX_POS_ATTR		= "maxpos";
	
	/**
	 * Public constructor taking the motor's name as a String and 
	 * other values as doubles.
	 * @param Name		The name of the motor
	 * @param MinWidth	The minimum width of the motor, in degrees
	 * @param MaxWidth	The maximum width of the motor, in degrees
	 * @param MinHeight	The minimum height of the motor, in degrees
	 * @param MaxHeight	The maximum height of the motor, in degrees
	 * @param MinPos	The minimum position of the motor
	 * @param MaxPos	The maximum position of the motor
	 */
	public UnitMotor(String Name, 
					double MinWidth, double MaxWidth, 
					double MinHeight, double MaxHeight, 
					double MinPos, double MaxPos)
	{
		super(Name, MOTOR);
		
		setMinWidth(MinWidth);
		setMaxWidth(MaxWidth);
		setMinHeight(MinHeight);
		setMaxHeight(MaxHeight);
		setMinPos(MinPos);
		setMaxPos(MaxPos);
	}
	
	/**
	 * Public constructor taking the motor's name as a String and 
	 * other values as Strings as well.
	 * @param Name		The name of the motor
	 * @param MinWidth	The minimum width of the motor, in degrees
	 * @param MaxWidth	The maximum width of the motor, in degrees
	 * @param MinHeight	The minimum height of the motor, in degrees
	 * @param MaxHeight	The maximum height of the motor, in degrees
	 * @param MinPos	The minimum position of the motor
	 * @param MaxPos	The maximum position of the motor
	 */
	public UnitMotor(String Name, 
			String MinWidth, String MaxWidth, 
			String MinHeight, String MaxHeight, 
			String MinPos, String MaxPos)
	{
		super(Name, MOTOR);
		
		setMinWidth(Utility.maxToZeroOrOriginal(Utility.stringToDouble(MinWidth)));
		setMaxWidth(Utility.maxToZeroOrOriginal(Utility.stringToDouble(MaxWidth)));
		setMinHeight(Utility.maxToZeroOrOriginal(Utility.stringToDouble(MinHeight)));
		setMaxHeight(Utility.maxToZeroOrOriginal(Utility.stringToDouble(MaxHeight)));
		setMinPos(Utility.maxToZeroOrOriginal(Utility.stringToDouble(MinPos)));
		setMaxPos(Utility.maxToZeroOrOriginal(Utility.stringToDouble(MaxPos)));
	}
	
	public void setMinWidth(double MinWidth)
	{
		minWidth = MinWidth;
	}
	
	public void setMaxWidth(double MaxWidth)
	{
		maxWidth = MaxWidth;
	}
	
	public void setMinHeight(double MinHeight)
	{
		minHeight = MinHeight;
	}
	
	public void setMaxHeight(double MaxHeight)
	{
		maxHeight = MaxHeight;
	}
	
	public void setMinPos(double MinPos)
	{
		minPosition = MinPos;
	}
	
	public void setMaxPos(double MaxPos)
	{
		maxPosition = MaxPos;
	}
	
	public double getMinWidth()
	{
		return minWidth;
	}
	
	public double getMaxWidth()
	{
		return maxWidth;
	}
	
	public double getMinHeight()
	{
		return minHeight;
	}
	
	public double getMaxHeight()
	{
		return maxHeight;
	}
	
	public double getMinPos()
	{
		return minPosition;
	}
	
	public double getMaxPos()
	{
		return maxPosition;
	}
	
	/**
	 * Gets the JDOM XML Element that represents this Unit.
	 * @return	The JDOM XML Element that describes this Unit
	 */
	public Element getXML()
	{
		Element unitElement = super.getXML();
		
		unitElement.setAttribute(MIN_WIDTH_ATTR, String.valueOf(minWidth));
		unitElement.setAttribute(MAX_WIDTH_ATTR, String.valueOf(maxWidth));
		unitElement.setAttribute(MIN_HEIGHT_ATTR, String.valueOf(minHeight));
		unitElement.setAttribute(MAX_HEIGHT_ATTR, String.valueOf(maxHeight));
		unitElement.setAttribute(MIN_POS_ATTR, String.valueOf(minPosition));
		unitElement.setAttribute(MAX_POS_ATTR, String.valueOf(maxPosition));
		
		return unitElement;
	}
}
