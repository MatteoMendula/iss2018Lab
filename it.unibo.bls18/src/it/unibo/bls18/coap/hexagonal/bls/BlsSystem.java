package it.unibo.bls18.coap.hexagonal.bls;

import org.eclipse.californium.core.CoapServer;

import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.coap.hexagonal.CommonBlsHexagNames;
import it.unibo.bls18.coap.hexagonal.button.ButtonAsGuiRestful;
import it.unibo.bls18.coap.hexagonal.button.ButtonResource;
import it.unibo.bls18.coap.hexagonal.button.ButtonResourceGofObserver;
import it.unibo.bls18.coap.hexagonal.led.LedResource;
import it.unibo.bls18.coap.hexagonal.led.LedResourceGofObserver;
 

public class BlsSystem {
	private CoapServer server;
	private ButtonResource buttonResource;
	
	public BlsSystem() {
		createServer();
		configure();
	}
	
	protected  void createServer( ) {	//port=5683 default
		server   = new CoapServer( CommonBlsHexagNames.port );
		server.start();
		System.out.println("MainCoapBasicLed Server started");
	}
	
	protected void configure( ) {
 		createInputDevice();
		createResourceModel(  ) ;
 		createApplLogic(  );
   	}
	
	protected void createResourceModel(  ) {
		LedResource ledResource = new LedResource( ) ;
		ledResource.setObserver( new LedResourceGofObserver() );  //add a model viewer
		System.out.println("CREATE ledResource ");
		buttonResource = new ButtonResource();
		System.out.println("CREATE buttonResource ");
		server.add( ledResource );
 		server.add( buttonResource );
		System.out.println("createResourceModel DONE ");
	}

	protected void createApplLogic(  ) {
 		buttonResource.setObserver( new BlsHexagApplLogic() );
 	}

	protected void createInputDevice() {
		ButtonAsGuiRestful.createButton( UtilsBls.initFrame(200,200), "press");
	}
/*
 * 	
 */
	public static void main(String[] args) throws Exception {
		new BlsSystem();
	}

}
