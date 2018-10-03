package it.unibo.bls18.coapBasic.led;

import java.awt.Frame;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
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
public class MainCoapBasicLed {
private CoapServer server;
private CoapClient coapClient;
private AsynchListener asynchListener = new AsynchListener();
	
	public MainCoapBasicLed(int port, String resourceName) {
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
	
	protected   void addResource(String name) {
		//Create a ledGui as concrete device
 		Frame blsFrame    = UtilsBls.initFrame(200,200);
		IObserver ledgui  = LedAsGui.createLed(blsFrame);
		//Create a LedCoapResource that makes reference to the ledgui
		ILedObservable led          = LedObservableModel.createLed(ledgui);
		LedCoapResource ledResource = new LedCoapResource(name,led) ;
		server.add( ledResource );
		led.turnOff();
		System.out.println("MainCoapBasicLed addResource done");
	}
	
	protected void synchGet() {
		//Synchronously send the GET message (blocking call)
		CoapResponse coapResp = coapClient.get();
		//The "CoapResponse" message contains the response. 
 		System.out.println("%%% MainCoapBasicLed ANSWER get " );
		System.out.println(Utils.prettyPrint(coapResp));
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
	
/*
 *  	
 */
	public static void main(String[] args) throws Exception {
 		String resourceName="led";
 		int port = 5683; //8010;
		MainCoapBasicLed appl = new MainCoapBasicLed(port,resourceName);
 		appl.synchGet();
		Thread.sleep(500);
		appl.put("true");
		Thread.sleep(1000);
		System.out.println("NEW VALUE:");
		appl.asynchGet();
		
	}	
}
