package it.unibo.bls18.coapBasic.led;

import static org.eclipse.californium.core.coap.CoAP.ResponseCode.BAD_REQUEST;
import java.util.Observable;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls18.mqtt.utils.MqttUtils;
 

/*
 * A LedCoapResource is a observable CoapResource that uses and observes a LedObservableModel
 * Usage: MainCoapBasicLed  MainCoapBasicBls
 */
public class LedCoapResource extends CoapResource implements IObserver{ //observes the reused ledModel
	protected MqttUtils mqttutils = MqttUtils.getMqttSupport(   );
	protected String clientid 	  = CommonCoapNames.mqttClientId;
	protected String serverAddr   = CommonCoapNames.mqttUrl;
	protected String topic        = CommonCoapNames.mqttTopic;

	protected	ILedObservable ledModel ;
	protected  MqttClient clientmqtt = null;

	public LedCoapResource(String name, ILedObservable model ) {
		super(name);
		setObservable(true); 				// enable observing	!!!!!!!!!!!!!!
		setObserveType(Type.CON); 			// configure the notification type to CONs
		getAttributes().setObservable();	// mark observable in the Link-Format	
		
		// schedule a periodic update task, otherwise let events call changed()
//		Timer timer = new Timer();
//		timer.schedule(new UpdateTask(), 0, 1000);
		
		
 		clientmqtt = mqttutils.connect(clientid, serverAddr);
		
		ledModel = model;
		ledModel.addObserver(this);
	}

	    @Override	//CoapResource
	    public void handleGET(CoapExchange exchange) {
 		    //CommonCoapNames.showExchange(exchange);
	    	//System.out.println("	LedResource GET source="+exchange.getSourceAddress() + " value=" + getValue());
	    	String value = getLedValue();
            System.out.println("LedCoapResource handleGET "+ value);
	        exchange.respond( ResponseCode.CONTENT, value, MediaTypeRegistry.TEXT_PLAIN) ;
	    }

	    @Override	//CoapResource
	    public  void handlePUT(CoapExchange exchange) {  
 	        try {
 	            System.out.println("LedCoapResource handlePUT " );
 		    	//CommonCoapNames.showExchange(exchange);
	        	String msg = exchange.getRequestText();//new String(payload, "UTF-8"); 
	        	
	        	if( msg.equals("switch"))  switchValue();	
	            else if( msg.equals( CommonCoapNames.cmdTurnOn) || 
	            		 msg.equals( CommonCoapNames.cmdTurnOff) ) setValue(msg);
	        	
 	            System.out.println("LedCoapResource handlePUT New Led Value="+ getLedValue());
	            //exchange.respond(CHANGED,  value);
 	           //RETURN to the Coap client the current value of the Led
	            exchange.respond( ResponseCode.CONTENT, getLedValue(), MediaTypeRegistry.TEXT_PLAIN) ;
 	        } catch (Exception e) {
 	            exchange.respond(BAD_REQUEST, "Invalid String");
	        }
	    }
	    
	    protected void modelModified() {
	    	changed(); // notify all CoAp observers	
	    	//Emit an event using MQTT 
	    	try {
	    		System.out.println("LedCoapResource modelModified clientmqtt="+ clientmqtt );
				if( clientmqtt != null ) mqttutils.publish(clientmqtt, topic, getLedValue() );
			} catch (MqttException e) {
				 System.out.println("LedCoapResource WARNING "+ e.getMessage());
			}
	    }
 		   protected void switchValue() {
			   if( getLedValue( ).equals("true")) ledModel.turnOff();
			   else ledModel.turnOn();
//			   modelModified();		//When the ldModel changes, the update/2 methid is called
	 	    }
		   protected void setValue(String v) {
			   if( v.equals("true")) ledModel.turnOn();
			   else ledModel.turnOff();
//			   modelModified();		//When the ldModel changes, the update/2 methid is called
	 	    }
		   protected String getLedValue( ) {
			    return ""+ledModel.getState();
	 	   }

		@Override  //Called by the ledModel when modified by a Button
		public void update(Observable source, Object value) {
			System.out.println(" LedCoapResource update/2 (ledModel CHANGED)" );
			modelModified();			
		}
}
