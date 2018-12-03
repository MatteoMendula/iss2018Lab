package it.unibo.rxJava2Utils;
import java.io.BufferedReader;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class SonarStream extends AbstractSensorStream{
private int maxNumItems = 0;
private int interval    = 0;
private int counter     = 0;
protected   String distance = null; // 
protected   BufferedReader readerC;
	 

	public SonarStream(String name, int interval, int maxNumItems) {
		super( name );
 		this.interval    = interval;
		this.maxNumItems = maxNumItems;
 	}
 
	/*
	 * A local generator of item 
	 */
	@Override
	public void startGenItems() {
		startSonarC( );
		Runnable r = () -> {
//			for( int i=0; i< maxNumItems; i ++) {	
			while(true) {
  				try {
					getDistanceFromSonar(   );
					RxUtils.delay(interval);
				} catch (Exception e) {
					System.out.println("SonarStream startGenItems ERROR" +  e.getMessage());
// 					e.printStackTrace();
					break;
				}
	 		}
		};
		final Thread t = new Thread(r);
		t.start();
	}
 	
 	//SONAR
		public   void startSonarC( ){
	  		try {
	  			System.out.println("startSonarC");
	  	  		//Execute a C program that generates the sonar values
				Process p = Runtime.getRuntime().exec("sudo ./SonarAlone");
				readerC   = new BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
			} catch (IOException e) {
				System.out.println("startSonarC ERROR:" +  e.getMessage());
//	 			e.printStackTrace();
			}		
		}
		
		public   void getDistanceFromSonar(   ) throws Exception {
//			try {
				String inpS = readerC.readLine();
				int data    = Integer.parseInt(inpS);
				//distance    = "data( "+ counter++ + ", distance, d("+data+") )";  
				distance = "d("+ data + ")";
				setItem( distance );
//			} catch (Exception e) {
//	  			//distance = "data( "+ counter++ + ", distance, d(0) )";  
//				System.out.println("getDistanceFromSonar ERROR" +  e.getMessage());
//				distance = "d(0)";
//				setItem( distance );
// //				e.printStackTrace();
//			}
		}	
	
		// MAIN	
		public static void main( String[] args){
			  int maxNum = 50;
			  int rate   = 100;
			  String name = "SonarRxStream"
; 			  SonarStream sonarSrc = new SonarStream(name, rate,  maxNum );
			  Observable<String> sonarObsSrc = Observable.create( sonarSrc );
			  sonarObsSrc
			  	.subscribeOn(Schedulers.computation())
			  	.subscribe( v -> RxUtils.log(name+": " + v) );
		}
	
}
