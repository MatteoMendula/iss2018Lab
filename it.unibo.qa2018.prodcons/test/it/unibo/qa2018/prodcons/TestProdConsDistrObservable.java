/*
 * The system works correctly if each produced item is consumed in the same order.
 * For each produced item, the producer stores in its actorKb the fact produced( item(X) )
 * For each consumed item, the consumer stores in its actorKb the fact consumed( item(X) )
 */

package it.unibo.qa2018.prodcons;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
 
import it.unibo.consumerobservable.Consumerobservable;
 
import it.unibo.producerobservable.Producerobservable;
import it.unibo.qactors.QActorUtils;
import it.unibo.tester.Tester;

public class TestProdConsDistrObservable {
private Producerobservable producer;
private Consumerobservable consumer;
private Tester   tester;

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
						public void run() { startTester(); }
					}.start();
					Thread.sleep(1000);
   				} catch (Exception e) {
			 			fail( e.getMessage() ) ;
			 	}	
	}
	
	protected void startProducer() {
		try { 
			System.out.println("setUp STARTS producer="+producer   );
			it.unibo.ctxProdObservable.MainCtxProdObservable.initTheContext();
 			Thread.sleep( 1000 );  //give the time to start  
  	 		System.out.println("setUp ENDS producer="   );
		} catch (Exception e) {
	 			fail( e.getMessage() ) ;
	 	}			
	}
	protected void startConsumer() {
		try { 
			System.out.println("setUp STARTS consumer="+consumer   );
 			it.unibo.ctxConsObservable.MainCtxConsObservable.initTheContext();
			Thread.sleep( 1000 );  //give the time to start and execute
 	 		System.out.println("setUp ENDS consumer"    );
		} catch (Exception e) {
	 			fail( e.getMessage() ) ;
	 	}			
	}
	protected void startTester() {
		try { 
			System.out.println("setUp STARTS tester"    );
 			it.unibo.ctxTester.MainCtxTester.initTheContext();
			Thread.sleep( 1000 );  //give the time to start and execute
  	 		System.out.println("setUp ENDS consumer="+consumer   );
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
