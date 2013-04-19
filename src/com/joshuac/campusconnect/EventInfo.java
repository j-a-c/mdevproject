package com.joshuac.campusconnect;

import com.google.android.gms.maps.model.LatLng;

public class EventInfo {
	LatLng markerLoc;
	String eventName;
	
	EventInfo (LatLng markerLoc, String eventName){
		this.markerLoc = markerLoc;
		this.eventName = eventName;
	}
	
	public LatLng getLatLong(){
		return this.markerLoc;
	}
	
	public String getName(){
		return this.eventName;
	}
	
}
