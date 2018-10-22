package it.unibo.bls18.coapBasic.led.raspberry;
import org.eclipse.californium.core.CoapServer;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.LedObservableModel;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.coapBasic.led.CommonCoapNames;
import it.unibo.bls18.coapBasic.led.LedCoapResource;
 
/*
 */
public class MainLedCoapOnRaspberry {
private CoapServer server;

private ILedObservable ledmodel;
private IObserver ledonrasp;
 
	public MainLedCoapOnRaspberry(String hostName, int port, String resourceName) {
		createServer(port);
		configure(port, resourceName);
	}

	protected void configure(int port, String resourceName) {
 		createResourceModel(  ) ;
 		createCoapEvelopeAroundResource( resourceName );
 		createConcreteResource();
 		addConcreteResourceToResourceModel();
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
 		System.out.println("PLEASE RUN MainCoapClientForLedOnRasp after the starting of this system");
 		System.out.println("---------------------------------------------------------------------");
 		UtilsBls.delay(2000);

 		
 		MainLedCoapOnRaspberry appl = new MainLedCoapOnRaspberry(hostName, port, resourceName);
		
//		MainCoapClient client = new MainCoapClient(CommonCoapNames.hostRaspName, port, resourceName);
//		client.synchGet();
// 		for( int i=0; i<3; i++ ) {
//			Thread.sleep(500);
//			client.put("true");
//			Thread.sleep(1000);
//			client.put("false");
// 		}
//		System.out.println("LAST VALUE:");
 	}	
}
