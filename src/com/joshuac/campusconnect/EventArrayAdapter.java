package com.joshuac.campusconnect;

import java.util.List;
import android.content.Context;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventArrayAdapter extends ArrayAdapter<EventObj>{
	   private final Context context;
	  private final List<EventObj> eventList;  

	  public EventArrayAdapter(Context context, List<EventObj> eventList) {
	    super(context, R.layout.eventobj, eventList );
	    this.context = context;
	    this.eventList = eventList;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		View eventView = convertView; 
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		eventView = inflater.inflate(R.layout.eventobj, parent, false);
		EventObj event = eventList.get(position);
	    TextView textView = (TextView) eventView.findViewById(R.id.text);
	    textView.setText(event.eventName + " @ " + event.location + " : " + event.date);
	    textView.setTextSize(20);
	    textView.setGravity(Gravity.CENTER);
	    eventView.setTag(event);
	    return eventView;
	  }

}