
package com.joshuac.campusconnect;


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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends FragmentActivity implements LocationListener,ClientThread.newEventReceiver
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
	
	//Marker location
	private double markerLat = 0;
	private double markerLong = 0;
	Button submitButton;

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
		
		//Admin can Set marker 
		if(isAdmin){
			submitButton = (Button) findViewById(R.id.submitButton);
			submitButton.setOnClickListener(buttonHandler);
			submitButton.setVisibility(View.GONE);
			// Setting a click event handler for the map
			mMap.setOnMapClickListener(new OnMapClickListener() {
 
				@Override
	            public void onMapClick(LatLng latLng) {
					submitButton.setVisibility(View.VISIBLE);
	                // Creating a marker
	                MarkerOptions markerOptions = new MarkerOptions();
	 
	                // Setting the position for the marker
	                markerOptions.position(latLng);
	                
	                markerLat = latLng.latitude;
	                markerLong = latLng.longitude;
	                // Setting the title for the marker.
	                // This will be displayed on taping the marker
	                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
	 
	                // Clears the previously touched position
	                mMap.clear();
	 
	                // Animating to the touched position
	                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
	 
	                // Placing a marker on the touched position
	                mMap.addMarker(markerOptions);
	            }
        });
		}
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
                getMapEvents();
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
	
	View.OnClickListener buttonHandler = new View.OnClickListener() {
	    public void onClick(View v) {
	    	Intent intent = new Intent(getBaseContext(), AddEventActivity.class);
	    	Bundle b = new Bundle();
	    	b.putDouble("lat", markerLat);
	    	b.putDouble("lng", markerLong);
	    	intent.putExtras(b);
	        startActivity(intent);
	    }
	  };
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

