package com.joshuac.campusconnect;

import java.io.Serializable;

public class Login implements Serializable {
	private static final long serialVersionUID = 1L;
	public String name, pass;
	
	public Login(String name, String pass){
		this.name=name;
		this.pass=pass;
	}
}
