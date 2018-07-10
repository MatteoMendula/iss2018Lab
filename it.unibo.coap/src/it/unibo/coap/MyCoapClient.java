/*
=======================================================
WORKING WITH
	 wotNat\qaCoap.js
=======================================================
*/ 
package it.unibo.coap;
import java.net.URI;
import java.net.URISyntaxException;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.server.resources.ConcurrentCoapResource;
//import org.eclipse.californium.core.CoapClient;
//import org.eclipse.californium.core.CoapResponse;
//import org.eclipse.californium.core.Utils;
//import org.eclipse.californium.core.coap.MediaTypeRegistry;
//import org.json.JSONObject;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Request;

public class MyCoapClient {

private CoapClient client;
//private int msgNum=0;	  
private static String resourceName = "writeme!";

  public void connect(String uristr) throws URISyntaxException {
//	  URI uri = new URI(uristr);
	  client  = new  CoapClient(uristr);
	  
  }
  
  public void workPost() {
	  System.out.println( "workPost" );
	  CoapResponse resp = client.post("data(10)", MediaTypeRegistry.TEXT_PLAIN);
		System.out.println("RESPONSE workPost isSuccess: " + resp.isSuccess());
		System.out.println("RESPONSE workPost options: " + resp.getOptions());
		System.out.println("RESPONSE workPost code: " + resp.getCode());
  }
  public void workPut() {
	  System.out.println( "workPut" );
	  CoapResponse resp = client.put("data(10)", MediaTypeRegistry.TEXT_PLAIN);
		System.out.println("RESPONSE workPut isSuccess  : " + resp.isSuccess());
		System.out.println("RESPONSE workPut getOptions : " + resp.getOptions());
		System.out.println("RESPONSE workPut getCode    : " + resp.getCode());
		System.out.println("RESPONSE workPut text       : " + resp.getResponseText() );
  }
  public void workGet() {
	  System.out.println( "workGet" );
	  String content1 = client.get().getResponseText();
	  System.out.println("RESPONSE workGet : " + content1);  
  }
  
  
  public void work() throws InterruptedException {
	    for(int i=1; i<=3; i++){
		    CoapResponse response = client.get();		 
		    if ( response != null ) {
//		      JSONObject jsonObject = new JSONObject( getResponse(response) );
//		      int co2Val  = jsonObject.getInt("co2");		      
//		      String outS = "msg( sensor, event, co2, none, " + co2Val + ", " + msgNum++ + " )";		      
		      System.out.println( response.getResponseText() );		      
		      Thread.sleep(2000);
		    }
	    }
  }

  public String getResponse( CoapResponse response ) {
      // Calfornium class provides response details:
//      System.out.println(Utils.prettyPrint(response));
//      System.out.println(response.getCode());
//      System.out.println(response.getOptions());
      String jsonString = response.getResponseText();
      System.out.println(jsonString);
      return jsonString;
  }
  
  public static void main(String args[])  throws Exception {	
	  CoapServer server = new CoapServer(8012);
	  server.add( ConcurrentCoapResource.createConcurrentCoapResource(1, new WritableResource()).add( new TimeResource() ) );
	  server.start();
	  
	  MyCoapClient c = new MyCoapClient(); 
	  /* */
  	  c.connect("coap://localhost:8012/"+resourceName);	 
  	  c.workGet();
//	  c.workPost();
	  c.workPut();
 	  
 	   //coap://vs0.inf.ethz.ch:5683/
	   //coap://coap.me:5683/
// 		CoapClient client = new CoapClient("coap://iot.eclipse.org:5683/obs");
// 		CoapClient client = new CoapClient("coap://californium.eclipse.org:5683/obs"); //https://iot.eclipse.org/getting-started
//		System.out.println("SYNCHRONOUS 1 " + client);
		
//		Request request = new Request(Code.GET);
//		CoapResponse coapResp=client.advanced(request);
//		System.out.println("RESPONSE 1: " + Utils.prettyPrint(coapResp));
		
 		
 /*
		System.out.println("SYNCHRONOUS 2 " + client);  
		// synchronous
		String content1 = client.get().getResponseText();
		System.out.println("RESPONSE 2: "   );
		System.out.println(  content1 );
		System.out.println("RESPONSE 2b: "   );
		System.out.println( Utils.prettyPrint(client.get()));

//		CoapClient client2 = new CoapClient("coap://iot.eclipse.org:5683/obs");
		CoapResponse resp = client.put("payload", MediaTypeRegistry.TEXT_PLAIN); // for response detail
		System.out.println("RESPONSE 3: " + resp.isSuccess());
		System.out.println("RESPONSE 4: " + resp.getOptions());
		System.out.println("RESPONSE 5 CODE: " + resp.getCode());
*/
 		
  }
}
/*
 * npm install -g npm-windows-upgrade
 * npm-windows-upgrade
 * 
 * git clone https://github.com/PeterEB/quick-demo.git
 * npm install
 * npm start
 */
 