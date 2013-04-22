package com.joshuac.campusconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

public class SettingsActivity extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
	}
	
	
	//called when "Turn Off GPS" button is pressed
	public void turnOffGps(View v)
	{
		startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	}//end turnOffGPS

}
