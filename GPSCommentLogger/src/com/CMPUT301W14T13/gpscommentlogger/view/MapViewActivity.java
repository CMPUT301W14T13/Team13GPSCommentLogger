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

 


public class MapViewActivity extends Activity {

	private MapController mapController;
    private MapView mapView;
    private GeoPoint returnPoint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        // problem with this action bar is Mapview has multiple parents
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("lat", 53.5333);
        double lon = intent.getDoubleExtra("lon",113.5000);
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
	
	public void submitLocation(View v){
		Intent result = new Intent();
		result.putExtra("lat", returnPoint.getLatitude());
		result.putExtra("lon", returnPoint.getLongitude());
		setResult(RESULT_OK, result);	
		finish();
	}
	
}
