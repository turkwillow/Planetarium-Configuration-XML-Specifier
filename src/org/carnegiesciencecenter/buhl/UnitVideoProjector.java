package org.carnegiesciencecenter.buhl;

public class UnitVideoProjector extends UnitSlideProjector
{
	public static final String VIDEO_PROJ = "Video Projector";
	
	public UnitVideoProjector(String Name, double Azimuth, double Elevation, double Rotation, double Width, double Height)
	{
		super(Name, Azimuth, Elevation, Rotation, Width, Height);
		this.deviceType = VIDEO_PROJ;
	}
	
	public UnitVideoProjector(String Name, String Azimuth, String Elevation, String Rotation, String Width, String Height)
	{
		super(Name, Azimuth, Elevation, Rotation, Width, Height);
		this.deviceType = VIDEO_PROJ;
	}
}
