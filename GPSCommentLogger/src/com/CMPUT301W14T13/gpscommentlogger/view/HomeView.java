package com.CMPUT301W14T13.gpscommentlogger.view;

import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.R.layout;
import com.CMPUT301W14T13.gpscommentlogger.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.util.Collection;


public class HomeView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	/** 
	 * @uml.property name="c"
	 * @uml.associationEnd aggregation="composite" inverse="h:com.CMPUT301W14T13.gpscommentlogger.view.CommentMakingView"
	 */
	private CommentMakingView c;


	/** 
	 * Getter of the property <tt>c</tt>
	 * @return  Returns the c.
	 * @uml.property  name="c"
	 */
	public CommentMakingView getC()
	
	
	{
		return c;
	}


	/** 
	 * Setter of the property <tt>c</tt>
	 * @param c  The c to set.
	 * @uml.property  name="c"
	 */
	public void setC(CommentMakingView c)
	
	
	{
		this.c = c;
	}


	/**
	 * @uml.property  name="cc"
	 * @uml.associationEnd  multiplicity="(0 -1)" aggregation="composite" inverse="h:com.CMPUT301W14T13.gpscommentlogger.view.CommentView"
	 */
	private Collection<CommentView> cc;


	/**
	 * Getter of the property <tt>cc</tt>
	 * @return  Returns the cc.
	 * @uml.property  name="cc"
	 */
	public Collection<CommentView> getCc()
	{

		return cc;
	}


	/**
	 * Setter of the property <tt>cc</tt>
	 * @param cc  The cc to set.
	 * @uml.property  name="cc"
	 */
	public void setCc(Collection<CommentView> cc)
	{

		this.cc = cc;
	}


	 
}
