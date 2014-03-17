package com.CMPUT301W14T13.gpscommentlogger;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


/*
 * This is the custom adapter for the various listviews throughout the app
 *
 */
public class UsernameAdapter extends BaseAdapter {


	private Context context;
	private ArrayList<String> data = new ArrayList<String>();
	private static LayoutInflater inflater = null;


	public UsernameAdapter(Context context, ArrayList<String> usernames) {

		this.context = context;
		this.data = usernames;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public int getCount() {

		return data.size();
	}

	@Override
	public String getItem(int position) {

		return data.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		Button deleteButton;
		
		if (vi == null){

			vi = inflater.inflate(R.layout.username_row, null);
			TextView text = (TextView) vi.findViewById(R.id.usernameTextView);
			text.setText(data.get(position));

		}

		deleteButton = (Button) vi.findViewById(R.id.deleteButton);
		deleteButton.setTag(position); //gives a unique tag for identifying comments
		
		
		return vi;
	}

	


}
