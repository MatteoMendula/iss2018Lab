package it.unibo.commToRadar;
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
}
