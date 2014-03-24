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
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.MySavesLocalTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.PageMockTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.ServerTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.TaskFactory;

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
	
	public ClientController(Handler handler)
	{
		isInit = false;
		this.debuggingWindow = null;
		tasks = new ArrayList<Task>();
		offlineDataEntity = new DataManager(DATA_STORAGE_LOCATION);
		this.handler = handler;
	}

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
		listener = new ServerListener(this, handler);
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
		Log.w("ClientController", currentTask.getClass().getCanonicalName());
		currentTask.execute();
		return null; //TODO: can this be eliminated?
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
		
    	PageMockTask task = new TaskFactory(dispatcher, onlineDataEntityMockup, offlineDataEntity).getNewMockBrowser();
    	task.setSearchTerm(debugActivity.getCurrentComment().getID());
		
		this.addTask(task);
	}

	public void simulateDiconnectFromServer()
	{
		hasConnection = false;
		
    	MySavesLocalTask task = new TaskFactory(dispatcher, onlineDataEntityMockup, offlineDataEntity).getNewSavesBrowser();
    	task.setSearchTerm(debugActivity.getCurrentComment().getID());
		
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
	
	public ServerDispatcher getDispatcher(){
		return dispatcher;
	}
	
	public DataEntityMockup getMockup()
	{
		return onlineDataEntityMockup;
	}

	@Override
	protected void processResult(Result result)
	{

		// TODO Auto-generated method stub
		
	}
}
