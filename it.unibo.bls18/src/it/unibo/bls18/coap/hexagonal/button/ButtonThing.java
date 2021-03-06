package it.unibo.bls18.coap.hexagonal.button;

import java.util.Hashtable;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.coap.hexagonal.CommonBlsHexagNames;


/*
 * 
 */
public class ButtonThing {
private static Hashtable<Integer, ButtonThing> things = new Hashtable<Integer,ButtonThing>();

private	ButtonResource resource;
private CoapServer server;
 
 
public static void create( int port ) {
	if( things.get(port) != null ) return;
	ButtonThing buttonThing = new ButtonThing(port);
	things.put(port, buttonThing);
}

 
//===============================================================
public ButtonThing(int port ) {
	try {
  		resource = new ButtonResource() ;
		resource.setObserver( new ButtonResourceGofObserver() );
		server   = new CoapServer(port);
		server.add( resource );
		server.start();
 	}catch(Exception e) {
		showMsg("ButtonThing ERROR " + e.getMessage()) ;
	}		
}

protected void showMsg(String msg) {
	 System.out.println( msg ) ;
}

//===============================================================
/*
 * Just for testing (with Firefox Copper)
 */
	public static void main(String[] args) throws Exception {
		create(CommonBlsHexagNames.buttonPort);
 		CoapClient buttonClient  = new  CoapClient( CommonBlsHexagNames.ButtonUriStr );
		CoapResponse resp;
 		for( int i=1; i<=3; i++ ) {
 			UtilsBls.delay(2000);
			resp = buttonClient.put("pressed", MediaTypeRegistry.TEXT_PLAIN);
			System.out.println("RESPONSE buttonPut : " + resp.getResponseText() );
			resp = buttonClient.get( );
			System.out.println("RESPONSE buttonGet after PUT: " + resp.getResponseText() );
			UtilsBls.delay(3500);
			resp = buttonClient.get( );
			System.out.println("RESPONSE buttonGet after DELAY: " + resp.getResponseText() );
 		}

	}
}
