package cmput301w14t13.project.models;

import java.util.ArrayList;
import java.util.Stack;

import android.app.ActionBar;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import auxilliary.other.NavigationItems;
import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.tasks.Task;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.NetworkReceiver;
import cmput301w14t13.project.views.HomeView;

/**
 * The model for the entire app to modify. It holds the root which contains the list of topics,
 * a comment list which displays every comment made in a topic, the user's global username, 
 * a number which is used to grab topics from root, and adapters for updating the lists.
 * 
 * @author  arweber
 */
public class CommentTree extends ViewList<UpdateInterface> implements AsyncProcess
{
	private Stack<CommentTreeElement> stack = new Stack<CommentTreeElement>();
	private Stack<ArrayList<CommentTreeElement>> commentListsInDisplayOrder = new Stack<ArrayList<CommentTreeElement>>(); 

	private String currentUsername = "Anonymous";
	private NavigationItems currentNavigationItem = NavigationItems.Relevant;
	private Location location = new Location("default");

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

	public ArrayList<CommentTreeElement> getCommentList(RankedHierarchicalActivity updateable){
		return commentListsInDisplayOrder.elementAt(updateable.getRank().getRank());
	}

	public ArrayList<CommentTreeElement> getChildren(RankedHierarchicalActivity updateable){
		return stack.elementAt(updateable.getRank().getRank()).getChildren();
	}

	/**
	 * Adds a CommentTreeElement to another CommentTreeElement that is currently
	 * on top of the CommentTree stack
	 * 
	 * Used when adding a comment as a child to the Topic currently on top of the CommentTreeStack
	 * 
	 * @param comment
	 */
	public void addElementToCurrent(CommentTreeElement ele){
		stack.peek().addChild(ele);
		notifyViews();
	}


	/**
	 * Replaces the Comment list specified by the injected activity ,updateable, with
	 * the provided ArrayList, sortedList,
	 * 
	 * Used in HomeView to sort Topics by the users current location
	 * 
	 * @param updateable The RankedHierarchicalActivity calling this method
	 */
	public void sortListByCurrentLocation(RankedHierarchicalActivity updateable){	
		ArrayList<CommentTreeElement> sortedList = SortFunctions.sortByCurrentLocation(getCommentList(updateable));
		addSortedList(updateable, sortedList);
	}
	/**
	 * Replaces the Comment list specified by the injected activity ,updateable, with
	 * the provided ArrayList, sortedList,
	 * 
	 * Used in HomeView to sort Topics by a Location given by the user
	 * 
	 * @param updateable The RankedHierarchicalActivity calling this method
	 * @param location The Location to be used for sorting
	 */
	public void sortListByGivenLocation(RankedHierarchicalActivity updateable, Location location){
		ArrayList<CommentTreeElement> sortedList = SortFunctions.sortByGivenLocation(getCommentList(updateable), location);
		addSortedList(updateable, sortedList);
	}
	/**
	 * Replaces the Comment list specified by the injected activity ,updateable, with
	 * the provided ArrayList, sortedList,
	 * 
	 * Used in HomeView to sort Topics by a Location given by the user 
	 * 
	 * @param updateable The RankedHierarchicalActivity calling this method
	 */
	public void sortListByPicture(RankedHierarchicalActivity updateable){
		ArrayList<CommentTreeElement> sortedList = SortFunctions.sortByPicture(getCommentList(updateable));
		addSortedList(updateable, sortedList);
	}
	/**
	 * Replaces the Comment list specified by the injected activity ,updateable, with
	 * the provided ArrayList, sortedList,
	 * 
	 * Used in HomeView to sort Topics from newest created to oldest created 
	 * 
	 * @param updateable The RankedHierarchicalActivity calling this method
	 */
	public void sortListByNewest(RankedHierarchicalActivity updateable){
		ArrayList<CommentTreeElement> sortedList = SortFunctions.sortByNewest(getCommentList(updateable));
		addSortedList(updateable, sortedList);
	}
	/**
	 * Replaces the Comment list specified by the injected activity ,updateable, with
	 * the provided ArrayList, sortedList,
	 * 
	 * Used in HomeView to sort Topics from oldest created to newest created 
	 * 
	 * @param updateable The RankedHierarchicalActivity calling this method
	 */
	public void sortListByOldest(RankedHierarchicalActivity updateable){
		ArrayList<CommentTreeElement> sortedList = SortFunctions.sortByOldest(getCommentList(updateable));
		addSortedList(updateable, sortedList);
	}
	/**
	 * Replaces the Comment list specified by the injected activity ,updateable, with
	 * the provided ArrayList, sortedList,
	 * 
	 * Used in HomeView to sort Topics by most relevant to the user 
	 * 
	 * @param updateable The RankedHierarchicalActivity calling this method
	 */
	public void sortListByMostRelevant(RankedHierarchicalActivity updateable){
		ArrayList<CommentTreeElement> sortedList = SortFunctions.sortByMostRelevant(getCommentList(updateable));
		addSortedList(updateable, sortedList);
	}

	public void addSortedList(RankedHierarchicalActivity updateable, ArrayList<CommentTreeElement> sortedList)
	{
		int rank = updateable.getRank().getRank();
		getChildren(updateable).clear();
		getChildren(updateable).addAll(sortedList);
		notifyViews();
	}



	/**
	 * updates the commentList to be displayed in a topic. For each topic child,
	 * it gets the children all the way down and adds them to the comment list.
	 */
	public void update(RankedHierarchicalActivity updateable){
		int rank = updateable.getRank().getRank();
		commentListsInDisplayOrder.elementAt(rank).clear();
		for (CommentTreeElement each : getChildren(updateable)){
			fillTopicChildren(each,updateable);
		}
	}

	public void updateTop(){
		int rank = stack.size()-1;
		commentListsInDisplayOrder.elementAt(rank).clear();
		for (CommentTreeElement each : stack.elementAt(rank).getChildren()){
			fillTopicChildren(each,rank);
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
	public void fillTopicChildren(CommentTreeElement comment, RankedHierarchicalActivity updateable){
		commentListsInDisplayOrder.elementAt(updateable.getRank().getRank()).add(comment);
		ArrayList<CommentTreeElement> children = comment.getChildren();

		for (int i = 0; i < children.size(); i++){
		
			children.get(i).setIndentLevel(comment.getIndentLevel() + 1);
			
			fillTopicChildren(children.get(i), updateable);
		}
	}

	public void fillTopicChildren(CommentTreeElement comment, int rank){
		commentListsInDisplayOrder.elementAt(rank).add(comment);
		ArrayList<CommentTreeElement> children = comment.getChildren();
		for (int i = 0; i < children.size(); i++){
			fillTopicChildren(children.get(i), rank);
		}
	}

	public void updateCommentList(RankedHierarchicalActivity updateable){
		update(updateable);
		notifyViews();
	}

	public void pushToCommentStack(CommentTreeElement comment)
	{
		stack.push(comment);
		commentListsInDisplayOrder.push(new ArrayList<CommentTreeElement>());
		updateTop();
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


	public synchronized void refresh() throws InterruptedException
	{
		CommentTreeElement ele = stack.pop();
		commentListsInDisplayOrder.pop(); /* cached versions of the linearized hierarchy */
		Task task;
		DataStorageService dss = DataStorageService.getInstance();
		Log.w("REFRESH", ele.getID());
		if(NetworkReceiver.isConnected)
		{
			task = new TaskFactory(dss).getNewBrowser(ele.getID());
		}
		else
		{
			task = new TaskFactory(dss).getNewSavesBrowser(ele.getID());
		}
		DataStorageService.getInstance().doTask(this, task);
		wait();
		pushToCommentStack(task.getObj());
	}

	/* used for non- root pops off the comment tree stack */
	public synchronized void popFromCommentStack(Context cxt) throws InterruptedException
	{
		stack.pop();
		CommentTreeElement ele = stack.pop();

		commentListsInDisplayOrder.pop(); /* cached versions of the linearized hierarchy */
		commentListsInDisplayOrder.pop();

		Task task;
		if(NetworkReceiver.isConnected)
		{
			task = new TaskFactory(DataStorageService.getInstance()).getNewBrowser(ele.getID());
		}
		else
		{
			task = new TaskFactory(DataStorageService.getInstance()).getNewSavesBrowser(ele.getID());
		}
		DataStorageService.getInstance().doTask(this, task);
		wait();
		pushToCommentStack(task.getObj());
	}

	@Override
	public synchronized void receiveResult(String result) {
		notify();
	}

	public CommentTreeElement getElement(RankedHierarchicalActivity updateable)
	{
		return stack.elementAt(updateable.getRank().getRank());
	}



	public void sortElements(NavigationItems item, RankedHierarchicalActivity view, Location location)
	{
		ArrayList<CommentTreeElement> sortedTopics = getCommentList(view);
		switch (item) {
			case ProximityToMe:

				sortedTopics = SortFunctions.sortByCurrentLocation(sortedTopics);
				if(view instanceof HomeView){
					currentNavigationItem = NavigationItems.ProximityToMe;
					this.location = location;
				}
				break;

			case ProximityToLocation:
				sortedTopics = SortFunctions.sortByGivenLocation(sortedTopics, location);			
				if(view instanceof HomeView){
					currentNavigationItem = NavigationItems.ProximityToLocation;
					this.location = location;
				}
				break;

			case Pictures:
				sortedTopics = SortFunctions.sortByPicture(sortedTopics);
				if(view instanceof HomeView){
					currentNavigationItem = NavigationItems.Pictures;
					this.location = location;
				}
				break;
			case Newest:

				sortedTopics = SortFunctions.sortByNewest(sortedTopics);
				if(view instanceof HomeView){
					currentNavigationItem = NavigationItems.Newest;
					this.location = location;
				}
				break;

			case Oldest:

				sortedTopics = SortFunctions.sortByOldest(sortedTopics);
				if(view instanceof HomeView){
					currentNavigationItem = NavigationItems.Oldest;
					this.location = location;
				}
				break;
			case Relevant:
				sortedTopics = SortFunctions.sortByMostRelevant(sortedTopics);
				if(view instanceof HomeView){
					currentNavigationItem = NavigationItems.Relevant;
					this.location = location;
				}
				break;
		}
		ActionBar actionBar = view.getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		//actionBar.setListNavigationCallbacks(view.adapter, this);
		actionBar.setSelectedNavigationItem(item.getValue());
		addSortedList(view, sortedTopics);
	}

}
