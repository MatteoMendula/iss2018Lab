package it.unibo.bls18.coapBasic.led;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
 
public class  CoapLedObserverClient {
 	
	public static void main(String[] args) {
 		String hostName     = CommonCoapNames.hostName;
 		String resourceName = CommonCoapNames.resourceName;
 		int port            = CommonCoapNames.port;
	
		CoapClient client   =  new CoapClient("coap://"+hostName+":"+port+"/"+resourceName);
		System.out.println("SYNCHRONOUS");
		
		// synchronous
		String content1 = client.get().getResponseText();
		System.out.println("Client: RESPONSE GET: " + content1);
		
		// observe

		System.out.println("Client: OBSERVE (press enter to exit)");
		
		CoapObserveRelation relation = client.observe(
				new CoapHandler() {
					@Override public void onLoad(CoapResponse response) {
						String content = response.getResponseText();
						System.out.println("Client: NOTIFICATION: " + content);
					}
					
					@Override public void onError() {
						System.err.println("Client: OBSERVING FAILED (press enter to exit)");
					}
				});
		
		
		// wait for user
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try { br.readLine(); } catch (IOException e) { }
		
		System.out.println("Client:  CANCELLATION");		
		relation.proactiveCancel();
	}
}