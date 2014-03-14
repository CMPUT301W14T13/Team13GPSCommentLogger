package com.CMPUT301W14T13.gpscommentlogger.view;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

import com.CMPUT301W14T13.gpscommentlogger.R;

 


public class MapViewActivity extends Activity {

	private MapController mapController;
    private MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapView.setClickable(true);
        mapController = (MapController) mapView.getController();
        mapController.setZoom(15);
        
        //this point in future will be taken from previous activity
        GeoPoint center = new GeoPoint(48.13, -1.63);
        mapController.setCenter(center);

      
        mapView.setOnTouchListener( new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				GeoPoint point = (GeoPoint) mapView.getProjection().fromPixels(event.getX(), event.getY());
		        mapView.getOverlays().clear();
				Marker marker = new Marker(mapView);
		        marker.setPosition(point);
		        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
		        mapView.getOverlays().add(marker);
		        mapView.invalidate();
				return true;
			}
		});
        
        
	}
	

	
	protected boolean isRouteDisplayed() {
	    return false;
	}
	
	
}
