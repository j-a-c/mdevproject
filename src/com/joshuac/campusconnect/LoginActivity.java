package com.joshuac.campusconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity
{

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
		
		Intent intent;
		//set up intent - just used for testing
		if(username.equals("admin"))
		{
			intent = new Intent(getBaseContext(), AdminActivity.class);
		}
		else if(username.equals("student"))
		{
			intent = new Intent(getBaseContext(), StudentActivity.class);
		}
		else
			intent = new Intent(getBaseContext(), AdminActivity.class);
		
		//put data into intent
		intent.putExtra("username", username);
		startActivity(intent);
	}//end login

}//end LoginActivity
