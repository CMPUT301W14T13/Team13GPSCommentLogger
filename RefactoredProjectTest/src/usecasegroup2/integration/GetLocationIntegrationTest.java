package usecasegroup2.integration;

import android.annotation.SuppressLint;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.services.LocationSelection;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class GetLocationIntegrationTest extends
ActivityInstrumentationTestCase2<HomeView> {

	public GetLocationIntegrationTest() {
		super(HomeView.class);
	}
	
	
	
	public void testGetLocation() throws InterruptedException{
		
		Location location;
		
		getActivity();
		Thread.sleep(5000);
		location = LocationSelection.getInstance().getLocation();
		
		assertNotNull("Location shouldn't be null", location);
	}

}
