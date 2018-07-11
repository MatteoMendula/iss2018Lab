package it.unibo.coap.bls;
import org.eclipse.californium.core.server.resources.Resource;

public interface IResourceIot extends Resource {
	public void setObserver( IResourceLocalObserver obs );
	public void setValue(String v);
}
