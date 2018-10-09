package it.unibo.bls.devices.raspberry;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.is.interfaces.protocols.IConnInteraction;
import it.unibo.supports.FactoryProtocol;
import it.unibo.system.SituatedSysKb;

/*
 * Receives messages over a network connection working as a server
 * 
 */

public class LedOnRaspberry  extends Thread {
 		
	protected IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
	protected Runtime runtime          = Runtime.getRuntime();
	protected IConnInteraction conn; 	
	
	public LedOnRaspberry() {
        println("------------------------------------------------------------------");
        println("DeviceLedServer in  it.unibo.buttonLedSystem.proxy");
		println("ncores=" + SituatedSysKb.numberOfCores + " mem=" + Runtime.getRuntime().maxMemory());
        println("BLS with a Button on another node");
        println("Please run MainBlsModelWithLedOnRasp ");
        println("-------------------------------------------------------------------");
        blinkForAWhile();
        ScheduledExecutorService sched = Executors.newScheduledThreadPool(1);
        sched.submit(this); 
	}
	
	@Override
	public void run() {
		try {
			initServer();
			doJob();
			//endWork();
 		} catch (Exception e) {
 			//System.out.println("LedOnRaspberry ERROR " + e.getMessage());
 		}
	}
	
	protected void initServer(){
 		while(true)
		try {
			System.setProperty("inputTimeOut","60000");
			FactoryProtocol factoryP = new FactoryProtocol(outEnvView, "TCP", "LedServer");
			//waitForAConnection
			println("LedSereer WAITING FOR A CONNECTION on " + CommonNames.portNum + " inputTimeOut=60 sec" );
			conn = factoryP.createServerProtocolSupport( CommonNames.portNum );
			break;
		} catch (Exception e) {
			println("DeviceLedServer config ERROR " + e.getMessage());
		}		
	}

	/*
	 * The server waits for a command and then it executes the command
	 */
	 
	protected void doJob() throws Exception {
 		while(true){
			this.println("DevLedServer RECEIVING ...");
	 		String cmd = conn.receiveALine();
			this.println("LedSereer RECEIVED: " + cmd);
	 		if( cmd != null )  
	 			execTheCommand(cmd);
 		}
	}	
	
	/*
	 * COMMAND PATTERN
	 */
	protected void execTheCommand(String cmd ){
		if( cmd.contains(CommonNames.cmdTurnOn) ) turnOn();
		else if( cmd.contains(CommonNames.cmdTurnOff))  turnOff();
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
 		new LedOnRaspberry();
 	}

 		
}
 