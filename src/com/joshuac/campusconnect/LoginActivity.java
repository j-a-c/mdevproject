package com.joshuac.campusconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity
{
	String s;

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
		
		//set up intent
		Intent intent = new Intent(getBaseContext(), AdminActivity.class);
		intent.putExtra("username", usernameText.getText().toString());
		startActivity(intent);
	}

}//end LoginActivity
