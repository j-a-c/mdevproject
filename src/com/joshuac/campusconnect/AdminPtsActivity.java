package com.joshuac.campusconnect;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AdminPtsActivity extends Activity {
	//Event ArrayList User side
		 Attendance attend = null;
		 public ListView listView;
		 public List<Attendance> attendList =  new ArrayList<Attendance>();
		 AttendArrayAdapter adapter;
		
		 //Event ArrayList from Server
		 public ArrayList<Attendance> students =null;
		/** Called when the activity is first created. */

		public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_admin_pts); 
			
			
			//Test case
		    Attendance test = new Attendance("Pedro","test",5);
		    Attendance test2 = new Attendance("Patrick","test",500);
		    //attendList.add(test);
		    //attendList.add(test2);
		    //Fill text view with clickable arraylist objects		    
		 	adapter = new AttendArrayAdapter(this,attendList );
		    listView = (ListView)findViewById(R.id.attendView);
		    listView.setAdapter(adapter);
		    Button backButton = (Button) findViewById(R.id.backButton);
			backButton.setOnClickListener(new OnClickListener() {

			    public void onClick(View v) {
			    	finish();
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
		 
		 //populate points list
		 void popEventList(){
			 if(students != null){
				 for(int i = 0; i < students.size(); i++){
					 attendList.add(students.get(i));
					 adapter.notifyDataSetChanged();
				 }
		 	}
		 }
		 
		 
		

}
