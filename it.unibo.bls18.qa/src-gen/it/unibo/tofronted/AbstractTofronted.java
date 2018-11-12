/* Generated by AN DISI Unibo */ 
package it.unibo.tofronted;
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
public abstract class AbstractTofronted extends QActor { 
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
		public AbstractTofronted(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/tofronted/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/tofronted/plans.txt";
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
	    	stateTab.put("waitForCommand",waitForCommand);
	    	stateTab.put("handleCtrlEvent",handleCtrlEvent);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "tofronted tout : stops");  
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
	    	temporaryStr = "sendertofronted(starts)";
	    	println( temporaryStr );  
	    	//switchTo waitForCommand
	        switchToPlanAsNextState(pr, myselfName, "tofronted_"+myselfName, 
	              "waitForCommand",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForCommand = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForCommand",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForCommand";  
	    	//bbb
	     msgTransition( pr,myselfName,"tofronted_"+myselfName,false,
	          new StateFun[]{stateTab.get("handleCtrlEvent") }, 
	          new String[]{"true","E","ctrlEvent" },
	          6000000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForCommand){  
	    	 println( getName() + " plan=waitForCommand WARNING:" + e_waitForCommand.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForCommand
	    
	    StateFun handleCtrlEvent = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleCtrlEvent",-1);
	    	String myselfName = "handleCtrlEvent";  
	    	printCurrentEvent(false);
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("ctrlEvent(leds,led1,switch)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("ctrlEvent") && 
	    		pengine.unify(curT, Term.createTerm("ctrlEvent(CATEG,NAME,CMD)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			{/* JavaLikeMove */ 
	    			String arg1 = "true" ;
	    			//end arg1
	    			String arg2 = "http://localhost:8080/ledSwitch" ;
	    			//end arg2
	    			it.unibo.frontend.restClientHttp.sendPutLed(this,arg1,arg2 );
	    			}
	    	}
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("ctrlEvent(leds,led1,on)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("ctrlEvent") && 
	    		pengine.unify(curT, Term.createTerm("ctrlEvent(CATEG,NAME,CMD)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			{/* JavaLikeMove */ 
	    			String arg1 = "true" ;
	    			//end arg1
	    			String arg2 = "http://localhost:8080/ledSwitch" ;
	    			//end arg2
	    			it.unibo.frontend.restClientHttp.sendPutLed(this,arg1,arg2 );
	    			}
	    	}
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("ctrlEvent(leds,led1,off)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("ctrlEvent") && 
	    		pengine.unify(curT, Term.createTerm("ctrlEvent(CATEG,NAME,CMD)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			{/* JavaLikeMove */ 
	    			String arg1 = "false" ;
	    			//end arg1
	    			String arg2 = "http://localhost:8080/ledSwitch" ;
	    			//end arg2
	    			it.unibo.frontend.restClientHttp.sendPutLed(this,arg1,arg2 );
	    			}
	    	}
	    	repeatPlanNoTransition(pr,myselfName,"tofronted_"+myselfName,false,true);
	    }catch(Exception e_handleCtrlEvent){  
	    	 println( getName() + " plan=handleCtrlEvent WARNING:" + e_handleCtrlEvent.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleCtrlEvent
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
