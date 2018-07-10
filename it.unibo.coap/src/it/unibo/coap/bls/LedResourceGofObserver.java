package it.unibo.coap.bls;

import java.awt.Frame;
import it.unibo.bls.devices.LedAsGui;
import it.unibo.bls.utils.Utils;
import it.unibo.qactors.akka.QActor;


public class LedResourceGofObserver  extends ResourceLocalObserver implements IResourceLocalObserver{
	LedAsGui led ;
	public LedResourceGofObserver(QActor qa) {
		super(qa);
		Frame frame = Utils.initFrame(200,200);
		led = (LedAsGui) LedAsGui.createLed(frame);
 		showMsg("LedResourceGofObserver CREATED");
//		Utils.delay(100);
//		led.turnOn();
//		Utils.delay(500);
		led.turnOff();
	} 
	/*
	 * This can be used to control a physical device
	 */
	@Override
	public void update(String v) {
		//System.out.println("	LedResourceObserver ... " + v);		
		if( v.equals("true")) led.turnOn();
		else led.turnOff();
	}

}
