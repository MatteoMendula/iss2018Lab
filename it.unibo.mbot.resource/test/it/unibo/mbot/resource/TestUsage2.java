package it.unibo.mbot.resource;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import it.unibo.ctxTesterUser2.MainCtxTesterUser2;
 

public class TestUsage2 {
 
  	@Before
	public void setUp() throws Exception  {
 		System.out.println("====== setUp  start the second user "   );
 		MainCtxTesterUser2.initTheContext();
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
		fail("aTest " + e.getMessage() );
	}		
 	}

}
