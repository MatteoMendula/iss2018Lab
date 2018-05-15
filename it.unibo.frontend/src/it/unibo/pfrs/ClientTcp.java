package it.unibo.pfrs;
import java.io.DataOutputStream;
import java.net.Socket;
import org.json.JSONObject;
import it.unibo.is.interfaces.protocols.IConnInteraction;

public class ClientTcp   {
private String name="ClientTcp";
private String hostName= "localhost";
private int port = 8999;
private String sep=";";
protected IConnInteraction conn;
protected Socket clientSocket ;
protected DataOutputStream outToServer;

	protected void initClientConn() throws Exception {
		 clientSocket = new Socket(hostName, port);
		 outToServer  = new DataOutputStream(clientSocket.getOutputStream());
	}

	public void sendMsg(String msg) throws Exception {
		outToServer.writeBytes(msg);
		outToServer.flush();
	}

	protected void println(String msg) {
		System.out.println(name + ": " + msg);
	}
	
	public void doJob() throws Exception {
		initClientConn();
		String jsonString ="";
		JSONObject jsonObject;
		String msg="";
for( int i=1; i<=3; i++ ) {		
		jsonString = "{ 'type': 'moveForward', 'arg': 800 }";
		jsonObject = new JSONObject(jsonString);
		msg = sep+jsonObject.toString()+sep;
		println("sending msg=" + msg);
		sendMsg( msg  );
		Thread.sleep(1000);
		
			jsonString = "{ 'type': 'moveBackward', 'arg': 800 }";
 			jsonObject = new JSONObject(jsonString);
 			msg = sep+jsonObject.toString()+sep;
 			println("sending msg=" + msg);
			sendMsg( msg  );			 
			Thread.sleep(1000); 		
}			 
			clientSocket.close();
	}
	
public static void main(String[] args) throws Exception {
  		new ClientTcp().doJob();
}

}
