package cmput301w14t13.project.services;

import java.util.ArrayList;

import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.models.CommentTreeProxy;
import cmput301w14t13.project.models.tasks.Task;
import cmput301w14t13.project.views.HomeView;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;


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
	private static CommentTreeProxy offlineDataEntity = null;
	private static final String WEB_URL = "http://cmput301.softwareprocess.es:8080/cmput301w14t13/viewables/";
	private static final DataStorageService Instance = new DataStorageService();

	private static CacheProcessor cacheProcessor;
	
	private DataStorageService()
	{
	}
	
	public void registerContext(final HomeView hv)
	{
		if(offlineDataEntity == null)
		{
			try {
				offlineDataEntity = new CommentTreeProxy(
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
	
	public CommentTreeProxy getProxy(){
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
