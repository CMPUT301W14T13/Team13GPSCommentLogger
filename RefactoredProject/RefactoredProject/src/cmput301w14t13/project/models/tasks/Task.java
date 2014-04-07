package cmput301w14t13.project.models.tasks;

import android.app.Activity;
import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.services.DataStorageService;


/**
 * An Abstract class 
 * Tasks contain server execution commands (eg. server object request) that are passed to controllers.
 */

public abstract class Task
{

	protected CommentTreeElement obj;

	protected String searchTerm;
	
	
	protected DataStorageService esc;
	
	public Task(DataStorageService esc, String searchTerm, CommentTreeElement obj)
	{
		this.searchTerm = searchTerm;
		this.obj = obj;
		this.esc = esc;
	}
	

	public CommentTreeElement getObj() {
		return obj;
	}
	
	public String getSearchTerm() {
		return searchTerm;
	}
	
	public abstract String doTask() throws InterruptedException;

}


