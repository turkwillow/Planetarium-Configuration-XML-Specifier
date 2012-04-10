package org.carnegiesciencecenter.buhl;

import org.jdom.Element;

public class UnitProjector extends Unit
{
	private double azimuth, elevation, rotation, width, height;
	// Used to refer to XML elements:
	public static final String AZIMUTH 		= "azimuth";
	public static final String ELEVATION	= "elevation";
	public static final String ROTATION 	= "rotation";
	public static final String WIDTH 		= "width";
	public static final String HEIGHT 		= "height";
	
	/**
	 * Constructor taking the name and type of projector as Strings
	 * and the rest of the values as doubles
	 * @param Name		The name of the projector
	 * @param Azimuth	The azimuth of the projector (degrees)
	 * @param Elevation	The Elevation of the projector (degrees)
	 * @param Rotation	The Rotation of the projector (degrees)
	 * @param Width		The Width of the projector (degrees)
	 * @param Height	The Height of the projector (degrees)
	 * @param projType	The type of the projector (e.g, "Slide Projector")
	 */
	public UnitProjector(String Name, double Azimuth, double Elevation, double Rotation, double Width, double Height, String projType)
	{
		super(Name, projType);
		azimuth = Azimuth;
		elevation = Elevation;
		rotation = Rotation;
		width = Width;
		height = Height;
	}
	
	/**
	 * Constructor taking the name and type of projector as Strings
	 * and the rest of the values as Strings as well
	 * @param Name		The name of the projector
	 * @param Azimuth	The azimuth of the projector (degrees)
	 * @param Elevation	The Elevation of the projector (degrees)
	 * @param Rotation	The Rotation of the projector (degrees)
	 * @param Width		The Width of the projector (degrees)
	 * @param Height	The Height of the projector (degrees)
	 * @param projType	The type of the projector (e.g, "Slide Projector")
	 */
	public UnitProjector(String Name, String Azimuth, String Elevation, String Rotation, String Width, String Height, String projType)
	{
		super(Name, projType);
		azimuth 	= Utility.maxToZeroOrOriginal(Utility.stringToDouble(Azimuth));
		elevation 	= Utility.maxToZeroOrOriginal(Utility.stringToDouble(Elevation));
		rotation 	= Utility.maxToZeroOrOriginal(Utility.stringToDouble(Rotation));
		width 		= Utility.maxToZeroOrOriginal(Utility.stringToDouble(Width));
		height 		= Utility.maxToZeroOrOriginal(Utility.stringToDouble(Height));
	}
	
	public double getAzimuth()
	{
		return azimuth;
	}
	
	public double getElevation()
	{
		return elevation;
	}
	
	public double getRotation()
	{
		return rotation;
	}
	
	public double getWidth()
	{
		return width;
	}
	
	public double getHeight()
	{
		return height;
	}
	
	public void setAzimuth(double Azimuth)
	{
		azimuth = Azimuth;
	}
	
	public void setElevation(double Elevation)
	{
		elevation = Elevation;
	}
	
	public void setRotation(double Rotation)
	{
		rotation = Rotation;
	}
	
	public void setWidth(double Width)
	{
		width = Width;
	}
	
	public void setHeight(double Height)
	{
		height = Height;
	}
	
	/**
	 * Gets the JDOM XML Element that represents this Unit.
	 * @return	The JDOM XML Element that describes this Unit
	 */
	public Element getXML()
	{
		Element unitElement = super.getXML();
		
		unitElement.setAttribute(AZIMUTH, String.valueOf(azimuth));
		unitElement.setAttribute(ELEVATION, String.valueOf(elevation));
		unitElement.setAttribute(ROTATION, String.valueOf(rotation));
		unitElement.setAttribute(WIDTH, String.valueOf(width));
		unitElement.setAttribute(HEIGHT, String.valueOf(height));
		
		return unitElement;
	}
}
