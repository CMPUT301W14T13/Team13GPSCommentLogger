package com.CMPUT301W14T13.gpscommentlogger;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.model.Topic;


/*
* This is the custom adapter for the various listviews throughout the app
*
*/
public class CustomAdapter extends BaseAdapter {


Context context;
ArrayList<Topic> data = new ArrayList<Topic>();
boolean mainActivity;
    private static LayoutInflater inflater = null;

    public CustomAdapter(Context context, ArrayList<Topic> data) {
       
        this.context = context;
        this.data = data;
        
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
@Override
public int getCount() {

return data.size();
}

@Override
public Object getItem(int position) {

return data.get(position);
}

@Override
public long getItemId(int position) {

return position;
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.root_comment_view, null);
        
        TextView text = (TextView) vi.findViewById(R.id.title);
        text.setText(String.valueOf(data.get(position).getTitle()));
        
        text = (TextView) vi.findViewById(R.id.username);
        text.setText("by " + String.valueOf(data.get(position).getUsername()));
        
        text = (TextView) vi.findViewById(R.id.coordinates);
        text.setText("coordinates");
        
        text = (TextView) vi.findViewById(R.id.age);
        text.setText(String.valueOf("age"));
        
        text = (TextView) vi.findViewById(R.id.number_of_comments);
        text.setText(String.valueOf("number of comments"));
        return vi;
}



}