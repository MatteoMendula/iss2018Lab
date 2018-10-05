package it.unibo.bls.raspberry.components;

import it.unibo.buttonLed.components.DeviceButtonImpl;
import it.unibo.gpio.base.GpioOnPi4j;
import it.unibo.is.interfaces.IOutputView;
import it.unibo.system.SituatedSysKb;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
   
/*
 * =======================================================================
 * The button is implemented as an observable by using the library pi4j
 * =======================================================================
 */
public class DeviceButtonPi4J extends DeviceButtonImpl   {
protected GpioPinDigitalInput device;
	public DeviceButtonPi4J(String name,  IOutputView outView , int pinNum ) {
 		super(name,outView);
		com.pi4j.io.gpio.Pin buttonGpioPinNum = GpioOnPi4j.getPin(pinNum);
		println("DeviceButtonPi4J pinNum="  + pinNum + " gpioPinNum=" + buttonGpioPinNum);
		/*
		 * To access a GPIO pin with Pi4J, we must first provision the pin. 
		 * Provisioning configures the pin based on how we intend to use it. 
		 * Provisioning can automatically export the pin, set its direction, 
		 * and setup any edge detection for interrupt based events.
		 * We provision the gpio pin as an input pin with its 
		 * internal pull down resistor enabled
 		 */
		device = GpioOnPi4j.controller.provisionDigitalInputPin(
				buttonGpioPinNum, PinPullResistance.PULL_DOWN);		
		println("DeviceButtonPi4J register Pi4jHandler "  );
 		device.addListener( new Pi4jHandler( ) );		
	}
	/*
	 * ----------------------------------------------------------------
	 * Pi4jHandler class 
	 * An adapter from a pi4j GpioPinListenerDigital to a DeviceButtonImpl (execAction)
	 * ----------------------------------------------------------------
	 */
 	protected class Pi4jHandler implements GpioPinListenerDigital{
  		@Override
		public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
  			PinState input = event.getState();	//HIGH or LOW
  			println("DeviceButtonPi4J Pi4jHandler= "  + ""+input );
  			if( input.isLow() ) return;
  			String s = input.toString().toLowerCase();
			execAction( s );		
		}	
	}    
 	
 	public void terminate(){
  		device.removeAllListeners();
 	}
 	
	/*
	 * --------------------------------------
	 * Main (rapid check)
	 * --------------------------------------
	 */
	public static void main(String args[]) throws Exception {
//  		IButton button = 
  				new DeviceButtonPi4J("btn",  SituatedSysKb.standardOutView ,   24 );
  		System.out.println("================================================================");
  		System.out.println("Using the Pi4j library to handle the IButton on gpio-18 in Java");
 		System.out.println("================================================================");
 		Thread.sleep(7000);
 	}

}