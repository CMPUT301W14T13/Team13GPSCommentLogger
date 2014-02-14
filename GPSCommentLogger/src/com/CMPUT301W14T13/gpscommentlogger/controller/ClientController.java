package com.CMPUT301W14T13.gpscommentlogger.controller;

import java.util.ArrayList;

import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.model.ClientTask;
import com.CMPUT301W14T13.gpscommentlogger.model.Result;
import com.CMPUT301W14T13.gpscommentlogger.model.ServerTask;
import com.CMPUT301W14T13.gpscommentlogger.model.Task;
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
	
	//store server result
	protected Result result = null;
	
	//store reference to output window for debugging
	protected TextView debuggingWindow;
	
	//stores reference to current page
	String pageReference;
	
	//mockup for debugging
	DataEntityMockup dataEntityMockup;

	public ClientController(TextView debuggingWindow)
	{
		isInit = false;
		this.debuggingWindow = debuggingWindow;
		tasks = new ArrayList<Task>();
		dataEntityMockup = new DataEntityMockup(this);
	}
	
	@Override
	public void init()
	{
		if (isInit) return;
		
		dispatcher = new ServerDispatcher(server);
		listener = new ServerListener(this);
		listener.start();
		isInit = true;
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
		default:
			throw new InterruptedException("Invalid Task Code in ClientController");
		}
		//TODO: code in tasks for server here
		//ServerTask sTask= new ServerTask();
		//dispatcher.dispatch(sTask);
		wait();
		
		return result;
	}
	
	private void processBrowseRequest(ClientTask task) throws InterruptedException
	{
		switch(task.getSourceCode())
		{
		//case LOCAL_DATA:
			//break;
		case MOCK_DATA_ENTITY:
			dataEntityMockup.pageRequest(task.getObj());
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
		notify();
	}
	
	protected void processResult(Result result)
	{
		//TODO: handle results here
	}

	public void setServer(ServerController server)
	{

		this.server = server;
		
	}


	
}
