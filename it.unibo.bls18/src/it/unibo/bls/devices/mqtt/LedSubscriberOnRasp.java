package it.unibo.bls.devices.mqtt;

import java.io.IOException;
import java.util.Observable;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.bls.devices.raspberry.CommonNames;
import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls18.mqtt.utils.MqttUtils;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;

public class LedSubscriberOnRasp extends Observable implements IObservable, MqttCallback {
	protected IOutputEnvView outEnvView;
	protected String ledName;
	protected String serverAddr;
 	protected boolean isOn = false;
	protected MqttUtils mqttutils = MqttUtils.getMqttSupport(   );
	protected String clientid ;
	protected String topic  = CommonLedNames.allLedTopic;
	protected int count = 0;
	protected  MqttClient clientmqtt = null;
	protected Runtime runtime        = Runtime.getRuntime();
	
	//Factory method
	public static IObservable createLed( String ledName, String serverAddr, String topic ){
		LedSubscriberOnRasp led = new LedSubscriberOnRasp( ledName,serverAddr, topic);
	 	return led;
	}

	public LedSubscriberOnRasp( String ledName, String serverAddr, String topic  ) {
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
		System.out.println("	%%% LedSubscriber configure "  );
  		clientmqtt = mqttutils.connect(ledName, serverAddr);  
		clientmqtt.setCallback(this);
		clientmqtt.subscribe(topic);
		System.out.println("	%%% LedSubscriber configure DONE"  );
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
		System.out.println("	%%% LedSubscriber connectionLost  = "+ cause.getMessage() );
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
//				setChanged();
//				notifyObservers( msgcontent );	
			 execTheCommand( msgcontent );
		} catch (Exception e) {
			System.out.println("messageArrived ERROR "+e.getMessage() );
		}
		
	}

	/*
	 * COMMAND PATTERN
	 */
	protected void execTheCommand(String cmd ){
		if( cmd.contains(CommonNames.cmdTurnOn) ) turnOn();
		else if( cmd.contains(CommonNames.cmdTurnOff))  turnOff();
 	}

	/*
	 * LED IMPLEMENTATION (just to start ... )
	 */
	protected void turnOn() {
		try {
			runtime.exec("sudo bash led25GpioTurnOn.sh");
		} catch (IOException e) {
			System.out.println("LedOnRaspberry turnOn WARNING: perhaps not running on a Raspberry "  );
		}
	}
	protected void turnOff() {
		try {
			runtime.exec("sudo bash led25GpioTurnOff.sh");
		} catch (IOException e) {
			System.out.println("LedOnRaspberry turnOff WARNING: perhaps not running on a Raspberry "  );
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
		
		String severAddr = CommonLedNames.serverMqttAddr;   
		System.out.println("LED SUBSCRIBER STARTS severAddr=" + severAddr);
			//Create the led thing
		IObservable ledsubscriber = LedSubscriberOnRasp.createLed("ledNat",severAddr,CommonLedNames.allLedTopic);
		System.out.println("LED SUBSCRIBER CREATED");

 	}
			
 
}
