package it.unibo.coap.bls;

import java.util.Hashtable;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapServer;
import it.unibo.qactors.akka.QActor;


public class LedThing {
	private static Hashtable<Integer, LedThing> things = new Hashtable<Integer,LedThing>();

	private	LedResource resource;
	private CoapServer server;
	private QActor qa;

 
	public static void create(int port, QActor qa) {
		if( things.get(port) != null ) return;
		LedThing ledThing = new LedThing(port,qa);
		things.put(port, ledThing);
		try {
			System.out.println("LedThing started with path="+ getPath(port) );
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}

	public static String getPath( int port ) throws Exception {
		if( things.get(port) == null ) throw new Exception("Resource not created");
		return "coap://localhost:"+ port + "/" + LedResource.resourcePath;
	} 

	public LedThing(int port, QActor qa) {
		try {
			this.qa  = qa;
			resource = new LedResource() ;
			server   = new CoapServer(port);
			server.add( resource );
			//Single thread executor
			//server.add( ConcurrentCoapResource.createConcurrentCoapResource(1, resource   ) );
			server.start();
			resource.setObserver(new LedResourceGofObserver(qa));
		}catch(Exception e) {
			showMsg("ButtonThing ERROR " + e.getMessage()) ;
		}		
	}

	protected void showMsg(String msg) {
		if( qa != null ) qa.println( msg ) ;
		else System.out.println( msg ) ;
	}
	
/*
* Just for testing (with Firefox Copper)
*/
public static void main(String[] args) throws Exception {
	create(8010, null);
	CoapClient ledClient     = //new  CoapClient( LedThing.getPath(8010)    );
			new  CoapClient("coap://localhost:8010/led" );
	ledClient.get();
}

}
