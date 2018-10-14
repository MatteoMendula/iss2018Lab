package it.unibo.bls.devices.mqtt;

import java.awt.Frame;

import it.unibo.bls.devices.gui.ButtonAsGui;
import it.unibo.bls.devices.gui.LedAsGui;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.ButtonModel;
import it.unibo.bls.oo.model.LedObservableModel;
import it.unibo.bls.oo.model.MainBlsOOModel;
import it.unibo.bls.utils.UtilsBls;

public class MainTreeLeds {
protected final int nunOfLeds  = CommonLedNames.nunOfTreeRows;
	protected int ledCount     = 0;
	protected String severAddr = CommonLedNames.serverAddr;
	
 	protected IObservable startBtn;
 	protected IObservable stopBtn;
 	
//	protected ILedObservable ledmodel;
	protected ButtonModel startButtonmodel;
	protected ButtonModel stopButtonmodel;
//	protected IObserver ledpublisher;
	protected IObservable leds[] ;
	
	public static void createSystem() {
		new MainTreeLeds();
	}
	public MainTreeLeds() {
		createTheModel();		
		createTheLeds();
		createCmdButton();		
		configure();
	}
	
	protected void createTheModel() {
		startButtonmodel = ButtonModel.createButton(CommonLedNames.startCmd );
		stopButtonmodel  = ButtonModel.createButton(CommonLedNames.stopCmd );
//		ledmodel         = LedObservableModel.createLed();
	}
	protected void createTheLeds() {
//		ledpublisher = LedPublisher.createLed(severAddr,CommonLedNames.ledLine1Topic);
		leds         = new IObservable[nunOfLeds];
		 
 		for (int i=0; i<nunOfLeds; i++ ) leds[i] = createASubscriberLed( CommonLedNames.ledRowTopcs[i]);		
	}
	protected IObservable createASubscriberLed( String topic) {
		IObserver ledgui          = LedAsGui.createLed(UtilsBls.initFrame(200,200));
 		IObservable ledsubscriber = LedSubscriber.createLed("treeled"+ledCount++,severAddr,topic);
 		ledsubscriber.addObserver(ledgui);
 		return ledsubscriber;
	}
	
	protected void createCmdButton() {
 		startBtn = ButtonAsGui.createButton( UtilsBls.initFrame(300,300), CommonLedNames.startCmd, startButtonmodel );
		stopBtn  = ButtonAsGui.createButton( UtilsBls.initFrame(300,300), CommonLedNames.stopCmd,  stopButtonmodel );
	}
	protected void configure() {
		TreeLedLogicOrchestrator applLogic = TreeLedLogicOrchestrator.create( leds );
		 
		startButtonmodel.addObserver( applLogic );
		stopButtonmodel.addObserver( applLogic );
		 
		
//		ledmodel.addObserver ( ledpublisher );
	}
	public static void main(String[] args) {
		MainTreeLeds.createSystem();
	}
}
