package it.unibo.bls18.coapBasic.led;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.coapBasic.AsynchListener;
 
/*
 * Create a CoaP server and a CoaP client
 * The server refers a LedCoapResource linked to a LedGui
 * The client sends to the server requests to get/put values of the led
 */
public class MainCoapBasicLedLookAt {
 
private CoapClient coapClient;
private AsynchListener asynchListener = new AsynchListener();
	
	public MainCoapBasicLedLookAt(int port, String resourceName) {
		configure(port, resourceName);
	}

	protected void configure(int port, String resourceName) {
 		createClient(port,resourceName);
 	}
	
	public CoapClient createClient(int port, String resourceName) {
		coapClient= //new CoapClient("coap://192.168.1.12:"+port+"/"+resourceName);
				new CoapClient("coap://localhost:"+port+"/"+resourceName);
		System.out.println("MainCoapBasicLedLookAt Client started");
		return coapClient;
	}
	
 	
	protected void synchGet() {
		//Synchronously send the GET message (blocking call)
		CoapResponse coapResp = coapClient.get();
		//The "CoapResponse" message contains the response. 
 //		System.out.println(Utils.prettyPrint(coapResp));
		System.out.println("%%% MainCoapBasicLedLookAt ANSWER get " + coapResp.getResponseText());
		
	}

	protected void asynchGet() {
 		coapClient.get( asynchListener );
	}
 	
	public void put(String v) {
		CoapResponse coapResp = coapClient.put(v, MediaTypeRegistry.TEXT_PLAIN);
		//The "CoapResponse" message contains the response.
 		System.out.println("%%% MainCoapBasicLedLookAt ANSWER put " );
		System.out.println(Utils.prettyPrint(coapResp));
	}
 	
	public void monitorTheled() {
		new Thread() {
			public void run() {
				for(int i=0; i<50; i++) {
					synchGet();
					UtilsBls.delay(2000);
 				}
			}
		}.start();
	}
	
/*
 *  	
 */
	public static void main(String[] args) throws Exception {
 		String resourceName="Led";
 		int port = 5683; //8010;
		MainCoapBasicLedLookAt appl = new MainCoapBasicLedLookAt(port,resourceName);
 		appl.synchGet();
		Thread.sleep(500);
		appl.put("true");
		Thread.sleep(1000);
		System.out.println("NEW VALUE:");
		appl.asynchGet();	
		appl.monitorTheled();
	}	
}

/*
curl http://localhost:5683/led
*/