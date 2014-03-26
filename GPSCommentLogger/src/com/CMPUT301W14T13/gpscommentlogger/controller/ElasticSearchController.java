package com.CMPUT301W14T13.gpscommentlogger.controller;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.MySavesLocalTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.TaskFactory;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.Task;


public class ElasticSearchController extends Thread
{
	//data manager for local data storage
	private static DataManager offlineDataEntity;
	private static final String DATA_STORAGE_LOCATION = "data.sav";
	private static final String WEB_URL = "http://cmput301.softwareprocess.es:8080/cmput301w14t13/viewables/";
	
	private static Handler _handler;
	
	//The current task
	private ArrayList<Task> tasks;
	
	private static final ElasticSearchController Instance = new ElasticSearchController();
	
	private ElasticSearchController()
	{
		offlineDataEntity = new DataManager(DATA_STORAGE_LOCATION);
		tasks = new ArrayList<Task>();
	}
	
	public static ElasticSearchController getInstance()
	{
		return Instance;
	}
	
	public static void init(Handler handler)
	{
		_handler = handler;
	}
	
	@Override
	public void run()
	{		
		while(true){
			try{
				checkTasks();
				doTask();
			}
			catch(InterruptedException ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private synchronized void checkTasks() throws InterruptedException {
		if(tasks.isEmpty())wait();
	}

	public synchronized void addTask(Task task)
	{
		tasks.add(task);
		notify();
	}
	
	protected synchronized void doTask() throws InterruptedException
	{
		Task currentTask = tasks.remove(0);
		String result = currentTask.doTask();
	}
	
	public DataManager getDataManager(){
		return offlineDataEntity;
	}
	
	public String getURL()
	{
		return WEB_URL;
	}

}
