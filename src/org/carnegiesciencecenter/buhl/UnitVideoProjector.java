package org.carnegiesciencecenter.buhl;

public class UnitVideoProjector extends UnitProjector
{
	public static final String VIDEO_PROJ = "Video Projector";
	
	/**
	 * Constructor taking the name and type of projector as Strings
	 * and the rest of the values as doubles
	 * @param Name		The name of the projector
	 * @param Azimuth	The azimuth of the projector (degrees)
	 * @param Elevation	The Elevation of the projector (degrees)
	 * @param Rotation	The Rotation of the projector (degrees)
	 * @param Width		The Width of the projector (degrees)
	 * @param Height	The Height of the projector (degrees)
	 */
	public UnitVideoProjector(String Name, double Azimuth, double Elevation, double Rotation, double Width, double Height)
	{
		super(Name, Azimuth, Elevation, Rotation, Width, Height, VIDEO_PROJ);
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
	 */
	public UnitVideoProjector(String Name, String Azimuth, String Elevation, String Rotation, String Width, String Height)
	{
		super(Name, Azimuth, Elevation, Rotation, Width, Height, VIDEO_PROJ);
	}
}
