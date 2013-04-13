package com.joshuac.campusconnect;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import oracle.jdbc.rowset.OracleCachedRowSet;
import android.content.Context;

class ClientThread extends Thread {
	
	public interface newEventReceiver{
		public void getNewEvents(OracleCachedRowSet cset);
	}
	Socket socket=null;
	Context context=null;
	String SERVER_IP=null;
	OracleCachedRowSet cset=null;
	ObjectOutputStream out=null;
	MapActivity mapActivity=null;
	public ClientThread(Context context, String SERVER_IP){
		this.context=context;
		this.SERVER_IP=SERVER_IP;
	}
	public ClientThread(MapActivity mapActivity){
		this.mapActivity=mapActivity;
	}
    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName("10.136.52.0");
            this.socket = new Socket(serverAddr, 9000);
            
            try {
            	ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            	cset = (OracleCachedRowSet)in.readObject();
            	mapActivity.getNewEvents(cset);
//            	out = new ObjectOutputStream(socket.getOutputStream());
//            	out.writeObject(gs);
//            	System.out.println("test sendData6");
//            	out.flush();
            }
            catch(Exception e){
            	System.out.println(e.toString());
            }
//            try{
//            	FileOutputStream fos = context.openFileOutput("tcpaftertest.txt",0);
//                OutputStreamWriter osw = new OutputStreamWriter(fos);
//
//                osw.write("IP addr : "+gs.getUser1());
//                osw.flush();
//                osw.close();
//                fos.close();
//            } 
//            catch (Exception e) {
//                final String error = e.getLocalizedMessage();
//                Log.d("test2", "IP addr : " + error);
//            }

        } 
        catch (Exception e) {
            final String error = e.getLocalizedMessage();
            System.out.println(error);
        }
        finally{
            //Close connections
            try{
                    //in.close();
                    out.close();
                    socket.close();
            }
            catch(Exception ioException){
                    ioException.printStackTrace();
            }
    }
    }
}