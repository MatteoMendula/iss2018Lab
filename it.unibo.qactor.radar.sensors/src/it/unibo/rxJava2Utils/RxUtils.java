package it.unibo.rxJava2Utils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxUtils {
	public static long start = System.currentTimeMillis();
	
	public static void log( Object msg ){
		long now = System.currentTimeMillis();
		System.out.println(
				now -start +"\t|"+
				Thread.currentThread().getName()+"\t|"+
				msg);
	}
	
	public static Observer<String> logObserver(String logo){
		return new Observer<String>(){
			/*
			 * onSubscribe (not present in RxJava1) gets the Disposable as a parameter which  
			 * can be used for disposing the connection between the Observable and the Observer itself 
			 * as well as checking whether we're already disposed or not.
			 */
			@Override
			public void onSubscribe(Disposable d) {
				log(logo + "subscribes Disposable=" + d  );
			}
			@Override
			public void onNext(String s) {
				log(logo + s);
			}
			@Override
			public void onError(Throwable e) {
 				e.printStackTrace();
			}
			@Override
			public void onComplete() {
				log(logo + "completed");
			}			
		};
	}
	
	public static void delay( int dt ){
		try {
			Thread.sleep(dt);
//			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
	 		e.printStackTrace();
		}
	}		

}
