package it.unibo.bls18.coap.hexagonal;
import org.eclipse.californium.core.server.resources.Resource;

public interface IResourceIot extends Resource {
	public void setObserver( IResourceLocalObserver obs );
	public void setValue(String v);
}
