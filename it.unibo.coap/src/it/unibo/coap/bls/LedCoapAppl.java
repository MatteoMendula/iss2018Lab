package it.unibo.coap.bls;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
 
public class LedCoapAppl{
//	public static final String ledResourceName    = "led";
//	public static final String buttonResourceName = "button";

	private CoapClient ledClient;
	private CoapClient buttonClient;
	private boolean ledBlinkGoon = false;
	
	public LedCoapAppl() {
 			try {
				configureTheSystem();
			} catch (Exception e) {
 				e.printStackTrace();
			}
 	}

	public void configureTheSystem() throws Exception {
		//CREATE a Led Thing and a Button Thing
		LedThing.create(8010, null);
		ButtonThing.create(8020, null);
 		//CREATE the clients for the things
		ledClient     = new  CoapClient( LedThing.getPath(8010)    );
		buttonClient  = new  CoapClient( ButtonThing.getPath(8020) );
		//CREATE the button CoAP observer that embeds the application logic
		setAButtonCoapObserver();
	}

	 protected  void setAButtonCoapObserver() {  
		CoapObserveRelation relation = buttonClient.observe(
				new CoapHandler() {
					@Override public void onLoad(CoapResponse response) {
						String content = response.getResponseText();
//						handleTheLed(content);
						if( content.equals("true") && ! ledBlinkGoon) {
							ledBlinkGoon = true;
							blinkTheLed( );
						}else {
							ledBlinkGoon = false;
						}
					}					
					@Override public void onError() {
						System.err.println("Client: OBSERVING FAILED (press enter to exit)");
					}
				});
		System.out.println("setAButtonCoapObserver ... " + buttonClient);
	} 

 	protected  void handleTheLed(String content) {
		new Thread() {
			public void run() {
				CoapResponse rrr = ledClient.put(content, MediaTypeRegistry.TEXT_PLAIN); 
				System.out.println("handleTheLed. RESPONSE led put: " + rrr.getResponseText());									
			}
		}.start();		
	}
	
	protected  void blinkTheLed( ) {
			new Thread() {
  				public void run() {
  					System.out.println("blinkTheLed STARTS"  );
					CoapResponse rrr;
					while( ledBlinkGoon ) {
						rrr = ledClient.put("true", MediaTypeRegistry.TEXT_PLAIN); 
						delay(500);
 						rrr = ledClient.put("false", MediaTypeRegistry.TEXT_PLAIN); 
						delay(500);
					}
					System.out.println("blinkTheLed STOPS " );
				}
			}.start();	
	}

	protected void delay( int dt ) {
		try { Thread.sleep(dt);} catch (InterruptedException e) { e.printStackTrace();	}
	}
	
/* 	
 * Just for testing ...
 */
	 protected  boolean ledGet() {
		 	System.out.println("  ledGet "  );
		    String content = ledClient.get().getResponseText();
 		    System.out.println("RESPONSE ledGet : " + content);
		    return content.equals("true");
	  }
 	
 	public void startTheSystem() throws  Exception {
 		System.out.println("-------------------------------aaaa");
 		   System.out.println("ledGet="+ledGet());
		   ledClient.put("true", MediaTypeRegistry.TEXT_PLAIN);
		   System.out.println("ledGet="+ledGet());
		   ledClient.put("false", MediaTypeRegistry.TEXT_PLAIN);
		   System.out.println("ledGet="+ledGet()); 
		System.out.println("-------------------------------");
		   buttonClient.put("true", MediaTypeRegistry.TEXT_PLAIN);
 		   Thread.sleep(3000); //give time to change ...
		   buttonClient.put("false", MediaTypeRegistry.TEXT_PLAIN);
		   System.out.println("ledGet="+ledGet());	//should be false
		System.out.println("-------------------------------"); 		
 	}
 	
	public static void main(String args[])  throws Exception {	
		  LedCoapAppl c = new LedCoapAppl(); 
		  c.startTheSystem();
	}

}

/*
	public void configureTheSystemOld() {
	try {
	//CREATE a Led Thing and a Button Thing
	  new CoapServer(8010).add( 
				  ConcurrentCoapResource.createConcurrentCoapResource(1, new LedResource()   )).start();
//	  new CoapServer(8020).add( 
//			  ConcurrentCoapResource.createConcurrentCoapResource(1, new ButtonResource())).start();

//	  new CoapServer(8010).add(  new LedResource()     ).start();
		  new CoapServer(8020).add(  new ButtonResource()  ).start();		 
	  Thread.sleep(500); //give time the servers to start ... 	  		

	  //CREATE the clients for the things
	  ledClient     = new  CoapClient("coap://localhost:8010/"+ledResourceName);
	  buttonClient  = new  CoapClient("coap://localhost:8020/"+buttonResourceName );
  	  setAButtonCoapObserver();
	} catch (Exception e) {
			e.printStackTrace();
	}
	}
*/