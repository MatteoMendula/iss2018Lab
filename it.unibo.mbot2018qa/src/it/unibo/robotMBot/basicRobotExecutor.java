/*
 * ======================================================================================
 * The file ./hardwareConfiguration.properties  must contain the robot name:
  		configuration=mocksimple
 * The file ./configuration/mocksimple/iotRobot.properties must contain the robot configuration
* ======================================================================================
*/
package it.unibo.robotMBot;
import it.unibo.mbot.MbotConnArduinoObj;
import it.unibo.qactors.akka.QActor;

public class basicRobotExecutor  { 
private static MbotConnArduinoObj robotSupport ; //singleton
 	
	public static void setUp(QActor qa, String port) {
 		if( robotSupport == null ) {
			robotSupport = new MbotConnArduinoObj();
			robotSupport.initPc(port);
			robotSupport.addObserverToSensors( new SensorObserverFromArduino(qa) );
		}		
	}	
	public  static void doMove( QActor qa, String cmd ) { //Args MUST be String
		robotSupport.executeTheCommand(cmd.charAt(0));
	} 
 }
