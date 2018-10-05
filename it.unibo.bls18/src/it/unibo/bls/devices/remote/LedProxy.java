package it.unibo.bls.devices.remote;

import java.util.Observable;
import it.unibo.bls.devices.LedAsGui;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.LedObservableModel;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.contactEvent.interfaces.IActorMessage;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.is.interfaces.protocols.IConnInteraction;
import it.unibo.qactors.QActorMessage;
import it.unibo.supports.FactoryProtocol;
import it.unibo.system.SituatedSysKb;

public class LedProxy implements IObserver{

protected IConnInteraction conn;  
protected String protocol;
protected IOutputEnvView outEnvView;
protected String hostName;
protected int portNum;
protected boolean isOn = false;
protected IActorMessage msg;
protected int count = 0;

//Factory method
public static IObserver createLed( String protocol, String hostName, int portNum){
	LedProxy led = new LedProxy(protocol,   hostName,   portNum);
 	return led;
}


public LedProxy( String protocol, String hostName, int portNum ) {
	this.protocol = protocol;
	this.hostName = hostName;
	this.portNum  = portNum;
	outEnvView = SituatedSysKb.standardOutEnvView;
	try {
		configure();
	} catch (Exception e) {
 		e.printStackTrace();
	}
}
public void configure( ) throws Exception {
	if( protocol.equals("SERIAL") ){
//		FactoryProtocol factoryProtocol = new FactoryProtocol(outView,"SERIAL",PORT_NAME);
//		conn = factoryProtocol.createSerialProtocolSupport(PORT_NAME);
		return;
	}
	if( protocol.equals("TCP") || protocol.equals("UDP") ){
 		FactoryProtocol factoryProtocol = new FactoryProtocol(outEnvView,protocol,"LedClient");
 		conn = factoryProtocol.createClientProtocolSupport(hostName, portNum);
		return;
	}
}

	protected void turnOn(){
	try { 			
			if( ! isOn && conn != null   ) { 
				//String MSGID, String MSGTYPE, String SENDER, String RECEIVER, String CONTENT, String SEQNUM
				msg = new QActorMessage("ledCmd","dispatch","ledClient","ledThing","true",""+count++);
				conn.sendALine( msg.toString() );	
				isOn = ! isOn;
		}
		} catch (Exception e) {
			System.out.println("DeviceLedArduino turnOn ERROR " + e.getMessage() );
		}
	}
	protected void turnOff() {
	try { 			
		if( isOn && conn != null   ) { 
			//String MSGID, String MSGTYPE, String SENDER, String RECEIVER, String CONTENT, String SEQNUM
			msg = new QActorMessage("ledCmd","dispatch","ledClient","ledThing","false",""+count++);
			conn.sendALine( msg.toString() );	
			isOn = ! isOn;
		}
		} catch (Exception e) {
		System.out.println("DeviceLedArduino turnOff ERROR " + e.getMessage() );
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
		@Override
		public void update(Observable source, Object value) {
			System.out.println(" LedClient update " + value  );
			String v = ""+value;
			if( v.equals("true") ) turnOn();
			else turnOff();
		}

 /*
 * Just for a rapid test	
 */
	
	public static void main(String[] args) {
		String protocol = "UDP";
		int portNum     = 8010;
		//Create the led thing
		IObserver ledgui = LedAsGui.createLed(UtilsBls.initFrame(200,200));
		System.out.println("LED GUI CREATED");
		IObservable ledReceiver = LedThingReceiver.createLed(protocol, portNum);
		System.out.println("LED RECEIVER CREATED");
		ledReceiver.addObserver(ledgui);
		UtilsBls.delay(1000);
		System.out.println("LED THING CREATED");
		//Create the led client (proxy)
		IObserver ledproxy = LedProxy.createLed(protocol,"localhost",portNum);
		System.out.println("LED PROXY CREATED");
		//Create the led model
		ILedObservable ledmodel  = LedObservableModel.createLed(ledproxy);
		System.out.println("LED MODEL CREATED");
		//Do some interaction
 		for( int i=0; i<5; i++ ) {
			UtilsBls.delay(1000);
			ledmodel.turnOn(); 		
			UtilsBls.delay(1000);
			ledmodel.turnOff();
 		}
	}


}