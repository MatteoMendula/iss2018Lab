package it.unibo.bls.devices.arduino;

import jssc.SerialPort;
import java.util.Observable;

import it.unibo.bls.applLogic.BlsApplicationLogic;
import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.ButtonModel;
import it.unibo.bls.utils.UtilsBls;
import it.unibo.is.interfaces.protocols.IConnInteraction;
 

/*
 * Implements a concrete button (proxy) that handles Arduino 'messages' 
 * of the form 0 / 1 (BlsArduinoSysKb.off, BlsArduinoSysKb.on)
 */
public class ButtonOnArduino extends Observable  implements IObservable  { 
protected  String PORT_NAME ;
protected IConnInteraction portConn; //the comm channel with Arduino is a "general" two-way IConnInteraction
protected SerialPort serialPort;
protected int n = 0;	
 

protected boolean buttonPressed = false;

	public ButtonOnArduino(String name,  String PORT_NAME) {
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
	
	@Override //from IObservable
	public void addObserver(IObserver observer) {
		super.addObserver(observer);		
	} 

	public void doJob() {
		new Thread() {
			public void run() {
				try {
					while( true ) {
						System.out.println("ButtonOnArduino receiving .... "    );
						String msg = portConn.receiveALine();
						System.out.println("ButtonOnArduino msg=" + msg  );
						if( msg.contains("pressed" )) {
							setChanged();
							notifyObservers("pressed");
						}
					}
				} catch (Exception e) {
 					e.printStackTrace();
				}
			}
		}.start();
	}
 	 	 
	protected void close() {
		  try {
			  portConn.closeConnection(); 
		  } catch (Exception e) {
			  System.out.println("DevButtonArduino close ERROR:"+e.getMessage());			 
		  }	
	}

	
	/*
	 * -----------------------------------------
	 * MAIN (rapid check)
	 * -----------------------------------------
	 */
	 	public static void main(String[] args) throws Exception {
   		    System.out.println("Arduino: it.unibo.buttonLedSystem.arduino buttoninterrupt.ino "  );

   		    ButtonOnArduino button = new ButtonOnArduino("btnArduino", "COM9" ) ;
	 		
	 		ButtonModel buttonmodel = ButtonModel.createButton( "press", new BlsApplicationLogic(null) );
	 		
	 		button.addObserver(buttonmodel);
	 		
	 		button.doJob();
	 		
   	 	}
	
  }