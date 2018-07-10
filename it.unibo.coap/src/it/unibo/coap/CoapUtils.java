/*
=======================================================
WORKING WITH
	 wotNat\qaCoap.js
=======================================================
*/ 
package it.unibo.coap;

import it.unibo.qactors.akka.QActor;
import java.net.URI;
import java.net.URISyntaxException;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.json.JSONObject;
 
public class CoapUtils {
 private static CoapClient client;
 private static QActor actor;
 
 public static void connect(QActor qa, String uriStr) throws URISyntaxException 	{
	    URI uri = new URI(uriStr); //"coap://localhost:5683/co2"
	    actor   = qa;
	    client  = new  CoapClient(uri);
 }
 
 public static void getValue(String tag){
	 String outS = "";
	 //actor.println("CoapUtils getValue " + tag + " " + actor.getName() + " client=" + client);
	 CoapResponse response = client.get();
	 //actor.println("CoapUtils response " + response);
	 if ( response != null ) {
	      String jsonString = response.getResponseText();
 	      //actor.println("CoapUtils getValue=" + jsonString);
 	      JSONObject jsonObject = new JSONObject(jsonString);
	      int co2Val  = jsonObject.getInt("co2");	      
	      outS = ""+co2Val;        
 	 }else{  outS = "unknown"; 	 }
	 actor.emit("sensor", "sensor(co2,"+outS+")");
 }

}
 