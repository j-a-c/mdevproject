package com.joshuac.campusconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	Button loginButton;
	Button helpButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		loginButton = (Button) findViewById(R.id.loginButton);
		helpButton = (Button) findViewById(R.id.helpButton);
		loginButton.setOnClickListener(buttonHandler);
		helpButton.setOnClickListener(buttonHandler);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	View.OnClickListener buttonHandler = new View.OnClickListener() {
		  public void onClick(View v) {
		      if( loginButton.getId() == ((Button)v).getId() ){
		    	  Intent intent = new Intent(getBaseContext(), LoginActivity.class);
		          startActivity(intent);
		      }
		      else if( helpButton.getId() == ((Button)v).getId() ){
		          // it was the second button
		      }
		  }
		};

}
