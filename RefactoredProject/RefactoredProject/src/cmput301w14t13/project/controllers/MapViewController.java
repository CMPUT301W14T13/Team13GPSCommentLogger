package cmput301w14t13.project.controllers;

import java.util.ArrayList;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.auxilliary.interfaces.UpdateRank;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.views.TopicView;


/**
 * This Activity provides the user with a map using OpenStreetMaps for android.
 * 
 * It is used to Edit a Users Location when connection is available, the user simply
 * places a marker where they wish to set a location and clicks submit location to set
 * the Topic or Comments location. This is done in CreateSubmissionView by clicking the 
 * edit location imageButton.
 * 
 * It is also used to Map the locations of a Topics Thread(its replies) as well as the 
 * topics location itself. This is Done in TopicView by clicking the "Map Thread" button
 * Once the user has selected a point for his comment they push the SubmitLocation button
 * which returns the current points latitude and longitude to CreateSubmissionActivity
 * 
 * 
 * @author navjeetdhaliwal
 *
 */

public class MapViewController extends RankedHierarchicalActivity implements UpdateInterface{

	private MapController mapController;
	private MapView mapView;

	private GeoPoint returnPoint;
	private int canSetMarker;
	
	/**
	 * using the CanSetMarker indication the map is set up to either facilitate editing of a users location
	 * or viewing of locations of a Topic and all of its replies
	 * 
	 * If canSetMarker is set, a marker is placed at the current location passed to this activity
	 * and a MapEventsReceiver is set to change the location of the marker to were ever the user
	 * taps
	 * 
	 * If canSetMarker is set to 0, then the map is populated with the location of the current Topic and all of its replies
	 * 
	 */

	private static TopicView topicView;

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


		} 
		else {
			
			// here we implement new requirement of displaying Topic Thread Location
			setContentView(R.layout.map_thread_view);
			CommentTree commentTree = CommentTree.getInstance();
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
			
			//set topic marker with special icon
			
			Marker topicMarker = new Marker(mapView);
			topicMarker.setPosition(returnPoint);
			topicMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
			topicMarker.setIcon(getResources().getDrawable(R.drawable.marker_via));
			mapView.getOverlays().add(topicMarker);
			mapView.invalidate();
			
			ArrayList<CommentTreeElement> commentList = commentTree.getCommentList(topicView);
			for(int i=0;i<commentList.size();i++){
				CommentTreeElement comment = commentList.get(i);			
				GeoPoint point = new GeoPoint(comment.getGPSLocation().getLatitude(), comment.getGPSLocation().getLongitude());
				setMarker(point);
			}
			
			MapEventsReceiver receiver = new MapEventsReceiver() {

				@Override
				public boolean singleTapUpHelper(IGeoPoint tapLocation) {
					// once the user taps, we remove the old marker and place a new one
					
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
	 * Sets a green marker at the GeoPoint, point, then refreshes
	 * the map to show the newly placed marker, returnPoint is used
	 * when the user is editing location to return the current markers
	 * location
	 * When editing location only one marker is on the screen at a time
	 * for mapping a thread multiple markers are on the screen and returnPoint
	 * is not needed
	 * 
	 * @param point The Geopoint of the marker,
	 * @return markerIndex Index is used when CreateSubmissionActivity calls MapView Activity and is used
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

	protected boolean isRouteDisplayed() {
		return false;
	}
	/**
	 * Sets an intent and adds returnPoints latitude
	 * and longitude, then finishes the activity
	 * used whenever the user is setting a location
	 * to send a location back to the activity calling 
	 * this activity.
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
	 * Ends the activity without sending a location
	 * back, used whenever the user is mapping a Topic
	 * thread, we dont need to send any information back
	 * on the activities finish
	 * @param v
	 */
	public void doneMapThread(View v){
		finish();
	}
	@Override
    public void onDestroy() {
        super.onDestroy();
       // CommentLogger cl = CommentLogger.getInstance();
	}
        

	/**
	 * This method returns the point that was selected by the user when they chose to 
	 * edit their comment or topic location using the interactive map provided. 
	 * This is currently being used as a way to make an integration test to debug this MapViewActivity.
	 * 
	 * @return  the geopoint that was selected by the current marker on the screen.
	 *  
	 */
	public GeoPoint getReturnPoint(){
		return returnPoint;
	}

	@Override
	public void update()
	{

	}

	@Override
	public UpdateRank getRank()
	{
		return rank;
	}
	
	public static void setTopicView(TopicView view){
		topicView = view;
	}
	
}
