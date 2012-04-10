package org.carnegiesciencecenter.buhl;

import java.util.Arrays;
import org.jdom.Element;

public class UnitSlideProjector extends UnitProjector
{
	public static final String SLIDE_PROJ	= "Slide Projector";
	
	
	public UnitSlideProjector(String Name, double Azimuth, double Elevation, double Rotation, double Width, double Height)
	{
		super(Name, Azimuth, Elevation, Rotation, Width, Height, SLIDE_PROJ);
	}
	
	public UnitSlideProjector(String Name, String Azimuth, String Elevation, String Rotation, String Width, String Height)
	{
		super(Name, Azimuth, Elevation, Rotation, Width, Height, SLIDE_PROJ);
	}
}
