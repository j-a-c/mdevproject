package com.joshuac.campusconnect;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import oracle.jdbc.rowset.OracleCachedRowSet;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


public class MapActivity extends FragmentActivity implements LocationListener, ClientThread.newEventReceiver
{
	//map instance
	private GoogleMap mMap;
	//location manager
	private LocationManager locationManager;
	
	//is user viewing map an admin?
	private boolean isAdmin = false;
	//list of location providers
	private List<String> enabledProviders;
	
	//current location
	private double currentLat = 0;
	private double currentLong = 0;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//set view
		setContentView(R.layout.activity_map);
		
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		//get bundle and set admin status
		Intent intentInfo = getIntent();
		if (intentInfo != null)
			isAdmin = intentInfo.getBooleanExtra("admin", false);
		
		setUpMapIfNeeded();
		
	}//end onCreate
	
	
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
	
	
	//ensure that map is ready and set up only once
	private void setUpMapIfNeeded() 
	{
        //do a null check to confirm that we have not already instantiated the map
        if (mMap == null) 
        {
            //try to obtain the map from the SupportMapFragment
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            //check if we were successful in obtaining the map
            if (mMap != null) 
            {
                setUpMap();
            }
        }
    }//end setUpMapIfNeeded
	
	
	//set up objects on map
	private void setUpMap() 
	{
		mMap.setMyLocationEnabled(true);
		if(isAdmin)
		{
			//set map listeners
		}
		else
		{
			//load events in area
			getMapEvents();
		}
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
	}//end setUpMap

	
	/*
	 * 
	 * LocationListener Interface
	 * 
	 */
	
	
	@Override
	public void onLocationChanged(Location location) 
	{	
		//update camera with coords and zoom
		CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),
				location.getLongitude()));
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
		//update map
		mMap.moveCamera(center);
		mMap.animateCamera(zoom);
		
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
	
	
	public void getMapEvents(){
		Thread client = new Thread(new ClientThread(this));
	    client.start();
	    try{
	    	client.join();
        } 
	    catch (Exception e) { 
    	}
	}
	
	//ClientThread.newEventReceiver interface
	public void getNewEvents(OracleCachedRowSet cset){
		String str=null;
		try{
			str=cset.getString(1);
		}
		catch(Exception e){
			
		}
		System.out.println(str);
	}
}
