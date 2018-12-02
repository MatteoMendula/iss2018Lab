package it.unibo.radar18Utils;

import java.io.BufferedReader;
import it.unibo.qactors.akka.QActor;

public class radarStepperUtils {
    protected  static String pos      = null; //p(  angle  ) 
    protected  static String distance = null; //d(  distance  )
    protected  static String fused    = "none"; //p( distance,angle )

	protected static BufferedReader readerC;
 	protected static int counter = 1;

//STEPPER	
	public static void 	 startStepperPyhton(QActor qa){
		try {
//			qa.println("startStepperPyhton "  );
//			Process pPython =  
			Runtime.getRuntime().exec("sudo python stepper.py");
 		} catch (Exception e) {
 			e.printStackTrace();
		}		
	}
//SONAR
	public static void startSonarC(QActor qa){
  		try {
  	  		//Execute a C program that generates the sonar values
			Process p = Runtime.getRuntime().exec("sudo ./SonarAlone");
			readerC   = new BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
			qa.println("Process in C STARTED "  +  readerC);
		} catch (Exception e) {
 			e.printStackTrace();
		}		
	}
	
	public static void getDistanceFromSonar( QActor qa ){
		try {
			String inpS = readerC.readLine();
			int data   = Integer.parseInt(inpS);
			distance = "data( "+ counter++ + ", distance, d("+data+") )";  
			qa.addRule("sonarDistance("+ distance +")");
//			return distance;
		} catch (Exception e) {
  			distance = "data( "+ counter++ + ", distance, d(0) )";  
 			qa.addRule("sonarDistance("+ distance +")");
// 			return "data( "+ counter++ + ", distance, d(0) )"; 
			e.printStackTrace();
		}
	}	
	
	
//RADAR
    public static void fuseSensorData( QActor qa  ){
        if( pos != null && distance != null){	//received both
        	fused = "p(DISTANCE,ANGLE)".replace("DISTANCE", distance).replaceAll("ANGLE", pos);
     		qa.println( qa.getName() + " fused= " + fused );
     		qa.addRule("fusedData("+ fused +")");
       	} 
//		return fused;
     }// fuseSensorData
    
    public static void storeDistanceData( QActor qa, String dataStr ){
    	qa.println("storeDistanceData  : " + dataStr);
    	int data = Integer.parseInt(dataStr);
    	distance = ""+data;
    }    
    public static void storeDirectionData(QActor qa,  String dataStr ){
    	qa.println("storeDirectionData  : " + dataStr);
    	int data = Integer.parseInt(dataStr);
    	pos = ""+data;
    } 


}
