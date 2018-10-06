package it.unibo.bls18.coapBasic.led.raspberry;

import java.awt.Frame;
import org.eclipse.californium.core.CoapServer;
import it.unibo.bls.applLogic.BlsApplicationLogicCoap;
import it.unibo.bls.devices.ButtonAsGui;
import it.unibo.bls.devices.LedAsGui;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.LedObservableModel;
import it.unibo.bls.utils.UtilsBls;
 
 
/*
 */
public class MainCoapControlToLedRest {
//private CoapServer server;

//private ILedObservable ledmodel;
//private IObserver ledgui;
private IObserver applLogic;
private Frame blsFrame    = UtilsBls.initFrame(200,200);

	public MainCoapControlToLedRest(String hostName, int port, String resourceName) {
// 		createServer(port);
		configure(hostName, port, resourceName);
//		ledmodel.turnOff();
	}

	protected void configure(String hostName, int port, String resourceName) {
// 		createResourceModel(  ) ;
// 		createCoapEvelopeAroundResource( resourceName );
// 		createConcreteResource();
// 		addConcreteResourceToResourceModel();
		createApplicationLogic( hostName,  port,   resourceName );
		createButtonAsFrontEnd( );
  	}
	
//	protected  void createServer(int port) {	//port=5683 default
//		server   = new CoapServer(port);
//		server.start();
//		System.out.println("MainCoapBasicLed Server started");
//	}
// 	protected void createResourceModel(  ) {
//  		ledmodel = LedObservableModel.createLed(  );
// 	}
	protected void createApplicationLogic( String hostName, int port, String resourceName ) {
    	applLogic = new BlsApplicationLogicCoap( hostName,   port,   resourceName);
 	}
//	protected void createCoapEvelopeAroundResource( String resourceName ) {
//		//Create a LedCoapResource that makes reference to the LedObservableModel
//		LedCoapResource ledResource = new LedCoapResource(resourceName,ledmodel) ;
//		server.add( ledResource );
// 	}
//	protected void createConcreteResource( ) {
//		ledgui  = LedAsGui.createLed(blsFrame);		
//	}
//	protected void addConcreteResourceToResourceModel() {
//		ledmodel.addObserver(ledgui);
//	}
 	protected void createButtonAsFrontEnd( ) {
 		System.out.println("MainCoapBasicLed createButtonAsFrontEnd");
		ButtonAsGui.createButton( blsFrame, "press", applLogic);		
}
	
  	
	
/*
 *  	
 */
	public static void main(String[] args) throws Exception {
		String hostName = "192.168.137.2";//"localhost"; //192.168.107.15
 		String resourceName="Led";
 		int port = 5683; //8010;
		new MainCoapControlToLedRest(hostName, port,resourceName);
  	}	
}
 