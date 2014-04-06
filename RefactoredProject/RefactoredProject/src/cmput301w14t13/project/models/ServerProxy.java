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
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.serialization.BitmapSerializer;
import cmput301w14t13.project.services.serialization.CommentTreeElementLocalSerializer;
import cmput301w14t13.project.services.serialization.TaskSerializer;
import cmput301w14t13.project.views.HomeView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
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
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer(hv)).create();
		saves.put("ROOT", gson.toJson(new Root()));
	}
	
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
	
	/**
	 * @return
	 * 
	 */
	public ArrayList<String> getUsernames()
	{
		return usernames;
	}
	
	public void saveTask(Task newTask)
	{
		this.cachedTasks.add(newTask);
		DataStorageService.getInstance().getCacheProcessor().alertNewOrOnline();
		try {
			Log.w("DataSaving", "save task" );
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
	
	public void startSaveData(CommentTreeElement data)
	{
		Gson togson = new GsonBuilder().registerTypeHierarchyAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer(hv)).create();
		Gson fromgson = new GsonBuilder().registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer(hv)).create();
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
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer(hv)).create();
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
	
	public CommentTreeElement getData(String key)
	{
		Gson gson = new GsonBuilder().registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer(hv)).create();
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
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer(hv)).create();
		saves.put(root.getID(), gson.toJson(root));
	}
	
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
	
	public void save() throws IOException
	{
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Task.class, new TaskSerializer(hv)).registerTypeHierarchyAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer(hv)).registerTypeHierarchyAdapter(Bitmap.class, new BitmapSerializer()).create();
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
	
	public void load() throws IOException
	{
		File f = new File(filepath);
		if(!f.exists() || f.isDirectory())throw new FileNotFoundException();
		
		Gson gson = new GsonBuilder().registerTypeAdapter(Task.class, new TaskSerializer(hv)).registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer(hv)).registerTypeAdapter(Bitmap.class, new BitmapSerializer()).create();
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
