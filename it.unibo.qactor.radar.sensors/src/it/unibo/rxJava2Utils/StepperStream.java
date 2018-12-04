package it.unibo.rxJava2Utils;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
 
public class StepperStream extends AbstractSensorStream{
private static boolean pythonStarted= false;

	public StepperStream(String name) {
		super( name );
 	}
 
	/*
	 * A local generator of item 
	 */
	@Override
	public void startGenItems() {
		if( ! pythonStarted ) {
	 		new ServerTcpForPython( this ).start(); //calls inherited setItem
			startStepperPyhton( );
  			pythonStarted = true;
		}else {
			System.out.println("StepperStream startGenItems already done");
		}
 	}
 	
 	//STEPPER
	protected void startStepperPyhton(){
		try {
 	  		System.out.println("startStepperPyhton");
	//		Process pPython =  
			Runtime.getRuntime().exec("sudo python stepper.py");
  		} catch (Exception e) {
 			e.printStackTrace();
		}		
	}
 	
// MAIN	
    public static void main( String[] args){
 		String name = "StepperrRxStream";
  		StepperStream stepperSrc = new StepperStream(name );
		 Observable<String> stepperrObsSrc = Observable.create( stepperSrc );
		 stepperrObsSrc
			  	.subscribeOn(Schedulers.computation())
			  	.subscribe( v -> RxUtils.log(name+": " + v) );
    }
	
}
