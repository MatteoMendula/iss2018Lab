package it.unibo.bls.oo.model;

import java.util.Observable;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObserver;

/*
 * LedObservableModel is an observable logical model of the led
 */
public class LedObservableModel extends Observable implements ILedObservable{
private boolean state = false;

//Factory method
public static ILedObservable createLed(){
	return new LedObservableModel();
}
public static ILedObservable createLed(IObserver observer){
	ILedObservable led = new LedObservableModel();
	led.addObserver(observer);
	return led;
}

@Override
public void turnOn(){
	state = true;
	update();
}
@Override
public void turnOff() {
	state = false;
	update();
}
@Override
public boolean getState(){
	return state;
}

protected void update() {
	System.out.println(" LedObservableModel update state=" + state );
	this.setChanged();
	this.notifyObservers(""+state);		//Always a String
}
/*
 * (non-Javadoc)
 * @see it.unibo.bls.interfaces.IObservable#addObserver(it.unibo.bls.interfaces.IObserver)
 */
@Override
public void addObserver(IObserver observer) {
	if( observer != null ) super.addObserver(observer);
}

}
