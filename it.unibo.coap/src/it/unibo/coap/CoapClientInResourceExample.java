package it.unibo.coap;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.ConcurrentCoapResource;

public class CoapClientInResourceExample extends ConcurrentCoapResource {

	public CoapClientInResourceExample(String name) {
		super(name, SINGLE_THREADED);
	}

	@Override
	public void handleGET(final CoapExchange exchange) {
		exchange.accept();
		
		CoapClient client = createClient("localhost:5683/target");
		client.get(new CoapHandler() {
			@Override
			public void onLoad(CoapResponse response) {
				exchange.respond(response.getCode(), response.getPayload());
			}
			
			@Override
			public void onError() {
				exchange.respond(ResponseCode.BAD_GATEWAY);
			}
		});
		
		// exchange has not been responded yet
	}
	
	@Override
	public void handlePOST(CoapExchange exchange) {
		exchange.accept();
	
		ResponseCode response;
		synchronized (this) {
			// critical section
			response = ResponseCode.CHANGED;
		}

		exchange.respond(response);
	}

	public static void main(String[] args) {
		CoapServer server = new CoapServer();
		server.add(new CoapClientInResourceExample("example"));
		server.add(new CoapResource("target") {
			@Override
			public void handleGET(CoapExchange exchange) {
				System.out.println("	handleGET" );
				exchange.respond("Target payload");
//				exchange.reject();
			}
			@Override
			public void handlePOST(CoapExchange exchange) {
				System.out.println("	handlePOST" );
				exchange.respond("POST doane!");
//				exchange.reject();
			} 
		});
		server.start();
		
		CoapClient client = new CoapClient("coap://localhost:5683/example");
		System.out.println( client.get().getResponseText() );
		System.out.println( client.post("xxx",MediaTypeRegistry.TEXT_PLAIN).getResponseText() );
		System.out.println( client.get().getResponseText() );
	}
}
