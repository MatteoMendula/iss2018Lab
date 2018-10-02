package it.unibo.bls18.mainAppl;

import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.interfaces.IResourceIot;
import it.unibo.bls18.interfaces.IResourceLocalObserver;
import it.unibo.bls18.obj.LedResourceGofObserver;
import it.unibo.bls18.resources.LedResource;

public class MainLedAsPojo {
private static MainLedAsPojo appl=null; 	//SINGLETON
private IResourceIot led;
private IResourceLocalObserver observerLed;

	public static MainLedAsPojo createTheSystem() {
		if( appl == null ) { 
			appl = new MainLedAsPojo();
 			appl.configure();
		}
		return appl;
	}
	
 	protected void configure() {
		led         = new LedResource();
		led.setValue("false");
		observerLed = new LedResourceGofObserver();
		led.setGofObserver(observerLed);		
		//led.addObserver(observerLed);
	}
	
	public IResourceIot getLed() {
		return led;
	}
	public static void main(String[] args) throws Exception {
		MainLedAsPojo appl = MainLedAsPojo.createTheSystem();
		appl.getLed().setValue("true");
		UtilsBls.delay(1000);
		appl.getLed().setValue("false");
	}
	
}
