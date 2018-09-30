package it.unibo.coap.bls;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttUtil {
    private static final String MQTT_TOPIC = "test/topic";
    private static final String MQTT_URL   = "tcp://localhost:1883";
    private static boolean published;
    private static MqttClient client;
    private static final String TAG = MqttUtil.class.getName();


 
    private static void connect(){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setKeepAliveInterval(30);
        try{
//            client.connect(mqttConnectOptions, null, new IMqttActionListener() {
//                @Override
//                public void onSuccess(IMqttToken asyncActionToken) {
////                    Log.d(TAG, "onSuccess");
//                }
//                @Override
//                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
////                    Log.d(TAG, "onFailure. Exception when connecting: " + exception);
//                }
//            });
        }catch (Exception e) {
//            Log.e(TAG, "Error while connecting to Mqtt broker : " + e);
            e.printStackTrace();
        }
    }

    public static void publishMessage(final String payload){
        published = false;
        try {
            byte[] encodedpayload = payload.getBytes();
            MqttMessage message = new MqttMessage(encodedpayload);
            client.publish(MQTT_TOPIC, message);
            published = true;
//            Log.i(TAG, "message successfully published : " + payload);
        } catch (Exception e) {
//            Log.e(TAG, "Error when publishing message : " + e);
            e.printStackTrace();
        }
    }
    
    public static void close(){
        if(client != null) {
//            client.unregisterResources();
//            client.close();
        }
    }
}
