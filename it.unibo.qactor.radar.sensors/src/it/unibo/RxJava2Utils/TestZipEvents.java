package it.unibo.RxJava2Utils;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TestZipEvents {
	private int maxNum = 7;
	private int rate   = 500;
	private int ampl   = 4;
	
	AEventSource sonarSrc = new AEventSource("sonar", rate,      maxNum*ampl);
	AEventSource posSrc  =  new AEventSource("pos",   rate*ampl, maxNum );
	
	private Observable<String> sonarObsSrc = Observable.create( sonarSrc );
	private Observable<String> posObsSrc   = Observable.create( posSrc );
	
	public void doJob(){
		RxUtils.log("START");
 		Observable<Long> clock          = Observable.interval(rate*ampl/1000, TimeUnit.SECONDS);
		Observable<String> sonarSampled = sonarObsSrc.sample(clock);
		Observable< Pair<String> > s3 = 
				Observable.zip(
						posObsSrc.subscribeOn(Schedulers.computation()),
						sonarSampled.subscribeOn(Schedulers.computation()),
						//sonarObsSrc.sample(clock).subscribeOn(Schedulers.computation()),
						(v1,v2) -> new Pair<String>(v1,v2)
				);
		s3.subscribe( (Pair<String> p ) -> System.out.println("Pair: "+p)  );
   		
 		RxUtils.log("END");
   	}
// MAIN	
	public static void main( String[] args){
		new TestZipEvents().doJob();
	}
}

/*
 */
