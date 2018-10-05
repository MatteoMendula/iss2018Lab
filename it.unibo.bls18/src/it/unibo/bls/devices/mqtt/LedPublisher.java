package it.unibo.bls.devices.mqtt;

import java.util.Observable;

import org.eclipse.paho.client.mqttv3.MqttClient;

import it.unibo.bls.devices.LedAsGui;
import it.unibo.bls.devices.remote.LedProxy;
import it.unibo.bls.devices.remote.LedThingReceiver;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.LedObservableModel;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.mqtt.utils.MqttUtils;
import it.unibo.contactEvent.interfaces.IActorMessage;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.QActorMessage;
import it.unibo.system.SituatedSysKb;

public class LedPublisher implements IObserver {
	protected IOutputEnvView outEnvView;
	protected String serverAddr;
 	protected boolean isOn = false;
	protected MqttUtils mqttutils = MqttUtils.getMqttSupport(   );
	protected String clientid ;
	protected String topic  ;
	protected int count = 0;
	protected  MqttClient clientmqtt = null;
	
	//Factory method
	public static IObserver createLed( String serverAddr ){
		LedPublisher led = new LedPublisher( serverAddr );
	 	return led;
	}

	public LedPublisher( String serverAddr  ) {
		this.serverAddr = serverAddr;
 		outEnvView      = SituatedSysKb.standardOutEnvView;
		try {
			configure();
		} catch (Exception e) {
	 		e.printStackTrace();
		}
		
	}
	public void configure( ) throws Exception {
		clientid   = "led1sender";
		topic      = "unibo/ledState";
		clientmqtt = mqttutils.connect(clientid, serverAddr);  
 	}
	
	
	protected void turnOn(){
	try { 			
		if( ! isOn   ) { 
				//String MSGID, String MSGTYPE, String SENDER, String RECEIVER, String CONTENT, String SEQNUM
				IActorMessage msg = new QActorMessage("ledCmd","dispatch","ledClient","ledThing","true",""+count++);
				mqttutils.publish(clientmqtt, topic, msg.toString() );	
				isOn = ! isOn;
		}
		} catch (Exception e) {
			System.out.println("LedPublisher turnOn ERROR " + e.getMessage() );
		}
	}
	protected void turnOff() {
	try { 			
		if( isOn  ) { 
			//String MSGID, String MSGTYPE, String SENDER, String RECEIVER, String CONTENT, String SEQNUM
			IActorMessage msg = new QActorMessage("ledCmd","dispatch","ledClient","ledThing","false",""+count++);
			mqttutils.publish(clientmqtt, topic, msg.toString() );	
			isOn = ! isOn;
		}
		} catch (Exception e) {
		System.out.println("LedPublisher turnOff ERROR " + e.getMessage() );
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
		@Override
		public void update(Observable source, Object value) {
			System.out.println(" LedPublisher update " + value  );
			String v = ""+value;
			if( v.equals("true") ) turnOn();
			else turnOff();
		}

		 /*
		 * Just for a rapid test	
		 */
			
			public static void main(String[] args) {
				//ASSUMPTION:  launch local MQTT on docker
				
				String severAddr = "tcp://m2m.eclipse.org:1883";
  
 				
				//Create the led client (proxy)
				IObserver ledpublisher = LedPublisher.createLed( severAddr );
				System.out.println("LED PUBLISHER CREATED");
				//Create the led model
				ILedObservable ledmodel  = LedObservableModel.createLed(ledpublisher);
				System.out.println("LED MODEL CREATED");

				UtilsBls.delay(1000);
				
 				
				//Do some interaction
		 		for( int i=0; i<5; i++ ) {
					UtilsBls.delay(1000);
					ledmodel.turnOn(); 		
					UtilsBls.delay(1000);
					ledmodel.turnOff();
		 		}
			}

}
