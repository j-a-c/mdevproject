package com.joshuac.campusconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity
{
	String s;
	public Login login;
	public int logResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}//end onCreate

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}//end onCreateOptionsMenu
	
	
	//called when login button is pressed
	public void login(View v)
	{
		EditText usernameText = (EditText) findViewById(R.id.usernameText);
		EditText passwordText = (EditText) findViewById(R.id.passwordText);
		
		String username = usernameText.getText().toString();
		String password = passwordText.getText().toString();
		
		login = new Login(username,password);
		
//		Intent intent;
//		//set up intent - just used for testing
//		if(username.equals("admin"))
//		{
//			intent = new Intent(getBaseContext(), AdminActivity.class);
//		}
//		else if(username.equals("student"))
//		{
//			intent = new Intent(getBaseContext(), StudentActivity.class);
//		}
//		else
//			intent = new Intent(getBaseContext(), AdminActivity.class);
//		
//		//put data into intent
//		intent.putExtra("username", username);
//		startActivity(intent);
		
		 	Thread client = new Thread(new ClientThread(this));
		    client.start();
		    try{
		    	client.join();
		    	checkAdmin();
	        }
		    catch (Exception e) {
	    		Toast.makeText(getApplicationContext(), "Sorry, Could not connect to Server", Toast.LENGTH_LONG).show();
		    	//finish();
	        	
	    	}
		
	}//end login
	
	void checkAdmin(){
		Intent intent;
		//logResult = 0;
		if(logResult == 1){
			intent = new Intent(getBaseContext(), AdminActivity.class);
			startActivity(intent);
		}
		else if(logResult == 0){
			intent = new Intent(getBaseContext(), StudentActivity.class);
			startActivity(intent);
		}
		else{
    		Toast.makeText(getApplicationContext(), "Username/Password invalid", Toast.LENGTH_LONG).show();

		}
	}

}//end LoginActivity
