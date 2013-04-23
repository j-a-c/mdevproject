package com.joshuac.campusconnect;
import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.joshuac.campusconnect.R;


public class AttendArrayAdapter extends ArrayAdapter<Attendance>{
	 private final Context context;
	  private final List<Attendance> attendList;  

	  public AttendArrayAdapter(Context context, List<Attendance> attendList) {
	    super(context, R.layout.attendobj, attendList );
	    this.context = context;
	    this.attendList = attendList;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		View attendView = convertView; 
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		attendView = inflater.inflate(R.layout.attendobj, parent, false);
		Attendance attend = attendList.get(position);
	    TextView textView = (TextView) attendView.findViewById(R.id.text);
	    textView.setText(attend.uName + " : " + attend.pts);
	    textView.setTextSize(20);
	    textView.setGravity(Gravity.CENTER);
	    attendView.setTag(attend);
	    return attendView;
	  }

}

