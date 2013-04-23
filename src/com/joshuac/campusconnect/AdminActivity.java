package com.joshuac.campusconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminActivity extends Activity {

	ImageView addButton;
	ImageView upcomingButton;
	ImageView currentButton;
	ImageView attendButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		
		//find buttons for future use
		addButton = (ImageView) findViewById(R.id.addButton);
		upcomingButton = (ImageView) findViewById(R.id.upcomingButton);
		currentButton = (ImageView) findViewById(R.id.currentButton);
		attendButton = (ImageView) findViewById(R.id.attendButton);
		
	}//end onCreate
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_admin, menu);
		return true;
	}
	
	
	/*
	 * 
	 * ImageView Click Events
	 * 
	 */
	
	//called when "upcoming" button is pressed
	public void viewUpcomingEvents(View v)
	{
		Intent intent = new Intent(getBaseContext(), MapActivity.class);
		intent.putExtra("admin", false);
		startActivity(intent);
	}//end viewUpcomingEvents
	
	
	//called when "add event" button is pressed
	public void addEvent(View v)
	{
		Intent intent = new Intent(getBaseContext(), AddEventActivity.class);
  	  	startActivity(intent);
	}//end addEvent
	
	
	//called when "current" button is pressed
	public void viewCurrentEvents(View v)
	{
  	  Intent intent = new Intent(getBaseContext(), EventsInAreaActivity.class);
  	  startActivity(intent);
	}//end viewCurrentEvents
	
	
	//called when "students" button is pressed
	public void viewPoints(View v)
	{
  	  Intent intent = new Intent(getBaseContext(), AdminPtsActivity.class);
  	  startActivity(intent);
	}//end viewPoints
	

}
