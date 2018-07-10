package it.unibo.coap;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.*;

public  class WritableResource extends CoapResource {

	public String value = "to be replaced";

    public WritableResource() {
        super("writeme!");
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.respond(value);
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        byte[] payload = exchange.getRequestPayload();
        try {
            value = new String(payload, "UTF-8"); 
            System.out.println("	PUT source="+exchange.getSourceAddress());
            Thread.sleep(5000); 
            exchange.respond(CHANGED, value);
        } catch (Exception e) {
            e.printStackTrace();
            exchange.respond(BAD_REQUEST, "Invalid String");
        }
    }
  }