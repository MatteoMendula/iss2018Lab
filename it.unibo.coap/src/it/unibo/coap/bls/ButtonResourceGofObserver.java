package it.unibo.coap.bls;

import it.unibo.qactors.akka.QActor;

public class ButtonResourceGofObserver extends ResourceLocalObserver  {

 	public ButtonResourceGofObserver(QActor qa) {
 		super(qa);
 		showMsg("ButtonResourceGofObserver CREATED");
 	}
 	@Override
	public void update(String v) {
		System.out.println("	ButtonResourceGofObserver ... " + v);		
		if( qa == null ) return;
		qa.emit("usercmd", "usercmd(" + v + ")" );
 	}

}
