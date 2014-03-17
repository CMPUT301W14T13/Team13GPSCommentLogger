package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    Context context;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
 
    public Preferences(Context context) {
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
    }
 
    /**
    * Converts the provided ArrayList<String>
    * into a JSONArray and saves it as a single
    * string in the apps shared preferences
    * @param array ArrayList<String> containing the list items
    */
    public void saveArray(ArrayList<String> array) {
        JSONArray jArray = new JSONArray(array);
        editor.remove("buttonArray");
        editor.putString("buttonArray", jArray.toString());
        editor.commit();
    }
 
    /**
    * Loads a JSONArray from shared preferences
    * and converts it to an ArrayList<String>
    * @return ArrayList<String> containing the saved values from the JSONArray
    */
    public ArrayList<String> getArray() {
        ArrayList<String> array = new ArrayList<String>();
        String jArrayString = prefs.getString("buttonArray", "cr5315AI");
        if (jArrayString.matches("cr5315AI")) return getDefaultArray();
        else {
            try {
                JSONArray jArray = new JSONArray(jArrayString);
                for (int i = 0; i < jArray.length(); i++) {
                    array.add(jArray.getString(i));
                }
                return array;
            } catch (JSONException e) {
                return getDefaultArray();
            }
        }
    }
 // Get the default list if there isn't anything saved
    private ArrayList<String> getDefaultArray() {
        ArrayList<String> array = new ArrayList<String>();
 
        return array;
    }
    
}
