package it.unibo.bls.raspberry.components;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import it.unibo.bls.highLevel.interfaces.IDevLed.LedColor;
import it.unibo.buttonLed.components.DeviceLedImpl;
import it.unibo.gpio.base.GpioOnPi4j;
import it.unibo.is.interfaces.IOutputEnvView;

/*
 * =======================================================================
 * The led is implemented by using the library pi4j
 * =======================================================================
 */
public class DeviceLedPi4j extends DeviceLedImpl  {
protected GpioPinDigitalOutput ledpi4j; 
 	public DeviceLedPi4j( String name,  IOutputEnvView outEnvView, LedColor ledColor, int pinNum ) throws Exception{
		super(name, outEnvView, ledColor);
 		myconfigure(pinNum);
 	}
	protected void myconfigure(int ledPinNum) throws Exception{
		/*
		 * To access a GPIO pin with Pi4J, we must first provision the pin. 
		 * Provisioning configures the pin based on how we intend to use it. 
		 * Provisioning can automatically export the pin, set its direction, 
		 * and setup any edge detection for interrupt based events.
		 */
		ledpi4j = GpioOnPi4j.controller.provisionDigitalOutputPin( GpioOnPi4j.getPin(ledPinNum) );		
	}
	@Override
	public void turnOn() {
		ledpi4j.high();
		super.turnOn();
	}
	@Override
	public void turnOff() {
 		if(ledpi4j!=null) ledpi4j.setState(PinState.LOW);
		super.turnOff();
	}	
}