package it.unibo.coap.bls;

import org.eclipse.californium.core.CoapResource;

public class CoapGofObservableResource extends CoapResource{
protected IResourceLocalObserver observer  ;
	
	public CoapGofObservableResource(String name) {
		super(name);
 	}
	
	public void setObserver( IResourceLocalObserver obs ) {
		observer = obs;
	}
	protected void update(String v) {
		observer.update(v);
	}

}
