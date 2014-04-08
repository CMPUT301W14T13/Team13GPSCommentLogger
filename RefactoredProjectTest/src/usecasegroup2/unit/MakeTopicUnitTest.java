package usecasegroup2.unit;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class MakeTopicUnitTest extends ActivityInstrumentationTestCase2<HomeView> {

	public MakeTopicUnitTest() {
		super(HomeView.class);
	}

	
	public void testMakeTopic(){
		
		CommentTree ct = CommentTree.getInstance();
		CommentTreeElement testTopic = new Topic("test topic");
		ct.addElementToCurrent(testTopic);
		
		CommentTreeElement topic = ct.getChildren(getActivity()).get(0);
		
		assertTrue("topics should be equal", topic.equals(testTopic));
	}
	

}
