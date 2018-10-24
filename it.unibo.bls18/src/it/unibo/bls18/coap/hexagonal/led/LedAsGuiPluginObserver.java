package it.unibo.bls18.coap.hexagonal.led;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import it.unibo.bls18.coap.hexagonal.ResourceLocalGofObserver;
 
/*
 * A Led that USES a GUI Panel into a given a Frame
 */
public class LedAsGuiPluginObserver extends ResourceLocalGofObserver  {  

private Panel p = new Panel(); 
private final Dimension sizeOn  = new Dimension(100,100);
private final Dimension sizeOff = new Dimension(30,30);

private boolean ledGuiState = false;

//Factory method
public static ResourceLocalGofObserver createLed( Frame frame){
	LedAsGuiPluginObserver led = new LedAsGuiPluginObserver(frame);
	led.turnOff();
	return led;
}
	public LedAsGuiPluginObserver( Frame frame ) {
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

 	@Override  //from ResourceLocalGofObserver
 	public void update(String value) {
		System.out.println("LedAsGuiPluginObserver update " + value );
		String v = ""+value;
		if( v.equals("true") ) turnOn();
		else if( v.equals("false") )  turnOff();
		else if( v.equals("switch") ) { if(ledGuiState) turnOff(); else turnOn();}
 	}

/*
 */
//	@Override
//	public void update(Observable source, Object value) {
//		update(""+value);
// 	}
	
}
