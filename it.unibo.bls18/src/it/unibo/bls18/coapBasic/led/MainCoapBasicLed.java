package it.unibo.bls18.coapBasic.led;

import java.awt.Frame;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import it.unibo.bls.devices.gui.LedAsGui;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.LedObservableModel;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.coapBasic.AsynchListener;
 
/*
 */
public class MainCoapBasicLed {
private CoapServer server;
private CoapClient coapClient;
private AsynchListener asynchListener = new AsynchListener();

private ILedObservable ledmodel;
private IObserver ledgui;
private Frame blsFrame = UtilsBls.initFrame(200,200);

 
	public MainCoapBasicLed(String hostName, int port, String resourceName) {
		createServer(port);
		configure(port, resourceName);
	}

/*
    server(5683)LedCoapResource --> ledmodel --> ledAsGui	
*/
	
	
	protected void configure(int port, String resourceName) {
 		createResourceModel(  ) ;
 		createCoapEvelopeAroundResource( resourceName );
 		createConcreteResource();
 		addConcreteResourceToResourceModel();
  	}

	
	protected void createResourceModel(  ) {
  		ledmodel = LedObservableModel.createLed(  );
 	}

	protected  void createServer(int port) {	//port=5683 default
		server   = new CoapServer(port);
  		server.start();
		System.out.println("MainCoapBasicLed Server started");
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
 	
	protected void synchGet() {
		//Synchronously send the GET message (blocking call)
		CoapResponse coapResp = coapClient.get();
		//The "CoapResponse" message contains the response. 
 //		System.out.println(Utils.prettyPrint(coapResp));
		System.out.println("%%% MainCoapBasicLed ANSWER get " + coapResp.getResponseText());		
	}

	protected void asynchGet() {
 		coapClient.get( asynchListener );
	}
	
	public void put(String v) {
		CoapResponse coapResp = coapClient.put(v, MediaTypeRegistry.TEXT_PLAIN);
		//The "CoapResponse" message contains the response.
		//System.out.println(Utils.prettyPrint(coapResp));
		System.out.println("%%% MainCoapBasicLed NEW LED STATE= " + coapResp.getResponseText());	
 		
	}
	
/*
 *  	
 */
	public static void main(String[] args) throws Exception {
		String hostName     = CommonCoapNames.hostName;
 		String resourceName = CommonCoapNames.resourceName;
 		int port            = CommonCoapNames.port;
 		System.out.println("---------------------------------------------------------------------");
 		System.out.println("WARNING: we will use a resource that requires MQTT. See CommonCoapNames");
 		System.out.println(" ");
 		System.out.println("PLEASE RUN MainCoapControlToLedRest after the starting of this system");
 		System.out.println("You should also RUN CoapLedObserverClient ... ");
 		System.out.println("... and ACTIVATE the nodecode/servers/serverHttpToCoap.js");
 		System.out.println("---------------------------------------------------------------------");
 		UtilsBls.delay(2000);
		MainCoapBasicLed appl = new MainCoapBasicLed(hostName, port, resourceName);
	}	
}
