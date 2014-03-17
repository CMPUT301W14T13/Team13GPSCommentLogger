package com.CMPUT301W14T13.gpscommentlogger.model;

/**
 * Taken from FillerCreepForAndroid: https://github.com/abramhindle/FillerCreepForAndroid/blob/master/src/es/softwareprocess/fillercreep/FModel.java
 * 
 * @author Abram Hindle
 *
 * @param <M>
 */
import java.util.ArrayList;

import com.CMPUT301W14T13.gpscommentlogger.view.FView;

public class FModel<V extends FView> {
    private ArrayList<V> views;

    public FModel() {
        views = new ArrayList<V>();
    }

    public void addView(V view) {
        if (!views.contains(view)) {
            views.add(view);
        }
    }

    public void deleteView(V view) {
        views.remove(view);
    }

    public void notifyViews() {
        for (V view : views) {
            view.update(this);
        }
    }
}
