package cmput301w14t13.project.models;

/**
 * Taken from FillerCreepForAndroid: https://github.com/abramhindle/FillerCreepForAndroid/blob/master/src/es/softwareprocess/fillercreep/FModel.java
 * 
 * @author Abram Hindle
 *
 * @param <M>
 */
import java.util.ArrayList;

import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;

public class ViewList<V extends UpdateInterface> {
    private ArrayList<V> views;

    public ViewList() {
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
            view.update();
        }
    }
}
