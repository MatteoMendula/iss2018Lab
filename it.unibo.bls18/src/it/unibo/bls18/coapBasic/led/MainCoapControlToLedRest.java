package it.unibo.bls18.coapBasic.led;

import java.awt.Frame;
import it.unibo.bls.applLogic.BlsApplicationLogicCoap;
import it.unibo.bls.devices.gui.ButtonAsGui;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.utils.UtilsBls;
 
/*
 */
public class MainCoapControlToLedRest {
 
private IObserver applLogic;
private Frame blsFrame    = UtilsBls.initFrame(200,200);

	public MainCoapControlToLedRest(String hostName, int port, String resourceName) {
		configure(hostName, port, resourceName);
	}

	protected void configure(String hostName, int port, String resourceName) {
		createApplicationLogic( hostName,  port,   resourceName );
		createButtonAsFrontEnd( );
  	}
	
	protected void createApplicationLogic( String hostName, int port, String resourceName ) {
    	applLogic = new BlsApplicationLogicCoap( hostName,   port,   resourceName);
 	}

 	protected void createButtonAsFrontEnd( ) {
 		System.out.println("MainCoapControlToLedRest createButtonAsFrontEnd");
		ButtonAsGui.createButton( blsFrame, "press", applLogic);		
}
	

/*
 *  	
 */
	public static void main(String[] args) throws Exception {
		String hostName = "localhost"; //192.168.107.15 //"192.168.137.2";//
 		String resourceName="Led";
 		int port = 5683; //8010;
		new MainCoapControlToLedRest(hostName, port,resourceName);
  	}	
}
 