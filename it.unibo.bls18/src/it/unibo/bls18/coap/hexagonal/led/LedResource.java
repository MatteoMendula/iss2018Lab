package it.unibo.bls18.coap.hexagonal.led;

import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import it.unibo.bls18.coap.hexagonal.CoapGofObservableResource;
import it.unibo.bls18.coap.hexagonal.CommonBlsHexagNames;
import it.unibo.bls18.coap.hexagonal.IResourceLocalGofObserver;
import it.unibo.bls18.coapBasic.led.CommonCoapNames;
import it.unibo.bls18.mqtt.utils.MqttUtils;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.*;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.CoAP.Type;

public  class LedResource extends CoapGofObservableResource {
 
protected  MqttClient clientmqtt = null;
protected MqttUtils mqttutils = MqttUtils.getMqttSupport(   );
protected String clientid 	  = CommonCoapNames.mqttClientId;
protected String serverAddr   = CommonCoapNames.mqttUrl;
protected String topic        = CommonCoapNames.mqttTopic;
protected String resourcePath = CommonBlsHexagNames.ledResourceName;

/*
 * LED STATE (model)
 */
protected String value = CommonBlsHexagNames.cmdTurnOff;


    public LedResource() {
    	this(null);
    }
    public LedResource(IResourceLocalGofObserver obs) {
        super( CommonBlsHexagNames.ledResourceName);
 		if( obs != null ) {
 			this.setObserver(obs);
 			setValue("false");
 		}
		setObservable(true); 				// enable observing	!!!!!!!!!!!!!!
		setObserveType(Type.CON); 			// configure the notification type to CONs
		getAttributes().setObservable();	// mark observable in the Link-Format	
		
		clientmqtt = mqttutils.connect(clientid, serverAddr);
		System.out.println("LedResource CREATED "   ) ;
    }
    
    @Override
    public void setValue(String v) {
    	value = v ;
    	modelModified();     	
    }
    
    protected void switchTheLed() {
    	System.out.println("	LedResource switchTheLed value="+ value);
		if( value.equals("true")) setValue("false"); else setValue("true");
    }

    @Override
    public void handleGET(CoapExchange exchange) {
    	System.out.println("	LedResource GET source="+exchange.getSourceAddress() + " value=" + value);
        exchange.respond( value ); //"ledValue("+value+")"
    }

    @Override
    public  void handlePUT(CoapExchange exchange) {
        try {
        	String msg = exchange.getRequestText();//new String(payload, "UTF-8"); 
            System.out.println("	LedResource PUT "+ msg);
            if( msg.equals("switch")) switchTheLed();
            else if( msg.equals( CommonBlsHexagNames.cmdTurnOn) || 
            		msg.equals( CommonBlsHexagNames.cmdTurnOff) ) setValue(msg);
            //exchange.respond(CHANGED,  value);
            //RETURN to the Coap client the current value of the Led
            exchange.respond( ResponseCode.CONTENT, value, MediaTypeRegistry.TEXT_PLAIN) ;
         } catch (Exception e) {
            exchange.respond(BAD_REQUEST, "Invalid String");
        }
    }
    
    protected void modelModified() {
    	System.out.println("	LedResource modelModified "+ value);
    	changed();      // notify all CoAp observers	
    	update(value);	// notify the GOF observer
    	//Emit an event using MQTT 
    	try {
			if(clientmqtt != null) mqttutils.publish(clientmqtt, topic, value );
		} catch (MqttException e) {
				e.printStackTrace();
		}
    }
    
    
  }