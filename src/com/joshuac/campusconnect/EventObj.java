package com.joshuac.campusconnect;

import java.io.Serializable;

public class EventObj implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String eventName;
	String date,startTime,endTime;
	String location,eventType;
	double latitude,longitude;
	int pts;
	
	public EventObj (String eventName,String eventType,String date, String startTime, String endTime,
			String location, double latitude, double longitude, int pts)
	{
		this.eventName = eventName;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.location = location;
		this.eventType = eventType;
		this.latitude = latitude;
		this.longitude = longitude;
		this.pts = pts;
	}
}
