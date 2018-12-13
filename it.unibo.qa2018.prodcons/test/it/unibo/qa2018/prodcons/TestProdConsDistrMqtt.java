/*
 * The system works correctly if each produced item is consumed in the same order.
 */

package it.unibo.qa2018.prodcons;
 
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class TestProdConsDistrMqtt {
 

	@Before
	public void setUp(){  		
			System.out.println("================== setUp  ==================");
				try { 
					new Thread() {
						public void run() { startProducer(); }
					}.start();
					new Thread() {
						public void run() { startConsumer(); }
					}.start();
					new Thread() {
						public void run() { startConsumer2(); }
					}.start();
					Thread.sleep(1000);
   				} catch (Exception e) {
			 			fail( e.getMessage() ) ;
			 	}	
	}
	
	protected void startProducer() {
		try { 
 			it.unibo.ctxProdDistrMqtt.MainCtxProdDistrMqtt.initTheContext();
   	 		System.out.println("setUp ENDS producer"   );
		} catch (Exception e) {
	 			fail( e.getMessage() ) ;
	 	}			
	}
	protected void startConsumer() {
		try { 
  			it.unibo.ctxConsDistrMqtt.MainCtxConsDistrMqtt.initTheContext();
  	 		System.out.println("setUp ENDS consumer"    );
		} catch (Exception e) {
	 			fail( e.getMessage() ) ;
	 	}			
	}
	protected void startConsumer2() { //TESTER
		try { 
  			it.unibo.ctxCons2DistrMqtt.MainCtxCons2DistrMqtt.initTheContext();
   	 		System.out.println("setUp ENDS consumer2 "   );
		} catch (Exception e) {
	 			fail( e.getMessage() ) ;
	 	}			
	}
	
  	@Test
	public void test(){
		System.out.println("&&&&&&&&&&&&&&& test &&&&&&&&&&&&&&&&&&&&&&&");
		try {
			Thread.sleep( 60000 );
		} catch (InterruptedException e) {
 			e.printStackTrace();
		}
 	}


}
