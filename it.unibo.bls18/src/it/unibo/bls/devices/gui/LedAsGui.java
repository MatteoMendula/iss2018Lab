package it.unibo.bls.devices.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import java.util.Observable;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.LedObservableModel;
import it.unibo.bls.utils.UtilsBls;
 
/*
 * A Led that USES a GUI Panel into a given a Frame
 */
public class LedAsGui implements IObserver {

private Panel p = new Panel(); 
private final Dimension sizeOn  = new Dimension(100,100);
private final Dimension sizeOff = new Dimension(30,30);

private boolean ledGuiState = false;


//Factory method
public static IObserver createLed( Frame frame){
	LedAsGui led = new LedAsGui(frame);
	led.turnOff();
	return led;
}
	public LedAsGui( Frame frame ) {
 		configure(frame); 
  	}	
	protected void configure(Frame frame){
		frame.add(BorderLayout.CENTER,p);
 		p.setBackground(Color.red); 
 		p.setSize( new Dimension(10,10) );
 		frame.validate(); 		 
		p.validate();
 	}		    
 	protected void turnOn(){
 		ledGuiState = true;
 		p.setSize( sizeOn );
		p.validate();
	}
 	protected void turnOff() {
 		ledGuiState = false;
 		p.setSize( sizeOff );
		p.validate();
	}
	
/*
 * 	(non-Javadoc)
 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
 */
	@Override
	public void update(Observable source, Object value) {
// 		System.out.println(" LedAsGui update " + value );
		String v = ""+value;
		if( v.equals("true") ) turnOn();
		else if( v.equals("false") )  turnOff();
		else if( v.equals("switch") ) { if(ledGuiState) turnOff(); else turnOn();}
	}
	
/*
 * Just for a rapid test	
 */
	
	public static void main(String[] args) {
		IObserver ledgui = LedAsGui.createLed(UtilsBls.initFrame(200,200));
		ILedObservable led = LedObservableModel.createLed();
		led.addObserver(ledgui);
		led.turnOff();
		UtilsBls.delay(1000);
		led.turnOn();
		UtilsBls.delay(1000);
		led.turnOff();
		UtilsBls.delay(1000);
		led.turnOn();
	}
}
