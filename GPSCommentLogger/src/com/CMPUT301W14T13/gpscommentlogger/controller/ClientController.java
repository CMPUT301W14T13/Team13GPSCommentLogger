package com.CMPUT301W14T13.gpscommentlogger.controller;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.DebugActivityInterface;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.model.results.MockResult;
import com.CMPUT301W14T13.gpscommentlogger.model.results.Result;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.ClientTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.ClientTaskSourceCode;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.ClientTaskTaskCode;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.ServerTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.ServerTaskBuilder;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.ServerTaskCode;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.Task;
import com.CMPUT301W14T13.gpscommentloggertests.mockups.DataEntityMockup;


public class ClientController extends Controller
{
	//store references to auxiliary classes
	private ServerDispatcher dispatcher;
	private ServerListener listener;
	
	//store reference to mock server
	private ServerController server;
	
	//store list of tasks
	private ArrayList<Task> tasks;
	
	//store reference to output window for debugging
	protected TextView debuggingWindow;
	
	//data manager for local data storage
	DataManager offlineDataEntity;
	private final String DATA_STORAGE_LOCATION = "data.sav";
	
	//mockups for debugging
	DataEntityMockup onlineDataEntityMockup;
	DebugActivityInterface debugActivity;
	Handler handler;
	boolean hasConnection = true;

	public ClientController(TextView debuggingWindow)
	{
		isInit = false;
		this.debuggingWindow = debuggingWindow;
		tasks = new ArrayList<Task>();
		offlineDataEntity = new DataManager(DATA_STORAGE_LOCATION);
	}
	
	public ClientController(DebugActivityInterface activity, String storageLocation, Handler handler, TextView debuggingWindow)
	{
		isInit = false;
		this.debuggingWindow = debuggingWindow;
		tasks = new ArrayList<Task>();
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
		onlineDataEntityMockup = new DataEntityMockup(listener);
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
				doTask();
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
		Task currentTask = tasks.remove(0);	
		currentTask.execute();
		return null; //TODO: can this be eliminated?
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
			serverTask.setCode(ServerTaskCode.SEARCH);
			serverTask.setSearchTerm(task.getSearchTerm());
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
		case SERVER_DATA:
			//Convert ClientTask into ServerTask
			ServerTask serverTask = new ServerTask();
			serverTask.setCode(ServerTaskCode.INSERT);
			serverTask.setSearchTerm(task.getSearchTerm()); //Search Term should be parent ID
			serverTask.setObj((Viewable)task.getObj());
			this.dispatcher.dispatch(serverTask);
			break;
		default:
			throw new InterruptedException("Invalid Source Code in ClientController");
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
	
	public Handler getHandler(){
		return handler;
	}
	
	public DataManager getDataManager(){
		return offlineDataEntity;
	}

	@Override
	protected void processResult(Result result)
	{

		// TODO Auto-generated method stub
		
	}
}
