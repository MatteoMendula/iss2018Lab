package it.unibo.bls.oo.model;
 
import it.unibo.bls.applLogic.BlsApplicationLogic;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.ButtonModel;
import it.unibo.bls.oo.model.LedObservableModel;
import it.unibo.bls.utils.UtilsBls;
 
public class MainBlsOOModel  {
private String cmdName;
private ILedObservable ledmodel;
private IObserver buttonmodel;
private IObserver applLogic;


//Factory method   	
	public static MainBlsOOModel createTheSystem( String cmdName ){
		return new MainBlsOOModel( cmdName ); 	} 	
  	
	public static MainBlsOOModel createTheSystem( String cmdName, IObserver applLogic ){
  		return new MainBlsOOModel( applLogic );
 	} 	
 	protected MainBlsOOModel( String cmdName ) {
 		this.cmdName = cmdName;
  		configure();
 	}		
 	protected MainBlsOOModel( IObserver applLogic ) {
 		this.applLogic = applLogic;
 		configure();
 	}		
  	
 	protected void configure(){
 		createLogicalComponents();
  		createConcreteComponents();
  		configureSystemArchitecture();
// 		blink();
 	}
 	protected void createLogicalComponents(){
		//Create the led model t 
 		ledmodel    = LedObservableModel.createLed();
		//Create the Application logic that refers the led model (as ILed)
    	if( applLogic == null ) applLogic = new BlsApplicationLogic(ledmodel);
    	//Create the button model that refers the Application logic
    	buttonmodel = ButtonModel.createButton(cmdName, applLogic);
 	} 	
 	protected void createConcreteComponents(){
  	} 	
 	protected void configureSystemArchitecture(){
  	}
/*
 * Selectors	
 */
 	public ILedObservable getLedModel() {
 		return ledmodel;
 	}
 	public IObserver getButtonModel() {
 		return buttonmodel;
 	}
 /*
  * Work
  */
 	protected void blink() {
  		ledmodel.turnOff();
		UtilsBls.delay(1000);
		ledmodel.turnOn(); 		
		UtilsBls.delay(1000);
		ledmodel.turnOff();
		UtilsBls.delay(1000);
		ledmodel.turnOn(); 		
 	}
public static void main(String[] args) {
   MainBlsOOModel.createTheSystem( "switch"  );
 }
}