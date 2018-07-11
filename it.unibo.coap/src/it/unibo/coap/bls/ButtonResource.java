package it.unibo.coap.bls;

import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.server.resources.CoapExchange;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.*;

public  class ButtonResource extends CoapGofObservableResource { //CoapResource with a GOF observer
public static final String resourcePath = "button";

	private String value = "false";
	
    public ButtonResource() { 
        super(resourcePath);
		setObservable(true); 				// enable observing	!!!!!!!!!!!!!!
 		setObserveType(Type.CON); 			// configure the notification type to CONs
		getAttributes().setObservable();	// mark observable in the Link-Format		
    }

    @Override
    public void setValue(String v) {
    	value = v ;
    	update(v);	// notify the GOF observer
    }
    
    @Override
    public void handleGET(CoapExchange exchange) {
    	exchange.setMaxAge( 600000 ); 
        exchange.respond(  value );
    }

    @Override 
    public  void handlePUT(CoapExchange exchange) {
//        byte[] payload = exchange.getRequestPayload();
        try {
            String v = exchange.getRequestText(); //new String(payload, "UTF-8"); 
            setValue( v ) ;
//            System.out.println("ButtonResource PUT source="+exchange.getSourceAddress() + " value=" + value );
            exchange.respond(CHANGED,  value);
            changed(); 	// notify all CoAP observers           
        } catch (Exception e) {
            exchange.respond(BAD_REQUEST, "Invalid String"); 
        }
    }
  }