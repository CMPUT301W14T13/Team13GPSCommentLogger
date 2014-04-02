package cmput301w14t13.project.models.tasks;

import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.services.DataStorageService;


/**
 * Tasks contain server execution commands (eg. server object request) that are passed to controllers.
 */

public abstract class Task
{
	/**
	 * @uml.property  name="obj"
	 * @uml.associationEnd  
	 */
	protected CommentTreeElement obj;
	/**
	 * @uml.property  name="searchTerm"
	 */
	protected String searchTerm;
	
	/**
	 * @uml.property  name="esc"
	 * @uml.associationEnd  
	 */
	protected DataStorageService esc;
	
	public Task(DataStorageService esc, String searchTerm, CommentTreeElement obj)
	{
		this.searchTerm = searchTerm;
		this.obj = obj;
		this.esc = esc;
	}
	
	/**
	 * @return
	 * @uml.property  name="obj"
	 */
	public CommentTreeElement getObj() {
		return obj;
	}
	
	/**
	 * @return
	 * @uml.property  name="searchTerm"
	 */
	public String getSearchTerm() {
		return searchTerm;
	}
	
	public abstract String doTask() throws InterruptedException;

}
