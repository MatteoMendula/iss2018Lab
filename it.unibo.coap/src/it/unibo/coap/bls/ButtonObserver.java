package it.unibo.coap.bls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

public class ButtonObserver {
	public static void waitForUser() {
		// wait for user
		System.out.println("Press CR to END");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try { br.readLine(); } catch (IOException e) { }
	}

	/*
	 * The ButtonObserver is called when the Button changes its state and
	 * performs the application logic, i.e. it changes the state of the Led 
	 */
	public static void main(String[] args) throws Exception {
		CoapClient buttonClient  = new  CoapClient("coap://127.0.0.1:8020/button" );
 		CoapClient ledClient     = new  CoapClient("coap://localhost:8010/led");
		System.out.println("ButtonObserver setAnObserver ... " );
		
		CoapObserveRelation relation = buttonClient.observe(
				new CoapHandler() {
					@Override public void onLoad(CoapResponse response) {
						String content = response.getResponseText();
						System.out.println("CoapHandler. NOTIFICATION: " + content);
							System.out.println("CoapHandler. Turn THE LED " + content );
  							new Thread() {
  								public void run() {
  		 							CoapResponse rrr = ledClient.put(content, MediaTypeRegistry.TEXT_PLAIN); 
   		 		 					System.out.println("CoapHandler. RESPONSE: " + rrr.getResponseText());									
  								}
  							}.start();
					}					
					@Override public void onError() {
						System.err.println("Client: OBSERVING FAILED (press enter to exit)");
					}
				});
		

		waitForUser();
	}
}
