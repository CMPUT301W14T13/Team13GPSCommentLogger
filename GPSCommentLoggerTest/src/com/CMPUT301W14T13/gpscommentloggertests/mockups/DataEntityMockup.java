package com.CMPUT301W14T13.gpscommentloggertests.mockups;

import java.util.ArrayList;
import java.util.HashMap;

import com.CMPUT301W14T13.gpscommentlogger.controller.ClientController;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentRoot;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentThread;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;

public class DataEntityMockup {

	//This class sends data to the debugger for preliminary testing
	// In reality, the activity will send a request to the ClientController,
	// which will forward a request to the ServerController, which will send the relevant
	// data back down the chain to display in the activity.
	
	private ClientController clientController;
	private HashMap<String, Viewable> comments;
	
	public DataEntityMockup(ClientController clientController) {
		this.clientController = clientController;
		
		comments = new HashMap<String, Viewable>();
		
		CommentRoot root = new CommentRoot("root");
		
		CommentThread thread1 = new CommentThread("thread1");
		CommentThread thread2 = new CommentThread("thread2");
		CommentThread thread3 = new CommentThread("thread3");
		
		ArrayList<CommentThread> rootThreads = new ArrayList<CommentThread>();
		rootThreads.add(thread1);
		rootThreads.add(thread2);
		rootThreads.add(thread3);
		root.setC(rootThreads);
		
		Comment comment1_1 = new Comment("comment1_1");
		Comment comment1_2 = new Comment("comment1_2");
		Comment comment1_3 = new Comment("comment1_3");
		Comment comment2_1 = new Comment("comment2_1");
		Comment comment2_2 = new Comment("comment2_2");
		Comment comment2_3 = new Comment("comment2_3");
		Comment comment3_1 = new Comment("comment3_1");
		Comment comment3_2 = new Comment("comment3_2");
		Comment comment3_3 = new Comment("comment3_3");
		
		ArrayList<Comment> thread1comments = new ArrayList<Comment>();
		thread1comments.add(comment1_1);
		thread1comments.add(comment1_2);
		thread1comments.add(comment1_3);
		thread1.setC(thread1comments);
		
		ArrayList<Comment> thread2comments = new ArrayList<Comment>();
		thread2comments.add(comment2_1);
		thread2comments.add(comment2_2);
		thread2comments.add(comment2_3);
		thread2.setC(thread2comments);
		
		ArrayList<Comment> thread3comments = new ArrayList<Comment>();
		thread3comments.add(comment3_1);
		thread3comments.add(comment3_2);
		thread3comments.add(comment3_3);
		thread3.setC(thread3comments);
		
		comments.put(root.getID(), root);
		comments.put(thread1.getID(), thread1);
		comments.put(thread2.getID(), thread2);
		comments.put(thread3.getID(), thread3);
		comments.put(comment1_1.getID(), comment1_1);
		comments.put(comment1_2.getID(), comment1_2);
		comments.put(comment1_3.getID(), comment1_3);
		comments.put(comment2_1.getID(), comment2_1);
		comments.put(comment2_2.getID(), comment2_2);
		comments.put(comment2_3.getID(), comment2_3);
		comments.put(comment3_1.getID(), comment3_1);
		comments.put(comment3_2.getID(), comment3_2);
		comments.put(comment3_3.getID(), comment3_3);
	}

	public void pageRequest(String obj) {
		
		MockResult result = new MockResult();
		
		clientController.registerResult(result);
	}
	
	
}
