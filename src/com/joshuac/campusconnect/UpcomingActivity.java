package com.joshuac.campusconnect;

import oracle.jdbc.rowset.OracleCachedRowSet;

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
			//init new row, layout params, and gravity
			TableRow trow = (TableRow) getLayoutInflater().inflate(R.layout.activity_upcoming_row, null);
			TableLayout.LayoutParams lp = new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			trow.setGravity(Gravity.CENTER);
			//add contents to row
			
			//add row to table
			upcomingActivityTable.addView(trow, lp);
			
		}
		catch(Exception e){
			
		}
	}
	
}
