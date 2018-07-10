package it.unibo.coap.bls;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

public class ButtonCaller {

	public static void main(String args[])  throws Exception {	
		CoapClient buttonClient  = new  CoapClient("coap://localhost:8020/button" );
		CoapClient ledClient     = new  CoapClient("coap://localhost:8010/led");
		CoapResponse resp;
		String ledval;
		for( int i=1; i<=3; i++ ) {
			resp = buttonClient.put("true", MediaTypeRegistry.TEXT_PLAIN);
			System.out.println("RESPONSE buttonPut : " + resp.getResponseText() );
			Thread.sleep(500);
			 ledval = ledClient.get().getResponseText();
			System.out.println("LED value=         : " + ledval);
			
			Thread.sleep(1000);
			resp = buttonClient.put("false", MediaTypeRegistry.TEXT_PLAIN);
			System.out.println("RESPONSE buttonPut : " + resp.getResponseText() );
			Thread.sleep(500);
			 ledval = ledClient.get().getResponseText();
			System.out.println("LED value=         : " + ledval);
		}
	}
}
