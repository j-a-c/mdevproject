package com.joshuac.campusconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StudentActivity extends Activity
{

	String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student);
		
		Intent loginInfo = getIntent();
		if (loginInfo != null)
			username = (String) loginInfo.getSerializableExtra("username");
		System.out.println(username);

		
	}//end onCreate
	
	
	
	//called when "view upcoming events" button is pressed
	public void viewUpcomingEvents(View v)
	{
		System.out.println("up");
	}
	
	//called when "current events in area" button is pressed
	public void viewEventsInArea(View v)
	{
		System.out.println("area");
	}
	
	//called when "checkin" button is pressed
	public void checkinToEvent(View v)
	{
		System.out.println("in");
	}
	
	//called when "friends" button is pressed
	public void viewFriendActivity(View v)
	{
		System.out.println("friend");
	}
	
	//called when "setting" button is pressed
	public void viewSettings(View v)
	{
		System.out.println("settings");
	}
	
}//end StudentActivity