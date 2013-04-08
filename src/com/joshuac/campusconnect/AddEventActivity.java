package com.joshuac.campusconnect;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddEventActivity extends Activity {
	Button dateButton;
	Button timeButton;
	Button locationButton;
	Button submitButton;
	
	//Time Variables
	private TextView curtimeText;
	private int hour;
	private int minute;
	private String am_pm;
	
	//Date Variables
	private TextView curDateText;
	private TextView weekDayText;
	private int curYear;
	private int curMonth;
	private int curDay;
	private int year;
	private int month;
	private int day;
	private String days[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	int dayOfWeek;
	final Calendar c = Calendar.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		dateButton = (Button) findViewById(R.id.curdateButton);
		timeButton = (Button) findViewById(R.id.curtimeButton);
		locationButton = (Button) findViewById(R.id.locationButton);
		submitButton = (Button) findViewById(R.id.submitButton);
		dateButton.setOnClickListener(buttonHandler);
		timeButton.setOnClickListener(buttonHandler);
		locationButton.setOnClickListener(buttonHandler);
		submitButton.setOnClickListener(buttonHandler);
		

		curtimeText = (TextView) findViewById(R.id.curtimeText);
		hour = 12;
		minute = 0;
		am_pm = "PM";
		
		curDateText = (TextView) findViewById(R.id.curdateText);
		weekDayText = (TextView) findViewById(R.id.weekDayText);
		setCurrentDateOnView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_event, menu);
		
		return true;
	}
	
	View.OnClickListener buttonHandler = new View.OnClickListener() {
		  public void onClick(View v) {
		      if( dateButton.getId() == ((Button)v).getId() ){
		    	  	
		    	  		timeDialog(2);
		      }
		      else if( timeButton.getId() == ((Button)v).getId() ){
		    			 
						timeDialog(1);
		 
		      }
		      else if( locationButton.getId() == ((Button)v).getId() ){
		          // it was the second button
		      }
		      else if( submitButton.getId() == ((Button)v).getId() ){
		          // it was the second button
		    	  Intent intent = new Intent(getBaseContext(), AdminActivity.class);
		          startActivity(intent);
		      }
		  }
		};
		
		//Sets selected time on dialog
		private TimePickerDialog.OnTimeSetListener timePickerListener = 
	            new TimePickerDialog.OnTimeSetListener() {
			public void onTimeSet(TimePicker view, int selectedHour,
					int selectedMinute) {
				hour = selectedHour;
				minute = selectedMinute;
				
				 	am_pm = "";

				    Calendar datetime = Calendar.getInstance();
				    datetime.set(Calendar.HOUR_OF_DAY, hour);
				    datetime.set(Calendar.MINUTE, minute);

				    if (datetime.get(Calendar.AM_PM) == Calendar.AM){
				        am_pm = "AM";
				        if(hour == 0){
				    		hour = 12;
				    	}
				    }else if (datetime.get(Calendar.AM_PM) == Calendar.PM){
				        am_pm = "PM";
				    	if(hour != 12){
				    		hour = hour - 12;
				    	}
				    }
				// set current time into textview
				curtimeText.setText(new StringBuilder().append(pad(hour))
						.append(":").append(pad(minute)).append(" ").append(am_pm)); 
	 
			}
		};
		private DatePickerDialog.OnDateSetListener datePickerListener 
			= new DatePickerDialog.OnDateSetListener() {

			// when dialog box is closed, below method will be called.
			public void onDateSet(DatePicker view, int selectedYear,
					int selectedMonth, int selectedDay) {
					year = selectedYear;
					month = selectedMonth;
					day = selectedDay;
					
					Calendar cal = new GregorianCalendar(year, month, day);
					dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
					// set selected date into textview
					curDateText.setText(new StringBuilder().append(month + 1).append("/").append(day).append("/").append(year)
							.append(" "));
					weekDayText.setText(days[dayOfWeek-1]);

			}
		};
		//sets default date as current date
		public void setCurrentDateOnView() {
			 
			
			curYear = c.get(Calendar.YEAR);
			curMonth = c.get(Calendar.MONTH);
			curDay = c.get(Calendar.DAY_OF_MONTH);
			year = curYear;
			month = curMonth;
			day = curDay;
			Calendar cal = new GregorianCalendar(year, month, day);
			dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			// set current date into textview 
			// Month is 0 based, just add 1
			curDateText.setText(new StringBuilder().append(month + 1).append("/").append(day).append("/")
				.append(year).append(" "));
			weekDayText.setText(days[dayOfWeek-1]);

		}
		//Date and Time dialog funciton
		 public void timeDialog(final int id) {
			    this.runOnUiThread(new Runnable() {
			        @Override
			        public void run() {
			        	
			        	switch (id) {
			        	  case 1:
			        		 int tempHour = hour;
			        		 if(am_pm.equals("PM") && hour != 12){
			        			 tempHour = hour + 12;
			        		 }else if (am_pm.equals("AM") && hour == 12){
			        			 tempHour = 0;
			        		 }
			        		 TimePickerDialog time =  new TimePickerDialog(AddEventActivity.this, timePickerListener, tempHour, minute,false);
			        		 time.show();
			        		 break;
			        	  
			        	  case 2:
			        		  DatePickerDialog date = new DatePickerDialog(AddEventActivity.this, datePickerListener, year, month,day);
			        		  date.show();
			        	}
			        }
			    });
			    
		 }
	 
		//Formats Time
		private static String pad(int c) {
			if (c >= 10)
			   return String.valueOf(c);
			else
			   return "0" + String.valueOf(c);
		}
		

}
