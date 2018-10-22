package it.unibo.bls18.coapBasic.led;

import org.eclipse.californium.core.server.resources.CoapExchange;

public class CommonCoapNames {

	public static String mqttUrl      = "tcp://localhost:1883"; //"ws://broker.hivemq.com:8000/mqtt"; tcp://m2m.eclipse.org:1883
	public static String mqttClientId ="LedCoapResourceNat";
	public static String mqttTopic    ="unibo/bls18";

	
	public static String hostName     ="localhost"; //"192.168.107.15"; 192.168.0.51//localhost
	public static String hostRaspName ="192.168.0.50";  
	public static String ledResourceName    ="Led";
	public static String buttonResourceName ="Button";
	public static String resourceName ="Led";
	public static int port            = 5683; //8010;
	
	public static String ledUriStr =  "coap://"+hostName+":"+port+"/"+ledResourceName ;

	public static final String cmdTurnOn  = "true";
	public static final String cmdTurnOff = "false";
	
	
	
	public static void showExchange(CoapExchange exchange) {
		System.out.println("---------------------------------------------------" ) ;
		System.out.println("request code	=" + exchange.getRequestCode());
		System.out.println("request options	=" + exchange.getRequestOptions());
		System.out.println("uri path        =" + exchange.getRequestOptions().getUriPathString());
		System.out.println("source address	=" + exchange.getSourceAddress());
		System.out.println("request text	=" + exchange.getRequestText());
		System.out.println("source port  	=" + exchange.getSourcePort());
		System.out.println("---------------------------------------------------" ) ;
	}
}
