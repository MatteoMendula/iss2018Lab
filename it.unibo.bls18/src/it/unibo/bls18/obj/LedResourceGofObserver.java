package it.unibo.bls18.obj;
import java.awt.Frame;

import it.unibo.bls.devices.gui.LedAsGui;
import it.unibo.bls.utils.UtilsBls;
 
public class LedResourceGofObserver  extends ResourceLocalObserver {
	LedAsGui led ;
	
	public LedResourceGofObserver( ) {
 		Frame frame = UtilsBls.initFrame(200,200);
		led = (LedAsGui) LedAsGui.createLed(frame);
 		showMsg("	LedResourceGofObserver CREATED");
// 		blinkForAwhile();
	} 
	
//	private void blinkForAwhile() {
// 		for( int i=1; i<=3; i++ ) {
//			Utils.delay(800);
//			led.turnOn();
//			Utils.delay(800);
//			led.turnOff();
// 		}
//		
//	}
	/*
	 * This can be used to control a physical device
	 */
	@Override
	public void update(String v) {
		System.out.println("	LedResourceObserver update ... " + v);		
//		if( v.equals("true")) led.turnOn();
//		else led.turnOff();
	}

}
