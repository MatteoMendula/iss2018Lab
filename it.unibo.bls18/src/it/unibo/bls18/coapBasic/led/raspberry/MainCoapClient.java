package it.unibo.bls18.coapBasic.led.raspberry;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

import it.unibo.bls18.coapBasic.led.CommonCoapNames;

public class MainCoapClient {
	private CoapClient coapClient;

	public MainCoapClient(String hostName, int port, String resourceName) {
 		createClient(hostName, port,resourceName);
 	}
	public void createClient(String hostName, int port, String resourceName) {
		coapClient=  new CoapClient("coap://"+hostName+":"+port+"/"+resourceName);
		System.out.println("MainCoapClient Client started");
 	}
	protected void synchGet() {
		//Synchronously send the GET message (blocking call)
		CoapResponse coapResp = coapClient.get();
		//The "CoapResponse" message contains the response. 
 //		System.out.println(Utils.prettyPrint(coapResp));
		System.out.println("%%% MainCoapClient ANSWER get " + coapResp.getResponseText());		
	}

	protected void asynchGet() {
// 		coapClient.get( asynchListener );
	}
	
	public void put(String v) {
		CoapResponse coapResp = coapClient.put(v, MediaTypeRegistry.TEXT_PLAIN);
		//The "CoapResponse" message contains the response.
 		//System.out.println(Utils.prettyPrint(coapResp));
		System.out.println("%%% MainCoapClient ANSWER put " + coapResp.getResponseText());		
	}

	public static void main(String[] args) throws Exception {
		String hostName     = CommonCoapNames.hostRaspName;
 		String resourceName = CommonCoapNames.resourceName;
 		int port            = CommonCoapNames.port;
 		MainCoapClient appl = new MainCoapClient(hostName, port, resourceName);
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
