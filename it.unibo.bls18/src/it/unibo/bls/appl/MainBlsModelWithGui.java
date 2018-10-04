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
private IObserver buttonmodel;
private BlsApplicationLogic applLogic;

private IObserver ledgui;
private IObservable buttongui;

//Factory method   	
  	public static MainBlsModelWithGui createTheSystem(){
 		return new MainBlsModelWithGui();
 	} 	
 	protected MainBlsModelWithGui( ) {
 		configure();
 	}		
 	protected void configureMud(){
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
 	
 	protected void configure(){
 		createLogicalComponents();
 		createConcreteComponents();
 		configureSystemArchitecture();
  		ledmodel.turnOff();
		blink();
 	}
 	protected void createLogicalComponents(){
		//Create the led model t 
 		ledmodel    = LedObservableModel.createLed();
		//Create the Application logic that refers the led model (as ILed)
    	applLogic   = new BlsApplicationLogic(ledmodel);
    	//Create the button model that refers the Application logic
    	buttonmodel = ButtonModel.createButton(applLogic);
 	} 	
 	protected void createConcreteComponents(){
 		//Create the frame
 		Frame blsFrame = UtilsBls.initFrame(200,200);
 		//Create the LedAsGui
		ledgui      = LedAsGui.createLed(blsFrame);
 		//Create the ButtonAsGui
		buttongui   = ButtonAsGui.createButton( blsFrame, "press");
 	} 	
 	protected void configureSystemArchitecture(){
 		ledmodel.addObserver(ledgui);
 		buttongui.addObserver(buttonmodel);
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