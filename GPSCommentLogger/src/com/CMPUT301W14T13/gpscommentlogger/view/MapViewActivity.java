package com.CMPUT301W14T13.gpscommentlogger.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.CMPUT301W14T13.gpscommentlogger.CustomAdapter;
import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.controller.ClientController;
import com.CMPUT301W14T13.gpscommentlogger.controller.ClientServerSystem;
import com.CMPUT301W14T13.gpscommentlogger.model.ClientTask;
import com.CMPUT301W14T13.gpscommentlogger.model.ClientTaskSourceCode;
import com.CMPUT301W14T13.gpscommentlogger.model.ClientTaskTaskCode;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapViewActivity extends android.support.v4.app.FragmentActivity implements OnMapClickListener {

	private GoogleMap mMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_view);
		
		mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.setOnMapClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onMapClick(LatLng position) {
		mMap.addMarker(new MarkerOptions().position(position).icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
		
	}
}
