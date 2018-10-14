package it.unibo.bls.devices.raspberry;

import it.unibo.bls.applLogic.BlsApplicationLogic;
import it.unibo.bls.devices.gui.ButtonAsGui;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.ButtonModel;
import it.unibo.bls.oo.model.LedObservableModel;
import it.unibo.bls.utils.UtilsBls;

public class MainBlsModelWithLedOnRasp  {

private ILedObservable ledmodel;
private IObserver buttonmodel;
private BlsApplicationLogic applLogic;

private IObservable buttongui;
private IObserver ledproxy;

private String protocol;
private String hostName;
private int portNum;


//Factory method   	
  	public static MainBlsModelWithLedOnRasp createTheSystem(){
 		return new MainBlsModelWithLedOnRasp();
 	} 	
 	protected MainBlsModelWithLedOnRasp( ) {
 		configure();
 	}		
 	
 	protected void configure(){
 		protocol = CommonNames.protocol;
 		hostName = CommonNames.hostName;
 		portNum  = CommonNames.portNum;
 		
  		
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
    	buttonmodel = ButtonModel.createButton("press",applLogic);
 	} 	
 	protected void createConcreteComponents(){
   		//Create the ButtonAsGui
		buttongui   = ButtonAsGui.createButton( UtilsBls.initFrame(200,200), "press");
		//Create the led proxy
		ledproxy = LedProxyForRaspberry.create(protocol,hostName,portNum);
  	} 	
 	
  	
 	protected void configureSystemArchitecture(){
 		ledmodel.addObserver(ledproxy);
 		buttongui.addObserver(buttonmodel);
 	}

 	protected void blink() {
		UtilsBls.delay(1000);
		ledmodel.turnOn(); 		
		UtilsBls.delay(1000);
		ledmodel.turnOff();
		UtilsBls.delay(1000);
		ledmodel.turnOn(); 		
		UtilsBls.delay(1000);
		ledmodel.turnOff();
 	}
public static void main(String[] args) {
   MainBlsModelWithLedOnRasp.createTheSystem();
 }
}