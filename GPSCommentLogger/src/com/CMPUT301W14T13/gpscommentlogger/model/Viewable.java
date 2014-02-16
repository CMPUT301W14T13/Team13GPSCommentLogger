package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.ArrayList;
import java.util.Collection;

public interface Viewable {

	String getID();
	String getUsername();

	ArrayList<Viewable> getC();
	String getTitle();

	void setTitle(String newTitle);
}
