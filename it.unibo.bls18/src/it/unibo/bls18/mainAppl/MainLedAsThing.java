package it.unibo.bls18.mainAppl;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.interfaces.IResourceIot;
import it.unibo.bls18.things.LedThing;

public class MainLedAsThing {
private static MainLedAsThing appl=null; 	//SINGLETON
private IResourceIot led;
 
	public static MainLedAsThing createTheSystem() {
		if( appl == null ) { 
			appl = new MainLedAsThing();
			appl.createComponents();
			appl.configure();
		}
		return appl;
	}
	
	protected void createComponents() {
		LedThing.create(8010);
 	}
	protected void configure() {
		//led.setGofObserver(observerLed);		
		//led.addObserver(observerLed);
	}
	
	public IResourceIot getLed() {
		return led;
	}
	
	
	public static void main(String[] args) throws Exception {
		MainLedAsThing.createTheSystem();
		CoapClient ledClient = new CoapClient("coap://localhost:8010/led" );
 		UtilsBls.delay(3000);
		System.out.println("MainLedAsThing START");
		ledClient.put("true", MediaTypeRegistry.TEXT_PLAIN);
		UtilsBls.delay(1000);
		ledClient.put("false", MediaTypeRegistry.TEXT_PLAIN);
		System.out.println("MainLedAsThing WAITS for Firefox");
//		Utils.delay(60000);
 	}
	
}
