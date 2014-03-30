package cmput301w14t13.project.services;

import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.models.CommentTreeProxy;
import cmput301w14t13.project.models.tasks.Task;

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
				wait();
				cp.setFlag(success);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void receiveResult(String result) {
			success = result.contains("Http/1.1 20");
			notify();
		}
	}
	
	private CommentTreeProxy offlineDataEntity;
	private boolean success;
	
	public CacheProcessor(CommentTreeProxy offlineDataEntity) {
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
					task.join();
					if(success)
					{
						offlineDataEntity.removeTask();
					}
				}
				wait();
			}
		}
		catch(InterruptedException ex)
		{
			ex.printStackTrace();
		}
	}

}
