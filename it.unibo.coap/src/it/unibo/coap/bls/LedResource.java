package it.unibo.coap.bls;

import org.eclipse.californium.core.server.resources.CoapExchange;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.*;

public  class LedResource extends CoapGofObservableResource {
public static final String resourcePath = "led";

private String value = "false";
	
    public LedResource() {
        super(resourcePath);
    }
    
    @Override
    public void setValue(String v) {
    	value = v ;
    	update(value);	// notify the GOF observer
    }

    @Override
    public void handleGET(CoapExchange exchange) {
   	System.out.println("	LedResource GET source="+exchange.getSourceAddress());
        exchange.respond( value );
    }

    @Override
    public  void handlePUT(CoapExchange exchange) {
//        byte[] payload = exchange.getRequestPayload();
        try {
        	value = exchange.getRequestText();//new String(payload, "UTF-8"); 
            //System.out.println("LedResource PUT "+ value);
        	setValue(value);
            exchange.respond(CHANGED,  value);
        } catch (Exception e) {
//            e.printStackTrace();
            exchange.respond(BAD_REQUEST, "Invalid String");
        }
    }
  }