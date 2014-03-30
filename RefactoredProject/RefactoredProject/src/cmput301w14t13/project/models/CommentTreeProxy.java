package cmput301w14t13.project.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.tasks.Task;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.MapOfInterfacesSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CommentTreeProxy {
	private HashMap<String, CommentTreeElement> saves;
	private HashMap<String, CommentTreeElement> favourites;
	private ArrayList<String> usernames;
	private ArrayList<Task> cachedTasks;
	private String filepath;

	
	public CommentTreeProxy(String filepath)
	{
		saves = new HashMap<String, CommentTreeElement>();
		favourites = new HashMap<String, CommentTreeElement>();
		usernames = new ArrayList<String>();
		cachedTasks = new ArrayList<Task>();
		this.filepath = filepath;

		try {
			load();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		DataStorageService.getInstance().getCacheProcessor().notify();
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
	
	public void saveData(CommentTreeElement data)
	{
		this.saves.put(data.getID(), data);
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveFavourite(CommentTreeElement data)
	{
		this.favourites.put(data.getID(), data);
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CommentTreeElement getData(String key)
	{
		return saves.get(key);
	}
	
	public CommentTreeElement getFavourite(String key)
	{
		return favourites.get(key);
	}
	
	public void save() throws IOException
	{
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(HashMap.class, new MapOfInterfacesSerializer()).create();
		
		FileWriter fw = new FileWriter(filepath);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(gson.toJson(saves, HashMap.class));
		Log.w("DataSaving", "JSON result: " + gson.toJson(saves, HashMap.class));
		bw.newLine();
		bw.write(gson.toJson(favourites, HashMap.class));
		Log.w("DataSaving", "JSON result: " + gson.toJson(favourites, HashMap.class));
		bw.newLine();
		bw.write(gson.toJson(usernames));
		Log.w("DataSaving", "JSON result: " + gson.toJson(usernames));
		bw.newLine();
		bw.write(gson.toJson(cachedTasks));
		Log.w("DataSaving", "JSON result: " + gson.toJson(cachedTasks));
		bw.newLine();
		bw.close();
		fw.close();
	}
	
	public void load() throws IOException
	{
		File f = new File(filepath);
		if(!f.exists() || f.isDirectory())return;
		
		Gson gson = new GsonBuilder().registerTypeAdapter(HashMap.class, new MapOfInterfacesSerializer()).create();
		
		FileReader fr = new FileReader(filepath);
		BufferedReader br = new BufferedReader(fr);
		
		String json = br.readLine();
		Log.w("DMLoad", "Saves: " + json);
		saves = gson.fromJson(json, HashMap.class);
		
		json = br.readLine();
		Log.w("DMLoad", "Favourites: " + json);
		favourites = gson.fromJson(json, HashMap.class);

		json = br.readLine();
		Log.w("DMLoad", "Usernames: " + json);
		Type arrayListStringType = new TypeToken<ArrayList<String>>(){}.getType();
		usernames = gson.fromJson(json,  arrayListStringType);
		
		json = br.readLine();
		Log.w("DMLoad", "CachedTasks: " + json);
		Type arrayListTaskType = new TypeToken<ArrayList<Task>>(){}.getType();
		cachedTasks = gson.fromJson(json, arrayListTaskType);
		
		br.close();
		fr.close();
	}
}
