package com.joshuac.campusconnect;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

public class CheckinActivity extends Activity implements LocationListener
{
	
	//location manager
	private LocationManager locationManager;
	//list of location providers
	private List<String> enabledProviders;
	//lat, long
	public double latitude, longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//set view
		setContentView(R.layout.activity_checkin);
		
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
	}
	
	protected void onResume()
	{
		super.onResume();

		//location criteria
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE); //set to fine later?
		criteria.setCostAllowed(false);
		
		enabledProviders = locationManager.getProviders(criteria, true);
		
		if (enabledProviders.isEmpty())
		{
			//no location providers are enabled
			//prompt user to choose providers
			Toast.makeText(getApplicationContext(), 
					"Enable location services to continue", Toast.LENGTH_LONG).show();
			startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		}
		else
		{
			//request a single location update
			for (String enabledProvider : enabledProviders)
				locationManager.requestSingleUpdate(enabledProvider,this,null);
		
		}
	}//end onResume
	
	
	@Override
	protected void onPause() 
	{
		super.onPause();
		locationManager.removeUpdates(this);
	}//end onPause
	
	
	@Override
	public void onLocationChanged(Location location) 
	{	
		//update location
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
		
	}//end onLocationChanged

	public void onChangeLocationProvidersSettingsClick(View view)
	{
		startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	}
	
	@Override
	public void onProviderDisabled(String provider) 
	{
		// TODO Auto-generated method stub	
	}

	@Override
	public void onProviderEnabled(String provider) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		// TODO Auto-generated method stub
	}


}
