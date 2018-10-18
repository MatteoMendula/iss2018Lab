package it.unibo.bls18.coapBasic.led.raspberry;

//import org.eclipse.californium.core.CoapClient;
//import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.CoapServer;
//import org.eclipse.californium.core.coap.MediaTypeRegistry;
//import it.unibo.bls.applLogic.BlsApplicationLogic;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.LedObservableModel;
//import it.unibo.bls18.coapBasic.AsynchListener;
import it.unibo.bls18.coapBasic.led.CommonCoapNames;
import it.unibo.bls18.coapBasic.led.LedCoapResource;
 
/*
 */
public class MainLedCoapOnRaspberry {
private CoapServer server;
//private CoapClient coapClient;
//private AsynchListener asynchListener = new AsynchListener();

private ILedObservable ledmodel;
private IObserver ledonrasp;
//private IObserver applLogic;
 
	public MainLedCoapOnRaspberry(String hostName, int port, String resourceName) {
		createServer(port);
//		createClient(hostName, port,resourceName);
		configure(port, resourceName);
	}

	protected void configure(int port, String resourceName) {
 		createResourceModel(  ) ;
 		createCoapEvelopeAroundResource( resourceName );
 		createConcreteResource();
 		addConcreteResourceToResourceModel();
//		createApplicationLogic(  );
   	}
 
	protected  void createServer(int port) {	//port=5683 default
		server   = new CoapServer(port);
		server.start();
		System.out.println("MainLedCoapOnRaspberry Server started");
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
		System.out.println("%%% MainLedCoapOnRaspberry createConcreteResource "  );	
		ledonrasp  = LedConcreteOnRaspberry.createLed();		
	}
	protected void addConcreteResourceToResourceModel() {
		ledmodel.addObserver(ledonrasp);
	}
//	protected void createApplicationLogic(  ) {
//    	applLogic = new BlsApplicationLogic(ledmodel);
// 	}
 
//	public void createClient(String hostName, int port, String resourceName) {
//		coapClient=  new CoapClient("coap://"+hostName+":"+port+"/"+resourceName);
//		System.out.println("MainLedCoapOnRaspberry Client started");
// 	}
//	
//	protected void synchGet() {
//		//Synchronously send the GET message (blocking call)
//		CoapResponse coapResp = coapClient.get();
//		//The "CoapResponse" message contains the response. 
// //		System.out.println(Utils.prettyPrint(coapResp));
//		System.out.println("%%% MainLedCoapOnRaspberry ANSWER get " + coapResp.getResponseText());		
//	}
//
//	protected void asynchGet() {
// 		coapClient.get( asynchListener );
//	}
//	
//	public void put(String v) {
//		CoapResponse coapResp = coapClient.put(v, MediaTypeRegistry.TEXT_PLAIN);
//		//The "CoapResponse" message contains the response.
// 		//System.out.println(Utils.prettyPrint(coapResp));
//		System.out.println("%%% MainLedCoapOnRaspberry ANSWER put " + coapResp.getResponseText());		
//	}
	
/*
 *  	
 */
	public static void main(String[] args) throws Exception {
		String hostName     = CommonCoapNames.hostName;
 		String resourceName = CommonCoapNames.resourceName;
 		int port            = CommonCoapNames.port;
		MainLedCoapOnRaspberry appl = new MainLedCoapOnRaspberry(hostName, port, resourceName);
		
		MainCoapClient client = new MainCoapClient(CommonCoapNames.hostRaspName, port, resourceName);
		client.synchGet();
 		for( int i=0; i<3; i++ ) {
			Thread.sleep(500);
			client.put("true");
			Thread.sleep(1000);
			client.put("false");
 		}
		System.out.println("LAST VALUE:");
 	}	
}
