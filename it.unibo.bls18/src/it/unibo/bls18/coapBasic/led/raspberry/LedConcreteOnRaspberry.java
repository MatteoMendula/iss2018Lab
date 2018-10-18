package it.unibo.bls18.coapBasic.led.raspberry;

import java.io.IOException;
import java.util.Observable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.coapBasic.led.CommonCoapNames;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.is.interfaces.protocols.IConnInteraction;
import it.unibo.system.SituatedSysKb;

/*
 * Receives messages over a network connection working as a server
 * 
 */

public class LedConcreteOnRaspberry  implements IObserver {
 		
	protected IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
	protected Runtime runtime          = Runtime.getRuntime();
	protected IConnInteraction conn; 	
	
	//Factory method
	public static IObserver createLed(){
		return new LedConcreteOnRaspberry();
	}

	public LedConcreteOnRaspberry() {
        println("------------------------------------------------------------------");
        println("LedConcreteOnRaspberry");
		println("ncores=" + SituatedSysKb.numberOfCores + " mem=" + Runtime.getRuntime().maxMemory());
         println("-------------------------------------------------------------------");
        blinkForAWhile();
 	}
	
 	
	@Override
	public void update(Observable source, Object cmd) {
		execTheCommand(  ""+cmd );
		
	}

 	
	/*
	 * COMMAND PATTERN
	 */
	protected void execTheCommand(String cmd ){
		if( cmd.contains(CommonCoapNames.cmdTurnOn) ) turnOn();
		else if( cmd.contains(CommonCoapNames.cmdTurnOff))  turnOff();
 	}

	
	/*
	 * LED IMPLEMENTATION (just to start ... )
	 */
	protected void turnOn() {
		try {
			runtime.exec("sudo bash led25GpioTurnOn.sh");
		} catch (IOException e) {
			println("LedOnRaspberry turnOn WARNING: perhaps not running on a Raspberry "  );
		}
	}
	protected void turnOff() {
		try {
			runtime.exec("sudo bash led25GpioTurnOff.sh");
		} catch (IOException e) {
			println("LedOnRaspberry turnOff WARNING: perhaps not running on a Raspberry "  );
		}		
	}
	
	protected void println(String m) {
		if( outEnvView != null ) outEnvView.addOutput(m);
		else System.out.println(m);
	}
 
/*
 * Just to see something at start up...
 */
	protected void blinkForAWhile() {
		for( int i=0; i<2; i++) {
			UtilsBls.delay(1000);
			turnOn();
			UtilsBls.delay(1000);
			turnOff();
		}
		
	}

	/*
	 * Just for a rapid check ...	
	 */
	
 	public static void main(String[] args) throws Exception {
 		new LedConcreteOnRaspberry();
 	}


 		
}
 