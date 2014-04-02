package cmput301w14t13.project.services;

import android.util.Log;
import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.models.ServerProxy;
import cmput301w14t13.project.models.tasks.Task;

/**
 * @author  mjnichol
 */
public class CacheProcessor extends Thread {

	/**
	 * @author  mjnichol
	 */
	private class CacheTask extends Thread implements AsyncProcess{
		
		/**
		 * @uml.property  name="cp"
		 * @uml.associationEnd  
		 */
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
	
	/**
	 * @uml.property  name="offlineDataEntity"
	 * @uml.associationEnd  
	 */
	private ServerProxy offlineDataEntity;
	private boolean success;
	
	public CacheProcessor(ServerProxy offlineDataEntity) {
		super();
		this.offlineDataEntity = offlineDataEntity;
	}

	public void setFlag(boolean bool) {
		success = bool;	
	}

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
