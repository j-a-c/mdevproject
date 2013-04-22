package com.joshuac.campusconnect;

import java.io.Serializable;

public class Attendance implements Serializable {
	private static final long serialVersionUID = 1L;
	public String eName,uName;
	public int pts;
	
	public Attendance(String eName, String uName, int pts){
		this.eName=eName;
		this.uName=uName;
		this.pts=pts;
	}
}
