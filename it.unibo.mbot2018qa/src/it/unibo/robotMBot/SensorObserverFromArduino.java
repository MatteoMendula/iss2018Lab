package it.unibo.robotMBot;

import it.unibo.mbot.ISensorObserverFromArduino;
import it.unibo.qactors.akka.QActor;

public class SensorObserverFromArduino implements ISensorObserverFromArduino{
private QActor qa;
	public SensorObserverFromArduino( QActor qa ) { 
		this.qa = qa;
 	}
	@Override
	public void notify( String data) {
		System.out.println("SensorObserverFromArduino: " + data );
		qa.emit("", "");
	}
}
