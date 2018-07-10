package it.unibo.coap.platform;
/*******************************************************************************
 * Copyright (c) 2018 DISI University of Bologna.
 * 
 * Contributors:
 *    AN - creator and main architect
 ******************************************************************************/

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

public class  CoapPlatformClientSender {
private static int count = 20;
private static CoapClient client = new CoapClient("coap://localhost:5683/qachannel");
	
	public static String newMsg() {
		return "msg( info, dispatch, client, qarec, hello, COUNT )".replace("hello", "hello"+count).replace("COUNT", ""+count++);
	}

	public static void sendMsgs() {
		try {
			Thread.sleep(1000);
			CoapResponse resp3 = client.post(newMsg(), MediaTypeRegistry.TEXT_PLAIN);
			System.out.println("Client: RESPONSE 4 CODE: " + resp3.getCode() + " payload=" + resp3.getResponseText());
			Thread.sleep(3000);
			resp3 = client.post(newMsg(), MediaTypeRegistry.TEXT_PLAIN);
			System.out.println("Client: RESPONSE 4 CODE: " + resp3.getCode()  + " payload=" + resp3.getResponseText() );			
		} catch (InterruptedException e1) {
 			e1.printStackTrace();
		}		
	}
	public static void main(String[] args) {
 		sendMsgs();
	}
}