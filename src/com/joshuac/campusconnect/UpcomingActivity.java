package com.joshuac.campusconnect;


//import com.joshuac.beatmatrix.BeatButton;
//import com.joshuac.beatmatrix.R;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class UpcomingActivity extends Activity
{
	
	//is user viewing map an admin?
	private boolean isAdmin = false;
	
	//event list
	ArrayList<EventObj> events;
	
	//client thread
	private Thread client;
	//table layout for this view
	private TableLayout upcomingActivityTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upcoming);
		
		Intent intentInfo = getIntent();
		if (intentInfo != null)
			isAdmin = intentInfo.getBooleanExtra("admin", false);
		
		//find layout so we can dynamically add rows later
		upcomingActivityTable = (TableLayout) findViewById(R.id.upcomingActivityTable);
		
		//test events
		EventObj test = new EventObj("Movie","CISE","4/21/2013","1:00PM","2:00PM","Reitz",1,1,10);
	    EventObj test2 = new EventObj("Frisbee","Pedro","4/21/2013","5:00PM","7:00PM","Reitz",1,1,20);
	    ArrayList<EventObj> testEvents = new ArrayList<EventObj>();
	    testEvents.add(test);
	    testEvents.add(test2);
	    getNewEvents(testEvents);
	    
		
		//create thread to receive new events
		createClientThread();
		
	}//end onCreate
	
	@Override
	protected void onPause()
	{
		super.onPause();
		try{
		    	client.join();
        } 
	    catch (Exception e) { 
    	}
	}
	
	public void createClientThread(){
		client = new Thread(new ClientThread(this));
	    client.start();
	}
	
	//ClientThread.newEventReceiver interface
	public void getNewEvents(ArrayList<EventObj> events){
		try{
			//add all events to table
			for(EventObj event : events)
			{
				//init new row, layout params, and gravity
				TableRow trow = (TableRow) getLayoutInflater().inflate(R.layout.activity_upcoming_row, null);
				TableLayout.LayoutParams lp = new TableLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				trow.setGravity(Gravity.CENTER);
				
				//add contents to row
				TextView eventName = (TextView) trow.findViewById(R.id.eventName);
				eventName.setText(event.eventName);
				TextView location = (TextView) trow.findViewById(R.id.location);
				location.setText(event.location);
				TextView time = (TextView) trow.findViewById(R.id.time);
				time.setText(event.startTime);
				//add row to table
				upcomingActivityTable.addView(trow, lp);
			}
			//add all events to current list
			this.events.addAll(events);
		}
		catch(Exception e){
			
		}
	}
	
}
