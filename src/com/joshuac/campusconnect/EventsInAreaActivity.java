package com.joshuac.campusconnect;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.FloatMath;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class EventsInAreaActivity extends Activity  {
	//Current Location Cordinates
	Double currLat = (double) -99;
	Double currLong = (double) -99;

	//Event ArrayList User side
	 EventObj event = null;
	 public ListView listView;
	 public List<EventObj> eventList =  new ArrayList<EventObj>();
	 EventArrayAdapter adapter;
	
	 //Event ArrayList from Server
	 public ArrayList<EventObj> allEvents =null;
	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_events_in_area);
	    //current location manager
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    LocationListener ll = new mylocationlistener();
	    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);    
		//Test case
	    EventObj test = new EventObj("Movie","CISE","4/21/2013","1:00PM","2:00PM","Reitz",1,1,10);
	    EventObj test2 = new EventObj("Frisbee","Pedro","4/21/2013","5:00PM","7:00PM","Reitz",1,1,20);
	    //eventList.add(test);
	    //eventList.add(test2);
	    //Fill text view with clickable arraylist objects		    
	 	adapter = new EventArrayAdapter(this,eventList );
	    listView = (ListView)findViewById(R.id.eventList);
	    listView.setAdapter(adapter);
	    
	    //Back button
	    Button backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(new OnClickListener() {

		    public void onClick(View v) {
		    	finish();
		    }
		 });
	    
	    //When event is clicked open dialog
	    listView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	            int position, long id) {
	             event = eventList.get(position);	
	        	 AlertDialog.Builder alert = new AlertDialog.Builder(EventsInAreaActivity.this);                 
	      	     alert.setTitle("Join this Event");  
	      	     alert.setMessage("Event: " + event.eventName+"\n" +
	      	    		 		  "Type: " + event.eventType+"\n" +
	      	    		 		  "Date: " + event.date +"\n" +
	      	    		 		  "Start: " + event.startTime + "\n" +
	      	    		 		  "End: " + event.endTime + "\n" +
	      	    		 		  "Location: " +event.location + "\n" +
	      	    		 		  "PTS: " + event.pts);                
		      	    
		      	     alert.setPositiveButton("Join", new DialogInterface.OnClickListener() {  
		      	    	 public void onClick(DialogInterface dialog, int whichButton) {  
		      	    		//Join event
		      	    		//start checking time
		      	    		
		      	    		return;
		      	        }  
		      	      });  
		      	    

		      	     alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		      	         public void onClick(DialogInterface dialog, int which) {
		      	             return;   
		      	         }
		      	     });
		      	             alert.show();  
		      	  }
		         
		        	  
		      });
	}

	@Override
	//FETCH events from server
	protected void onStart() {
		super.onStart();
		//option is based on servers need
		//int option = 1;
	    Thread client = new Thread(new ClientThread(this));
	    client.start();
	    try{
	    	client.join();
		    //populate active games list 
	    	popEventList();
        }
	    catch (Exception e) {
    		Toast.makeText(getApplicationContext(), "Sorry, Could not connect to Server", Toast.LENGTH_LONG).show();
	    	//finish();
        	
    	}
	   
	}
	
		
	 @Override
	    protected void onStop() {
	        super.onStop();
	        return;
	    } 	 
	 
	 //populate event list
	 void popEventList(){
		 for(int i = 0; i < allEvents.size(); i++){
			 //Check If event is within 1km
			 if(currLat != -99 && currLong != -99){
				 Double tempLat =allEvents.get(i).latitude;
				 Double tempLong =allEvents.get(i).longitude;
				 if(gps2m(currLat.floatValue(),currLong.floatValue(),tempLat.floatValue(),tempLong.floatValue()) < 1000){
					 eventList.add(allEvents.get(i));
					 adapter.notifyDataSetChanged();
				 }
			 }
		 }
	 }
	//GETS CURRENT LOCATION
	 private class mylocationlistener implements LocationListener {
		    @Override 
		    public void onLocationChanged(Location location) {    
		        if (location != null) {
			        String str = "\n CurrentLocation: "+
			            "\n Latitude: "+ location.getLatitude() + 
			            "\n Longitude: " + location.getLongitude() + 
			            "\n Accuracy: " + location.getAccuracy(); 
			        System.out.println(str);
			        
			        currLat = location.getLatitude();
			        currLong = location.getLongitude();
		          //.makeText(EventsInAreaActivity.this,str,Toast.LENGTH_LONG).show();             
		        } 
		    } 
		    @Override
		    public void onProviderDisabled(String provider) {
		    	Toast.makeText(getApplicationContext(), "Enable location services to continue", Toast.LENGTH_LONG).show();
				startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		    }    
		    @Override
		    public void onProviderEnabled(String provider) {
		       Toast.makeText(EventsInAreaActivity.this,"onProviderEnabled",Toast.LENGTH_LONG).show();
		    }
		    @Override
		    public void onStatusChanged(String provider, int status, Bundle extras) {
		    // Toast.makeText(EventsInAreaActivity.this,"onStatusChanged",Toast.LENGTH_LONG).show();
		    }
	   }
	 
	 //Calculates the distance between two gps points in meters
	 private double gps2m(float lat_a, float lng_a, float lat_b, float lng_b) {
		    float pk = (float) (180/3.14169);

		    float a1 = lat_a / pk;
		    float a2 = lng_a / pk;
		    float b1 = lat_b / pk;
		    float b2 = lng_b / pk;

		    float t1 = (float) (Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2));
		    float t2 = (float) (Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2));
		    float t3 = (float) (Math.sin(a1)*Math.sin(b1));
		    double tt = Math.acos(t1 + t2 + t3);
		   
		    return 6366000*tt;
		}
		// see http://androidsnippets.com/distance-between-two-gps-coordinates-in-meter
	
}