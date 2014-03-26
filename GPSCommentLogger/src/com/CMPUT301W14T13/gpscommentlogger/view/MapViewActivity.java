package com.CMPUT301W14T13.gpscommentlogger.view;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

import com.CMPUT301W14T13.gpscommentlogger.R;

 


/**
 * This method displays a map to let the user select the location 
 * they wish to attach to their comment or topic. it takes a current gps
 * location from the activity that calls it and sets the map with that
 * point in the center. Then the user can use normal google maps controls
 * such as pinch zoom and dragging to find the location they wish to set.
 * To indicate the location they wish to set they tap the screen and a marker 
 * appears where they tap, then they click submit location to close this activity
 * and pass the location back to the activity that called this one.
 * 
 * @author navjeetdhaliwal
 *
 */

public class MapViewActivity extends Activity {

	private MapController mapController;
    private MapView mapView;
    private GeoPoint returnPoint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        // problem with this action bar is Mapview has multiple parents
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        
        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("lat", 53.5333);
        double lon = intent.getDoubleExtra("lon",-113.5000);
        returnPoint = new GeoPoint(lat, lon);
       
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapView.setClickable(false);
        mapController = (MapController) mapView.getController();
        mapController.setZoom(10);
        mapController.setCenter(returnPoint);
        
        final int markerIndex = setMarker(returnPoint);
        
        MapEventsReceiver receiver = new MapEventsReceiver() {
			int mIndex = markerIndex;
			@Override
			public boolean singleTapUpHelper(IGeoPoint arg0) {
				returnPoint = (GeoPoint) arg0;
			
				mapView.getOverlays().remove(mIndex);
				mIndex = setMarker(returnPoint);
				return true;
			}
			
			@Override
			public boolean longPressHelper(IGeoPoint arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		
        MapEventsOverlay mEvent = new MapEventsOverlay(this, receiver);
        mapView.getOverlays().add(mEvent);
        
        
	}
	
/**
 * This activity creates a marker overlay from a given latitude and longitude
 * and places it on the screen and refreshes the map. It returns an index
 * that is used to delete it when another marker is placed so that only 1 marker
 * is on the screen at a time, since the user can only submit one location
 * per comment or topic.
 * @param point
 * @return markers Index
 */
	protected  int setMarker(GeoPoint point){
		Marker marker = new Marker(mapView);
        marker.setPosition(point);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(marker);
        mapView.invalidate();
        
        return  mapView.getOverlays().indexOf(marker);
	}
	
	protected boolean isRouteDisplayed() {
	    return false;
	}
	/**
	 * Submits the location of the current marker on the screen 
	 * back to the activity that called this activity.
	 * @param v 
	 */
	public void submitLocation(View v){
		Intent result = new Intent();
		result.putExtra("lat", returnPoint.getLatitude());
		result.putExtra("lon", returnPoint.getLongitude());
		setResult(RESULT_OK, result);	
		finish();
	}
	
}
