package it.unibo.bls18.coapBasic.led;

import static org.eclipse.californium.core.coap.CoAP.ResponseCode.BAD_REQUEST;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CHANGED;
import java.util.Observable;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.server.resources.CoapExchange;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObserver;
 

/*
 * A LedCoapResource is a CoapResource that uses and observes a LedObservableModel
 * Usage: MainCoapBasicLed  MainCoapBasicBls
 */
public class LedCoapResource extends CoapResource implements IObserver{ //observes the ledModel
private	ILedObservable ledModel ;
	
	public LedCoapResource(String name, ILedObservable model ) {
		super(name);
		setObservable(true); 				// enable observing	!!!!!!!!!!!!!!
		setObserveType(Type.CON); 			// configure the notification type to CONs
		getAttributes().setObservable();	// mark observable in the Link-Format		
		ledModel = model;
		ledModel.addObserver(this);
	}

	    @Override	//CoapResource
	    public void handleGET(CoapExchange exchange) {
 		    CommonCoapNames.showExchange(exchange);
	    	//System.out.println("	LedResource GET source="+exchange.getSourceAddress() + " value=" + getValue());
	        exchange.respond( ResponseCode.CONTENT, getValue(), MediaTypeRegistry.TEXT_PLAIN) ;
	    }

	    @Override	//CoapResource
	    public  void handlePUT(CoapExchange exchange) {
 	        try {
  		    	CommonCoapNames.showExchange(exchange);
	        	String value = exchange.getRequestText();//new String(payload, "UTF-8"); 
	            System.out.println("LedCoapResource handlePUT "+ value);
	            if( value.equals("switch"))  switchValue();	             
	            else setValue(value);
	            exchange.respond(CHANGED,  value);
 	        } catch (Exception e) {
 	            exchange.respond(BAD_REQUEST, "Invalid String");
	        }
	    }
	    
 		   protected void switchValue() {
			   if( getValue( ).equals("true")) ledModel.turnOff();
			   else ledModel.turnOn();
	    	   changed(); // notify all observers

	 	    }
		   protected void setValue(String v) {
			   if( v.equals("true")) ledModel.turnOn();
			   else ledModel.turnOff();
	    	   changed(); // notify all observers

	 	    }
		   protected String getValue( ) {
			    return ""+ledModel.getState();
	 	   }

		@Override
		public void update(Observable arg0, Object arg1) {
			changed(); // notify all observers
			
		}

 	
}
