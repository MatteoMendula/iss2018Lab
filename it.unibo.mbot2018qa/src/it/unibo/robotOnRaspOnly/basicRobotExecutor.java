/*
 * ======================================================================================
 * The file ./hardwareConfiguration.properties  must contain the robot name:
  		configuration=mocksimple
 * The file ./configuration/mocksimple/iotRobot.properties must contain the robot configuration
* ======================================================================================
*/
package it.unibo.robotOnRaspOnly;
import it.unibo.qactors.akka.QActor;
import it.unibo.robotRaspOnly.BasicRobotUsageNaive;
 

public class basicRobotExecutor  { 
 
private static BasicRobotUsageNaive robotSupport ;
 	
	public static void setUp(QActor qa) {
		if( robotSupport == null ) {
			robotSupport = new BasicRobotUsageNaive();
			robotSupport.addObserverToSensors( new SensorObserver(qa) );
		}
		
	}
	
	
	public static void doMove( QActor qa, String cmd ) { //Args MUST be String
//		IBaseRobotCommand command = null;
//		switch( cmd ) {
//			case "h" : command = new BaseRobotStop(SPEED_LOW );break;
//			case "w" : command = new BaseRobotForward(SPEED_HIGH );break;
//			case "s" : command = new BaseRobotBackward(SPEED_HIGH );break;
//			case "a" : command = new BaseRobotLeft(SPEED_MEDIUM );break;
//			case "d" : command = new BaseRobotRight(SPEED_MEDIUM );break;
//			default: System.out.println( "Sorry, command not found");
//		}
//		if( command != null ) robot.execute(command);		
		
		robotSupport.executeTheCommand(cmd.charAt(0));
	}
 	  
//	protected static void addObserverToSensors(QActor qa, IBasicRobot basicRobot){
//		ISensorObserver observer = new SensorObserver(qa);
//		for (ISensor<?> sensor : basicRobot.getSensors()) {  
//			System.out.println( "doJob sensor= "  + sensor.getDefStringRep() + " class= "  + sensor.getClass().getName() );
// 			sensor.addObserver(observer);
//		}		
//	}
 	

}
