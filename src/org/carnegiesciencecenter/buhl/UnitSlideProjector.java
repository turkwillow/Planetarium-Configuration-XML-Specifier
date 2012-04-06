package org.carnegiesciencecenter.buhl;

import java.util.Arrays;
import org.jdom.Element;

public class UnitSlideProjector extends Unit
{
	private double azimuth, elevation, rotation, width, height;
	public static final String SLIDE_PROJ	= "Slide Projector";
	public static final String AZIMUTH 		= "azimuth";
	public static final String ELEVATION	= "elevation";
	public static final String ROTATION 	= "rotation";
	public static final String WIDTH 		= "width";
	public static final String HEIGHT 		= "height";
	
	public UnitSlideProjector(String Name, double Azimuth, double Elevation, double Rotation, double Width, double Height)
	{
		super(Name, SLIDE_PROJ);
		azimuth = Azimuth;
		elevation = Elevation;
		rotation = Rotation;
		width = Width;
		height = Height;
	}
	
	public UnitSlideProjector(String Name, String Azimuth, String Elevation, String Rotation, String Width, String Height)
	{
		super(Name, SLIDE_PROJ);
		azimuth 	= maxToZeroOrOriginal(EditUnitSlideProjGUI.StringToDouble(Azimuth));
		elevation 	= maxToZeroOrOriginal(EditUnitSlideProjGUI.StringToDouble(Elevation));
		rotation 	= maxToZeroOrOriginal(EditUnitSlideProjGUI.StringToDouble(Rotation));
		width 		= maxToZeroOrOriginal(EditUnitSlideProjGUI.StringToDouble(Width));
		height 		= maxToZeroOrOriginal(EditUnitSlideProjGUI.StringToDouble(Height));
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
