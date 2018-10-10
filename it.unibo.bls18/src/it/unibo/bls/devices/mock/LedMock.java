package it.unibo.bls.devices.mock;

import it.unibo.bls.interfaces.ILed;

public class LedMock implements ILed{
private boolean state = false;

//Factory method
public static ILed createLed(){
	return new LedMock();
}
@Override
public void turnOn(){
	state = true;
}
@Override
public void turnOff() {
	state = false;
}
@Override
public boolean getState(){
	return state;
}

}
