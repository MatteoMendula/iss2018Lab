package it.unibo.bls.devices.arduino;

import jssc.SerialPort;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.is.interfaces.protocols.IConnInteraction;
import it.unibo.supports.FactoryProtocol;
 

/*
 * Implements a concrete button (proxy) that handles Arduino 'messages' 
 * of the form 0 / 1 (BlsArduinoSysKb.off, BlsArduinoSysKb.on)
 */
public class ButtonProxyForArduino    { 
protected  String PORT_NAME ;
protected IConnInteraction portConn; //the comm channel with Arduino is a "general" two-way IConnInteraction
protected SerialPort serialPort;
protected int n = 0;	
protected FactoryProtocol factoryProtocol;

protected boolean buttonPressed = false;

	public ButtonProxyForArduino(String name,  String PORT_NAME) {
 		this.PORT_NAME = PORT_NAME;
   		configure();
	}
	protected void configure() {
 		try {
			portConn =  BlsArduinoSysKb.getConnection(null, PORT_NAME );
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}
// 	@Override
	public void execAction(String cmd) {
		System.out.println("DeviceButtonArduino cmd=" + cmd  + "/" +  cmd.length());
 		cmd = cmd.replaceAll("\n", "");
  		try {
			portConn.sendALine(cmd);
		} catch (Exception e) {
 			e.printStackTrace();
		}
   	} 	
	 
 	 
	protected void close() {
		  try {
			  portConn.closeConnection(); 
		  } catch (Exception e) {
			  System.out.println("DevButtonArduino close ERROR:"+e.getMessage());			 
		  }	
	}

	/*
	 * API for debugging
 	 */
//	@Override
	public void high() {
		buttonPressed  = true;
		execAction(BlsArduinoSysKb.on);
 	}
//	@Override
	public void low() {
		buttonPressed  = false;
		execAction(BlsArduinoSysKb.off);
 	}
	
	/*
	 * -----------------------------------------
	 * MAIN (rapid check)
	 * -----------------------------------------
	 */
	 	public static void main(String[] args) throws Exception {
	 		ButtonProxyForArduino buttonPxy = new ButtonProxyForArduino("btnArduino", "COM9" ) ;
 		    UtilsBls.delay(3000);
 		    System.out.println("Arduino: it.unibo.buttonLedSystem.arduino/blsMain/blsMain.ino "  );
 		    buttonPxy.high();
 		    UtilsBls.delay(1000);
		    buttonPxy.low();
  	 	}
	
  }