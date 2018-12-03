package it.unibo.rxJava2Utils;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class Radar18EventSource implements ObservableOnSubscribe<String>{
private   String item          = null;
private   final int maxNumItems = 5;
private  String prefix = "";
private int interval = 0;

	public Radar18EventSource(String prefix, int interval) {
		this.prefix   = prefix;
		this.interval = interval;
		startGenItems();
	}
	
	@Override
	public void subscribe(ObservableEmitter<String> emitter) throws Exception {
 		for( int i=0; i<maxNumItems; i ++) {	
 			if( emitter.isDisposed() ) break;
 			consume();  //The observer waits in its own thread
   			emitter.onNext(item);
   			item = null;
 		}
 		emitter.onComplete();
 	}

	protected synchronized void consume() {
		while( item == null ) {
			try {
				wait();
 			} catch (InterruptedException e) {
 				e.printStackTrace();
			}
		}//
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
			 for( int i=0; i< maxNumItems*2; i ++) {	
				 System.out.println("Generated " + (prefix + i) );
				 setItem( prefix + i );
				 RxUtils.delay(interval);
 			 }
		 };
		 final Thread t = new Thread(r);
		 t.start();
	}
}
