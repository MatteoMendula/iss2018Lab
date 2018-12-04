package it.unibo.rxJava2Utils;
import java.util.concurrent.TimeUnit;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import it.unibo.qactors.akka.QActor;

public class sonarStepperZipStreams {
//	private int sampleInterval= 200;
	private Observable<String> sonarObsSrc     ;
	private Observable<String> stepperObsSrc, stepperObsSrc1   ;
 	
	private static sonarStepperZipStreams mySelf = null;
	
	public static void createTheStreams( QActor qa, String sampleIntervalStr ) {
		if(  mySelf == null) {
			mySelf = new sonarStepperZipStreams(  );
			int sampleInterval = Integer.parseInt( sampleIntervalStr );
			mySelf.doJob(qa,sampleInterval);
		}
	}
	
//	public sonarStepperZipStreams(QActor qa, int sampleInterval) {
//		this.sampleInterval=sampleInterval;
//	}
	
	protected  void doJob( QActor qa, int sampleInterval ){
		RxUtils.log("doJob INIT");
		
 		sonarObsSrc    = new SonarStream("sonar" ).createObservableStream(  );
		stepperObsSrc  = new StepperStream("stepper" ).createObservableStream();
//		stepperObsSrc1  = new StepperStream("stepper" ).createObservableStream();
		
 		Observable<Long> clock   = Observable.interval(sampleInterval, TimeUnit.MILLISECONDS);
		RxUtils.log("doJob START");
		
//		stepperObsSrc1.subscribeOn(Schedulers.computation()).subscribe( (v) -> RxUtils.log( "obsStr1)"+v ) );
		
		Observable<String>   streamOfPos = 
				Observable.zip(
						stepperObsSrc.sample(clock).subscribeOn(Schedulers.computation()),
						sonarObsSrc.sample(clock).subscribeOn(Schedulers.computation()),
						(v1,v2) -> buildPosition(v1,v2)
				);
// 		streamOfPos
// 			.subscribeOn(Schedulers.computation())
// 			.subscribe(  RxUtils.logObserver("(log)polar: ", null) ); 
		streamOfPos
 			.subscribeOn(Schedulers.computation())
			.subscribe(  
				( String p )    -> emitData(p,qa),   
				( Throwable e ) -> RxUtils.log("streamOfPosZip Error:" + e),
				() -> RxUtils.log("streamOfPosZip completed") 
		);
		
// 		streamOfPos.subscribe(  RxUtils.logObserver("(log)polar(file): ", "logStream.txt") ); 
 		RxUtils.log("doJob END");
   	}
	
	protected String buildPosition(String v1, String v2) {
		//v1=p(X)  v2=d(Y)
		Struct tv1 = (Struct) Term.createTerm(v1);
		Struct tv2 = (Struct) Term.createTerm(v2);
		return "p("+tv2.getArg(0) + "," + tv1.getArg(0) +")" ; //p(DISTANCE,ANGLE)
	}
	protected void emitData(String pos, QActor qa ) {
 		System.out.println("polar: "+pos) ;		
		RxUtils.log(pos );
		RxUtils.logOnFile(pos, "logStream.txt");
		if( qa != null ) qa.emit("polar", pos);
	}
// MAIN	
	public static void main( String[] args){
		System.out.println("USAGE: java -jar sonarStepperZipStreams.jar 300");
		if( args != null ) sonarStepperZipStreams.createTheStreams(null, args[0]); 
		else sonarStepperZipStreams.createTheStreams(null, "200"); 
	}
}

/*
 */
