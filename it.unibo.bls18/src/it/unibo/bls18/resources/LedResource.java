package it.unibo.bls18.resources;

import org.eclipse.californium.core.server.resources.CoapExchange;
import it.unibo.bls18.obj.CoapGofObservableResource;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.*;

import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

public  class LedResource extends CoapGofObservableResource {
public static final String resourcePath = "led";

private String value = "false";
	
    public LedResource() {
        super(resourcePath); //required by CoaP Californium (Cf)
    }
    
    @Override
    public void setValue(String v) {
    	value = v ;
    	update(value);	// notify the GOF observer
    }

    @Override	//CoapResource
    public void handleGET(CoapExchange exchange) {
    	System.out.println("	LedResource GET source="+exchange.getSourceAddress() + " value=" + value);
        exchange.respond( ResponseCode.CONTENT, value, MediaTypeRegistry.TEXT_PLAIN) ;
    }

    @Override	//CoapResource
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