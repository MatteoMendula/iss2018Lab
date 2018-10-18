package it.unibo.bls.devices.mqtt;

public class CommonLedNames {

	public static final String ledCmd        = "switch";
	public static final String startCmd      = "start";
	public static final String stopCmd       = "stop";
	public static final String serverAddr    = "tcp://localhost:1883"; //tcp://m2m.eclipse.org:1883
	public static final String serverMqttAddr    = "tcp://192.168.0.51:1883";  
	public static final String allLedTopic   = "unibo/ledState";
	public static final String[] ledRowTopcs =  new String[] { "ledTree/line1", "ledTree/line2", "ledTree/line3"}; //, "ledTree/line4"
	public static final int nunOfTreeRows    =  ledRowTopcs.length;

}
