package it.unibo.bls18.coap.hexagonal.button;

import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.server.resources.CoapExchange;
import it.unibo.bls18.coap.hexagonal.CoapGofObservableResource;
import it.unibo.bls18.coap.hexagonal.CommonBlsHexagNames;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.*;

public  class ButtonResource extends CoapGofObservableResource { //CoapResource with a GOF observer
public static final String resourcePath = CommonBlsHexagNames.buttonResourceName;

	private String value = CommonBlsHexagNames.cmdTurnOff;
	
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
        try {
            String v = exchange.getRequestText(); //new String(payload, "UTF-8"); 
            setValue( v ) ;
            exchange.respond(CHANGED,  value);
            changed(); 	// notify all CoAP observers           
        } catch (Exception e) {
            exchange.respond(BAD_REQUEST, "Invalid String"); 
        }
    }
  }