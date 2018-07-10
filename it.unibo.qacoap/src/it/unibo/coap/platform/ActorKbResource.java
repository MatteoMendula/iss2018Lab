package it.unibo.coap.platform;
/*******************************************************************************
 * Copyright (c) 2018 DISI University of Bologna.
 * 
 * Contributors:
 *    AN - creator and main architect
 ******************************************************************************/

import static org.eclipse.californium.core.coap.CoAP.ResponseCode.DELETED;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Term;
import it.unibo.qactors.akka.QActor;

public class ActorKbResource extends CoapResource{
	private String curQuery = null;
 	private QActor qa;
	
	public ActorKbResource(String name, QActor qa) {
		super(name);
		this.qa = qa;
		qa.println("ActorKbResource CREATED name=" + name); 
 	}
    @Override
    public void handleGET(CoapExchange exchange) {
//        System.out.println("ActorKblResource handleGET source=" + exchange.getSourcePort());             
        if( curQuery == null ) exchange.respond( "noQueryYet"  );
        else {
        	SolveInfo sol = qa.solveGoal(curQuery);
        	if( sol.isSuccess() )
				try {
					exchange.respond( sol.getSolution().toString()  );
				} catch (NoSolutionException e) {
					exchange.respond( "failure"  );
				}
			else exchange.respond( "failure"  );
        }
		
     }
    @Override
    public void handlePOST(CoapExchange exchange) {
 		curQuery = exchange.getRequestText();
 		try {
 			Term.createTerm(curQuery);
 			exchange.respond(curQuery + " stored");
		} catch (Exception e) {
			curQuery = null;
			exchange.respond("wrong syntax for query");
		}
   } 
	@Override
	public void handlePUT(CoapExchange exchange) {
		handlePOST(exchange);
	}
	@Override
	public void handleDELETE(CoapExchange exchange) {
		qa.println("ActorKblResource handleDELETE "  ) ;
		delete(); // will also call clearAndNotifyObserveRelations(ResponseCode.NOT_FOUND)
		exchange.respond(DELETED);
	}

}
