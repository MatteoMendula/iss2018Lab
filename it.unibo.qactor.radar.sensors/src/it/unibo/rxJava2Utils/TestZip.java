package it.unibo.rxJava2Utils;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public class TestZip {
 	private Observable<String> s1 = Observable.fromArray("1","2","3","4","5" );
 	private Observable<String> s2 = Observable.fromArray("a","b","c" );

	public void doZipAsynch(){ 
		RxUtils.log("START doZipAsynch_1");
		s1
			.zipWith( s2, (v1,v2) -> new Pair<String>(v1,v2) )
			.subscribe( p -> System.out.println("Pair: "+p) );
		RxUtils.log("doZipAsynch_2");
  		
		Observable< Pair<String> > s3 = 
				Observable.zip(
						s1.subscribeOn(Schedulers.computation()),
						s2.subscribeOn(Schedulers.computation()),
						(v1,v2) -> new Pair<String>(v1,v2)
				);
		s3.subscribe( (Pair<String> p ) -> System.out.println("Pair with schedule A: "+p)  );
		s3.subscribe( (Pair<String> p ) -> System.out.println("Pair with schedule B: "+p)  );
		//.subscribe( (Pair<String> p ) -> Utils.logObserver("Pair shed: " + p )  );
		RxUtils.delay(2000);
		RxUtils.log("END doZipAsynch");
   	}	
	// MAIN	
		public static void main( String[] args){
			new TestZip().doZipAsynch();
		}

}
