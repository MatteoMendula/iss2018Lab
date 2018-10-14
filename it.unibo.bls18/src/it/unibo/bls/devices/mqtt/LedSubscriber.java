package it.unibo.bls.devices.mqtt;

import java.util.Observable;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.bls.devices.gui.LedAsGui;
import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.bls18.mqtt.utils.MqttUtils;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;

public class LedSubscriber extends Observable implements IObservable, MqttCallback {
	protected IOutputEnvView outEnvView;
	protected String ledName;
	protected String serverAddr;
 	protected boolean isOn = false;
	protected MqttUtils mqttutils = MqttUtils.getMqttSupport(   );
	protected String clientid ;
	protected String topic  = CommonLedNames.allLedTopic;
	protected int count = 0;
	protected  MqttClient clientmqtt = null;
	
	//Factory method
	public static IObservable createLed( String ledName, String serverAddr, String topic ){
		LedSubscriber led = new LedSubscriber( ledName,serverAddr, topic);
	 	return led;
	}

	public LedSubscriber( String ledName, String serverAddr, String topic  ) {
		this.ledName    = ledName;
		this.serverAddr = serverAddr;
		this.topic      = topic;
 		outEnvView      = SituatedSysKb.standardOutEnvView;
		try {
			configure();
		} catch (Exception e) {
	 		e.printStackTrace();
		}		
	}
	public void configure( ) throws Exception {
  		clientmqtt = mqttutils.connect(ledName, serverAddr);  
		clientmqtt.setCallback(this);
		clientmqtt.subscribe(topic);
 	}

	@Override	//from IObservable
	public void addObserver(IObserver obs) {
		super.addObserver(obs);		
	}

/*
 * FROM MqttCallback
 */
	
	@Override
	public   void connectionLost(Throwable cause) {
		System.out.println("	%%% MqttUtils connectionLost  = "+ cause.getMessage() );
	}
	@Override
	public   void deliveryComplete(IMqttDeliveryToken token) {
//		System.out.println("			%%% MqttUtils deliveryComplete token= "+ token );
	}

	@Override
	public void messageArrived(String topic, MqttMessage msg)   {
 		try {
  		     System.out.println("	%%% LedSubscriber messageArrived on "+ topic + ": "+msg.toString());
			 Struct msgt      = (Struct) Term.createTerm(msg.toString());
// 			 String msgID      = msgt.getArg(0).toString();
//			 String msgType    = msgt.getArg(1).toString();
//			 String msgSender  = msgt.getArg(2).toString();
//			 String dest       = msgt.getArg(3).toString();
			 String msgcontent = msgt.getArg(4).toString();
				setChanged();
				notifyObservers( msgcontent );	
		} catch (Exception e) {
			System.out.println("messageArrived ERROR "+e.getMessage() );
		}
		
	}

/*
 * Just for a rapid test 
 */
	public static void main(String[] args) {
		/*
		 * ASSUMPTION:  launch local MQTT on docker 
		 * docker images
		 * docker run -ti -p 1883:1883 -p 9001:9001 eclipse-mosquitto
		 */
		
		String severAddr = CommonLedNames.serverAddr;   
			//Create the led thing
		IObserver ledgui = LedAsGui.createLed(UtilsBls.initFrame(200,200));
		System.out.println("LED GUI CREATED");
		IObservable ledsubscriber = LedSubscriber.createLed("led1",severAddr,CommonLedNames.allLedTopic);
		System.out.println("LED SUBSCRIBER CREATED");
		ledsubscriber.addObserver(ledgui);
		System.out.println("LED THING CREATED");

 	}
			
 
}
