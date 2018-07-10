package it.unibo.coap.bls;

import org.eclipse.californium.core.observe.ObserveRelation;
import org.eclipse.californium.core.server.resources.Resource;

import it.unibo.qactors.akka.QActor;

public abstract class ResourceLocalObserver implements IResourceLocalObserver{
	protected  QActor qa; 

 	protected ResourceLocalObserver(QActor qa) {
 		this.qa = qa;
 	}
 	protected void showMsg(String msg) {
 		if( qa != null ) qa.println( msg ) ;
 		else System.out.println( msg ) ;
 	}

	@Override
	public void changedName(String old) { 		
	}
	@Override
	public void changedPath(String old) {		
	}
	@Override
	public void addedChild(Resource child) {		
	}
	@Override
	public void removedChild(Resource child) {		
	}
	@Override
	public void addedObserveRelation(ObserveRelation relation) { 		
	}
	@Override
	public void removedObserveRelation(ObserveRelation relation) {		
	}
}
