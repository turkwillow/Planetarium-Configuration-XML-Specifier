package org.carnegiesciencecenter.buhl;

import org.jdom.Element;

public class UnitVideoSource extends UnitWithVideoSource
{
	public static final String VIDEO_SOURCE = "Video Source";
	
	public UnitVideoSource(String Name, int videoSrcNum)
	{
		super(Name, videoSrcNum, VIDEO_SOURCE);
	}
}
