package it.unibo.RxJava2Utils;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
 
public abstract class AbstractSensorStream implements ObservableOnSubscribe<String>{
private  String item     = null;
private  boolean goon    = true;
protected String name    = "";
protected int numOfItems = 0;

	public  AbstractSensorStream( String name ) {
		this.name = name;
 		startGenItems();
	}
	
	@Override
	public void subscribe(ObservableEmitter<String> emitter) throws Exception {
 		while( goon ) {	
 			if( emitter.isDisposed() ) break;
 			consume();  //The observer waits in its own thread
   			emitter.onNext(item);
   			numOfItems++;
   			item = null;
 		}
  		RxUtils.log("AbstractSensorStream(" + name + ") terminated" );
 		emitter.onComplete();
 	}

	protected synchronized void consume( ) {
		while( item == null ) {
			try {
				wait();
 			} catch (InterruptedException e) {
 				RxUtils.log("AbstractSensorStream(" + name + ") consume: interrupted numOfItems=" + numOfItems  );
			}
		}
 	}
	
	public synchronized void setItem( String v ) {
		item = v;
		notifyAll();
	}
	protected void terminate(   ) {
 	}


	/*
	 * Local generator of items. It must call setItem
	 */
	public abstract void startGenItems();
	
	
	
 	
}
