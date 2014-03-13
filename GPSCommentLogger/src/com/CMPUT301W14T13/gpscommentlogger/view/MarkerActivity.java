package com.CMPUT301W14T13.gpscommentlogger.view;

import java.util.ArrayList;

import org.osmdroid.views.overlay.ItemizedOverlay;

import com.google.android.maps.OverlayItem;


public class MarkerActivity extends ItemizedOverlaOverlayItemprivate ArrayList<OverlayItem> mOverlays = new ArrayList<OverleyItem>();
	
	public MarkerActivity(Drawable defaultMarker){
		super(boundCenterBoom(defaultMarker));
	}
	public void addOverlay(OverlayItem overlay){
		mOverlays.add(overlay);
		populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);	
	}
	

}
