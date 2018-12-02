package it.unibo.RxJava2Utils;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TestRada18EventSource {
	private Observable<String> s1 = Observable.create( new Radar18EventSource("A", 1000) );
	private Observable<String> s2 = Observable.create( new Radar18EventSource("B", 2000) );
	public void doJob(){
		RxUtils.log("START");
 		RxUtils.log(" --------- STEP 1");
  		s1
			.subscribeOn(Schedulers.computation())		//asynch: BETTER DESIGN
			.mergeWith(s2.subscribeOn(Schedulers.computation()))
			.subscribe( v -> RxUtils.log("Result: " + v) );
//  		RxUtils.delay(5000);
//  		s2
//			.subscribeOn(Schedulers.computation())		//asynch: BETTER DESIGN observeOn
//			.subscribe( RxUtils.logObserver("Step2:") );
  		
 		RxUtils.log("END");
   	}
// MAIN	
	public static void main( String[] args){
		new TestRada18EventSource().doJob();
	}
}

/*
 */
