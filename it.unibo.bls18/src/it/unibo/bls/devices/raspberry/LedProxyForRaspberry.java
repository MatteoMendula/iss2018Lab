package it.unibo.bls.devices.raspberry;

import java.util.Observable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.is.interfaces.protocols.IConnInteraction;
import it.unibo.supports.FactoryProtocol;
import it.unibo.system.SituatedSysKb;

/*
 * Sends messages over a network connection
 */
public class LedProxyForRaspberry implements IObserver{
	protected FactoryProtocol factoryP;
	protected IConnInteraction conn; 	
	protected IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
	protected String hostName=""; 
	protected int portNum = 0;

	public static IObserver create( String protocol, String hostName, int portNum  ){
		IObserver led = new LedProxyForRaspberry( protocol, hostName,  portNum,   null);
		return led;
	}

	
	public LedProxyForRaspberry( String protocol, String hostName, int portNum, IOutputEnvView outEnvView ){
		if( outEnvView != null ) this.outEnvView = outEnvView;
 		this.hostName = hostName;
		this.portNum  = portNum;
		factoryP      = new FactoryProtocol(outEnvView, protocol, "LedProxyForRaspberry");
		connect();
		turnOff();
	}

	protected void  connect() {
		while( true ){
		try {
				println("LedProxyForRaspberry attempt to connect to " + hostName + ":" + portNum + " ... " );
	 			conn = factoryP.createClientProtocolSupport( hostName,  portNum);
				println("DevLedPassiveProxy connected to " + hostName + ":" + portNum);
				break;
		} catch (Exception e) {
//			println("DevLedPassiveProxy connect ERROR " + e.getMessage() );
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				break;
			}
		}
		}	
	}
	
	
	public void turnOn() {
 		forwardDispatch( CommonNames.cmdTurnOn );	
   	}
 	public void turnOff() {
 		forwardDispatch( CommonNames.cmdTurnOff );	 
  	}
	
	protected void forwardDispatch( String cmd ){
		try {
			if( conn != null ){
				//println("LedProxyForRaspberry sending " +cmd );
 				conn.sendALine(cmd);
			}		
		} catch (Exception e) {
			println("LedProxyForRaspberry ERROR " + e.getMessage() );
  		}
	}

	protected void println(String m) {
		if( outEnvView != null ) outEnvView.addOutput(m);
		else System.out.println(m);
	}

	@Override
	public void update(Observable source, Object value) {
		println(" LedProxyForRaspberry update " + value );
		String v = ""+value;
		if( v.equals("true") ) turnOn();
		else turnOff(); 		
	}

}
