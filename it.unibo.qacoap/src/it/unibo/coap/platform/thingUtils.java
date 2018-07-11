package it.unibo.coap.platform;

import it.unibo.coap.bls.ButtonThing;
import it.unibo.coap.bls.LedThing;
import it.unibo.qactors.akka.QActor;

public class thingUtils {

	public static void createLedThing(QActor qa, String port ) {
		LedThing.create(Integer.parseInt(port), qa);
	}
	public static void createButtonThing(QActor qa,String port ) {
		ButtonThing.create(Integer.parseInt(port), qa);
	}
}
