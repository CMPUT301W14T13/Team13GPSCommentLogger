package com.CMPUT301W14T13.gpscommentlogger;

import java.util.ArrayList;


import com.CMPUT301W14T13.gpscommentlogger.controller.ClientController;
import com.CMPUT301W14T13.gpscommentlogger.controller.ClientServerSystem;
import com.CMPUT301W14T13.gpscommentlogger.controller.DataManager;
import com.CMPUT301W14T13.gpscommentlogger.controller.ServerController;
import com.CMPUT301W14T13.gpscommentlogger.model.ClientTask;
import com.CMPUT301W14T13.gpscommentlogger.model.ClientTaskSourceCode;
import com.CMPUT301W14T13.gpscommentlogger.model.ClientTaskTaskCode;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;

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
	
	private DataManager dataManager;
	private String savePath;
	
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
        savePath = getFilesDir().getPath().toString() + "/GCLLocalData_" + intent.getExtras().getString("filePath") + ".sav";
        ClientServerSystem.getInstance().init(activity, savePath, textHandler, listHandler, debugWindow);
        client = ClientServerSystem.getInstance().getClient();
        server = ClientServerSystem.getInstance().getServer();
        
    	ClientTask task = new ClientTask();
    	task.setTaskCode(ClientTaskTaskCode.BROWSE);
    	task.setSourceCode(ClientTaskSourceCode.MOCK_DATA_ENTITY);
    	task.setObj("root");
    	client.addTask(task);
  	
        
    }
    
    public Handler getTextHandler()
    {
    	return textHandler;
    }
    

    public void simulateOnlineBrowseClick(int index)
    {
    	ClientTask task = new ClientTask();
    	task.setTaskCode(ClientTaskTaskCode.BROWSE);
    	task.setSourceCode(ClientTaskSourceCode.MOCK_DATA_ENTITY);
    	task.setObj(contentList.get(index).getID());
    	
    	client.addTask(task);
    }
    
    public void simulateOfflineSaveBrowseClick(int index)
    {
    	ClientTask task = new ClientTask();
    	task.setTaskCode(ClientTaskTaskCode.BROWSE);
    	task.setSourceCode(ClientTaskSourceCode.LOCAL_DATA_SAVES);
    	task.setObj(contentList.get(index).getID());
    	
    	client.addTask(task);
    }
    
    public void simulateOfflineFavouriteBrowseClick(int index)
    {
    	ClientTask task = new ClientTask();
    	task.setTaskCode(ClientTaskTaskCode.BROWSE);
    	task.setSourceCode(ClientTaskSourceCode.LOCAL_DATA_FAVOURITES);
    	task.setObj(contentList.get(index).getID());
    	
    	client.addTask(task);
    }
    
    public void simulatePostTopic(Topic topic){
    	ClientTask task = new ClientTask();
    	task.setTaskCode(ClientTaskTaskCode.POST);
    	task.setSourceCode(ClientTaskSourceCode.MOCK_DATA_ENTITY);
    	task.setObj(topic);
    	
    	client.addTask(task);
    	
    }
    
    public void simulatePostComment(Comment comment)
    {
    	ClientTask task = new ClientTask();
    	task.setTaskCode(ClientTaskTaskCode.POST);
    	task.setSourceCode(ClientTaskSourceCode.MOCK_DATA_ENTITY);
    	task.setObj(comment);
    	
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
