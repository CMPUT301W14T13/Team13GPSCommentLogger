package usecasegroup2.unit;

import android.annotation.SuppressLint;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.services.LocationSelection;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class EditLocationUnitTest extends
ActivityInstrumentationTestCase2<HomeView> {

	public EditLocationUnitTest() {
		super(HomeView.class);
	}
	
	private static final String PROVIDER = "gps";
    private static final double LAT = 37.377166;
    private static final double LNG = -122.086966;
    private static final float ACCURACY = 3.0f;

    private Location createLocation(double lat, double lng, float accuracy) {
        // Create a new Location
        Location newLocation = new Location(PROVIDER);
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setAccuracy(accuracy);
        return newLocation;
    }
    
	public void testEditCommentLocation(){
		
		Comment comment = new Comment();
		assertEquals("Original GPS location should be null", comment.getGPSLocation(), null);
		/* set the new location */
		Location location = createLocation(LAT, LNG, ACCURACY);
		comment.setGPSLocation(location);
		assertEquals("New GPS location should match Edmonton", comment.getGPSLocation(), location);
		
	}
	
	public void testEditTopicLocation(){
		
		Topic topic = new Topic();
		assertEquals("Original GPS location should be null", topic.getGPSLocation(), null);
		Location location = createLocation(LAT, LNG, ACCURACY);
		topic.setGPSLocation(location);
		assertEquals("New GPS location should match Edmonton", topic.getGPSLocation(), location);
	}
	
	/* this test fails, ask Navjeet for help.*/
    public void testLocationSelection () throws InterruptedException{
    	LocationSelection locationGetter = LocationSelection.getInstance();
    	
    	locationGetter.startLocationSelection();
    	locationGetter.setProvider(PROVIDER);
    	
    	Location mockLocation = createLocation(LAT, LNG, ACCURACY);
    	
    	locationGetter.setProviderLocation(PROVIDER, mockLocation);
    	
    	Thread.sleep(100); // wait for phone to fetch location    	
    	
    	Location location = locationGetter.getLocation();
    	
    	assertNotNull("Didn't get a location", location);
    	assertEquals("Mock location latitudes not equal to provider location", location.getLatitude(), mockLocation.getLatitude());
    	assertEquals("Mock location longitudes not equal to provider location", location.getLongitude(), mockLocation.getLongitude());

    }
}
