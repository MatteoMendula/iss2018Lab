package it.unibo.bls.devices.proxy;

import java.util.Observable;

import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.contactEvent.interfaces.IActorMessage;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.is.interfaces.protocols.IConnInteraction;
import it.unibo.qactors.QActorMessage;
import it.unibo.supports.FactoryProtocol;
import it.unibo.system.SituatedSysKb;

public class LedThingReceiver extends Observable implements IObservable{
	protected IConnInteraction conn;  
	protected String protocol;
	protected IOutputEnvView outEnvView;
	protected String hostName;
	protected int portNum;
	protected FactoryProtocol factoryProtocol;
	
	//Factory method
	public static IObservable createLed( String protocol,  int portNum){
		LedThingReceiver led = new LedThingReceiver(protocol, portNum);
  		return led;
	}

	public LedThingReceiver(String protocol,  int portNum) {
		this.protocol = protocol;
 		this.portNum  = portNum;
		outEnvView = SituatedSysKb.standardOutEnvView;
		try {
			configure();
			doJob();
		} catch (Exception e) {
	 		e.printStackTrace();
		}		
	}

	public void configure( ) throws Exception {
		outEnvView.addOutput("LedThingReceiver CREATING ... " + protocol.equals("TCP") );
		if( protocol.equals("TCP") || protocol.equals("UDP") ){
			outEnvView.addOutput("LedThingReceiver CREATING ... " + protocol  );
	 		factoryProtocol = new FactoryProtocol(outEnvView,protocol,"LedThingReceiver");
			return;
		}
	}
 
	@Override	//from IObservable
	public void addObserver(IObserver obs) {
		super.addObserver(obs);		
	}
	
	
	protected void doJob() {
		new Thread() {
			public void run() {
				try {
					outEnvView.addOutput("LedThingReceiver STARTED");
					conn = factoryProtocol.createServerProtocolSupport( portNum ); //BLOCKS
					while(true) {
							String msg = conn.receiveALine();
							outEnvView.addOutput("LedThingReceiver receives:" + msg);
							IActorMessage inputmsg = new QActorMessage( msg );
								setChanged();
								notifyObservers( inputmsg.msgContent() );	
					}
				} catch (Exception e) {
						e.printStackTrace();
				}
			}
		}.start();
	}
	

	/*
	 * Just for a rapid test: see LedClient	
	 */

}
