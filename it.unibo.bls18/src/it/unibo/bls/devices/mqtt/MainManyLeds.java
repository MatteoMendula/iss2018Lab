package it.unibo.bls.devices.mqtt;

import it.unibo.bls.devices.gui.ButtonAsGui;
import it.unibo.bls.devices.gui.LedAsGui;
import it.unibo.bls.interfaces.ILedObservable;
import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;
import it.unibo.bls.oo.model.MainBlsOOModel;
import it.unibo.bls.utils.UtilsBls;

public class MainManyLeds {
protected final int nunOfLeds  = 3;
protected final String cmdName = "switch";

	protected int ledCount = 0;
	protected String severAddr = CommonLedNames.serverAddr;
	
	protected MainBlsOOModel sysmodel;
	protected ILedObservable ledmodel;
	protected IObserver buttonmodel;
	protected IObserver ledpublisher;
	protected IObservable leds[] ;
	
	public static void createSystem() {
		new MainManyLeds();
	}
	public MainManyLeds() {
		createTheModel();		
		createTheLeds();
		createCmdButton();		
		configure();
	}
	
	protected void createTheModel() {
		sysmodel  = MainBlsOOModel.createTheSystem(cmdName);
		ledmodel  = sysmodel.getLedModel();
		buttonmodel = sysmodel.getButtonModel();
	}
	protected void createTheLeds() {
		ledpublisher = LedPublisher.createLed(severAddr,CommonLedNames.allLedTopic);
		leds         = new IObservable[nunOfLeds];
		for (int i=0; i<nunOfLeds; i++ ) leds[i] = createASubscriberLed();		
	}
	protected IObservable createASubscriberLed() {
		IObserver ledgui = LedAsGui.createLed(UtilsBls.initFrame(200,200));
 		IObservable ledsubscriber = LedSubscriber.createLed("ledNat"+ledCount++,severAddr,CommonLedNames.allLedTopic);
 		ledsubscriber.addObserver(ledgui);
 		return ledsubscriber;
	}
	
	protected void createCmdButton() {
		ButtonAsGui.createButton( UtilsBls.initFrame(250,250), CommonLedNames.ledCmd, buttonmodel);
	}
	protected void configure() {
		ledmodel.addObserver ( ledpublisher );
	}
	public static void main(String[] args) {
		MainManyLeds.createSystem();
	}
}
