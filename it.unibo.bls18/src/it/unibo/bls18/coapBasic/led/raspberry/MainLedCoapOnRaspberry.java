package it.unibo.bls18.coapBasic.led.raspberry;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import it.unibo.bls.applLogic.BlsApplicationLogic;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.LedObservableModel;
import it.unibo.bls18.coapBasic.AsynchListener;
import it.unibo.bls18.coapBasic.led.LedCoapResource;
 
/*
 * Create a CoaP server and a CoaP client
 * The server refers a LedCoapResource linked to a LedGui
 * The client sends to the server requests to get/put values of the led
 */
public class MainLedCoapOnRaspberry {
private CoapServer server;
private CoapClient coapClient;
private AsynchListener asynchListener = new AsynchListener();

private ILedObservable ledmodel;
private IObserver ledonrasp;
private IObserver applLogic;
 
	public MainLedCoapOnRaspberry(String hostName, int port, String resourceName) {
		createServer(port);
		createClient(hostName, port,resourceName);
		configure(port, resourceName);
	}

	protected void configure(int port, String resourceName) {
 		createResourceModel(  ) ;
 		createCoapEvelopeAroundResource( resourceName );
 		createConcreteResource();
 		addConcreteResourceToResourceModel();
		createApplicationLogic(  );
   	}
 
	protected  void createServer(int port) {	//port=5683 default
		server   = new CoapServer(port);
		server.start();
		System.out.println("MainCoapBasicLed Server started");
	}
 
	public void createClient(String hostName, int port, String resourceName) {
		coapClient=  new CoapClient("coap://"+hostName+":"+port+"/"+resourceName);
		System.out.println("MainCoapBasicLed Client started");
 	}
	
	protected void createResourceModel(  ) {
  		ledmodel = LedObservableModel.createLed(  );
 	}
	protected void createApplicationLogic(  ) {
    	applLogic = new BlsApplicationLogic(ledmodel);
 	}
	protected void createCoapEvelopeAroundResource( String resourceName ) {
		//Create a LedCoapResource that makes reference to the LedObservableModel
		LedCoapResource ledResource = new LedCoapResource(resourceName,ledmodel) ;
		server.add( ledResource );
 	}
	protected void createConcreteResource( ) {
		System.out.println("%%% MainCoapBasicLed createConcreteResource TODO "  );	
		ledonrasp  = LedMockOnRasp.createLed();		
	}
	protected void addConcreteResourceToResourceModel() {
		ledmodel.addObserver(ledonrasp);
	}
 
	protected void synchGet() {
		//Synchronously send the GET message (blocking call)
		CoapResponse coapResp = coapClient.get();
		//The "CoapResponse" message contains the response. 
 //		System.out.println(Utils.prettyPrint(coapResp));
		System.out.println("%%% MainCoapBasicLed ANSWER get " + coapResp.getResponseText());		
	}

	protected void asynchGet() {
 		coapClient.get( asynchListener );
	}
	
	public void put(String v) {
		CoapResponse coapResp = coapClient.put(v, MediaTypeRegistry.TEXT_PLAIN);
		//The "CoapResponse" message contains the response.
 		//System.out.println(Utils.prettyPrint(coapResp));
		System.out.println("%%% MainCoapBasicLed ANSWER put " + coapResp.getResponseText());		
	}
	
/*
 *  	
 */
	public static void main(String[] args) throws Exception {
		String hostName     ="localhost";
 		String resourceName ="Led";
 		int port            = 5683; //8010;
		MainLedCoapOnRaspberry appl = new MainLedCoapOnRaspberry(hostName, port, resourceName);
 		appl.synchGet();
 		for( int i=0; i<3; i++ ) {
			Thread.sleep(500);
			appl.put("true");
			Thread.sleep(1000);
			appl.put("false");
 		}
		System.out.println("LAST VALUE:");
		appl.asynchGet();
	}	
}
