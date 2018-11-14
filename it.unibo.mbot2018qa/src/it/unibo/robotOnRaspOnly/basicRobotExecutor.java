/*
 * ======================================================================================
 * The file ./hardwareConfiguration.properties  must contain the robot name:
  		configuration=mocksimple
 * The file ./configuration/mocksimple/iotRobot.properties must contain the robot configuration
* ======================================================================================
*/
package it.unibo.robotOnRaspOnly;
import java.io.IOException;
import it.unibo.iot.baseRobot.hlmodel.BasicRobot;
import it.unibo.iot.baseRobot.hlmodel.IBasicRobot;
import it.unibo.iot.executors.baseRobot.IBaseRobot;
import it.unibo.iot.models.commands.baseRobot.BaseRobotBackward;
import it.unibo.iot.models.commands.baseRobot.BaseRobotForward;
import it.unibo.iot.models.commands.baseRobot.BaseRobotLeft;
import it.unibo.iot.models.commands.baseRobot.BaseRobotRight;
import it.unibo.iot.models.commands.baseRobot.BaseRobotSpeed;
import it.unibo.iot.models.commands.baseRobot.BaseRobotSpeedValue;
import it.unibo.iot.models.commands.baseRobot.BaseRobotStop;
import it.unibo.iot.models.commands.baseRobot.IBaseRobotCommand;
import it.unibo.iot.models.commands.baseRobot.IBaseRobotSpeed;
import it.unibo.iot.sensors.ISensor;
import it.unibo.iot.sensors.ISensorObserver;
import it.unibo.qactors.akka.QActor;
 

public class basicRobotExecutor{ 
private final static IBaseRobotSpeed SPEED_LOW    = new BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_LOW);
private final static IBaseRobotSpeed SPEED_MEDIUM = new BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_MEDIUM);
private final static IBaseRobotSpeed SPEED_HIGH   = new BaseRobotSpeed(BaseRobotSpeedValue.ROBOT_SPEED_HIGH);
private static IBaseRobot robot ;

 	
	public static void setUp(QActor qa) {
		IBasicRobot basicRobot = BasicRobot.getRobot();
		robot                  = basicRobot.getBaseRobot();
  		addObserverToSensors(qa,basicRobot);  						
	}
	
	public static void doMove( QActor qa, String cmd ) { //Args MUST be String
		IBaseRobotCommand command = null;
		switch( cmd ) {
			case "h" : command = new BaseRobotStop(SPEED_LOW );break;
			case "w" : command = new BaseRobotForward(SPEED_HIGH );break;
			case "s" : command = new BaseRobotBackward(SPEED_HIGH );break;
			case "a" : command = new BaseRobotLeft(SPEED_MEDIUM );break;
			case "d" : command = new BaseRobotRight(SPEED_MEDIUM );break;
			default: System.out.println( "Sorry, command not found");
		}
		if( command != null ) robot.execute(command);		
	}
 	  
	protected static void addObserverToSensors(QActor qa, IBasicRobot basicRobot){
		ISensorObserver observer = new SensorObserver(qa);
		for (ISensor<?> sensor : basicRobot.getSensors()) {  
			System.out.println( "doJob sensor= "  + sensor.getDefStringRep() + " class= "  + sensor.getClass().getName() );
 			sensor.addObserver(observer);
		}		
	}
 	

}
