package com.CMPUT301W14T13.gpscommentlogger.controller;

import java.io.BufferedReader;
import com.CMPUT301W14T13.gpscommentlogger.model.InterfaceSerializer;
import com.CMPUT301W14T13.gpscommentlogger.model.MapOfInterfacesSerializer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import android.util.Log;

import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class DataManager {
	private HashMap<String, Viewable> saves;
	private HashMap<String, Viewable> favourites;
	private String filepath;

	
	public DataManager(String filepath)
	{
		saves = new HashMap<String, Viewable>();
		favourites = new HashMap<String, Viewable>();
		this.filepath = filepath;

		try {
			load();
		} catch (IOException e) {
			Log.e("DataManager", "Load Error");
			e.printStackTrace();
		}

	}
	
	public void saveData(Viewable data)
	{
		this.saves.put(data.getID(), data);
		try {
			save();
		} catch (IOException e) {
			Log.e("DataManager", "Save Error");
			e.printStackTrace();
		}
	}
	
	public void saveFavourite(Viewable data)
	{
		this.favourites.put(data.getID(), data);
		try {
			save();
		} catch (IOException e) {
			Log.e("DataManager", "Save Error");
			e.printStackTrace();
		}
	}
	
	public Viewable getData(String key)
	{
		return saves.get(key);
	}
	
	public Viewable getFavourite(String key)
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

		br.close();
		fr.close();
	}
}
