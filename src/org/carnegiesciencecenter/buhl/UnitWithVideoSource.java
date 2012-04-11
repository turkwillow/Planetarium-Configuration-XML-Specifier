package org.carnegiesciencecenter.buhl;

import org.jdom.Element;

/**
 * A class for those devices that are associated with a number 
 * in SelectSource calls (e.g., the 7 in "SelectSource 7 VPRJ A"
 */
public class UnitWithVideoSource extends Unit
{
	private int videoSourceNumber;	// The number used as the first parameter in SelectSource calls (should be 0-8)
	public static final int MIN_SRC_NUM = 0;
	public static final int MAX_SRC_NUM = 8;
	public static final String VIDEO_SRC_ATTR = "videosource";
	
	public UnitWithVideoSource(String Name, int videoSrc, String devType)
	{
		super(Name, devType);
		videoSourceNumber = videoSrc;
	}
	
	public int getVideoSrcNum()
	{
		return videoSourceNumber;
	}
	
	/**
	 * Sets the video source number for the unit.
	 * @param videoSrc
	 * @return
	 */
	public Boolean setVideoSrcNum(int videoSrc)
	{
		// Check bounds, returning false if out of bounds and correction was made
		if (videoSrc < MIN_SRC_NUM)
		{
			videoSourceNumber = MIN_SRC_NUM;
			return false;
		}
		else if (videoSrc > MAX_SRC_NUM)
		{
			videoSourceNumber = MAX_SRC_NUM;
			return false;
		}
		
		// Within bounds - return true
		videoSourceNumber = videoSrc;
		return true;
	}
	
	/**
	 * Gets the JDOM XML Element that represents this Unit.
	 * @return	The JDOM XML Element that describes this Unit
	 */
	public Element getXML()
	{
		Element unitElement = super.getXML();
		
		unitElement.setAttribute(VIDEO_SRC_ATTR, String.valueOf(videoSourceNumber));
		
		return unitElement;
	}
}
