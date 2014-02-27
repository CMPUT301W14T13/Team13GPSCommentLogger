package com.CMPUT301W14T13.gpscommentlogger.view;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;



public class LocationSelectionView
{
	
	private static Location currentLocation;
	private static LocationListener locationListener;
	
	public static Location getLocation(){
		return currentLocation;
	}
	
	public static LocationListener getLocationListener(){
		return locationListener;
	}
	/** 
	 * @uml.property name="c"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="l:com.CMPUT301W14T13.gpscommentlogger.view.CommentMakingView"
	 */
	private CreateCommentActivity c = new com.CMPUT301W14T13.gpscommentlogger.view.CreateCommentActivity();

	/** 
	 * Getter of the property <tt>c</tt>
	 * @return  Returns the c.
	 * @uml.property  name="c"
	 */
	public CreateCommentActivity getC()
	
	{
		return c;
	}

	/** 
	 * Setter of the property <tt>c</tt>
	 * @param c  The c to set.
	 * @uml.property  name="c"
	 */
	public void setC(CreateCommentActivity c)
	
	{
		this.c = c;
	}

	public static LocationManager getSystemService(String locationService) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
