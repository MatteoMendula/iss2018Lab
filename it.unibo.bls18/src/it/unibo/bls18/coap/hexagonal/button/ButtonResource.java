package it.unibo.bls18.coap.hexagonal.button;

import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.server.resources.CoapExchange;
import it.unibo.bls18.coap.hexagonal.CoapGofObservableResource;
import it.unibo.bls18.coap.hexagonal.CommonBlsHexagNames;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.*;

public  class ButtonResource extends CoapGofObservableResource { //CoapResource with a GOF observer
 
	private String value = CommonBlsHexagNames.cmdTurnOff;
	
    public ButtonResource() { 
        super(CommonBlsHexagNames.buttonResourceName);

        setObservable(true); 				// enable observing	!!!!!!!!!!!!!!
 		setObserveType(Type.CON); 			// configure the notification type to CONs
		getAttributes().setObservable();	// mark observable in the Link-Format		
    }

    @Override
    public void setValue(String v) {
    	value = v ;
    	modelModified();
    }
    
    @Override
    public void handleGET(CoapExchange exchange) {
    	exchange.setMaxAge( 600000 ); 
        exchange.respond(  "buttonValue("+value+")" );
    }

    @Override 
    public  void handlePUT(CoapExchange exchange) {
        try {
            String v = exchange.getRequestText(); 
            if( v.equals(CommonBlsHexagNames.buttonCmd) ) setValue( v ) ;
            exchange.respond(CHANGED,  value);
        } catch (Exception e) {
            exchange.respond(BAD_REQUEST, "Invalid String"); 
        }
    }
    
    protected void modelModified() {
    	System.out.println("	ButtonResource modelModified "+ value);
    	changed();      // notify all CoAp observers	
    	update(value);	// notify the GOF observer
    }
    
  }