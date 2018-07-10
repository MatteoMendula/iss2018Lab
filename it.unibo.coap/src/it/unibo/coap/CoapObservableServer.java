package it.unibo.coap;

import static org.eclipse.californium.core.coap.CoAP.ResponseCode.*;
import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.server.resources.CoapExchange;


public class CoapObservableServer extends CoapResource {
private int count = 0;
private String curMsg = "";
	public CoapObservableServer(String name) {
		super(name);
		setObservable(true); 				// enable observing	!!!!!!!!!!!!!!
		setObserveType(Type.CON); 			// configure the notification type to CONs
		getAttributes().setObservable();	// mark observable in the Link-Format		
		// schedule a periodic update task, otherwise let events call changed()
//		Timer timer = new Timer();
//		timer.schedule(new UpdateTask(), 0, 2000);
	}
/*	
	private class UpdateTask extends TimerTask {
		@Override
		public void run() {
			// .. periodic update of the resource
//			System.out.println("UpdateTask " + count ) ;
			count++;
			changed(); // notify all observers
		}
	}
*/	
	@Override
	public void handleGET(CoapExchange exchange) {
		System.out.println("handleGET " + count ) ;
		exchange.setMaxAge(1); // the Max-Age value should match the update interval
		exchange.respond( curMsg );
	}
	
	@Override
	public void handleDELETE(CoapExchange exchange) {
		System.out.println("handleDELETE "  ) ;
		delete(); // will also call clearAndNotifyObserveRelations(ResponseCode.NOT_FOUND)
		exchange.respond(DELETED);
	}
	
	@Override
	public void handlePUT(CoapExchange exchange) {
		// ...
		System.out.println("handlePUT " + exchange.getRequestText() ) ;
		exchange.respond(CHANGED);
		changed(); // notify all observers
	}

	@Override
	public void handlePOST(CoapExchange exchange) {
		// ...
		curMsg = exchange.getRequestText();
		System.out.println("handlePOST " + curMsg ) ;	
		exchange.respond(CHANGED);
		changed(); // notify all observers
	}

	public static void main(String[] args) {
		try {
			CoapServer server = new CoapServer();
			server.add(new CoapObservableServer("channel"));
			server.start();
		}catch(Exception e) {
			System.out.println("ERROR " + e.getMessage()) ;
		}
	}

}
