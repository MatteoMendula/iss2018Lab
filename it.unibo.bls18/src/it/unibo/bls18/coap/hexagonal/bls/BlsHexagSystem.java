package it.unibo.bls18.coap.hexagonal.bls;

import org.eclipse.californium.core.CoapServer;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.coap.hexagonal.CommonBlsHexagNames;
import it.unibo.bls18.coap.hexagonal.button.ButtonAsGuiRestful;
import it.unibo.bls18.coap.hexagonal.button.ButtonResource;
import it.unibo.bls18.coap.hexagonal.led.LedAsGuiPluginObserver;
import it.unibo.bls18.coap.hexagonal.led.LedResource;

 

public class BlsHexagSystem {
	private CoapServer server;
	private ButtonResource buttonResource;
	
	public BlsHexagSystem() {
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
		ledResource.setObserver( new LedAsGuiPluginObserver( UtilsBls.initFrame(200,200) ) );  //add a model viewer
		System.out.println("CREATE ledResource ");
		buttonResource = new ButtonResource();
		System.out.println("CREATE buttonResource ");
		server.add( ledResource );
 		server.add( buttonResource );
		System.out.println("createResourceModel DONE ");
	}

	protected void createApplLogic(  ) {
 		buttonResource.setObserver( new BlsHexagApplLogicObserver() );
 	}

	protected void createInputDevice() {
		ButtonAsGuiRestful.createButton( UtilsBls.initFrame(200,200), "press");
	}
/*
 * 	
 */
	public static void main(String[] args) throws Exception {
		new BlsHexagSystem();
	}

}
