package it.unibo.bls18.coapBasic.led;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import it.unibo.bls.utils.UtilsBls;
 
public class  CoapLedCmdClient {
 	
	public static void main(String[] args) {
 		String hostName     = CommonCoapNames.hostName;
 		String resourceName = CommonCoapNames.resourceName;
 		int port            = CommonCoapNames.port;
	
		CoapClient coapClient   =  new CoapClient("coap://"+hostName+":"+port+"/"+resourceName);
//		System.out.println("SYNCHRONOUS");
//		
//		// synchronous
//		String content1 = coapClient.get().getResponseText();
//		System.out.println("CoapLedObserverClient: RESPONSE GET: " + content1);
		
		CoapResponse coapResp = coapClient.put("true", MediaTypeRegistry.TEXT_PLAIN);
		System.out.println(
				"CoapLedCmdClient: RESPONSE PUT (true) code: " + coapResp.getCode() +
				" text=" + coapResp.getResponseText());
		UtilsBls.delay(1000);
		coapResp = coapClient.put("false", MediaTypeRegistry.TEXT_PLAIN);
		System.out.println(
			"CoapLedCmdClient: RESPONSE PUT (false) code: " + coapResp.getCode() +
			" text=" + coapResp.getResponseText());
		
 	}
}