/* Generated by AN DISI Unibo */ 
package it.unibo.realsonardetector;
import it.unibo.qactors.PlanRepeat;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.StateExecMessage;
import it.unibo.qactors.QActorUtils;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction;
import it.unibo.qactors.action.IActorAction.ActionExecMode;
import it.unibo.qactors.action.IMsgQueue;
import it.unibo.qactors.akka.QActor;
import it.unibo.qactors.StateFun;
import java.util.Stack;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.qactors.action.ActorTimedAction;
public abstract class AbstractRealsonardetector extends QActor { 
	protected AsynchActionResult aar = null;
	protected boolean actionResult = true;
	protected alice.tuprolog.SolveInfo sol;
	protected String planFilePath    = null;
	protected String terminationEvId = "default";
	protected String parg="";
	protected boolean bres=false;
	protected IActorAction action;
	 
	
		protected static IOutputEnvView setTheEnv(IOutputEnvView outEnvView ){
			return outEnvView;
		}
		public AbstractRealsonardetector(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/realsonardetector/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/realsonardetector/plans.txt";
	  	}
		@Override
		protected void doJob() throws Exception {
			String name  = getName().replace("_ctrl", "");
			mysupport = (IMsgQueue) QActorUtils.getQActor( name ); 
			initStateTable(); 
	 		initSensorSystem();
	 		history.push(stateTab.get( "init" ));
	  	 	autoSendStateExecMsg();
	  		//QActorContext.terminateQActorSystem(this);//todo
		} 	
		/* 
		* ------------------------------------------------------------
		* PLANS
		* ------------------------------------------------------------
		*/    
	    //genAkkaMshHandleStructure
	    protected void initStateTable(){  	
	    	stateTab.put("handleToutBuiltIn",handleToutBuiltIn);
	    	stateTab.put("init",init);
	    	stateTab.put("waitForEvents",waitForEvents);
	    	stateTab.put("handleRealSonar",handleRealSonar);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "realsonardetector tout : stops");  
	    		repeatPlanNoTransition(pr,myselfName,"application_"+myselfName,false,false);
	    	}catch(Exception e_handleToutBuiltIn){  
	    		println( getName() + " plan=handleToutBuiltIn WARNING:" + e_handleToutBuiltIn.getMessage() );
	    		QActorContext.terminateQActorSystem(this); 
	    	}
	    };//handleToutBuiltIn
	    
	    StateFun init = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("init",-1);
	    	String myselfName = "init";  
	    	temporaryStr = "\"sonardetector STARTS \"";
	    	println( temporaryStr );  
	     connectToMqttServer("tcp://192.168.43.229:1883");
	    	//switchTo waitForEvents
	        switchToPlanAsNextState(pr, myselfName, "realsonardetector_"+myselfName, 
	              "waitForEvents",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForEvents = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("waitForEvents",-1);
	    	String myselfName = "waitForEvents";  
	    	//bbb
	     msgTransition( pr,myselfName,"realsonardetector_"+myselfName,false,
	          new StateFun[]{stateTab.get("handleRealSonar") }, 
	          new String[]{"true","E","realSonar" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForEvents){  
	    	 println( getName() + " plan=waitForEvents WARNING:" + e_waitForEvents.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForEvents
	    
	    StateFun handleRealSonar = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleRealSonar",-1);
	    	String myselfName = "handleRealSonar";  
	    	printCurrentEvent(false);
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("sonar(DISTANCE)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("realSonar") && 
	    		pengine.unify(curT, Term.createTerm("sonar(DISTANCE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			String parg="sonar(realsonar,DISTANCE)";
	    			/* RaiseEvent */
	    			parg = updateVars(Term.createTerm("sonar(DISTANCE)"),  Term.createTerm("sonar(DISTANCE)"), 
	    				    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    			if( parg != null ) emit( "sonarSensor", parg );
	    	}
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("sonar(DISTANCE)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("realSonar") && 
	    		pengine.unify(curT, Term.createTerm("sonar(DISTANCE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			String parg="p(DISTANCE,0)";
	    			/* RaiseEvent */
	    			parg = updateVars(Term.createTerm("sonar(DISTANCE)"),  Term.createTerm("sonar(DISTANCE)"), 
	    				    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    			if( parg != null ) emit( "polar", parg );
	    	}
	    	//switchTo waitForEvents
	        switchToPlanAsNextState(pr, myselfName, "realsonardetector_"+myselfName, 
	              "waitForEvents",false, false, null); 
	    }catch(Exception e_handleRealSonar){  
	    	 println( getName() + " plan=handleRealSonar WARNING:" + e_handleRealSonar.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleRealSonar
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
