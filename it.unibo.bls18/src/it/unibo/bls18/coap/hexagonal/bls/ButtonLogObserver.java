package it.unibo.bls18.coap.hexagonal.bls;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import it.unibo.bls.devices.mqtt.CommonLedNames;
import it.unibo.bls18.coap.hexagonal.CommonBlsHexagNames;
import it.unibo.bls18.coap.hexagonal.ResourceLocalGofObserver;
import it.unibo.bls18.mqtt.utils.MqttUtils;
 

public class ButtonLogObserver extends ResourceLocalGofObserver  {
protected  MqttClient clientmqtt = null;
protected String clientid ;
protected MqttUtils mqttutils = MqttUtils.getMqttSupport(   );

 	public ButtonLogObserver( ) {
		clientid   = CommonLedNames.buttonMqttCientId;
 		clientmqtt = mqttutils.connect(clientid, CommonBlsHexagNames.serverMqttAddr);  
   	    showMsg("ButtonLogObserver CREATED  "  );
 	}
 	
  	/*
 	 * Called by  ButtonResource
  	 */
 	@Override
	public void update(String v) {
		System.out.println("ButtonLogObserver updated with: " + v);
		try {
			mqttutils.publish(clientmqtt, CommonBlsHexagNames.buttonLogTopic, v);
		} catch (MqttException e) {
 			e.printStackTrace();
		}
    }
 }
