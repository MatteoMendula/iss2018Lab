package it.unibo.coap.bls;

import org.eclipse.californium.core.server.resources.ResourceObserver;

public interface IResourceLocalObserver extends ResourceObserver{
	public void update( String v );
}
