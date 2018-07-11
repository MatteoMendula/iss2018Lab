package it.unibo.coap.bls;

import org.eclipse.californium.core.CoapResource;

public abstract class CoapGofObservableResource extends CoapResource implements IResourceIot{
protected IResourceLocalObserver observer  ;
	
	public CoapGofObservableResource(String name) {
		super(name);
 	}
	
	public void setObserver( IResourceLocalObserver obs ) {
		observer = obs;
	}
	/*
	 * To be defined by the application designer.
	 * It can be used by physical devices to change the model
	 */
	public abstract void setValue(String v);
	
	protected void update(String v) {
		observer.update(v);
	}

}
