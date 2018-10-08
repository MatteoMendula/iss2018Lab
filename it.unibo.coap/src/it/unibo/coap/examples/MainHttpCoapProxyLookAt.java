package it.unibo.coap.examples;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
  
/*
  */
public class MainHttpCoapProxyLookAt {
 
private CoapClient coapClient;
//private AsynchListener asynchListener = new AsynchListener();
	
	public MainHttpCoapProxyLookAt( String hostName, int port, String resourceName ) {
		configure(hostName, port, resourceName);
	}

	protected void configure(String hostName, int port, String resourceName) {
 		createClient(hostName,port,resourceName);
 	}
	
	public void createClient(String hostName, int port, String resourceName) {
		coapClient=  new CoapClient("coap://"+hostName+":"+port+"/"+resourceName);
		System.out.println("MainCoapBasicLedLookAt Client started");
 	}
	
 	
	protected void synchGet() {
		//Synchronously send the GET message (blocking call)
		CoapResponse coapResp = coapClient.get();
		//The "CoapResponse" message contains the response. 
 //		System.out.println(Utils.prettyPrint(coapResp));
		System.out.println("%%% MainCoapBasicLedLookAt ANSWER get " + coapResp.getResponseText());		
	}

//	protected void asynchGet() {
// 		coapClient.get( asynchListener );
//	}
 	
	public void put(String v) {
		CoapResponse coapResp = coapClient.put(v, MediaTypeRegistry.TEXT_PLAIN);
		//The "CoapResponse" message contains the response.
 		System.out.println("%%% MainCoapBasicLedLookAt ANSWER put " );
		System.out.println(Utils.prettyPrint(coapResp));
	}
 	
 	
/*
 *  	
 */
	public static void main(String[] args) throws Exception {
 		String resourceName="target";
 		int port = 5683; //8010;
 		//coap://localhost:PORT/targetA
		MainHttpCoapProxyLookAt appl = new MainHttpCoapProxyLookAt("localhost", port,resourceName);
  		appl.synchGet();
//		Thread.sleep(500);
 		appl.put("200");
//		Thread.sleep(1000);
//		System.out.println("NEW VALUE:");
//		appl.asynchGet();	
//		appl.monitorTheled();
	}	
}

/*
curl http://localhost:5683/led
*/