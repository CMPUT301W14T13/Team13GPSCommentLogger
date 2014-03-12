package com.CMPUT301W14T13.gpscommentlogger.view;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import com.CMPUT301W14T13.gpscommentlogger.R;




public class MapViewActivity extends Activity {

	private MapController mapController;
    private MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapController = (MapController) mapView.getController();
        mapController.setZoom(15);
        GeoPoint point2 = new GeoPoint(51496994, -134733);
        mapController.setCenter(point2);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	protected boolean isRouteDisplayed() {
	    return false;
	}
	
	
}
