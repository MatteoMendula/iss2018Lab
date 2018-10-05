package it.unibo.bls.devices.arduino;

import java.util.Observable;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.LedObservableModel;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.is.interfaces.protocols.IConnInteraction;

public class LedOnArduino implements IObserver{
protected String PORT_NAME;
protected IConnInteraction conn; 
protected boolean isOn = false;

public static IObserver createLed( String portName){
	LedOnArduino led = new LedOnArduino(portName);
	led.turnOff();
	return led;
}
	public LedOnArduino(String portName) {
		PORT_NAME = portName;
		configure();
	}
	
	protected void configure() {
		try {
			conn = BlsArduinoSysKb.getConnection(null, PORT_NAME);	//IOutputEnvView
  		} catch (Exception e) { e.printStackTrace(); }		
	}

 	protected void turnOn(){
		try { 			
 			if( ! isOn && conn != null   ) { 
  				conn.sendALine(BlsArduinoSysKb.on);	
  				isOn = ! isOn;
			}
  		} catch (Exception e) {
			System.out.println("DeviceLedArduino turnOn ERROR " + e.getMessage() );
 		}
  	}
 	protected void turnOff() {
		try { 			
			if( isOn && conn != null   ) { 
  				conn.sendALine(BlsArduinoSysKb.off);	
  				isOn = ! isOn;
			}
  		} catch (Exception e) {
			System.out.println("DeviceLedArduino turnOff ERROR " + e.getMessage() );
 		}
  	}
/*
 * (non-Javadoc)
 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
 */
	@Override
	public void update(Observable source, Object value) {
		System.out.println(" LedOnArduino update " + value  );
		String v = ""+value;
		if( v.equals("true") ) turnOn();
		else turnOff();
	}
	
 	public void blink() {
 		for( int i=0; i<5; i++ ) {
			UtilsBls.delay(1000);
			turnOn(); 		
			UtilsBls.delay(1000);
			turnOff();
 		}
  	}
	
	/*
	 * Just for a rapid test	
	 */
		
		public static void main(String[] args) {
			IObserver ledOnArduino = LedOnArduino.createLed("COM9");
			ILedObservable led     = LedObservableModel.createLed(ledOnArduino);
			((LedOnArduino)ledOnArduino).blink();
  		}
	
}
