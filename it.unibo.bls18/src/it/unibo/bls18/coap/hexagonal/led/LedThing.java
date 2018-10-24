package it.unibo.bls18.coap.hexagonal.led;

import java.util.Hashtable;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.coap.hexagonal.CommonBlsHexagNames;


public class LedThing {
	private static Hashtable<Integer, LedThing> things = new Hashtable<Integer,LedThing>();

	private	LedResource resource;
	private CoapServer server;

 
	public static void createLedThing( int port ) {
		if( things.get(port) != null ) return;
		LedThing ledThing = new LedThing( port );
		things.put(port, ledThing);
		System.out.println("LedThing started  " );
	}

 
	public LedThing(int port ) {
		try {
 			resource = new LedResource( new LedAsGuiPluginObserver(UtilsBls.initFrame(200,200)) ) ;
			showMsg("LedThing 0 "  ) ;
			server   = new CoapServer(port);
			showMsg("LedThing 1 "  ) ;
			server.add( resource );
			//Single thread executor
			//server.add( ConcurrentCoapResource.createConcurrentCoapResource(1, resource   ) );
			server.start();
//			resource.setObserver( new LedAsGuiPluginObserver(UtilsBls.initFrame(200,200)) );
		}catch(Exception e) {
			showMsg("LedThing ERROR " + e.getMessage()) ;
		}		
	}

	protected void showMsg(String msg) {
		 System.out.println( msg ) ;
	}
	
/*
* Just for testing (with Firefox Copper)
*/
public static void main(String[] args) throws Exception {
 	createLedThing( CommonBlsHexagNames.port );
 	//Test by running a client
	CoapClient ledClient = new  CoapClient( CommonBlsHexagNames.BlsLedUriStr );
	ledClient.put("false", MediaTypeRegistry.TEXT_PLAIN);
 	ledClient.get();
 	System.out.println( "START" ) ;
	for( int i=0; i<3; i++) {
		UtilsBls.delay(500);
//		ledClient.put("true", MediaTypeRegistry.TEXT_PLAIN);
////	 	ledClient.get();
//		UtilsBls.delay(500);
//		ledClient.put("false", MediaTypeRegistry.TEXT_PLAIN);
////	 	ledClient.get();
	 	ledClient.put("switch", MediaTypeRegistry.TEXT_PLAIN);
	}
}

}
