package it.unibo.bls18.coap.hexagonal;
import org.eclipse.californium.core.server.resources.ResourceObserver;

import it.unibo.bls.interfaces.IObserver;

public interface IResourceLocalObserver extends IObserver, ResourceObserver{
	public void update( String v );
}
