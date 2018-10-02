package it.unibo.bls18.coapBasic;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;

/*
 * Dynamic resource discovery is useful when building a dynamic IoT application, 
 * where it’s not desired to hard-code or manually configure available
 * CoAP servers and their resources. 
 * 
 * multicast can only work within a LAN
 * Use IPv6 (obliviating the use of a NAT) 
 */
public class MainCoapDiscovery {
private static CoapServer server;
 	
	public static void createServer(int port) {	//port=5683 default
		server   = new CoapServer(port);
		server.start();
		System.out.println("Server started");
	}

	/*
	 * Servers that wish to be discoverable must listen on the “All CoAP Nodes” multicast address 
	 * and default port of 5683 (unless a different port is agreed upon in advance).
	 * The multicast “All CoAP Nodes” address is 
	 * 224.0.1.187 for IPv4,  and FF05::FD for IPv6
	 */
	public static void startDiscoverableServer(int port) throws UnknownHostException {
		System.out.println("startDiscoverableServer port=" + port);
		createServer( port );
		InetAddress addr                = InetAddress.getByName("224.0.1.187");
		InetSocketAddress bindToAddress = new InetSocketAddress(addr, port);
		CoapEndpoint multicast          = new CoapEndpoint(bindToAddress);
		//the multicast address is set as a CoapEndpoint to the Californium CoapServer object
		server.addEndpoint(multicast);		
	}
	
	/*
	 *  request to discover CoAP servers and their resources via a CoAP GET
	 */
	public static void discovery( int port ) throws Exception {
	System.out.println("discovery started");
	CoapClient client = new CoapClient("coap://FF05::FD:"+port+"/.well-known/core");  //base URI coap://FF05::FD:5683
 	client.useNONs(); //the request needs to be a NON GET request
			CoapResponse response = client.get();
			if ( response != null ) {
			    System.out.println("discovery Response code: " + response.getCode());
			    System.out.println("discovery Resources: " + response.getResponseText());
			    System.out.println("discovery Options: " + response.getOptions());

			    // get server’s IP address
			    InetAddress addr = response.advanced().getSource();
			    int servport = response.advanced().getSourcePort();
			    System.out.println("discovery Source address: " + addr + ":" + servport);
			} 		
	}
	
	public static void main(String[] args) throws Exception {
//		String resourceName="led";
		int port = 5683;
//		MainCoapDiscovery.startDiscoverableServer(port);
		MainCoapDiscovery.discovery(port);
	}	
}
