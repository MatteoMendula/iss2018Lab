package it.unibo.commToRadar;
import java.io.IOException;

import it.unibo.qactors.akka.QActor;

public class polarToRadar {
private static String addr;
private static String port;
private static int msgNum = 1;

	public static void connect( QActor qa, String addrStr, String portStr ) {
		addr = addrStr;
		port = portStr;
		qa.println("connect going ... " + addrStr+":"+portStr);
	}
	public static void sendPolar(QActor qa, String D, String A) throws Exception {
		qa.println("sendPolar D=" + D);
		String msgContent = "p("+D+","+A+")";
		String msg = "msg(polar,event,"+ qa.getName() +",none," + msgContent + ","  + msgNum++ +")";
		if( addr != null ) qa.sendTcpMsg( addr, port, msg );
	}
	public static void sendPolarToNodeServer(QActor qa, String D, String A) throws Exception {
 		String msgContent = "p("+D+","+A+")";
		qa.println("sendPolarToNodeServer: " + msgContent);
		qa.sendTcpMsg( "localhost", "8057", msgContent );
	}
	public static void customExecute(QActor qa, String cmd) {
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) { e.printStackTrace(); } 
	}
	public static void customRunJar(QActor qa, String cmd) {
		try {
			Runtime.getRuntime().exec("java -jar " + cmd );
		} catch (IOException e) { e.printStackTrace(); }
	}
	public static void customRunJava(QActor qa, String cmd) {
		try {
			Runtime.getRuntime().exec("java " + cmd );
		} catch (IOException e) { e.printStackTrace(); }
	}
}
