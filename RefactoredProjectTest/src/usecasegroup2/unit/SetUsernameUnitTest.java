package usecasegroup2.unit;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import cmput301w14t13.project.controllers.SelectUsernameController;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.views.HomeView;

public class SetUsernameUnitTest extends ActivityInstrumentationTestCase2<HomeView> {

	CommentTree ct;
	SelectUsernameController selectUsername;

	public SetUsernameUnitTest() {
		super(HomeView.class);
	}

	public void setUp(){
		ct = CommentTree.getInstance();
	}

	public void testDefaultUsername(){

		//create new comment with default username
		CommentTreeElement comment = new Comment();
		assertEquals("Username should be default username", "Anonymous", comment.getUsername());

	}

	public void testSetUsername(){

		CommentTreeElement comment = new Comment();
		comment.setUsername("Austin");
		assertEquals("Username should be what was set", "Austin", comment.getUsername());
	}

	public void testSetCurrentUsername(){

		ct.setCurrentUsername("Austin");
		assertEquals("Username should be what was set", "Austin", ct.getCurrentUsername());
	}

}


