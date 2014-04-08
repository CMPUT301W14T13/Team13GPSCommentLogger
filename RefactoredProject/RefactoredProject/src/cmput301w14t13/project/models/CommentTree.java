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

	/**
	 * Singleton for our model. Use this when you need access to the methods 
	 * within CommentTree that will modify the model of the app such as adding
	 * topics and comments.
	 * @return the instance of the model
	 */
	public static CommentTree getInstance()
	{
		return Instance;
	}
	
	/**
	 * Sets the global username to be used throughout the app. With this set,
	 * topics and comments will automatically have their username field set to
	 * this name unless changed during the creation of the topic or comment. This
	 * username is set in SelectUsernameController by the user.
	 * 
	 * 
	 * @param username  the username that you would like to use globally
	 * throughout the app
	 */
	public void setCurrentUsername(String username){
		currentUsername = username;
		notifyViews();
	}
	
	/**
	 * Used during creation of a topic or comment to automatically
	 * set the EditText field to the global username.
	 * 
	 * @return  the global username being used currently
	 */
	public String getCurrentUsername(){
		return currentUsername;
	}

	
	/**
	 * Gets the comment list of the current topic being viewed so it can be displayed
	 * to the user. This is obtained in TopicView
	 * 
	 * @param updateable  The current topic view being viewed. This view is then used
	 * to obtain its respective list of comments
	 * 
	 * @return  the topic's list of comments to be displayed to the user in TopicView
	 */
	public ArrayList<CommentTreeElement> getCommentList(RankedHierarchicalActivity updateable){
		return commentListsInDisplayOrder.elementAt(updateable.getRank().getRank());
	}

	
	/**
	 * Gets the topics to be displayed to the user. The list of topics are only displayed
	 * in HomeView, so only a HomeView should be passed in here.
	 * 
	 * @param updateable  the HomeView where the topics are to be displayed
	 * @return  The list of topics to be displayed
	 */
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

	
	/**
	 * This sets the current list of topics to the sorted list being passed in. The reason for this is so
	 * the topics are still in sorted order after leaving HomeView, such as when viewing a topic and then
	 * returning.
	 * 
	 * @param updateable  The view whose children are to be set to be sortedList
	 * @param sortedList  The sorted list to set the children of HomeView to
	 */
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

	
	/**
	 * Updates the comment list of the TopicView being currently viewed. This is done
	 * to update any changes to the commentList immediately
	 * 
	 * @param updateable  The view whose comment list is to be updated
	 */
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


	/**
	 * Refreshes the HomeView or TopicView depending on whether or not there is a 
	 * detected internet connection. If there is an internet connection then the 
	 * topics will be pulled from the server and update with the latest changes. If there
	 * is no connection then the user will be switched to an offline mode where they can
	 * view anything they have looked at before their disconnection. Anything they look at
	 * is cached locally.
	 * 
	 * @throws InterruptedException
	 */
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

	
	/**
	 * Get the current topic being viewed. This is used in TopicView so that the
	 * current topic being viewed can then be modified or updated.
	 * 
	 * @param updateable  the view of the topic being looked at
	 * @return  the topic
	 */
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
