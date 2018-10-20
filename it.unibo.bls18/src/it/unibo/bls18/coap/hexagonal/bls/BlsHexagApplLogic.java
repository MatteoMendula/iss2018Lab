package it.unibo.bls18.coap.hexagonal.bls;

import java.util.Observable;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

import it.unibo.bls18.coap.hexagonal.CommonBlsHexagNames;
import it.unibo.bls18.coap.hexagonal.ResourceLocalObserver;
 

public class BlsHexagApplLogic extends ResourceLocalObserver  {
protected CoapClient ledClient;
protected boolean ledState = false;

 	public BlsHexagApplLogic( ) {
 		ledClient = new  CoapClient("coap://localhost:5683/"+CommonBlsHexagNames.ledResourceName );
 		getCurrentLedState();
   		showMsg("BlsHexagApplLogic CREATED ledState=" + ledState);
 	}
 	
 	protected void getCurrentLedState() {  //just to be sure ...
 		String ledValStr = ledClient.get().getResponseText();
 		ledState = ledValStr.equals("true");		
 	}
 	/*
 	 * Called by Coap
 	 * @see it.unibo.bls18.coap.hexagonal.IResourceLocalObserver#update(java.lang.String)
 	 */
 	@Override
	public void update(String v) {
		System.out.println("	BlsHexagApplLogic updated with: " + v);
		//Switch
		if( ledState ) ledClient.put("false", MediaTypeRegistry.TEXT_PLAIN);
		else ledClient.put("true", MediaTypeRegistry.TEXT_PLAIN);
		getCurrentLedState();
  	}
 	/*
 	 * Called by
 	 */
	@Override
	public void update(Observable source, Object v) {
		System.out.println("	BlsHexagApplLogic update/2: " + v);			 		
	}

}
