package it.unibo.bls18.coapBasic.led;

import java.awt.Frame;
import org.eclipse.californium.core.CoapServer;
import it.unibo.bls.applLogic.BlsApplicationLogic;
import it.unibo.bls.devices.gui.ButtonAsGui;
import it.unibo.bls.devices.gui.LedAsGui;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.LedObservableModel;
import it.unibo.bls.utils.UtilsBls;

 
/*
 * Create a CoaP server and a CoaP client
 * The server refers a LedCoapResource linked to a LedGui
 * The client sends to the server requests to get/put values of the led
 */
public class MainCoapBasicBls {
private CoapServer server;
private ILedObservable ledmodel;
private IObserver ledgui;
private IObserver applLogic;
private Frame blsFrame    = UtilsBls.initFrame(200,200);

	public MainCoapBasicBls(int port, String resourceName) {
 		createServer(port);
		configure(port, resourceName);
		ledmodel.turnOff();
	}

/*
         server(5683)LedCoapResource --> ledmodel --> ledAsGui	
         								    ^
         								    |
         				ButtonAsGui --> BlsApplicationLogic    
*/
	
	protected void configure(int port, String resourceName) {
 		createResourceModel(  ) ;
 		createCoapEvelopeAroundResource( resourceName );
 		createConcreteResource();
 		addConcreteResourceToResourceModel();
		createApplicationLogic(  );
		createButtonAsFrontEnd( );
  	}
	
	protected  void createServer(int port) {	//port=5683 default
		server   = new CoapServer(port);
		server.start();
		System.out.println("MainCoapBasicLed Server started");
	}
	protected void createResourceModel(  ) {
  		ledmodel = LedObservableModel.createLed(  );
 	}
	protected void createCoapEvelopeAroundResource( String resourceName ) {
		//Create a LedCoapResource that makes reference to the LedObservableModel
		LedCoapResource ledResource = new LedCoapResource(resourceName,ledmodel) ;
		server.add( ledResource );
 	}
	protected void createConcreteResource( ) {
		ledgui  = LedAsGui.createLed(blsFrame);		
	}
	protected void addConcreteResourceToResourceModel() {
		ledmodel.addObserver(ledgui);
	}
	
	protected void createApplicationLogic(  ) {
    	applLogic = new BlsApplicationLogic(ledmodel);
 	}
 	protected void createButtonAsFrontEnd( ) {
		ButtonAsGui.createButton( blsFrame, "press", applLogic);		
}
	
/*
 *  	
 */
	public static void main(String[] args) throws Exception {
  		String resourceName = CommonCoapNames.resourceName;
 		int port            = CommonCoapNames.port;
 		System.out.println("---------------------------------------------------------------------");
 		System.out.println("PLEASE RUN CoapLedObserverClient after the starting of this system");
 		System.out.println("---------------------------------------------------------------------");
 		UtilsBls.delay(2000);
 		MainCoapBasicBls appl = new MainCoapBasicBls(port,resourceName);
	}	
}

 