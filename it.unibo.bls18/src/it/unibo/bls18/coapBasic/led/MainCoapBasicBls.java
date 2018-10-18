package it.unibo.bls18.coapBasic.led;

import java.awt.Frame;
import org.eclipse.californium.core.CoapClient;
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
private CoapClient coapClient;

private ILedObservable ledmodel;
private IObserver ledgui;
private IObserver applLogic;
private Frame blsFrame    = UtilsBls.initFrame(200,200);

	public MainCoapBasicBls(int port, String resourceName) {
 		createServer(port);
		createClient(port,resourceName);
		configure(port, resourceName);
		ledmodel.turnOff();
	}

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
	
	public CoapClient createClient(int port, String resourceName) {
		String hostName     = CommonCoapNames.hostName;
	
 		coapClient   =  new CoapClient("coap://"+hostName+":"+port+"/"+resourceName);
 		System.out.println("MainCoapBasicLed Client started");
		return coapClient;
	}
	
	protected void createResourceModel(  ) {
  		ledmodel = LedObservableModel.createLed(  );
 	}
	protected void createApplicationLogic(  ) {
    	applLogic = new BlsApplicationLogic(ledmodel);
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
 	protected void createButtonAsFrontEnd( ) {
		ButtonAsGui.createButton( blsFrame, "press", applLogic);		
}
	
/*
 *  	
 */
	public static void main(String[] args) throws Exception {
 		String resourceName="Led";
 		int port = 5683; //8010;
		MainCoapBasicBls appl = new MainCoapBasicBls(port,resourceName);
	}	
}

/*
curl http://localhost:5683/led
*/