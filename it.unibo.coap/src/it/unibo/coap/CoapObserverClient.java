package it.unibo.coap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

public class  CoapObserverClient {
private static int count = 1;
	
	public static String newMsg() {
		return "msg( info, dispatch, client, qarec, hello, COUNT )".replace("hello", "hello"+count).replace("COUNT", ""+count++);
	}
	public static void main(String[] args) {
//	String msg ="msg( info, dispatch, client, qarec, hello, 1 )";
	
		CoapClient client = new CoapClient("coap://localhost:5683/channel");

		System.out.println("SYNCHRONOUS");
		
		// synchronous
		String content1 = client.get().getResponseText();
		System.out.println("Client: RESPONSE GET: " + content1);
		
		CoapResponse resp2 = client.post(newMsg(), MediaTypeRegistry.TEXT_PLAIN);
		System.out.println("Client: RESPONSE POST: " + resp2.getCode());
		
		// asynchronous
/*		
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
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try { br.readLine(); } catch (IOException e) { }
*/		
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
		
		try {
			Thread.sleep(1000);
			CoapResponse resp3 = client.post(newMsg(), MediaTypeRegistry.TEXT_PLAIN);
			System.out.println("Client: RESPONSE 3 CODE: " + resp3.getCode());
			Thread.sleep(1000);
			resp3 = client.post(newMsg(), MediaTypeRegistry.TEXT_PLAIN);
			System.out.println("Client: RESPONSE 3 CODE: " + resp3.getCode());		
		} catch (InterruptedException e1) {
 			e1.printStackTrace();
		}
		
		// wait for user
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try { br.readLine(); } catch (IOException e) { }
		
		System.out.println("Client:  CANCELLATION");		
		relation.proactiveCancel();
	}
}