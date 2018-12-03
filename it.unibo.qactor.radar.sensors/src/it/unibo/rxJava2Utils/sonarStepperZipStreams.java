package it.unibo.rxJava2Utils;
import java.util.concurrent.TimeUnit;

import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import it.unibo.qactors.akka.QActor;

public class sonarStepperZipStreams {
	private int maxNum = 1000;
	private int rate   = 100;
 	
	private SonarStream sonarSrc     ;
	private StepperStream stepperSrc  ;
 	
	private Observable<String> sonarObsSrc     ;
	private Observable<String> stepperObsSrc   ;
 	
	private static sonarStepperZipStreams mySelf = null;
	
	public static void createTheStreams( QActor qa ) {
		if(  mySelf == null) {
			mySelf = new sonarStepperZipStreams();
			mySelf.doJob(qa);
		}
	}
	
	protected  void doJob( QActor qa ){
		RxUtils.log("doJob INIT");
		
		sonarSrc       = new SonarStream("sonar", rate,  maxNum );
		stepperSrc     = new StepperStream("stepper" );
		
		sonarObsSrc    = Observable.create( sonarSrc );
		stepperObsSrc  = Observable.create( stepperSrc );
		
 		Observable<Long> clock   = Observable.interval(500, TimeUnit.MILLISECONDS);
		RxUtils.log("doJob START");
		
		Observable<String>   streamOfPos = 
				Observable.zip(
						stepperObsSrc.sample(clock).subscribeOn(Schedulers.computation()),
						sonarObsSrc.sample(clock).subscribeOn(Schedulers.computation()),
						(v1,v2) -> buildPosition(v1,v2)
				);
		streamOfPos.subscribe(  
				( String p )    -> emitData(p,qa),   
				( Throwable e ) -> RxUtils.log("streamOfPos Error:" + e),
				() -> RxUtils.log("streamOfPos completed") 
		);
		
 		RxUtils.log("doJob END");
   	}
	
	protected String buildPosition(String v1, String v2) {
		//v1=p(X)  v2=d(Y)
		Struct tv1 = (Struct) Term.createTerm(v1);
		Struct tv2 = (Struct) Term.createTerm(v2);
		return "p("+tv2.getArg(0) + "," + tv1.getArg(0) +")" ; //p(DISTANCE,ANGLE)
	}
	protected void emitData(String pos, QActor qa ) {
//		System.out.println("polar: "+pos) ;
		if( qa != null ) qa.emit("polar", pos);
	}
// MAIN	
	public static void main( String[] args){
		  sonarStepperZipStreams.createTheStreams(null); 
	}
}

/*
 */
