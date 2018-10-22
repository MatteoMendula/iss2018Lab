package it.unibo.bls18.coap.hexagonal;

import org.eclipse.californium.core.server.resources.CoapExchange;

public class CommonBlsHexagNames {

	public static String hostName     ="localhost"; //"192.168.107.15"; 192.168.0.51//localhost
	public static String hostRaspName ="192.168.0.50";  
	public static String ledResourceName    ="Led";
	public static String buttonResourceName ="Button";
	public static String resourceName ="Led";
	public static int port            = 5683;  
	public static int ledPort         = 8010;  
	public static int buttonPort      = 8020;  
	
	public static String ledUriStr    =  "coap://"+hostName+":"+ledPort+"/"+ledResourceName ;
	public static String ButtonUriStr =  "coap://"+hostName+":"+buttonPort+"/"+buttonResourceName ;

	public static String BlsLedUriStr    =  "coap://"+hostName+":"+port+"/"+ledResourceName ;
	public static String BlsButtonUriStr =  "coap://"+hostName+":"+port+"/"+buttonResourceName ;

	public static final String cmdTurnOn  = "true";
	public static final String cmdTurnOff = "false";
	public static final String buttonCmd  = "pressed";
	
	
	
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
