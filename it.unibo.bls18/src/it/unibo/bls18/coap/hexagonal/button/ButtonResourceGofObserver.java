package it.unibo.bls18.coap.hexagonal.button;

import it.unibo.bls18.coap.hexagonal.ResourceLocalGofObserver;
 

public class ButtonResourceGofObserver extends ResourceLocalGofObserver  {

 	public ButtonResourceGofObserver( ) {
  		showMsg("ButtonResourceGofObserver CREATED");
 	}
 	/*
 	 * Called by Coap
 	 * @see it.unibo.bls18.coap.hexagonal.IResourceLocalObserver#update(java.lang.String)
 	 */
 	@Override
	public void update(String v) {
		System.out.println("	ButtonResourceGofObserver (controller, bl) updated with: " + v);		
  	}
 	/*
 	 * Called by
 	 */
//	@Override
//	public void update(Observable source, Object v) {
//		System.out.println("	ButtonResourceGofObserver update/2: " + v);			 		
//	}

}
