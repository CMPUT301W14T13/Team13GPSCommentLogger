package cmput301w14t13.project.models;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Root;
import cmput301w14t13.project.models.tasks.RootSearchServerTask;
import cmput301w14t13.project.models.tasks.Task;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.BitmapSerializer;
import cmput301w14t13.project.services.CommentTreeElementLocalSerializer;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.TaskSerializer;
import cmput301w14t13.project.views.HomeView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * This class saves Comments and Topics Locally on the users phone
 * It is used to cache recently viewed Topics and comments and to 
 * save Favorite Topics and Comments
 * 
 * @author  mjnichol
 */
public class ServerProxy implements AsyncProcess{
	private HashMap<String, String> saves;
	private HashMap<CommentTreeElement, String> favourites;

	private ArrayList<String> usernames;
	private ArrayList<Task> cachedTasks;
	private String filepath;

	private HomeView hv;
	
	public ServerProxy(String filepath, HomeView hv) throws InterruptedException
	{
		saves = new HashMap<String, String>();
		favourites = new HashMap<CommentTreeElement, String>();
		usernames = new ArrayList<String>();
		cachedTasks = new ArrayList<Task>();
		this.filepath = filepath;
		this.hv = hv;
		try {
			Log.w("DMLoad", "constructor" );
			load();
		}
		catch(FileNotFoundException e)
		{
			getCleanRoot();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	private synchronized void getCleanRoot() throws InterruptedException {
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer()).create();
		saves.put("ROOT", gson.toJson(new Root()));
	}
	
	/**
	 * Adds a username to the hashmap, usernames, then saves 
	 * the hashmap, usernames locally
	 * 
	 * Used in SelectUsernameController when the user wishes to add a 
	 * new username to their list of usernames
	 * 
	 * @param newUsername
	 */
	public void saveUsername(String newUsername)
	{
		this.usernames.add(newUsername);
		try {
			Log.w("DataSaving", "save username" );
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * removes a username from the hashmap, usernames, then
	 * saves the hashmap,usernames,\
	 * 
	 * Used in SelectUsernameController so the user can remove any username
	 * they might not want from a list of usernames
	 * of usernames 
	 * @param index
	 */
	public void removeUsername(int index)
	{
		this.usernames.remove(index);
		try {
			Log.w("DataSaving", "remove username" );
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getUsernames()
	{
		return usernames;
	}
	/**
	 * Adds a Task object, newTask, to the list, cachedTasks, then
	 * saves cachedTasks locally
	 * 
	 * Used when a CommentTreeElement has been created or edited
	 * to add the task in a list of tasks to complete in order
	 * 
	 * @param newTask
	 */
	public void saveTask(Task newTask)
	{
		this.cachedTasks.add(newTask);
		DataStorageService.getInstance().getCacheProcessor().alertNew();
		try {
			Log.w("DataSaving", "save task" );
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Removes the first Task object from the, cachedTasks, list
	 * then saves the, cashedTaks, list
	 * 
	 * Used when task has been completed
	 * 
	 */
	public void removeTask()
	{
		this.cachedTasks.remove(0);
		try {
			Log.w("DataSaving", "remove task" );
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Task> getTasks()
	{
		return cachedTasks;
	}
	
	/**
	 * places a CommentTreeElement into the hashmap, saves,
	 * then saves the hashmap locally
	 * 
	 * used when a user views a Topic or Comment
	 * 
	 * @param data A CommentTreeElement to be saved
	 */
	public void startSaveData(CommentTreeElement data)
	{
		Gson togson = new GsonBuilder().registerTypeHierarchyAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer()).create();
		Gson fromgson = new GsonBuilder().registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer()).create();
		Root root = (Root)fromgson.fromJson(saves.get("ROOT"), CommentTreeElement.class);
		root.addChild(data);
		this.saves.put("ROOT", togson.toJson(root));
		
		saveData(data);
		try {
			Log.w("DataSaving", "start save data" );
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * places a CommentTreeElement into the hashmap, favourites,
	 * then saves the hashmap locally
	 * 
	 * used when the user clicks to add a CommentTreeElement to favorites
	 * 
	 * @param data A CommentTreeElement to be saved
	 */
	public void startSaveFavourites(CommentTreeElement data)
	{
		saveFavourite(data);
		try {
			Log.w("DataSaving", "start save favourites" );
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveData(CommentTreeElement data)
	{
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer()).create();
		this.saves.put(data.getID(), gson.toJson(data));
		for(CommentTreeElement each : data.getChildren())
		{
			saveData(each);
		}
	}
	private void saveFavourite(CommentTreeElement data)
	{
		this.favourites.put(data, CommentTree.getInstance().peek().getID());
	}
	
	/**
	 * Using a String, key, it retrieves the matching json string from the
	 * hashmap, saves, and converts it from a json string a CommenTreeElement 
	 * then returns This CommentTreeElement
	 * 
	 * @param key A unique ID associated with a CommentTreeElement
	 * @return CommentTreeElement A single CommentTreeElement
	 */
	public CommentTreeElement getData(String key)
	{
		Gson gson = new GsonBuilder().registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer()).create();
		return gson.fromJson(saves.get(key), CommentTreeElement.class);
	}
	
	/**
	* This method is called when a user clicks a comment that they have saved as a favourite. It will then display the Topic along with 
	* the single comment that they have marked as a favourite. The comment will be displayed as well as all of its replies..
	* 
	* @param key The comment that we want to see in the context of the topic it was made within.
	* @return
	*/
	public CommentTreeElement getTopicOfFavouriteComment(CommentTreeElement key)
	{
		String Id = favourites.get(key);
		CommentTreeElement topic = getData(Id);
		topic.getChildren().clear();
		int level = key.getIndentLevel();
		/* subtract the indent level of the comment by the parents level */
		this.indentSubtraction(level, key);
		topic.addChild(key);
		return topic;
	}
	
	/**
	* This method makes sure that when we load a favourited comment to view that it and its children get displayed with the correct
	* level of indentation.
	* 
	* @param level The indentation level of the comment that we are viewing.
	* @param comment The favourited comment that we are trying to view. 
	*/
	private void indentSubtraction(int level, CommentTreeElement comment){
		comment.setIndentLevel(comment.getIndentLevel() - level);
		for(CommentTreeElement child : comment.getChildren()){
			indentSubtraction(level, child);
		}
	}
	
	/**
	 * Replaces the current hashmap, saves, with a new empty hashmap
	 * then saves this hashmap locally 
	 * 
	 * Used when the user wishes to clear their saves or when initializing the server
	 * @param newRoot
	 */
	public void clearSaves(Root newRoot)
	{
		saves = new HashMap<String, String>();
		addNewRoot(newRoot);
		try {
			Log.w("DataSaving", "clear saves" );
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addNewRoot(Root root)
	{
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer()).create();
		saves.put(root.getID(), gson.toJson(root));
	}
	
	
	/**
	 * Replaces the current hashmap, favourites, with an empty hashmap 
	 * and saves this empty hashmap locally
	 * 
	 *  used when the user wishes to clear their favourites or when initializing the server
	 *  
	 */
	public void clearFavourites()
	{
		favourites = new HashMap<CommentTreeElement, String>();
		try {
			Log.w("DataSaving", "clear favourites" );
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<CommentTreeElement> getFavouritesAsArrayList()
	{
		return new ArrayList<CommentTreeElement>(favourites.keySet());
	}
	
	/**
	 * Saves files locally using Gson to convert Java objects to
	 * json format and write those json strings into the appropriate file 
	 * in the device.
	 * 
	 * Used to save comments,favorites, and usernames locally onto the device
	 * when a user wishes to access them offline, or just to have a directory of them
	 * for easy reference.
	 * @throws IOException
	 */
	public void save() throws IOException
	{
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Task.class, new TaskSerializer()).registerTypeHierarchyAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer()).registerTypeHierarchyAdapter(Bitmap.class, new BitmapSerializer()).create();
		FileOutputStream fw = hv.getApplicationContext().openFileOutput(filepath, Context.MODE_PRIVATE);
		PrintStream bw = new PrintStream(fw);
		bw.println(gson.toJson(saves, HashMap.class));
		Log.w("DataSaving", "JSON result: " + gson.toJson(saves, HashMap.class));
		bw.println(gson.toJson(favourites, HashMap.class));
		Log.w("DataSaving", "JSON result: " + gson.toJson(favourites, HashMap.class));
		bw.println(gson.toJson(usernames));
		Log.w("DataSaving", "JSON result: " + gson.toJson(usernames));
		bw.println(gson.toJson(cachedTasks));
		Log.w("DataSaving", "JSON result: " + gson.toJson(cachedTasks));
		bw.close();
		fw.close();
	}
	/**
	 * Reads json strings from a specified filepath that representing comments,favorites, and usernames then loads
	 * them into appropriate hashmaps
	 * 
	 * Used when user wants to view locally saved content 
	 * 
	 * @throws IOException
	 */
	public void load() throws IOException
	{
		File f = new File(filepath);
		if(!f.exists() || f.isDirectory())throw new FileNotFoundException();
		
		Gson gson = new GsonBuilder().registerTypeAdapter(Task.class, new TaskSerializer()).registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer()).registerTypeAdapter(Bitmap.class, new BitmapSerializer()).create();
		Type hashmapsaves = new TypeToken<HashMap<String,String>>(){}.getType();
		Type hashmapfavourites = new TypeToken<HashMap<CommentTreeElement,String>>(){}.getType();
		
		StringWriter writer = new StringWriter();
		FileInputStream fr = hv.getApplicationContext().openFileInput(filepath);
		IOUtils.copy(fr, writer);
		String theString = writer.toString();
		Scanner br = new Scanner(theString);
		
		String json = br.nextLine();
		Log.w("DMLoad", "Saves: " + json);
		saves = gson.fromJson(json, hashmapsaves);
		
		json = br.nextLine();
		Log.w("DMLoad", "Favourites: " + json);
		favourites = gson.fromJson(json, hashmapfavourites);

		json = br.nextLine();
		Log.w("DMLoad", "Usernames: " + json);
		Type arrayListStringType = new TypeToken<ArrayList<String>>(){}.getType();
		usernames = gson.fromJson(json,  arrayListStringType);
		
		json = br.nextLine();
		Log.w("DMLoad", "CachedTasks: " + json);
		Type arrayListTaskType = new TypeToken<ArrayList<Task>>(){}.getType();
		cachedTasks = gson.fromJson(json, arrayListTaskType);
		
		br.close();
		fr.close();
	}

	@Override
	public synchronized void receiveResult(String result) {
		notify();
	}

}
