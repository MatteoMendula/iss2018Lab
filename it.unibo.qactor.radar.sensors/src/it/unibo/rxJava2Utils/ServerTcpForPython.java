package it.unibo.rxJava2Utils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import it.unibo.contactEvent.interfaces.IActorMessage;
import it.unibo.is.interfaces.IMessage;
import it.unibo.qactors.QActorMessage;
 
public class ServerTcpForPython  extends Thread  {
protected int portNum = 8111;
protected ServerSocket serverSock;
protected Socket socket;
protected StepperStream stream;
 
/*
 * The server interacts with a client written in python
 */
	public ServerTcpForPython(StepperStream stream ){ 
 		this.stream = stream;
  	}
  	@Override
	public void run()   {		 
  		System.out.println("ServerTcpForPython runs");
  		try {
			serverSock = new ServerSocket( 8111 );
			socket = serverSock.accept();
			BufferedReader reader = new BufferedReader( new InputStreamReader(socket.getInputStream()) ); 
			doJob(reader);
		} catch ( Exception e) {
 			e.printStackTrace();
		}
  	}
 	 
	protected void doJob( BufferedReader reader )  {
		while( true ){
 		try{
 		 		String receivedMsg = reader.readLine( );
 	   	 		//System.out.println("	ServerTcpForPython RECEIVED " + receivedMsg);
 	 	 		if( receivedMsg.equals("null" )) break;
 	 	 		//receivedMsg = msg/5
 	 	 		IActorMessage msg = new QActorMessage( receivedMsg ) ;
   		 		stream.setItem(msg.msgContent());
  		}catch( Exception e ){
  			System.out.println("ServerTcpForPython	Exception "  + e.getMessage());
  			stream.setItem("0");
  			break;
		}
 		}//while 
	}
  
}
