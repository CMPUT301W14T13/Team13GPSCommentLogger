package com.CMPUT301W14T13.gpscommentlogger.controller;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.ClientTask;
import com.CMPUT301W14T13.gpscommentlogger.model.ClientTaskSourceCode;
import com.CMPUT301W14T13.gpscommentlogger.model.ClientTaskTaskCode;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.MockResult;
import com.CMPUT301W14T13.gpscommentlogger.model.Result;
import com.CMPUT301W14T13.gpscommentlogger.model.ServerTask;
import com.CMPUT301W14T13.gpscommentlogger.model.ServerTaskCode;
import com.CMPUT301W14T13.gpscommentlogger.model.Task;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;
import com.CMPUT301W14T13.gpscommentloggertests.mockups.DataEntityMockup;


public class ClientController extends Controller
{
	//store references to auxiliary classes
	private ServerDispatcher dispatcher;
	private ServerListener listener;
	private Thread resultThread;
	
	//store reference to mock server
	private ServerController server;
	
	//store list of tasks
	private ArrayList<Task> tasks;
	
	//store server result
	protected Result result = null;
	
	//store reference to output window for debugging
	protected TextView debuggingWindow;
	
	//stores reference to current page
	String pageReference;
	
	//data manager for local data storage
	DataManager offlineDataEntity;
	private final String DATA_STORAGE_LOCATION = "data.sav";
	
	//mockups for debugging
	DataEntityMockup onlineDataEntityMockup;
	DebugActivity debugActivity;
	Handler handler;
	boolean hasConnection = true;

	public ClientController(TextView debuggingWindow)
	{
		isInit = false;
		this.debuggingWindow = debuggingWindow;
		tasks = new ArrayList<Task>();
		offlineDataEntity = new DataManager(DATA_STORAGE_LOCATION);
	}
	
	public ClientController(DebugActivity activity, String storageLocation, Handler handler, TextView debuggingWindow)
	{
		isInit = false;
		this.debuggingWindow = debuggingWindow;
		tasks = new ArrayList<Task>();
		onlineDataEntityMockup = new DataEntityMockup(this);
		offlineDataEntity = new DataManager(storageLocation);
		debugActivity = activity;
		this.handler = handler;
	}
	
	
	
	@Override
	public void init()
	{
		if (isInit) return;
		listener = new ServerListener(this);
		listener.start();
		isInit = true;
	}
	
	public void setPipes()
	{
		dispatcher = new ServerDispatcher(server.getListener());
	}

	@Override
	public void run()
	{
		while(true){
			try{
				//First check if there is something to do	
				checkTasks();
				//Do the oldest task in queue
				Result result = doTask();
				this.result = null; //reset serverResult
				//Handle edge cases and debugging info here
				processResult(result);
			}
			catch(InterruptedException ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	protected synchronized void checkTasks() throws InterruptedException
	{
		if(tasks.isEmpty()) wait();
	}
	
	public synchronized void addTask(Task task)
	{
		tasks.add(task);
		notify();
	}
	
	protected synchronized Result doTask() throws InterruptedException
	{
		ClientTask currentTask = (ClientTask)tasks.remove(0);
		
		switch(currentTask.getTaskCode())
		{
		case BROWSE:
			processBrowseRequest(currentTask);
			break;
		case POST:
			processPostRequest(currentTask);
			break;
		default:
			throw new InterruptedException("Invalid Task Code in ClientController");
		}
		//TODO: code in tasks for server here
		//ServerTask sTask= new ServerTask();
		//dispatcher.dispatch(sTask);
		//wait();
		
		return result;
	}
	
	private void processBrowseRequest(ClientTask task) throws InterruptedException
	{
		switch(task.getSourceCode())
		{
		//TODO: change these cases to use the searchTerm instead of the obj?
		case LOCAL_DATA_SAVES:
			offlineDataEntity.getData((String)task.getObj());
			break;
		case LOCAL_DATA_FAVOURITES:
			offlineDataEntity.getFavourite((String)task.getObj());
			break;
		case MOCK_DATA_ENTITY:
			if(!hasConnection)throw new InterruptedException("Error attempt to browse online while offline.");
			onlineDataEntityMockup.pageRequest((String)task.getObj());
			break;
		case SERVER_DATA:
			//Convert ClientTask into ServerTask
			ServerTask serverTask = new ServerTask();
			//TODO: add a switch statement to convert ClientTaskTaskCode to ServerTaskCode
			serverTask.setCode(ServerTaskCode.INSERT);
			serverTask.setSearchTerm(task.getSearchTerm());
			serverTask.setObj((Viewable)task.getObj());
			this.dispatcher.dispatch(serverTask);
		default:
			throw new InterruptedException("Invalid Source Code in ClientController");
		}
	}
	
	private void processPostRequest(ClientTask task) throws InterruptedException
	{
		switch(task.getSourceCode())
		{
		case LOCAL_DATA_SAVES:
			throw new InterruptedException("Cannot post saves while offline");
		case LOCAL_DATA_FAVOURITES:
			//TODO: enable save from saves->favourites?
			throw new InterruptedException("Cannot post favourites while offline");
		case MOCK_DATA_ENTITY:
			if(!hasConnection)throw new InterruptedException("Error attempt to post online while offline.");
			onlineDataEntityMockup.postRequest(debugActivity.getCurrentComment(),(Comment)task.getObj());
			break;
		//case SERVER_DATA:
			//break;
		default:
			throw new InterruptedException("Invalid Source Code in ClientController");
		}
	}
	
	public synchronized void registerResult(Result result)
	{
		this.result = result;
		//TODO: separate out the response and task threads so that the notifys do not conflict
		notify();
	}
	
	protected void processResult(Result result)
	{
		Log.w("ClientController", "Result received");
		if(result instanceof MockResult)
		{
			switch(((MockResult) result).getType())
			{
				case BROWSE:
					Log.w("ClientController", "Mock Result received");
					MockResult mock = (MockResult)result;
					Viewable data = (Viewable)mock.getData();
					Message msg = new Message();
					msg.obj = data;
					Log.w("DebugMessage", "Message Sent");	
					handler.dispatchMessage(msg);
					break;
				case POST:
					Log.w("ClientController", "Mock Result received");
					mock = (MockResult)result;
					boolean success = Boolean.parseBoolean(mock.getData().toString());
					Log.w("ClientController", "Post result: " + success);
					break;
				default:
					throw new IllegalArgumentException("Illegal MockResult Type");
			}
		}
	}

	public void setServer(ServerController server)
	{
		this.server = server;
	}
	
	//
	// For simulating server connection
	//

	public void simulateConnectToServer()
	{
		hasConnection = true;
		
    	ClientTask task = new ClientTask();
    	task.setTaskCode(ClientTaskTaskCode.BROWSE);
    	task.setSourceCode(ClientTaskSourceCode.MOCK_DATA_ENTITY);
    	task.setObj(debugActivity.getCurrentComment().getID());
		
		this.addTask(task);
	}

	public void simulateDiconnectFromServer()
	{
		hasConnection = false;
		
    	ClientTask task = new ClientTask();
    	task.setTaskCode(ClientTaskTaskCode.BROWSE);
    	task.setSourceCode(ClientTaskSourceCode.LOCAL_DATA_SAVES);
    	task.setObj(debugActivity.getCurrentComment().getID());
		
		this.addTask(task);
	}
	
	public void forceChangeOnline(String title)
	{
		onlineDataEntityMockup.forceTestChange(title);
	}
	
	public void forceChangeOffline(String title)
	{
		//offlineDataEntity.forceTestChange(title);
	}
	
	public ServerListener getListener(){
		return listener;
	}
}
