/*
=======================================================
WORKING WITH
	 wotNat\qaCoap.js
=======================================================
*/ 
package it.unibo.coap.examples;
 
import java.net.URISyntaxException;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
 
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;
 

public class CoapClientForLocal {

private CoapClient client;
//private int msgNum=0;	  
 

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
	  String resourceName = "5";  
	  /*
	  CoapClientForLocal c = new CoapClientForLocal(); 
	  /*  
  	  c.connect("coap://localhost:5683/"+resourceName);	 
  	  c.workGet();
//	  c.workPost();
//	  c.workPut();
 	  */
 		
		Request request = Request.newGet();
		Response response;
//		request.setURI("localhost:5683/benchmark");
//		request.send();
//		response = request.waitForResponse(1000);
//		System.out.println("received "+response);
 		
		request.setURI("localhost:5683/fibonacci?n=15");  //QUERY
		request.send();
		response = request.waitForResponse(1000);
		System.out.println("received "+response);
		System.out.println("received: "+response.getPayloadString());
 
 		
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
 