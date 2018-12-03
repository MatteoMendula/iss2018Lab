package it.unibo.rxJava2Utils;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class AEventSource implements ObservableOnSubscribe<String>{
private  String item     = null;
private  int maxNumItems = 0;
private  String prefix   = "";
private int interval     = 0;

	public AEventSource(String prefix, int interval, int maxNumItems) {
		this.prefix      = prefix;
		this.interval    = interval;
		this.maxNumItems = maxNumItems;
		startGenItems();
	}
	
	@Override
	public void subscribe(ObservableEmitter<String> emitter) throws Exception {
 		for( int i=0; i<maxNumItems; i++) {	
 			if( emitter.isDisposed() ) break;
 			consume(i);  //The observer waits in its own thread
   			emitter.onNext(item);
   			item = null;
 		}
 		RxUtils.delay(interval);
 		RxUtils.log("AEventSource(" + prefix + ") terminated; maxNumItems=" + maxNumItems);
 		emitter.onComplete();
 	}

	protected synchronized void consume(int i) {
		while( item == null ) {
			try {
				wait();
 			} catch (InterruptedException e) {
 				RxUtils.log("AEventSource(" + prefix + ") consume: interrupted at step=" + i +"/"+maxNumItems);
			}
		}
 	}
	
	public synchronized void setItem( String v ) {
		item = v;
		notifyAll();
	}


/*
 * A local generator of items, just to test 
 * Should be replaced by an emitter of events (e.g. pos or distance)
 */
	public void startGenItems() {
		 Runnable r = () -> {
			 for( int i=0; i< maxNumItems; i ++) {	
				 System.out.println("Generated " + (prefix + i) );
				 setItem( prefix + i );
				 RxUtils.delay(interval);
 			 }
		 };
		 final Thread t = new Thread(r);
		 t.start();
	}
}
