package com.CMPUT301W14T13.gpscommentlogger.view;

import java.util.ArrayList;

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
import android.view.View;

import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;




/**
 * This Activiy provides the user with a map using OpenStreetMaps for android.
 * 
 * It is used by CreateSubmissionActivity to edit the user's location, which is 
 * done by tapping on the map where you want to set the location, once tapped
 * a marker is placed as a visual aid indicating the current point the user has selected
 * for his comment/Topic.
 * 
 * Once the user has selected a point for his comment they push the SubmitLocation buton
 * which returns the current points latitude and longitude to CreateSubmissionActivity
 * 
 * 
 * It is used by TopicViewActivity do display all of the replies to the current topic by location markers
 * In topic view the used clicks the "Map Thread" button and then MapViewActivity takes the replies of the
 * current topic and displays their locations.
 * 
 * @author navjeetdhaliwal
 *
 */

public class MapViewActivity extends Activity {

	private MapController mapController;
	private MapView mapView;
	private GeoPoint returnPoint;
	private int canSetMarker;
	private CommentLogger cl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		Intent intent = getIntent();
		double lat = intent.getDoubleExtra("lat", 53.5333);
		double lon = intent.getDoubleExtra("lon",-113.5000);
		canSetMarker = intent.getIntExtra("canSetMarker", 0);

		
		
		// Here is the initialization if user is editing location
		if(canSetMarker == 1){
			setContentView(R.layout.map_edit_location_view);
			returnPoint = new GeoPoint(lat, lon);
			mapView = (MapView) findViewById(R.id.mapEditView);
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
				public boolean singleTapUpHelper(IGeoPoint tapLocation) {
					// once the user taps, we remove the old marker and place a new one
					mapView.getOverlays().remove(mIndex);
					mIndex = setMarker((GeoPoint) tapLocation);
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


		}else {
			// here we set up to display the locations of all replies to the current topic
			setContentView(R.layout.map_thread_view);
			cl = CommentLogger.getInstance();
			//Topic topic = cl.getCurrentTopic();
			returnPoint  = new GeoPoint(lat,lon);
			mapView = (MapView) findViewById(R.id.mapThreadView);
			mapView.setTileSource(TileSourceFactory.MAPNIK);
			mapView.setBuiltInZoomControls(true);
			mapView.setMultiTouchControls(true);
			mapView.setClickable(false);
			mapController = (MapController) mapView.getController();
			mapController.setZoom(5);
			mapController.setCenter(returnPoint);
			ArrayList<Viewable> commentList = cl.getCommentList();
			final int markerIndex = setMarker(returnPoint);
			
			for(int i=0;i<commentList.size();i++){
				Viewable comment = commentList.get(i);
				setMarkerWithInfo(comment);
				
			}
			mapView.invalidate();
			MapEventsReceiver receiver = new MapEventsReceiver() {
				int mIndex = markerIndex;
				@Override
				public boolean singleTapUpHelper(IGeoPoint tapLocation) {
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




	}
	

	/**
	 * This method creates and sets a marker onto the screen at the given location
	 * CreateSubmissionActivity needs the returnPoint for editing location, because we need only one 
	 * marker on screen at a time so we use the index to delete the previous marker
	 * 
	 * 
	 * @param point, a latitude and longitude pair.
	 * @return markers Index is used when CreateSubmissionActivity calls MapView Activity and is used
	 * to remove this current marker if a new one is created.
	 */
	public  int setMarker(GeoPoint point){
		Marker marker = new Marker(mapView);
		marker.setPosition(point);
		marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
		mapView.getOverlays().add(marker);
		mapView.invalidate();
		returnPoint = point;
		return  mapView.getOverlays().indexOf(marker);
	}
	/**
	 * This Method is sets a marker, representing a Topic/Comment, at a location 
	 * and adds additional information, such as user name and text
	 * @param comment, a topic/comment 
	 */
	public void setMarkerWithInfo(Viewable comment){
		GeoPoint point = new GeoPoint(comment.getGPSLocation().getLatitude(), comment.getGPSLocation().getLongitude());
		Marker marker = new Marker(mapView);
		marker.setPosition(point);
		marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
		marker.setTitle(comment.getUsername());
		mapView.getOverlays().add(marker);
		
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
	
	/**
	 * Finishes the mapViewActivity without returning anything
	 * It is used by the "done" button in Map_thread_view which is used when MapViewActivity is 
	 * called by Topic View for mapping all the replies to a topic
	 * 
	 */
	public void doneMapThread(){	
		finish();
	}


	/**
	 * This method returns the point that was selected by the user when they chose to 
	 * edit their comment or topic location using the interactive map provided.
	 * 
	 * This is currently being used as a way to make an integration test to debug this 
	 * MapViewActivity.
	 * 
	 * @return the geopoint that was selected by the current marker on the screen.
	 */
	public GeoPoint getReturnPoint(){
		return returnPoint;
	}
	
	
}
