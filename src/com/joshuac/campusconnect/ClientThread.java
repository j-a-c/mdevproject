package com.joshuac.campusconnect;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import oracle.jdbc.rowset.OracleCachedRowSet;
import android.app.Activity;
import android.content.Context;

class ClientThread extends Thread {

	Socket socket=null;
	Context context=null;
	String SERVER_IP=null;
	ObjectOutputStream out=null;
	ObjectInputStream in=null;
	
	//Activities
	UpcomingActivity upAct;
	StudentActivity stAct;
	LoginActivity lAct;
	EventsInAreaActivity eAct;
	AdminPtsActivity adminAct;
	AddEventActivity addEAct;
	
	
	int option;
	

	public ClientThread(UpcomingActivity act){
		this.upAct = act;
		this.option=2;
	}
	public ClientThread(EventsInAreaActivity act){
		this.eAct = act;
		this.option=3;
	}
	public ClientThread(AdminPtsActivity act){
		this.adminAct = act;
		this.option=5;
	}
	public ClientThread(AddEventActivity act){
		this.addEAct = act;
		this.option=6;
	}
	public ClientThread(StudentActivity act){ //MapActivity mapActivity
		this.stAct = act;
		this.option=4;
	}
	public ClientThread(LoginActivity act){ //MapActivity mapActivity
		this.lAct = act;
		this.option=1;
	}

    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName("10.137.106.76");
            this.socket = new Socket(serverAddr, 9000);
            
            try {
            	in = new ObjectInputStream(socket.getInputStream());
            	
            	System.out.println("test sendData5");
            	out = new ObjectOutputStream(socket.getOutputStream());
            	out.writeObject(Integer.toString(option));
            	System.out.println("test sendData6");
            	out.flush();
            	
            	switch(option){
            	case 1:		//login activity
            		out.writeObject(lAct.login);
            		out.flush();
            		lAct.logResult=in.readInt();
            		break;
            	case 2:		//retrieve all upcoming events
            		upAct.events = (ArrayList<EventObj>)in.readObject();
            		break;
            	case 3:		//retrieve all events
            		eAct.allEvents = (ArrayList<EventObj>)in.readObject();
            		break;
            	case 4:		//send attendance data for one student
            		out.writeObject(stAct.attended);
            		break;
            	case 5:		//retrieve attendance data for all students
//            		adminAct.students = (ArrayList<Attendance>)in.readObject();
            		break;
            	case 6:		//send attendance data for one student
            		out.writeObject(addEAct.event);
            		break;
            	}
            }
            catch(Exception e){
            	System.out.println(e.toString());
            }

        } 
        catch (Exception e) {
            final String error = e.getLocalizedMessage();
            System.out.println(error);
        }
        finally{
            //Close connections
            try{
                    in.close();
                    out.close();
                    socket.close();
            }
            catch(Exception ioException){
                    ioException.printStackTrace();
            }
    }
    }
}