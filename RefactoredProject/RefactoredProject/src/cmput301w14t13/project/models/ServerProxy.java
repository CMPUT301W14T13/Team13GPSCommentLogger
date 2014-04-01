package cmput301w14t13.project.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

import android.content.Context;
import android.util.Log;
import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Root;
import cmput301w14t13.project.models.tasks.Task;
import cmput301w14t13.project.services.CommentTreeElementLocalSerializer;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.views.HomeView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ServerProxy implements AsyncProcess{
	private HashMap<String, String> saves;
	private HashMap<CommentTreeElement, String> favourites;
	private ArrayList<String> usernames;
	private ArrayList<Task> cachedTasks;
	private String filepath;
	private HomeView hv;
	
	public ServerProxy(String filepath, HomeView hv) throws InterruptedException
	{
		/* id : Json*/
		saves = new HashMap<String, String>();
		favourites = new HashMap<CommentTreeElement, String>();
		usernames = new ArrayList<String>();
		cachedTasks = new ArrayList<Task>();
		this.filepath = filepath;
		this.hv = hv;
		try {
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
	
	public void saveUsername(String newUsername)
	{
		this.usernames.add(newUsername);
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeUsername(int index)
	{
		this.usernames.remove(index);
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getUsernames()
	{
		return usernames;
	}
	
	public void saveTask(Task newTask)
	{
		this.cachedTasks.add(newTask);
		DataStorageService.getInstance().getCacheProcessor().alertNew();
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeTask()
	{
		this.cachedTasks.remove(0);
		try {
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
		Gson togson = new GsonBuilder().registerTypeHierarchyAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer()).create();
		Gson fromgson = new GsonBuilder().registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer()).create();
		Root root = (Root)fromgson.fromJson(saves.get("ROOT"), CommentTreeElement.class);
		root.addChild(data);
		this.saves.put("ROOT", togson.toJson(root));
		
		saveData(data);
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startSaveFavourites(CommentTreeElement data)
	{
		saveFavourite(data);
		try {
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
	
	public void clearSaves(Root newRoot)
	{
		saves = new HashMap<String, String>();
		addNewRoot(newRoot);
		try {
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
	
	public void clearFavourites()
	{
		favourites = new HashMap<CommentTreeElement, String>();
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save() throws IOException
	{
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer()).create();
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
		
		Gson gson = new GsonBuilder().registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementLocalSerializer()).create();
		Type hashmap = new TypeToken<HashMap<String,String>>(){}.getType();
		
		StringWriter writer = new StringWriter();
		FileInputStream fr = hv.getApplicationContext().openFileInput(filepath);
		IOUtils.copy(fr, writer);
		String theString = writer.toString();
		Scanner br = new Scanner(theString);
		
		String json = br.nextLine();
		Log.w("DMLoad", "Saves: " + json);
		saves = gson.fromJson(json, hashmap);
		
		json = br.nextLine();
		Log.w("DMLoad", "Favourites: " + json);
		favourites = gson.fromJson(json, hashmap);

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
