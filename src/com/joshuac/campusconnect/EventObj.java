package com.joshuac.campusconnect;

import java.io.Serializable;

public class EventObj implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String eventName;
	String date,time;
	String location,eventType;
	double latitude,longitude;
	int pts;
	
	public EventObj (String eventName,String eventType,String date, String time,
			String location, double latitude, double longitude, int pts)
	{
		this.eventName = eventName;
		this.date = date;
		this.time = time;
		this.location = location;
		this.eventType = eventType;
		this.latitude = latitude;
		this.longitude = longitude;
		this.pts = pts;
	}
}
