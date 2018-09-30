package it.unibo.bls18.mains;

import it.unibo.bls.utils.Utils;
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
			appl.createComponents();
			appl.configure();
		}
		return appl;
	}
	
	protected void createComponents() {
		led         = new LedResource();
		led.setValue("false");
		observerLed = new LedResourceGofObserver();
	}
	protected void configure() {
		led.setGofObserver(observerLed);		
		//led.addObserver(observerLed);
	}
	
	public IResourceIot getLed() {
		return led;
	}
	public static void main(String[] args) throws Exception {
		MainLedAsPojo appl = MainLedAsPojo.createTheSystem();
		appl.getLed().setValue("true");
		Utils.delay(1000);
		appl.getLed().setValue("false");
	}
	
}
