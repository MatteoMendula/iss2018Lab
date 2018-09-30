package it.unibo.bls18.things;

import java.util.Hashtable;
import org.eclipse.californium.core.CoapServer;
import it.unibo.bls18.obj.LedResourceGofObserver;
import it.unibo.bls18.resources.LedResource;
 
public class LedThing {
	private static Hashtable<Integer, LedThing> things = new Hashtable<Integer,LedThing>();

	private	LedResource resource;
	private CoapServer server;
 
 
	public static void create(int port) {
		if( things.get(port) != null ) return;
		LedThing ledThing = new LedThing(port);
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

	public LedThing(int port) {
		try {
 			resource = new LedResource() ;
			server   = new CoapServer(port);
			server.add( resource );
			//Single thread executor
 			server.start();
			resource.setGofObserver(new LedResourceGofObserver());
		}catch(Exception e) {
			showMsg("ButtonThing ERROR " + e.getMessage()) ;
		}		
	}

	protected void showMsg(String msg) {
		System.out.println( msg ) ;
	}
	


}
