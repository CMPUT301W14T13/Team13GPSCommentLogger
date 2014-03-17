package com.CMPUT301W14T13.gpscommentlogger;

import java.util.ArrayList;


import com.CMPUT301W14T13.gpscommentlogger.controller.ClientController;
import com.CMPUT301W14T13.gpscommentlogger.controller.ClientServerSystem;
import com.CMPUT301W14T13.gpscommentlogger.controller.DataManager;
import com.CMPUT301W14T13.gpscommentlogger.controller.ServerController;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.MyFavouritesLocalTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.MySavesLocalTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.PageMockTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.PostMockTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.TaskFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class DebugActivity extends Activity implements DebugActivityInterface
{
	Handler textHandler;
	Handler listHandler;
	Viewable currentComment;
	ArrayList<Viewable> contentList;
	ArrayAdapter<Viewable> commentAdapter;
	ClientController client;
	ServerController server;
	ListView root;
	
	private String savePath = "test.sav";
	
	private TaskFactory taskFactory;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_view);
        
        final TextView debugWindow = (TextView)findViewById(R.id.debug_window);
        final DebugActivity activity = this;
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
        
        currentComment = new Root();
        contentList = currentComment.getChildren();
        root = (ListView)findViewById(R.id.debug_main_list);
        commentAdapter = new ArrayAdapter<Viewable>(activity, 0, 0, contentList);
        root.setAdapter(commentAdapter);
        
        listHandler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message inputMessage) {
            	final Viewable msg = (Viewable)inputMessage.obj;
            	activity.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						Log.w("DebugMessage", "Message Received: " + msg);
				    	currentComment = msg;
				    	contentList = msg.getChildren();
				    	commentAdapter.notifyDataSetChanged();
		            	Log.w("Debug Message", "Current Comment: " + currentComment.getID());
		            	}});			
            }
        };
        
        Intent intent = getIntent();      
        //savePath = getFilesDir().getPath().toString() + "/GCLLocalData_" + intent.getExtras().getString("filePath") + ".sav";
        ClientServerSystem.getInstance().init(activity, savePath, textHandler, listHandler, debugWindow);
        client = ClientServerSystem.getInstance().getClient();
        server = ClientServerSystem.getInstance().getServer();
        
        taskFactory = new TaskFactory(client.getDispatcher(), client.getMockup(), client.getDataManager());
        
    	PageMockTask task = taskFactory.getNewMockBrowser();
    	task.setSearchTerm("root");
    	client.addTask(task);
  	
        
    }
    
    public Handler getTextHandler()
    {
    	return textHandler;
    }
    

    /**
     * Add a comment to the root
     * @param comment
     */
    /*
    public void simulateAddComment(Viewable comment){
    	currentComment.addChild(comment);
    	
    }
    */
    public void simulateOnlineBrowseClick(int index)
    {
    	PageMockTask task = taskFactory.getNewMockBrowser();
    	task.setSearchTerm(contentList.get(index).getID());
    	
    	client.addTask(task);
    }
    
    public void simulateOfflineSaveBrowseClick(int index)
    {
    	MySavesLocalTask task = taskFactory.getNewSavesBrowser();
    	task.setSearchTerm(contentList.get(index).getID());
    	
    	client.addTask(task);
    }
    
    public void simulateOfflineFavouriteBrowseClick(int index)
    {
    	MyFavouritesLocalTask task = taskFactory.getNewFavouriteBrowser();
    	task.setSearchTerm(contentList.get(index).getID());
    	
    	client.addTask(task);
    }
    
	public Viewable getCurrentComment() {
		return currentComment;
	}

	public ArrayList<Viewable> getContentList() {
		return contentList;
	}
	
	public void simulateConnectToServer()
	{
		client.simulateConnectToServer();
	}
	
	public void simulateDisconnectFromServer()
	{
		client.simulateDiconnectFromServer();
	}
	
	public void forceChangeOnline(String title)
	{
		client.forceChangeOnline(title);
	}
	
	public DataManager getDataManager(){
		return client.getDataManager();
	}
	
	public String getSavePath()
	{
		return savePath;
	}
	
	public void forceChangeOffline(String title)
	{
		client.forceChangeOffline(title);
	}
	
	
}
