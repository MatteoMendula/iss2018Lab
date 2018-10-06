package it.unibo.bls18.coapBasic.led.raspberry;

import java.util.Observable;
import it.unibo.bls.interfaces.IObserver;

public class LedMockOnRasp implements IObserver{
private boolean state = false;

//Factory method
public static IObserver createLed(){
	return new LedMockOnRasp();
}
 
protected void turnOn(){
	state = true;
	showLedState();
}
 
protected void turnOff() {
	state = false;
	showLedState();
}
 
protected boolean getState(){
	return state;
}

protected void showLedState() {
	System.out.println(" ---------------------------------- "  );
	System.out.println(" LedMockOnRasp LED=" + state );
	System.out.println(" ---------------------------------- "  );	
}

@Override
public void update(Observable source, Object value) {
	//System.out.println(" LedMockOnRasp update " + value );
	String v = ""+value;
	if( v.equals("true") ) turnOn();
	else turnOff();
}

}
