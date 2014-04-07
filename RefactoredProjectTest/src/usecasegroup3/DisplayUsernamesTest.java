package usecasegroup3;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class DisplayUsernamesTest extends ActivityInstrumentationTestCase2<HomeView> {

	public DisplayUsernamesTest() {
		super(HomeView.class);
	}

	
	
	public void testDisplayUsername(){
		
		CommentTreeElement topic = new Topic();
		topic.setUsername("Austin");
		
		ListView topicList = (ListView) getActivity().findViewById(cmput301w14t13.project.R.id.topic_listview);
	}
	
}
