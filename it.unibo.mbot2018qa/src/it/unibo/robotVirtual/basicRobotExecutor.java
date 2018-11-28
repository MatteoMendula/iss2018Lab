package it.unibo.robotVirtual;
import it.unibo.qactors.akka.QActor;
import it.unibo.utils.clientTcpForVirtualRobot;

public class basicRobotExecutor {
		public static void setUp(QActor qa, String hostNameStr) {
			try {
				qa.println("robotVirtual setUp " + hostNameStr );
				clientTcpForVirtualRobot.initClientConn(qa, hostNameStr, "8999");
			} catch (Exception e) {
 				e.printStackTrace();
			}
		}		
		public static void doMove( QActor qa, String cmd ) { //Args MUST be String
//			qa.println("robotVirtual doMove  " + cmd );
 		  switch( cmd ) {
			case "h" : clientTcpForVirtualRobot.sendMsg(qa,"{'type': 'alarm', 'arg': 0 }");break;
			case "w" : clientTcpForVirtualRobot.sendMsg(qa,"{'type': 'moveForward', 'arg': -1 }");break;
			case "a" : clientTcpForVirtualRobot.sendMsg(qa,"{'type': 'turnLeft', 'arg': 400 }");break;
			case "d" : clientTcpForVirtualRobot.sendMsg(qa,"{'type': 'turnRight', 'arg': 400 }");break;
			case "s" : clientTcpForVirtualRobot.sendMsg(qa,"{'type': 'moveBackward', 'arg': -1 }");break;
		  }
		}			
}