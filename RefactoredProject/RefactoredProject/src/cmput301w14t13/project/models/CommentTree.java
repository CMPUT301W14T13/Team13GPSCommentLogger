package cmput301w14t13.project.models;

import java.util.ArrayList;
import java.util.Stack;

import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.tasks.SearchServerTask;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.views.HomeView;
import android.app.Activity;
import android.util.Log;

/**
 * The model for the entire app to modify. It holds the root which contains the list
 * of topics, a comment list which displays every comment made in a topic, the user's
 * global username, a number which is used to grab topics from root, and adapters for updating the lists.
 * 
 * @author arweber
 *
 */
public class CommentTree extends ViewList<UpdateInterface> implements AsyncProcess
{
	private Stack<CommentTreeElement> stack = new Stack<CommentTreeElement>();
	private Stack<ArrayList<CommentTreeElement>> commentListsInDisplayOrder = new Stack<ArrayList<CommentTreeElement>>(); 
	private String currentUsername = "Anonymous";
	
	private static final CommentTree Instance = new CommentTree();
	
	private CommentTree()
	{
		
	}
	
	public static CommentTree getInstance()
	{
		return Instance;
	}
	
	public void setCurrentUsername(String username){
		currentUsername = username;
		notifyViews();
	}
	
	public String getCurrentUsername(){
		return currentUsername;
	}
	
	public ArrayList<CommentTreeElement> getCommentList(UpdateInterface updateable){
		update(updateable);
		return commentListsInDisplayOrder.elementAt(updateable.getRank().getRank());
	}
	
	public ArrayList<CommentTreeElement> getChildren(UpdateInterface updateable){
		return stack.elementAt(updateable.getRank().getRank()).getChildren();
	}
	
	/**
	 * Function adds a commentTreeElement
	 * 
	 * @param comment
	 */
	public void addElementToCurrent(CommentTreeElement ele){
		stack.peek().addChild(ele);
		notifyViews();
	}
	
	/**
	 * updates the commentList to be displayed in a topic. For each topic child,
	 * it gets the children all the way down and adds them to the comment list.
	 */
	private void update(UpdateInterface updateable){
		int rank = updateable.getRank().getRank();
		commentListsInDisplayOrder.elementAt(rank).clear();
		for (CommentTreeElement each : getChildren(updateable)){
			fillTopicChildren(each,updateable);
		}
	}
	
	/**
	 *
	 * This function takes in a topic child and then recursively goes down the child comment
	 * trees to fill a list containing every comment that can then be displayed. It first 
	 * gets the children of this comment and adds the comment that was last passed into the function.
	 * If the comment has children then it recursively calls itself to add them. If a comment
	 * has no children then it doesn't do anything more and moves on to the next comment.
	 * 
	 * @param comment  the comment whose children are being added
	 */
	public void fillTopicChildren(CommentTreeElement comment, UpdateInterface updateable){
		commentListsInDisplayOrder.elementAt(updateable.getRank().getRank()).add(comment);
		ArrayList<CommentTreeElement> children = comment.getChildren();
		for (int i = 0; i < children.size(); i++){
			fillTopicChildren(children.get(i), updateable);
		}
	}
	
	public void updateCommentList(UpdateInterface updateable){
		update(updateable);
		notifyViews();
	}
	
	public void pushToCommentStack(CommentTreeElement comment)
	{
		stack.push(comment);
		commentListsInDisplayOrder.push(new ArrayList<CommentTreeElement>());
		notifyViews();
	}
	
	public void popRoot()
	{
		stack.pop();
	}
	
	public boolean isEmpty()
	{
		return stack.isEmpty();
	}
	
	public CommentTreeElement peek(){
		return stack.peek();
	}
	
	/* used for non- root pops off the comment tree stack */
	public synchronized void popFromCommentStack() throws InterruptedException
	{
		stack.pop();
		CommentTreeElement ele = stack.pop();

		commentListsInDisplayOrder.pop(); /* cached versions of the linearized hierarchy */

		commentListsInDisplayOrder.pop();
		SearchServerTask task = new TaskFactory(DataStorageService.getInstance()).getNewBrowser(ele.getID());
		DataStorageService.getInstance().doTask(this, task);
		wait();
		pushToCommentStack(task.getObj());
	}

	@Override
	public synchronized void receiveResult(String result) {
		notify();
	}
	
	public CommentTreeElement getElement(UpdateInterface updateable)
	{
		return stack.elementAt(updateable.getRank().getRank());
	}
	
}
