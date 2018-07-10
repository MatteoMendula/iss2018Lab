package it.unibo.coap.platform;
/*******************************************************************************
 * Copyright (c) 2018 DISI University of Bologna.
 * 
 * Contributors:
 *    AN - creator and main architect
 ******************************************************************************/

import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CHANGED;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.DELETED;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.server.resources.CoapExchange;
import it.unibo.qactors.QActorMessage;
import it.unibo.qactors.akka.QActor;

public class ChannelResource extends CoapResource{
	private String curMsg = "noMsgYet";
	private int count=1;
	private QActor qa;
	
	public ChannelResource(String name, QActor qa) {
		super(name);
		this.qa = qa;
 		setObservable(true); 				// enable observing	!!!!!!!!!!!!!!
		setObserveType(Type.CON); 			// configure the notification type to CONs
		getAttributes().setObservable();	// mark observable in the Link-Format		
 	}
    @Override
    public void handleGET(CoapExchange exchange) {
//    	System.out.println("ChannelResource	handleGET source=" + exchange.getSourcePort());              
        // respond to the request
    	exchange.setMaxAge(600); //the Max-Age value should match the update interval
//		exchange.respond("ChannelResource GET:" + curMsg + " counter=" + count++); //for testing
		exchange.respond( curMsg  );
     }
/*
Calling the CoapResource.changed() method causes this Calfornium CoAP server to
automatically send a subsequent response (an update) to the initial GET request for data
on the observable resource
*/
    @Override
    public void handlePOST(CoapExchange exchange) {
 		curMsg = exchange.getRequestText();
//		qa.println("ChannelResource handlePOST " + curMsg ) ;	
        // respond to the request
		exchange.respond(CHANGED);
		changed(); // notify all observers FROM NOW ON????
 		try {
			QActorMessage msg = new QActorMessage( curMsg );
			qa.println("ChannelResource handlePOST qactor msg= " + msg ) ;
			qa.sendMsg(msg.msgSender(), msg.msgId(), msg.msgReceiver(), msg.msgType(), msg.msgContent());
		} catch (Exception e) {
			qa.println("ChannelResource handlePOST a user msg " + curMsg  ) ;
		}
   } 
	@Override
	public void handlePUT(CoapExchange exchange) {
 		curMsg = exchange.getRequestText();
		qa.println("ChannelResource handlePUT " + curMsg ) ;	
 		exchange.respond(CHANGED);
//		changed(); // notify all observers
	}
	@Override
	public void handleDELETE(CoapExchange exchange) {
		qa.println("ChannelResource handleDELETE "  ) ;
		delete(); // will also call clearAndNotifyObserveRelations(ResponseCode.NOT_FOUND)
		exchange.respond(DELETED);
	}

}
