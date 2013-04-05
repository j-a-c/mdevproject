package com.joshuac.campusconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StudentActivity extends Activity
{

	String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student);
		
		Intent loginInfo = getIntent();
		if (loginInfo != null)
			username = (String) loginInfo.getSerializableExtra("username");
		System.out.println(username);

		
	}//end onCreate
}//end StudentActivity