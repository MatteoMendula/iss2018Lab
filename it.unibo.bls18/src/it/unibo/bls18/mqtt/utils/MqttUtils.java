package it.unibo.bls18.mqtt.utils;
 
 
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
 

public class MqttUtils  {
private static MqttUtils myself  = null;	
	protected String clientid    = null;
	protected String eventId     = "mqtt";
	protected String eventMsg    = "";
	protected  MqttClient client = null;
	
	public static MqttUtils getMqttSupport(   ){
//		System.out.println("	%%% MqttUtils getMqttSupport "   );
		if( myself == null )  myself = new MqttUtils();
		return myself  ;
	}
	public MqttUtils(){
		try {
 			myself   = this;
		} catch (Exception e) {
			println("	%%% MqttUtils WARNING: "+ e.getMessage() );
		} 
	}
  	public MqttClient connect( String clientid, String brokerAddr  ) {
 		try{
 			client = new MqttClient(brokerAddr, clientid);
// 			println("	%%% MqttUtils doing connect/4 "+ clientid + " " + brokerAddr   );//+ " " + client
			MqttConnectOptions options = new MqttConnectOptions();
			options.setKeepAliveInterval(480);
 			options.setWill("unibo/clienterrors", "crashed".getBytes(), 2, true);
			client.connect(options);	
			println("	%%% MqttUtils connect DONE "+ clientid + " to:" + brokerAddr  );//+ " " + client
			return client;
 		}catch(Exception e){
 			println("	%%% MqttUtils connect ERROR for:" + brokerAddr);
 			return null;
 		}
	}
	public void disconnect( )  {
		try{
			println("	%%% MqttUtils disconnect "+ client );
			if( client != null ) client.disconnect();
 		}catch(Exception e){
 			 println("	%%% MqttUtils disconnect ERROR " + e.getMessage());
 		}
	}	

//	public void subscribe( String  clientid, String topic) throws Exception {
//		try{
// 			client.setCallback(this);
// 			client.subscribe(topic);  
// 		}catch(Exception e){
////				println("	%%% MqttUtils subscribe error "+  e.getMessage() );
////				eventMsg = "mqtt(" + eventId +", failure)";
////				println("	%%% MqttUtils subscribe error "+  eventMsg );
//  	 			throw e;
//		}
//	}
	/*
	 * sends to a tpoic a content of the form
	 * 	     msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
	 */
	public void publish( MqttClient client,  String topic, String msg, 
			int qos, boolean retain) throws MqttException{
 		MqttMessage message = new MqttMessage();
		message.setRetained(retain);
		if( qos == 0 || qos == 1 || qos == 2){//qos=0 fire and forget; qos=1 at least once(default);qos=2 exactly once
			message.setQos(0);
		}
		message.setPayload(msg.getBytes());
		try{
			client.publish(topic, message);
//			println("			%%% MqttUtils published "+ message + " on topic=" + topic);
		}catch(MqttException e){
			println("	%%% MqttUtils publish ERROR  "+ e );
		}
	}	
	public void publish( MqttClient client,  String topic, String msg ) throws MqttException{
  		publish( client,    topic,   msg, 1, true );
 	}	
	/*
	 * receives a message of the form
	 * 	         msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
	 * and sends it to the RECEIVER  
	 */
 	private String msgSender   = null;

 	
 	
	public void println(String msg){
		 System.out.println(msg);
	}
	
 	
}
