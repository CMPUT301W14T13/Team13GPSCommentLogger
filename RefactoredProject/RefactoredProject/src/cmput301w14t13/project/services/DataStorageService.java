package cmput301w14t13.project.services;

import java.util.ArrayList;

import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.models.ServerProxy;
import cmput301w14t13.project.models.tasks.Task;
import cmput301w14t13.project.views.HomeView;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

/**
 * Responsible for running all tasks, such as posting to server,
 * grabbing from server, and editing objects in the server
 * 
 * Used Whenever online and Creating new Topics or Comments to push them to server
 * or for editing comments or Topics to push the changes to server 
 * 
 * @author nsd
 *
 */
public class DataStorageService extends Service
{

	/**
	 * @author http://www.techotopia.com/index.php/Android_Local_Bound_Services_%E2%80%93_A_Worked_Example
	 *
	 * An implementation of a bound service
	 */
	private final IBinder myBinder = new LocalBinder();
	
	@Override
	public IBinder onBind(Intent arg0) {
		return myBinder;
	}

	public class LocalBinder extends Binder {
        public DataStorageService getService() {
            return DataStorageService.this;
        }
	}

	//data manager for local data storage
	private static final String DATA_STORAGE_LOCATION = "data.sav";
	private static ServerProxy offlineDataEntity = null;
	private static final String WEB_URL = "http://cmput301.softwareprocess.es:8080/cmput301w14t13/viewables/";
	private static final DataStorageService Instance = new DataStorageService();
	private static CacheProcessor cacheProcessor;
	
	private DataStorageService()
	{
	}
	/**
	 * Creates a new ServerProxy with a, DATA_STORAGE_LOCATION, on the phone
	 * for offline caching as well as setting up a cacheprocessor to cache tasks
	 * to be completed when connected to server 
	 * @param hv HomeView activity 
	 */
	public void registerContext(final HomeView hv)
	{
		if(offlineDataEntity == null)
		{
			try {
				offlineDataEntity = new ServerProxy(
						DATA_STORAGE_LOCATION, hv);
				cacheProcessor = new CacheProcessor(offlineDataEntity);
				cacheProcessor.start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
	

	public static DataStorageService getInstance()
	{
		return Instance;
	}
	
	public void doTask(final AsyncProcess origin, final Task currentTask) throws InterruptedException
	{
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					String result = currentTask.doTask();
					origin.receiveResult(result);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
	}
	
	public ServerProxy getProxy(){
		return offlineDataEntity;
	}
	
	public CacheProcessor getCacheProcessor()
	{
		return cacheProcessor;
	}
	
	public String getURL()
	{
		return WEB_URL;
	}
	
}
