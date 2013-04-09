package com.joshuac.campusconnect;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends Activity {

	Button addButton;
	Button upcomingButton;
	Button currentButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		addButton = (Button) findViewById(R.id.addButton);
		upcomingButton = (Button) findViewById(R.id.upcomingButton);
		currentButton = (Button) findViewById(R.id.currentButton);
		addButton.setOnClickListener(buttonHandler);
		upcomingButton.setOnClickListener(buttonHandler);
		currentButton.setOnClickListener(buttonHandler);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_admin, menu);
		return true;
	}
	
	View.OnClickListener buttonHandler = new View.OnClickListener() {
		  public void onClick(View v) {
		      if( addButton.getId() == ((Button)v).getId() ){
		    	  Intent intent = new Intent(getBaseContext(), AddEventActivity.class);
		          startActivity(intent);
		      }
		      else if( upcomingButton.getId() == ((Button)v).getId() ){
		    	  //set admin to false to prevent touch events
		    	  Intent intent = new Intent(getBaseContext(), MapActivity.class);
		    	  intent.putExtra("admin", false);
		    	  startActivity(intent);
		      }
		      else if( currentButton.getId() == ((Button)v).getId() ){
		          // it was the second button
		      }
		  }
	};


}
