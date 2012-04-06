package org.carnegiesciencecenter.buhl;

import org.jdom.Element;

public class UnitMotor extends Unit
{
	private double minWidth, maxWidth;
	private double minHeight, maxHeight;
	private double minPosition, maxPosition;
	public static final String MOTOR = "Motor";
	public static final String MIN_WIDTH	= "minwidth";
	public static final String MAX_WIDTH	= "maxwidth";
	public static final String MIN_HEIGHT	= "minheight";
	public static final String MAX_HEIGHT	= "maxheight";
	public static final String MIN_POS		= "minpos";
	public static final String MAX_POS		= "maxpos";
	
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
	
	public UnitMotor(String Name, 
			String MinWidth, String MaxWidth, 
			String MinHeight, String MaxHeight, 
			String MinPos, String MaxPos)
	{
		super(Name, MOTOR);
		
		setMinWidth(maxToZeroOrOriginal(EditUnitSlideProjGUI.StringToDouble(MinWidth)));
		setMaxWidth(maxToZeroOrOriginal(EditUnitSlideProjGUI.StringToDouble(MaxWidth)));
		setMinHeight(maxToZeroOrOriginal(EditUnitSlideProjGUI.StringToDouble(MinHeight)));
		setMaxHeight(maxToZeroOrOriginal(EditUnitSlideProjGUI.StringToDouble(MaxHeight)));
		setMinPos(maxToZeroOrOriginal(EditUnitSlideProjGUI.StringToDouble(MinPos)));
		setMaxPos(maxToZeroOrOriginal(EditUnitSlideProjGUI.StringToDouble(MaxPos)));
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
		
		unitElement.setAttribute(MIN_WIDTH, String.valueOf(minWidth));
		unitElement.setAttribute(MAX_WIDTH, String.valueOf(maxWidth));
		unitElement.setAttribute(MIN_HEIGHT, String.valueOf(minHeight));
		unitElement.setAttribute(MAX_HEIGHT, String.valueOf(maxHeight));
		unitElement.setAttribute(MIN_POS, String.valueOf(minPosition));
		unitElement.setAttribute(MAX_POS, String.valueOf(maxPosition));
		
		return unitElement;
	}
}
