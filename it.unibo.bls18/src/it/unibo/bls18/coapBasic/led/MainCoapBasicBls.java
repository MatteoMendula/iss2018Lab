package it.unibo.bls18.coapBasic.led;

import java.awt.Frame;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import it.unibo.bls.applLogic.BlsApplicationLogic;
import it.unibo.bls.devices.ButtonAsGui;
import it.unibo.bls.devices.LedAsGui;
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
public class MainCoapBasicBls {
private CoapServer server;
private CoapClient coapClient;
private AsynchListener asynchListener = new AsynchListener();
	
	public MainCoapBasicBls(int port, String resourceName) {
		configure(port, resourceName);
	}

	protected void configure(int port, String resourceName) {
 		createServer(port);
		addResource(resourceName);
		createClient(port,resourceName);
 	}
	
	protected  void createServer(int port) {	//port=5683 default
		server   = new CoapServer(port);
		server.start();
		System.out.println("MainCoapBasicLed Server started");
	}
	
	public CoapClient createClient(int port, String resourceName) {
		coapClient= //new CoapClient("coap://192.168.1.12:"+port+"/"+resourceName);
				new CoapClient("coap://localhost:"+port+"/"+resourceName);
		System.out.println("MainCoapBasicLed Client started");
		return coapClient;
	}
	
	protected IObserver createConcreteComponent( Frame blsFrame ) {
		//Create a ledGui as concrete device
		IObserver ledgui  = LedAsGui.createLed(blsFrame);
		return ledgui;
	}
	
 	protected   void addResource(String name) {
 		Frame blsFrame    = UtilsBls.initFrame(200,200);
		
		//Create a LedObservableModel that makes reference to a concrete led
 		ILedObservable ledmodel = LedObservableModel.createLed( createConcreteComponent( blsFrame ) );
 		
		//Create a LedCoapResource that makes reference to the LedObservableModel
		LedCoapResource ledResource = new LedCoapResource(name,ledmodel) ;
		
		//Add the LedCoapResource to the server
		server.add( ledResource );
   		ledmodel.turnOff();
   		
		//Add to the application a Button to switch the Led (TODO: front-end server)
    	addAButton(blsFrame,ledmodel);
	}
	
	protected void addAButton(Frame blsFrame, ILedObservable led) {
    	BlsApplicationLogic applLogic = new BlsApplicationLogic(led);
   		ButtonAsGui.createButton( blsFrame, "press", applLogic);
		
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
 		System.out.println("%%% MainCoapBasicLed ANSWER put " );
		System.out.println(Utils.prettyPrint(coapResp));
	}
 	
//	public void monitorTheled() {
//		new Thread() {
//			public void run() {
//				for(int i=0; i<50; i++) {
//					synchGet();
//					UtilsBls.delay(2000);
// 				}
//			}
//		}.start();
//	}
	
/*
 *  	
 */
	public static void main(String[] args) throws Exception {
 		String resourceName="Led";
 		int port = 5683; //8010;
		MainCoapBasicBls appl = new MainCoapBasicBls(port,resourceName);
 		appl.synchGet();
		Thread.sleep(500);
		appl.put("true");
		Thread.sleep(1000);
		System.out.println("NEW VALUE:");
		appl.asynchGet();	
//		appl.monitorTheled();
	}	
}

/*
curl http://localhost:5683/led
*/