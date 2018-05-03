package it.unibo.mbot.divide;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import alice.tuprolog.SolveInfo;
import it.unibo.ctxVirtualRobotExecutor.MainCtxVirtualRobotExecutor;
import it.unibo.qactors.QActorUtils;
import it.unibo.qactors.akka.QActor;
 
public class TestVirtualRobotExecutor {
private QActor rover         = null;
private QActor polarlogagent = null; 
   	 
   	@Before
	public void systemSetUp() throws Exception  {
  		System.out.println("systemSetUp starts "   ); 	
   		activateRadar();		//(1)
   		activateUnity();		//(2)  //here the user must click on Play
  		MainCtxVirtualRobotExecutor.initTheContext(); //(3)
  		waitForRoverAndPolarLogRunning();  //(4)
  		createRoverWithoutUser();          //(5)
  	} 
 	 @After
	 public void terminate(){
	  	System.out.println("====== terminate  "   );
 	 }
  	
  	protected void activateRadar()    {
  		try {
 			System.out.println("activateRadar ... "    );
 			Runtime.getRuntime().exec("C:/Didattica2018Run/radarStart.bat" );
  			Thread.sleep( 15000 );
  		}catch( Exception e) {
  			System.out.println("activateRadar fails "  + e.getMessage() );
  		}
  	}
  	protected void activateUnity() {
 		try {
 			System.out.println("createUnity ... "    );
 			Runtime.getRuntime().exec("C:/Didattica2018Run/unityStart.bat" ); 		
 			Thread.sleep( 10000 );
  		}catch( Exception e) {
  			System.out.println("createUnity fails "  + e.getMessage() );
  		}		
  	}
  	protected void waitForRoverAndPolarLogRunning() {
  		try {
			System.out.println("waitForRoverAndLogRunning ... "    );
	 		while( rover == null || polarlogagent == null) {
	  			Thread.sleep(250);
	  			rover         = QActorUtils.getQActor("rover_ctrl");
	  			polarlogagent = QActorUtils.getQActor("polarlogagent_ctrl");
	  		}  
	  		System.out.println("polarlogagent RUNNING: "  + polarlogagent );
  		}catch( Exception e) {
  			System.out.println("waitForRoverRunning fails "  + e.getMessage() );
  		}		
  	} 	
  	protected void createRoverWithoutUser() {
 		rover.removeRule("unityConfig(X)");
		rover.initUnityConnection("localhost");
		rover.createSimulatedActor();
		rover.emit("mindcmd", "usercmd( robotgui(unityAddr(localhost)) )");	//just to setup the avatar  		
  	}
  	
 
 	@Test
	public void aTest() {  
 		System.out.println("====== aTest ==============="  );
		try {
	 		rover.emit("mindcmd", "usercmd( robotgui(w(X)) )");
	 		Thread.sleep(4000);
	 		rover.emit("mindcmd", "usercmd( robotgui(h(X)) )");
	 		Thread.sleep(200);	//just to say another time ...
	 		rover.emit("mindcmd", "usercmd( robotgui(h(X)) )");
			SolveInfo sol = polarlogagent.solveGoal("p(X,30)");
			if( sol.isSuccess() ) {
				System.out.println( "distance=" + sol.getVarValue("X") );
	 			assertTrue("" , sol.isSuccess() );	
 			}else {
 				System.out.println( "WARNING ... "  );
 				fail("aTest STRANGE BEHAVIOUR"   );
 			}
			//Avoid to break the testing too early
	  		Thread.sleep(300000);
		} catch (Exception e) {
			System.out.println( "ERROR=" + e.getMessage() );
			fail("aTest " + e.getMessage() );
		}		
 	}
 	
}
