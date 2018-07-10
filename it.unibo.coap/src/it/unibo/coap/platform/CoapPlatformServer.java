package it.unibo.coap.platform;

/*******************************************************************************
 * Copyright (c) 2018 DISI University of Bologna.
 * 
 * Contributors:
 *    AN - creator and main architect
 ******************************************************************************/

//import java.net.Inet4Address;
//import java.net.InetAddress;
//import java.net.InetSocketAddress;
import java.net.SocketException;
import org.eclipse.californium.core.CoapServer;
//import org.eclipse.californium.core.network.CoapEndpoint;
//import org.eclipse.californium.core.network.EndpointManager;
//import org.eclipse.californium.core.network.config.NetworkConfig;


public class CoapPlatformServer extends CoapServer {
//	private static final int COAP_PORT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT);

    /**
     * Add individual endpoints listening on default CoAP port on all IPv4 addresses of all network interfaces.
     */
//    private void addEndpoints() {
//        // add endpoints on all IP addresses
//    	for (InetAddress addr : EndpointManager.getEndpointManager().getNetworkInterfaces()) {
//    		// only binds to IPv4 addresses and localhost
//			if (addr instanceof Inet4Address || addr.isLoopbackAddress()) {
//				InetSocketAddress bindToAddress = new InetSocketAddress(addr, COAP_PORT);
//				addEndpoint(new CoapEndpoint(bindToAddress));
//			}
//		}
//    }

    /*
     * Constructor for a new ChannelResource server.  
     */
    public CoapPlatformServer() throws SocketException {        
    	System.out.println("	adding the 'qachannel'  resource");   
        add( new ChannelResource("qachannel", null) );
    }

    /*
     * Application entry point.
     */
    public static void main(String[] args) {       
        try {
            // create server
            CoapPlatformServer server = new CoapPlatformServer();
            //server.addEndpoints();
             server.start();
        } catch (SocketException e) {
            System.err.println("Failed to initialize server: " + e.getMessage());
        }
    }

}
//coap://localhost:5683/helloWorld