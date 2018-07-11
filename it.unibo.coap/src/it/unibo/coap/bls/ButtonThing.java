package it.unibo.coap.bls;

import java.util.Hashtable;
import org.eclipse.californium.core.CoapServer;
import it.unibo.qactors.akka.QActor;

/*
 * ButtonThing wraps into a CoAP server a ButtonResource 
 * associated with a ButtonResourceGofObserver 
 * that (in a QActor context) emits an event usercmd:usercmd(X)  X=true|false 
 */
public class ButtonThing {
private static Hashtable<Integer, ButtonThing> things = new Hashtable<Integer,ButtonThing>();

private	ButtonResource resource;
private CoapServer server;
private QActor qa;
private int port;
 
public static void create(int port, QActor qa) {
	if( things.get(port) != null ) return;
	ButtonThing buttonThing = new ButtonThing(port,qa);
	things.put(port, buttonThing);
	try {
		System.out.println("ButtonThing started with path="+ getPath(port) );
	} catch (Exception e) {
			e.printStackTrace();
	}
}

public static String getPath( int port ) throws Exception {
	if( things.get(port) == null ) throw new Exception("Resource not created");
	return "coap://localhost:"+ port + "/" + ButtonResource.resourcePath;
}
//===============================================================
public ButtonThing(int port, QActor qa) {
	try {
		this.port = port;
		this.qa   = qa;
		resource = new ButtonResource() ;
		resource.setObserver(new ButtonResourceGofObserver(qa));
		server   = new CoapServer(port);
		server.add( resource );
		server.start();
 	}catch(Exception e) {
		showMsg("ButtonThing ERROR " + e.getMessage()) ;
	}		
}

protected void showMsg(String msg) {
	if( qa != null ) qa.println( msg ) ;
	else System.out.println( msg ) ;
}

//===============================================================
/*
 * Just for testing (with Firefox Copper)
 */
	public static void main(String[] args) {
		create(8020, null);
	}
}
