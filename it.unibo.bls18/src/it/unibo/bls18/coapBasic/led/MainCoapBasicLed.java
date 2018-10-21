package it.unibo.bls18.coapBasic.led;

import java.awt.Frame;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import it.unibo.bls.applLogic.BlsApplicationLogic;
import it.unibo.bls.devices.gui.ButtonAsGui;
import it.unibo.bls.devices.gui.LedAsGui;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.LedObservableModel;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.coapBasic.AsynchListener;
 
/*
 * Create a CoaP server and a CoaP client
 * The server refers a LedCoapResource linked to a LedGui
 * The client sends to the server requests to get/put values of the led
 */
public class MainCoapBasicLed {
private CoapServer server;
private CoapClient coapClient;
private AsynchListener asynchListener = new AsynchListener();

private ILedObservable ledmodel;
private IObserver ledgui;
private IObserver applLogic;
private Frame blsFrame = UtilsBls.initFrame(200,200);
private String uriStr;
 
	public MainCoapBasicLed(String hostName, int port, String resourceName) {
		uriStr = "coap://"+hostName+":"+port+"/"+resourceName;
		createServer(port);
//		createCoapObserverClient( );
		configure(port, resourceName);
	}

	protected void configure(int port, String resourceName) {
 		createResourceModel(  ) ;
 		createCoapEvelopeAroundResource( resourceName );
 		createConcreteResource();
 		addConcreteResourceToResourceModel();
  	}

	protected void createResourceModel(  ) {
  		ledmodel = LedObservableModel.createLed(  );
 	}
//	protected void createApplicationLogic(  ) {
//    	applLogic = new BlsApplicationLogic(ledmodel);
// 	}

	protected  void createServer(int port) {	//port=5683 default
		server   = new CoapServer(port);
  		server.start();
		System.out.println("MainCoapBasicLed Server started");
	}
/*
	public void createCoapObserverClient( ) {
 		coapClient=  new CoapClient(uriStr);
  		createCoapObserverRelation(coapClient);
		System.out.println("MainCoapBasicLed Client started " + coapClient.getURI() );
 	}
	
	protected void createCoapObserverRelation(CoapClient coapClient) {
		CoapObserveRelation relation = coapClient.observe(
				new CoapHandler() {
					@Override public void onLoad(CoapResponse response) {
						String content = response.getResponseText();
						System.out.println("MainCoapBasicLed Client CoAp observes: " + content);
						
					}
					
					@Override public void onError() {
						System.err.println("MainCoapBasicLed Client: OBSERVING FAILED (press enter to exit)");
					}
				});
		//relation.proactiveCancel();			//TO DETACH
	}
*/	
	protected void createCoapEvelopeAroundResource( String resourceName ) {
		//Create a LedCoapResource that makes reference to the LedObservableModel
		LedCoapResource ledResource = new LedCoapResource(resourceName,ledmodel) ;
		server.add( ledResource );
 	}
	protected void createConcreteResource( ) {
		ledgui  = LedAsGui.createLed(blsFrame);		
	}
	protected void addConcreteResourceToResourceModel() {
		ledmodel.addObserver(ledgui);
	}
// 	protected void createButtonAsFrontEnd( ) {
//		ButtonAsGui.createButton( blsFrame, "press", applLogic);		
// 	}
 	
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
		System.out.println("%%% MainCoapBasicLed NEW LED STATE= " + coapResp.getResponseText());	
 		
	}

	
	
/*
 *  	
 */
	public static void main(String[] args) throws Exception {
		String hostName     = CommonCoapNames.hostName;
 		String resourceName = CommonCoapNames.resourceName;
 		int port            = CommonCoapNames.port;
		MainCoapBasicLed appl = new MainCoapBasicLed(hostName, port, resourceName);
// 		appl.synchGet();
		System.out.println(" ---  START --- ");
// 		for( int i=0; i<3; i++ ) {
//			Thread.sleep(500);
//			appl.put("true");
//			Thread.sleep(1000);
//			appl.put("false");
// 		}
//		System.out.println("LAST VALUE:");
//		appl.asynchGet();
	}	
}
