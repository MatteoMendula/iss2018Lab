package it.unibo.bls.appl;
import java.awt.Frame;
import it.unibo.bls.applLogic.BlsApplicationLogic;
import it.unibo.bls.devices.ButtonAsGui;
import it.unibo.bls.devices.LedAsGui;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.ButtonModel;
import it.unibo.bls.oo.model.LedObservableModel;
import it.unibo.bls.utils.UtilsBls;
 
public class MainBlsModelWithGui  {

private ILedObservable ledmodel;

//Factory method   	
  	public static MainBlsModelWithGui createTheSystem(){
 		return new MainBlsModelWithGui();
 	} 	
 	protected MainBlsModelWithGui( ) {
 		configure();
 	}		
 	protected void configure(){
 		//Create the frame
 		Frame blsFrame = UtilsBls.initFrame(200,200);
 		//Create the ledGui
		IObserver ledgui      = LedAsGui.createLed(blsFrame);
		//Create the led model that refers the ledGui
		ledmodel              = LedObservableModel.createLed(ledgui);
		//Create the Application logic that refers the led model (as ILed)
    	BlsApplicationLogic applLogic = new BlsApplicationLogic(ledmodel);
    	//Create the button model that refers the Application logic
    	IObserver buttonmodel = ButtonModel.createButton(applLogic);
    	//Create the button gui that refers the buttonmodel as observer
   		ButtonAsGui.createButton( blsFrame, "press", buttonmodel);
  		ledmodel.turnOff();
		blink();
	} 	
 	
 	protected void blink() {
		UtilsBls.delay(1000);
		ledmodel.turnOn(); 		
		UtilsBls.delay(1000);
		ledmodel.turnOff();
		UtilsBls.delay(1000);
		ledmodel.turnOn(); 		
 	}
public static void main(String[] args) {
   MainBlsModelWithGui sys = createTheSystem();
 }
}