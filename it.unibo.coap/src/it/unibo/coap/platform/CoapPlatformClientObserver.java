package it.unibo.coap.platform;
/*******************************************************************************
 * Copyright (c) 2018 DISI University of Bologna.
 * 
 * Contributors:
 *    AN - creator and main architect
 ******************************************************************************/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
 
public class  CoapPlatformClientObserver {
 
private static CoapClient client = new CoapClient("coap://localhost:5683/qachannel");
/*
A CoapHandler can be used to asynchronously react to responses from a CoAP client. 
When a response or in case of a CoAP observe relation a notification arrives, 
the method onLoad(CoapResponse) is invoked. 
If a request timeouts or the server rejects it, the method onError() is invoked. 	
*/
	public static CoapObserveRelation observeCall() {
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
		return relation;
	}
	
	public static void waitForUser() {
		// wait for user
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try { br.readLine(); } catch (IOException e) { }
	}

 	public static void main(String[] args) {
 		System.out.println("Client: OBSERVE (press enter to exit)");		
		CoapObserveRelation relation = observeCall() ;	
		waitForUser();		
		System.out.println("Client:  CANCELLATION");		
		relation.proactiveCancel();
	}
}