package it.unibo.robotRaspOnly;
import it.unibo.iot.models.sensorData.ISensorData;
import it.unibo.iot.sensors.ISensorObserver;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedPlainObject;

public class SensorObserver<T extends ISensorData>   implements ISensorObserver<T>{

	public SensorObserver( ) { 
 	}
	@Override
	public void notify(T data) {
		System.out.println("SensorObserver: " + data.getDefStringRep() + " | " + data.getJsonStringRep());
	}

}
