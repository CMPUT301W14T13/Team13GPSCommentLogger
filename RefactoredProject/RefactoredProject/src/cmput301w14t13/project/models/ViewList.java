package cmput301w14t13.project.models;

/**
 * Taken from FillerCreepForAndroid: 
 * https://github.com/abramhindle/FillerCreepForAndroid/blob/master/src/es/softwareprocess/fillercreep/FModel.java
 * 
 * @author Abram Hindle
 *
 */
import java.util.ArrayList;

import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.views.HomeView;

public class ViewList<V extends UpdateInterface> {
    private ArrayList<V> views;

	private HomeView hv = null;

    public ViewList() {
        views = new ArrayList<V>();
    }
	
	public void registerUIThread(HomeView hv)
	{
		if(this.hv == null)
		{
			this.hv = hv;
		}
	}

	/**
	 * Adds a view so that it can be notified when the model changes
	 * 
	 * @param view  The view that was created such as HomeView or TopicView
	 */
    public void addView(V view) {
        if (!views.contains(view)) {
            views.add(view);
        }
    }

    /**
	 * Deletes a view so that it can be no longer notified when the model changes
	 * 
	 * @param view  The view that was destroyed such as HomeView or TopicView
	 */
    public void deleteView(V view) {
        views.remove(view);
    }

    /**
     * Called whenever there is a change in the model. This will call the update
     * methods of these views so that they may update themselves with the newest
     * data from the model
     */
    public void notifyViews() {
        for (final V view : views) {
            hv.runOnUiThread(new Runnable()
			{
				public void run()
				{
					view.update();
				}
			});
        }
    }
}
