package it.unibo.utils.arduino;
import it.unibo.qactors.akka.QActor;

public class connArduino {
private static SerialPortConnSupport conn = null;
private static JSSCSerialComm serialConn;
//private static double dataFromArduino = 0;
private static String dataArduino;
private static QActor curActor ;

	public static void initRasp(QActor actor, String rate)   {
		curActor = actor;
		init( "/dev/ttyUSB0", rate );
	}
	public static void initPc(QActor actor, String port, String rate)   {
		curActor = actor;
		init( port, rate );
	}

 	private static void init(String port, String rateStr)   {
		try {
	 		System.out.println("connArduino starts");
	 		int rate = Integer.parseInt(rateStr);
			serialConn = new JSSCSerialComm(null, rate);
			conn = serialConn.connect(port);	//returns a SerialPortConnSupport
			if( conn == null ) return;
			startObserverDataFromArduino();
//			curDataFromArduino = conn.receiveALine();
// 			curActor.println("connArduino received:" + curDataFromArduino);
// 			startObserverDataFromArduino();
		}catch( Exception e) {
			curActor.println("connArduino ERROR" + e.getMessage());
		}
	}
	
	private static void startObserverDataFromArduino() {
		new Thread() {
			public void run() {
				try {
					curActor.println("connArduino startObserverDataFromArduino STARTED"  );
					while(true) {
						try {
							dataArduino = conn.receiveALine();
							if( dataArduino.startsWith("msg") ) curActor.println("connArduino received:" + dataArduino );
// 							double v = Double.parseDouble(curDataFromArduino);
//							//handle too fast change
// 							double delta =  Math.abs( v - dataFromArduino);
// 							if( delta < 7 && delta > 0.5 ) {
//								dataFromArduino = v;
////								System.out.println("connArduino sonar:" + dataSonar);
//								QActorUtils.raiseEvent(curActor, curActor.getName(), "realSonar", 
//										"sonar( DISTANCE )".replace("DISTANCE", ""+dataFromArduino ));
// 							}
						} catch (Exception e) {
							curActor.println("connArduino ERROR:" + e.getMessage());
						}
					}
				} catch (Exception e) {
  					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public static void sendToArduino( QActor actor, String cmd ) {
//		curActor.println("		connArduino sendToArduino:" + cmd.charAt(0));
		try { if( conn != null ) conn.sendCmd(""+cmd.charAt(0)); } catch (Exception e) {e.printStackTrace();}
	}
}
