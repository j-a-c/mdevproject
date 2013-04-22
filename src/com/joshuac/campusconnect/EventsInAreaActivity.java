package com.joshuac.campusconnect;

import java.util.ArrayList;
import java.util.List;


import oracle.jdbc.rowset.OracleCachedRowSet;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EventsInAreaActivity extends Activity  {


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
		//Test case
	    EventObj test = new EventObj("Movie","CISE","4/21/2013","1:00PM","2:00PM","Reitz",1,1,10);
	    EventObj test2 = new EventObj("Frisbee","Pedro","4/21/2013","5:00PM","7:00PM","Reitz",1,1,20);
	    eventList.add(test);
	    eventList.add(test2);
	    //Fill text view with clickable arraylist objects		    
	 	adapter = new EventArrayAdapter(this,eventList );
	    listView = (ListView)findViewById(R.id.eventList);
	    listView.setAdapter(adapter);
	    
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
			 eventList.add(allEvents.get(i));
			 adapter.notifyDataSetChanged();
		 }
	 }
	
	
	
}