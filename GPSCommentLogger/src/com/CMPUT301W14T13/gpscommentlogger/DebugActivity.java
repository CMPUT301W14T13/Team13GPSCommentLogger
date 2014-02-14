package com.CMPUT301W14T13.gpscommentlogger;

import java.util.ArrayList;


import com.CMPUT301W14T13.gpscommentlogger.controller.ClientController;
import com.CMPUT301W14T13.gpscommentlogger.controller.ClientServerSystem;
import com.CMPUT301W14T13.gpscommentlogger.controller.ServerController;
import com.CMPUT301W14T13.gpscommentlogger.model.ClientTask;
import com.CMPUT301W14T13.gpscommentlogger.model.ClientTaskCode;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentThread;
import com.CMPUT301W14T13.gpscommentloggertests.mockups.DataEntityMockup;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class DebugActivity extends Activity
{
	Handler textHandler;
	ArrayList<CommentThread> commentThreads;
	ArrayAdapter<CommentThread> rootAdapter;
	ArrayList<Comment> comments;
	ArrayAdapter<Comment> commentAdapter;
	ClientController client;
	ServerController server;
	ListView root;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_view);
        
        final TextView debugWindow = (TextView)findViewById(R.id.debug_window);
        final Activity activity = this;
        debugWindow.setText("Hello World!");
        
        textHandler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message inputMessage) {
            	final String msg = inputMessage.obj.toString();
            	activity.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						Log.w("DebugMessage", "Message Received: " + msg);
						((TextView)activity.findViewById(R.id.debug_window)).setText(msg);
		            	Log.w("Debug Message", "Current Text: " + debugWindow.getText().toString());
		            	}});			
            }
        };
        
        commentThreads = new ArrayList<CommentThread>();
        root = (ListView)findViewById(R.id.debug_main_list);
        rootAdapter = new ArrayAdapter<CommentThread>(activity, 0, 0, commentThreads);
        root.setAdapter(rootAdapter);
        
        comments = new ArrayList<Comment>();
        commentAdapter = new ArrayAdapter<Comment>(activity,0,0,comments);
        
        ClientServerSystem.getInstance().init(textHandler, debugWindow);
        client = ClientServerSystem.getInstance().getClient();
        server = ClientServerSystem.getInstance().getServer();
        
    	ClientTask task = new ClientTask();
    	task.setTaskCode(ClientTaskCode.BROWSE);
    	task.setSourceCode(ClientTaskCode.MOCK_DATA_ENTITY);
    	task.setObj("root");
        
    }
    
    public Handler getTextHandler()
    {
    	return textHandler;
    }
    
    public void switchToRoot()
    {
    	root.setAdapter(rootAdapter);
    }
    
    public void switchToComment()
    {
    	root.setAdapter(commentAdapter);
    }
    
    public void notifyRootAdapter()
    {
    	rootAdapter.notifyDataSetChanged();
    }
    
    public void notifyCommentAdapter()
    {
    	commentAdapter.notifyDataSetChanged();
    }
    
    public ArrayList<CommentThread> getRoot()
    {
    	return commentThreads;
    }
    
    public ArrayList<Comment> getCommentThread()
    {
    	return comments;
    }
    
    public void simulateBrowseClick(int index)
    {
    	ClientTask task = new ClientTask();
    	task.setTaskCode(ClientTaskCode.BROWSE);
    	task.setSourceCode(ClientTaskCode.MOCK_DATA_ENTITY);
    	task.setObj(commentThreads.get(index).getID());
    	
    	client.addTask(task);
    }
}
