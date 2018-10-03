package it.unibo.bls.appl;
import java.awt.Frame;

import it.unibo.bls.applLogic.BlsApplicationLogic;
import it.unibo.bls.devices.ButtonAsGui;
import it.unibo.bls.devices.LedAsGui;
import it.unibo.bls.devices.arduino.LedOnArduino;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.LedObservableModel;
import it.unibo.bls.utils.UtilsBls;
 
public class MainBlsModelWithArduino  {

private ILedObservable led;

//Factory method   	
  	public static MainBlsModelWithArduino createTheSystem(){
 		return new MainBlsModelWithArduino();
 	} 	
 	protected MainBlsModelWithArduino( ) {
 		configure();
 	}		
 	protected void configure(){
 		Frame blsFrame = UtilsBls.initFrame(200,200);
		IObserver ledOnArduino = LedOnArduino.createLed("COM9");
		led                    = LedObservableModel.createLed(ledOnArduino);
    	BlsApplicationLogic applLogic = new BlsApplicationLogic(led);
   		ButtonAsGui.createButton( blsFrame, "press", applLogic);
  		led.turnOff();
		blink();
	} 	
 	
 	protected void blink() {
		UtilsBls.delay(1000);
		led.turnOn(); 		
		UtilsBls.delay(1000);
		led.turnOff();
		UtilsBls.delay(1000);
		led.turnOn(); 		
		UtilsBls.delay(1000);
		led.turnOff();
 	}
public static void main(String[] args) {
   MainBlsModelWithArduino sys = createTheSystem();
 }
}