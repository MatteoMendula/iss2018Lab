package it.unibo.RxJava2Utils;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ZipSonarStepperStreams {
	private int maxNum = 100;
	private int rate   = 100;
 	
	private SonarStream sonarSrc     ;
	private StepperStream stepperSrc  ;
 	
	private Observable<String> sonarObsSrc     ;
	private Observable<String> stepperObsSrc   ;
 	
	public void doJob(){
		RxUtils.log("doJob INIT");
		
		sonarSrc       = new SonarStream("sonar", rate,  maxNum );
		stepperSrc     = new StepperStream("stepper" );
		sonarObsSrc    = Observable.create( sonarSrc );
		stepperObsSrc  = Observable.create( stepperSrc );
		
 		Observable<Long> clock   = Observable.interval(500, TimeUnit.MILLISECONDS);
		RxUtils.log("doJob START");
		
		Observable< Pair<String> > s3 = 
				Observable.zip(
						stepperObsSrc.subscribeOn(Schedulers.computation()),
						sonarObsSrc.sample(clock).subscribeOn(Schedulers.computation()),
						(v1,v2) -> new Pair<String>(v1,v2)
				);
		s3.subscribe( (Pair<String> p ) -> System.out.println("Pair: "+p)  );
   		
 		RxUtils.log("END");
   	}
// MAIN	
	public static void main( String[] args){
		new ZipSonarStepperStreams().doJob();
	}
}

/*
 */
