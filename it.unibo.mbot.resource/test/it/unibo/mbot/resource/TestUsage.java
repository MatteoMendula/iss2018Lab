package it.unibo.mbot.resource;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.ctxMbotManager.MainCtxMbotManager;
import it.unibo.ctxTesterUser1.MainCtxTesterUser1;
import it.unibo.ctxTesterUser2.MainCtxTesterUser2;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.QActorUtils;
import it.unibo.qactors.akka.QActor;

public class TestUsage {
private QActorContext mbotmanagerctx;
private QActor mbotmanager = null;

  	@Before
	public void setUp() throws Exception  {
//  		mbotmanagerctx = MainCtxMbotManager.initTheContext();
//  		while( mbotmanager == null ) {
//  			Thread.sleep(250);
//  			mbotmanager = QActorUtils.getQActor("mbotmanager_ctrl");
//  		}  
  		System.out.println("====== setUp  start the first user "   );
  		MainCtxTesterUser1.initTheContext();
// 		System.out.println("====== setUp  start the second user "   );
// 		MainCtxTesterUser2.initTheContext();
  		System.out.println("====== setUp   "   );
  	}
	 @After
	 public void terminate(){
	  	System.out.println("====== terminate  "   );
  	 }

 	@Test
	public void aTest() {  
 		System.out.println("====== aTest ==============="  );
		//Avoid to break the testing too early
 		try {
			Thread.sleep(600000);
		} catch (Exception e) {
		fail("actorTest " + e.getMessage() );
	}		
 	}

}
