package cmput301w14t13.project.services;

import android.util.Log;
import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.models.ServerProxy;
import cmput301w14t13.project.models.tasks.Task;

/**
 * Processes stored Tasks that require a web connection to complete
 * such as creating or editing Topics
 * 
 * Stared by DataStorageService and continues to run until the service dies
 * 
 * @author nsd
 *
 */
public class CacheProcessor extends Thread {

	
	private class CacheTask extends Thread implements AsyncProcess{
		
	
		private CacheProcessor cp;
		boolean success;
		public CacheTask(CacheProcessor cp)
		{
			super();
			this.cp = cp;
		}
		
		@Override
		public void run() {
			Task task = offlineDataEntity.getTasks().get(0);
			try {
				DataStorageService.getInstance().doTask(this, task);
				waitForResult();
				cp.setFlag(success);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public synchronized void receiveResult(String result) {
			Log.w("RESULT", result);
			success = result.contains("\"ok\":true");
			notify();
		}
		
		private synchronized void waitForResult() throws InterruptedException
		{
			wait();
		}
	}
	

	private ServerProxy offlineDataEntity;
	private boolean success;
	/**
	 * Creates a Cache processor and hands it ServerProxy 
	 * which has a stack of Tasks to be Completed 
	 * 
	 * @param offlineDataEntity the ServerProxy with a list of tasks to be completed
	 */
	public CacheProcessor(ServerProxy offlineDataEntity) {
		super();
		this.offlineDataEntity = offlineDataEntity;
	}

	public void setFlag(boolean bool) {
		success = bool;	
	}
	
	/**
	 * Grabs the first task off of a list of cached tasks stored in, offlineDataEntity,
	 * then runs the task, waits for it to complete, then if successful removes
	 * the task from the list of cached tasks
	 * 
	 * run when web connection is established to complete all undone tasks
	 * 
	 */
	@Override
	public void run() {
		try
		{
			while(true)
			{
				while(!offlineDataEntity.getTasks().isEmpty())
				{
					CacheTask task = new CacheTask(this);
					task.start();
					task.join();
					Log.w("Success", Boolean.toString(success));
					if(success)
					{
						offlineDataEntity.removeTask();
					}
				}
				waitForNew();
			}
		}
		catch(InterruptedException ex)
		{
			ex.printStackTrace();
		}
	}

	private synchronized void waitForNew() throws InterruptedException {
		wait();		
	}

	public synchronized void alertNew()
	{
		notify();
	}
}
