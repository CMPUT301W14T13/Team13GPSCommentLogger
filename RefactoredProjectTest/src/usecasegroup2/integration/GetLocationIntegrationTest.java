package usecasegroup2.integration;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class GetLocationIntegrationTest extends
ActivityInstrumentationTestCase2<HomeView> {

	public GetLocationIntegrationTest() {
		super(HomeView.class);
	}

}
