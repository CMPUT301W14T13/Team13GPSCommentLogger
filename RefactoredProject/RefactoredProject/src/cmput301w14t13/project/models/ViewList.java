package cmput301w14t13.project.models;

/**
 * Taken from FillerCreepForAndroid: 
 * https://github.com/abramhindle/FillerCreepForAndroid/blob/master/src/es/softwareprocess/fillercreep/FModel.java
 * 
 * @author Abram Hindle
 *
 * @param <M>
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

    public void addView(V view) {
        if (!views.contains(view)) {
            views.add(view);
        }
    }

    public void deleteView(V view) {
        views.remove(view);
    }

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
