package cmput301w14t13.project.models;

import java.util.ArrayList;
import java.util.Stack;

import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.tasks.SearchServerTask;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import android.app.Activity;

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
	private ArrayList<CommentTreeElement> commentListInDisplayOrder = new ArrayList<CommentTreeElement>(); //the comment list to be displayed in a topic
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
	
	public ArrayList<CommentTreeElement> getCommentList(){
		update();
		return commentListInDisplayOrder;
	}
	
	public ArrayList<CommentTreeElement> getCurrentChildren(){
		return stack.peek().getChildren();
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
	private void update(){
		commentListInDisplayOrder.clear();
		for (CommentTreeElement each : getCurrentChildren()){
			fillTopicChildren(each);
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
	public void fillTopicChildren(CommentTreeElement comment){
		commentListInDisplayOrder.add(comment);
		ArrayList<CommentTreeElement> children = comment.getChildren();
		for (int i = 0; i < children.size(); i++){
			fillTopicChildren(children.get(i));
		}
	}
	
	public void updateCommentList(){
		update();
		notifyViews();
	}
	
	public void pushToCommentStack(CommentTreeElement comment)
	{
		stack.push(comment);
		notifyViews();
	}
	
	public synchronized void popFromCommentStack() throws InterruptedException
	{
		CommentTreeElement ele = stack.pop();
		stack.pop();
		SearchServerTask task = new TaskFactory(DataStorageService.getInstance()).getNewBrowser(ele.getID());
		DataStorageService.getInstance().doTask(this, task);
		wait();
		pushToCommentStack(task.getObj());
	}

	@Override
	public synchronized void receiveResult(String result) {
		notify();
	}
	
	public CommentTreeElement getCurrentElement()
	{
		return stack.peek();
	}
	
}
