package it.unibo.utils;

import it.unibo.qactors.QActorContext;
import it.unibo.qactors.akka.QActor;

public class movePlanUtil {
	
	public static void move(QActor qa, String move, String duration ) {
	  try{	
  		   String temporaryStr = "robotCmd(MOVE,T)".replace("MOVE", move).replace("T", duration) ;
  		   //System.out.println( "movePlanUtil temporaryStr:" + temporaryStr );
 		   qa.sendMsg("robotCmd","player", QActorContext.dispatch, temporaryStr ); //do the robot move
  		   aiutil.doMove( qa ,move );  //update the map
  		   Thread.sleep( Integer.parseInt(duration));
  	  }catch(Exception e ){  
		   System.out.println( "movePlanUtil ERROR:" + e.getMessage() );
 	  }		
	}
	
	public static void moveNoMap(QActor qa, String move, String duration ) {
		  try{	
	  		   String temporaryStr = "robotCmd(MOVE,T)".replace("MOVE", move).replace("T", duration) ;
	  		   //System.out.println( "movePlanUtil moveNoMap temporaryStr:" + temporaryStr );
	 		   qa.sendMsg("robotCmd","player", QActorContext.dispatch, temporaryStr ); //do the robot move
 	  		   Thread.sleep( Integer.parseInt(duration));
 	 	  }catch(Exception e ){  
			   System.out.println( "movePlanUtil ERROR:" + e.getMessage() );
	 	  }		
		}
}
