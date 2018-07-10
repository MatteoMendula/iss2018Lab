package it.unibo.coap;

/*******************************************************************************
 * Copyright (c) 2015 Institute for Pervasive Computing, ETH Zurich and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * 
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 * 
 * Contributors:
 *    Matthias Kovatsch - creator and main architect
 *    Kai Hudalla (Bosch Software Innovations GmbH) - add endpoints for all IP addresses
 ******************************************************************************/


import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.EndpointManager;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.ConcurrentCoapResource;

public class MyCoapServer extends CoapServer {
	private static final int COAP_PORT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT);
    /*
     * Application entry point.
     */
    public static void main(String[] args) {       
        try {
            // create server
            MyCoapServer server = new MyCoapServer();
            // add endpoints on all IP addresses
//            server.addEndpoints();
            server.start();

        } catch (SocketException e) {
            System.err.println("Failed to initialize server: " + e.getMessage());
        }
    }

    /**
     * Add individual endpoints listening on default CoAP port on all IPv4 addresses of all network interfaces.
     */
    private void addEndpoints() {
    	for (InetAddress addr : EndpointManager.getEndpointManager().getNetworkInterfaces()) {
    		// only binds to IPv4 addresses and localhost
			if (addr instanceof Inet4Address || addr.isLoopbackAddress()) {
				InetSocketAddress bindToAddress = new InetSocketAddress(addr, COAP_PORT);
				addEndpoint(new CoapEndpoint(bindToAddress));
			}
		}
    }

    /*
     * Constructor for a new Hello-World server. Here, the resources
     * of the server are initialized.
     */
    public MyCoapServer() throws SocketException {        
        // provide an instance of a Hello-World resource
    	System.out.println("	adding a resource");   
//        add(new HelloWorldResource());
    	
//    	add( new TimeResource(), new WritableResource() ); 
    	 
//    	add( new WritableResource().add( new TimeResource() ) ); 
//Single thread    	
    	add( ConcurrentCoapResource.createConcurrentCoapResource(1, new WritableResource()).add( new TimeResource() ) );
/*    	
    	add( 
    			new CoapResource("A").add(
    					new CoapResource("A1").add(
    						new CoapResource("A1_a"),
    						new CoapResource("A1_b"),
    						new CoapResource("A1_c"),
    						new CoapResource("A1_d")
    					),
    					new CoapResource("A2").add(
    						new CoapResource("A2_a"),
    						new CoapResource("A2_b"),
    						new CoapResource("A2_c"),
    						new CoapResource("A2_d")
    					)
    				),
    				new CoapResource("B").add(
    					new CoapResource("B1").add(
    						new CoapResource("B1_a"),
    						new CoapResource("B1_b")
    					)
    				),
    				new CoapResource("C"),
    				new CoapResource("D")
    			);
*/    			
    }

    /*
     * Definition of the Hello-World Resource
     */
    class HelloWorldResource extends CoapResource {
        
        public HelloWorldResource() {           
            // set resource identifier
            super("helloWorld");            
            // set display name
            getAttributes().setTitle("Hello-World Resource");
        }

        @Override
        public void handleGET(CoapExchange exchange) {
            System.out.println("	handleGET");           
            // respond to the request
            exchange.respond("Hello World!");
        }
        @Override
        public void handlePOST(CoapExchange exchange) {
            System.out.println("	handlePOST");
            // respond to the request
            exchange.respond("DonePOST!");
        }
    }
}
//coap://localhost:5683/helloWorld