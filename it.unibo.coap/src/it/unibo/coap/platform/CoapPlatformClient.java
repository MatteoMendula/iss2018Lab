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
import org.eclipse.californium.core.coap.MediaTypeRegistry;

public class  CoapPlatformClient {
private static int count = 20;
private static CoapClient client = new CoapClient("coap://localhost:5683/qachannel");
	
	public static String newMsg() {
		return "msg( info, dispatch, client, qarec, hello, COUNT )".replace("hello", "hello"+count).replace("COUNT", ""+count++);
	}
	
	public static void synchCall() {
		System.out.println("SYNCHRONOUS");		
		// synchronous
		String content1 = client.get().getResponseText();
		System.out.println("Client: RESPONSE GET: " + content1);		
		CoapResponse resp2 = client.post(newMsg(), MediaTypeRegistry.TEXT_PLAIN);
		System.out.println("Client: RESPONSE POST: " + resp2.getCode());		
	}
	public static void asynchCall() {
 		System.out.println("ASYNCHRONOUS (press enter to continue)");		
		client.get(new CoapHandler() {
			@Override public void onLoad(CoapResponse response) {
				String content = response.getResponseText();
				System.out.println("RESPONSE 3: " + content);
			}
			
			@Override public void onError() {
				System.err.println("FAILED");
			}
		});		
		// wait for user
		waitForUser();
	}
	
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

	public static void sendMsgs() {
		try {
			Thread.sleep(1000);
			CoapResponse resp3 = client.post(newMsg(), MediaTypeRegistry.TEXT_PLAIN);
			System.out.println("Client: RESPONSE 4 CODE: " + resp3.getCode() + " payload=" + resp3.getResponseText());
			Thread.sleep(3000);
			resp3 = client.post(newMsg(), MediaTypeRegistry.TEXT_PLAIN);
			System.out.println("Client: RESPONSE 4 CODE: " + resp3.getCode());			
		} catch (InterruptedException e1) {
 			e1.printStackTrace();
		}		
	}
	public static void main(String[] args) {
 		// observe
//		System.out.println("Client: OBSERVE (press enter to exit)");		
//		CoapObserveRelation relation = observeCall() ;	
		sendMsgs();
//		waitForUser();		
//		System.out.println("Client:  CANCELLATION");		
//		relation.proactiveCancel();
	}
}