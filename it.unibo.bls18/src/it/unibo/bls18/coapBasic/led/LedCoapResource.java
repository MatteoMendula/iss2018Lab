package it.unibo.bls18.coapBasic.led;

import static org.eclipse.californium.core.coap.CoAP.ResponseCode.BAD_REQUEST;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CHANGED;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import it.unibo.bls.interfaces.ILedObservable;
 

/*
 * A LedCoapResource is a CoapResource that uses a LedObservableModel
 * Usage: MainCoapBasicLed
 */
public class LedCoapResource extends CoapResource{
private	ILedObservable ledModel ;
	
	public LedCoapResource(String name, ILedObservable model ) {
		super(name);
		ledModel = model;
	}

	    @Override	//CoapResource
	    public void handleGET(CoapExchange exchange) {
	    	//System.out.println("	LedResource GET source="+exchange.getSourceAddress() + " value=" + getValue());
	        exchange.respond( ResponseCode.CONTENT, getValue(), MediaTypeRegistry.TEXT_PLAIN) ;
	    }

	    @Override	//CoapResource
	    public  void handlePUT(CoapExchange exchange) {
 	        try {
	        	String value = exchange.getRequestText();//new String(payload, "UTF-8"); 
	            //System.out.println("LedCoapResource handlePUT "+ value);
	        	setValue(value);
	            exchange.respond(CHANGED,  value);
	        } catch (Exception e) {
 	            exchange.respond(BAD_REQUEST, "Invalid String");
	        }
	    }
	    
	    
		   protected void setValue(String v) {
			   if( v.equals("true")) ledModel.turnOn();
			   else ledModel.turnOff();
	 	    }
		   protected String getValue( ) {
			    return ""+ledModel.getState();
	 	   }

 	
}
