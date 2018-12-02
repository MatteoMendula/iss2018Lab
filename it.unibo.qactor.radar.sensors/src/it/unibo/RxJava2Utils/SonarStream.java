package it.unibo.RxJava2Utils;
import java.io.BufferedReader;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class SonarStream extends AbstractSensorStream{
private int maxNumItems = 0;
private int interval    = 0;
private int counter     = 0;
protected   String distance = null; // data( Counter, distance, d(V) )
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
			for( int i=0; i< maxNumItems; i ++) {	
  				getDistanceFromSonar(   );
				RxUtils.delay(interval);
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
				for( int i=0; i<maxNumItems; i++) {	
					getDistanceFromSonar(   );
				}
			} catch (Exception e) {
	 			e.printStackTrace();
			}		
		}
		
		public   void getDistanceFromSonar(   ){
			try {
				String inpS = readerC.readLine();
				int data    = Integer.parseInt(inpS);
				distance    = "data( "+ counter++ + ", distance, d("+data+") )";  
				setItem( distance );
			} catch (Exception e) {
	  			distance = "data( "+ counter++ + ", distance, d(0) )";  
				e.printStackTrace();
			}
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
