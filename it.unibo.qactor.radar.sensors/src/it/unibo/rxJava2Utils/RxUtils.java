package it.unibo.rxJava2Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxUtils {
	public static PrintWriter outLog = null;
//	public static long start = System.currentTimeMillis();	
	public static void log( Object msg ){
//		long now = System.currentTimeMillis();
		System.out.println("\t|"+msg);
//				now -start +"\t|"+Thread.currentThread().getName()+"\t|"+msg);
	}
	public static void logOnFile( Object msg, String fileName ){		
//		long now = System.currentTimeMillis();		
		String text = "\t|"+msg;
//				now -start +"\t|"+Thread.currentThread().getName()+"\t|"+msg;
		try {
			if( outLog == null ) {
				outLog = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
 			}
			outLog.println(text);
			outLog.flush();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Observer<String> logObserver(String logo, String fileName){
		return new Observer<String>(){
			/*
			 * onSubscribe (not present in RxJava1) gets the Disposable as a parameter which  
			 * can be used for disposing the connection between the Observable and the Observer itself 
			 * as well as checking whether we're already disposed or not.
			 */
			@Override
			public void onSubscribe(Disposable d) {
				String s = logo + "subscribes Disposable=" + d ;
				if( fileName != null ) logOnFile( s,fileName  );
				else log( s );
			}
			@Override
			public void onNext(String s) {
				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx " + s + " fileName=" + fileName);
				if( fileName != null ) logOnFile( logo + s,fileName  );
				else log(logo + s);
			}
			@Override
			public void onError(Throwable e) {
				String s = logo + " ERROR " + e.getMessage();
				if( fileName != null ) logOnFile( s,fileName  );
				else log( s );
			}
			@Override
			public void onComplete() {
				String s = logo + "completed";
				if( fileName != null ) logOnFile( s,fileName  );
				else log( s );
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
