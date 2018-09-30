package it.unibo.bls18.interfaces;
import org.eclipse.californium.core.server.resources.Resource;

public interface IResourceIot extends Resource {
	public void setGofObserver( IResourceLocalObserver obs );
	public void setValue(String v);
}
