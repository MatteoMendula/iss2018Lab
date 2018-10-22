package it.unibo.bls18.coap.hexagonal.button;

import it.unibo.bls18.coap.hexagonal.ResourceLocalGofObserver;
 

public class ButtonResourceGofObserver extends ResourceLocalGofObserver  {

 	public ButtonResourceGofObserver( ) {
  		showMsg("ButtonResourceGofObserver CREATED");
 	}
 	/*
 	 * Called by Coap
 	 */
 	@Override
	public void update(String v) {
		System.out.println("	ButtonResourceGofObserver (controller, bl) updated with: " + v);		
  	}

}
