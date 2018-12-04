package it.unibo.rxJava2Utils;
import java.io.BufferedReader;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class SonarStream extends AbstractSensorStream{
protected   String distance = null; // 
protected   BufferedReader readerC;
	 
 	public SonarStream( String name ) {
		super( name );
  	}
 
//The local generator of item 
	@Override
	public void startGenItems() {
		startSonarC( );
		Runnable r = () -> {
 			while(true) {
  				try {
					getDistanceFromSonar(   );
//					RxUtils.delay(interval);
				} catch (Exception e) {
					System.out.println("SonarStream startGenItems ERROR:" +  e.getMessage());
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
 			}		
		}
		
		public   void getDistanceFromSonar(   ) throws Exception {
 				String inpS = readerC.readLine();
				int data    = Integer.parseInt(inpS);
				//distance    = "data( "+ counter++ + ", distance, d("+data+") )";  
				distance = "d("+ data + ")";
				setItem( distance );
 		}	
	
		// MAIN	
		public static void main( String[] args){
  			  SonarStream sonarSrc = new SonarStream( "SonarRxStream"  );
			  Observable<String> sonarObsSrc = Observable.create( sonarSrc );
			  sonarObsSrc
			  	.subscribeOn(Schedulers.computation())
			  	.subscribe( v -> RxUtils.log("SonarRxStream: " + v) );
		}
}
