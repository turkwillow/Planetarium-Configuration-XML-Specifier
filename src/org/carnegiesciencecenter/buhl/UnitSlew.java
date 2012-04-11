package org.carnegiesciencecenter.buhl;

import org.jdom.Element;

public class UnitSlew extends Unit
{
	private double 	minPosition, maxPosition, 
					minValue, maxValue, 
					tripTime;
	// Used to refer to XML elements:
	public static final String SLEW = "Slew";
	public static final String MIN_POS_ATTR = "minpos";
	public static final String MAX_POS_ATTR = "maxpos";
	public static final String MIN_VAL_ATTR = "minval";
	public static final String MAX_VAL_ATTR = "maxval";
	public static final String TRIP_TIME_ATTR = "triptime";
	
	/**
	 * Public constructor taking the name of the slew (e.g., "SLEW") as a String
	 * and other values as doubles.
	 * @param name		The name of the slew (e.g., "SLEW")
	 * @param MinPos	The minimum position of the slew (degrees)
	 * @param MaxPos	The maximum position of the slew (degrees)
	 * @param MinValue	The minimum value of the slew (no units)
	 * @param MaxValue	The maximum value of the slew (no units)
	 * @param TripTime	The trip time of the slew (hudredths of a second)
	 */
	public UnitSlew(String name, double MinPos, double MaxPos, double MinValue, double MaxValue, double TripTime)
	{
		super(name, SLEW);
		
		setMinPos(MinPos);
		setMaxPos(MaxPos);
		setMinValue(MinValue);
		setMaxValue(MaxValue);
		setTripTime(TripTime);
	}
	
	/**
	 * Public constructor taking the name of the slew (e.g., "SLEW") as a String
	 * and other values as Strings as well.
	 * @param name		The name of the slew (e.g., "SLEW")
	 * @param MinPos	The minimum position of the slew (degrees)
	 * @param MaxPos	The maximum position of the slew (degrees)
	 * @param MinValue	The minimum value of the slew (no units)
	 * @param MaxValue	The maximum value of the slew (no units)
	 * @param TripTime	The trip time of the slew (hudredths of a second)
	 */
	public UnitSlew(String name, String MinPos, String MaxPos, String MinValue, String MaxValue, String TripTime)
	{
		super(name, SLEW);
		
		setMinPos(Utility.maxToZeroOrOriginal(Utility.stringToDouble(MinPos)));
		setMaxPos(Utility.maxToZeroOrOriginal(Utility.stringToDouble(MaxPos)));
		setMinValue(Utility.maxToZeroOrOriginal(Utility.stringToDouble(MinValue)));
		setMaxValue(Utility.maxToZeroOrOriginal(Utility.stringToDouble(MaxValue)));
		setTripTime(Utility.maxToZeroOrOriginal(Utility.stringToDouble(TripTime)));
	}
	
	public void setMinPos(double minPos)
	{
		minPosition = minPos;
	}
	
	public void setMaxPos(double maxPos)
	{
		maxPosition = maxPos;
	}
	
	public void setMinValue(double MinValue)
	{
		minValue = MinValue;
	}
	
	public void setMaxValue(double MaxValue)
	{
		maxValue = MaxValue;
	}

	public double getMinPos()
	{
		return minPosition;
	}
	
	public double getMaxPos()
	{
		return maxPosition;
	}

	public double getMinValue()
	{
		return minValue;
	}
	
	public double getMaxValue()
	{
		return maxValue;
	}
	
	public double getTripTime()
	{
		return tripTime;
	}
	
	public void setTripTime(double TripTime)
	{
		tripTime = TripTime;
	}
	
	/**
	 * Gets the JDOM XML Element that represents this Unit.
	 * @return	The JDOM XML Element that describes this Unit
	 */
	public Element getXML()
	{
		Element unitElement = super.getXML();
		
		unitElement.setAttribute(MIN_POS_ATTR, String.valueOf(minPosition));
		unitElement.setAttribute(MAX_POS_ATTR, String.valueOf(maxPosition));
		unitElement.setAttribute(MIN_VAL_ATTR, String.valueOf(minValue));
		unitElement.setAttribute(MAX_VAL_ATTR, String.valueOf(maxValue));
		unitElement.setAttribute(TRIP_TIME_ATTR, String.valueOf(tripTime));
		
		return unitElement;
	}
}
