package it.unibo.qarescuer;

import java.util.Hashtable;

import it.unibo.is.interfaces.protocols.IConnInteraction;
import it.unibo.supports.FactoryProtocol;
import it.unibo.system.SituatedSysKb;

public class CommsWithOutside {
	public static final String protocol = FactoryProtocol.TCP;
	public static Hashtable<String, IConnInteraction> androConns = new Hashtable<String, IConnInteraction>();

	public static IConnInteraction initConnection(String actorName, String hostName, int port) {
		System.out.println("initConnection actorName=" + actorName + " hostName=" + hostName);
		FactoryProtocol factoryP = new FactoryProtocol(SituatedSysKb.standardOutEnvView, protocol, "androclient");
		IConnInteraction conn = null;
		try {
			conn = factoryP.createClientProtocolSupport(hostName, port);
			androConns.put(actorName, conn); //memo the connection for the actor
		} catch (Exception e) {
			System.out.println("WARNING : no connection possible to Android for " + actorName);
			//e.printStackTrace();
		}
		return conn;
	}
	
	public static void sendMsg( IConnInteraction conn, String msg) throws Exception {
		if( conn != null ) conn.sendALine( msg );
		else System.out.println("WARNING : no connection to Android " );
	}
	
	public static void sendMsg( String actorName, String msg) throws Exception {
		sendMsg( androConns.get(actorName), msg );
	}
}