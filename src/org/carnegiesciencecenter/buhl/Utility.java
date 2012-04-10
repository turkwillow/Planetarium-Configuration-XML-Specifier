package org.carnegiesciencecenter.buhl;

public class Utility 
{
	/**
	 * Converts a String to a double
	 * @param sNum  The String containing the double
	 * @return	The double if possible, or Double.MAX_VALUE otherwise
	 */
	public static double stringToDouble(String sNum)
	{
		sNum = sNum.trim();
		if (sNum.length() == 0)
			return Double.MAX_VALUE;

		try
		{
			double num = Double.parseDouble(sNum);
			return num;
		}
		catch (NumberFormatException e)
		{
			return Double.MAX_VALUE;
		}
	}
	
	/**
	 * If the given number equals Double.MAX_VALUE, returns zero.
	 * Otherwise, the original number is returned.
	 * (Purpose of this function is to turn an unusable error value
	 *  into something practical, rather than just showing the user
	 *  something nasty)
	 * @param num	The number to check
	 * @return	The original number given if it does not equal Double.MAX_VALUE,
	 * 			0.0 otherwise
	 */
	public static double maxToZeroOrOriginal(double num)
	{
		if (num == Double.MAX_VALUE)
			return 0.0;
		else
			return num;
	}
}
