package it.unibo.coap;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
 

public  class TimeResource extends CoapResource {

    public TimeResource() {
        super("time");
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.respond(String.valueOf(System.currentTimeMillis()));
    }
}