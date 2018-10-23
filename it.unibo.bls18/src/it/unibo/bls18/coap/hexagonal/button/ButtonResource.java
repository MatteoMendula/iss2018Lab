package it.unibo.bls18.coap.hexagonal.button;

import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.server.resources.CoapExchange;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.coap.hexagonal.CoapGofObservableResource;
import it.unibo.bls18.coap.hexagonal.CommonBlsHexagNames;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.*;

public  class ButtonResource extends CoapGofObservableResource { //CoapResource with a GOF observer
	private final int unpressTime = 3000;
	private String buttonState = CommonBlsHexagNames.cmdTurnOff;
	
    public ButtonResource() { 
        super(CommonBlsHexagNames.buttonResourceName);

        setObservable(true); 				// enable observing	!!!!!!!!!!!!!!
 		setObserveType(Type.CON); 			// configure the notification type to CONs
		getAttributes().setObservable();	// mark observable in the Link-Format		
    }

    @Override
    public void setValue(String v) {
    	buttonState = v ;
    	modelModified();
    }
    
    @Override
    public void handleGET(CoapExchange exchange) {
    	/*
    	 * Setting setMaxAge to 0 will delete the cookie. 
    	 * Setting it to -1 will preserve it until the browser is closed.
    	 */
    	exchange.setMaxAge( 600000 ); 
        exchange.respond(  "buttonValue("+buttonState+")" );
    }

    @Override 
    public  void handlePUT(CoapExchange exchange) {
        try {
            String v = exchange.getRequestText(); 
            if( v.equals(CommonBlsHexagNames.buttonCmd) && 
            		buttonState.equals(CommonBlsHexagNames.cmdTurnOff)) setValue( v ) ;
            exchange.respond(CHANGED,  buttonState);
            simulateUnpress();
        } catch (Exception e) {
            exchange.respond(BAD_REQUEST, "Invalid String"); 
        }
    }
    
    protected void modelModified() {
    	System.out.println("	ButtonResource modelModified "+ buttonState);
    	changed();      // notify all CoAp observers	
    	update(buttonState);	// notify the GOF observer
    }
    /*
     * The button remains unusable for  unpressTime 
     * (To see the state true when /Button)
     */
    protected void simulateUnpress() {
    	new Thread() {
    		public void run() {
    		   	UtilsBls.delay(unpressTime);
    		   	buttonState = CommonBlsHexagNames.cmdTurnOff;    			
    	    	System.out.println("	ButtonResource buttonState returned to"+ buttonState);
   		}
    	}.start();
    }
    
  }
/*
curl -X PUT -d "pressed" http://localhost:8080/Button
*/