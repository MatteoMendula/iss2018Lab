/* Generated by AN DISI Unibo */ 
package it.unibo.mvccontroller;
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
public abstract class AbstractMvccontroller extends QActor { 
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
		public AbstractMvccontroller(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/mvccontroller/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/mvccontroller/plans.txt";
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
	    	stateTab.put("waitForInputEvent",waitForInputEvent);
	    	stateTab.put("handleInputEvent",handleInputEvent);
	    	stateTab.put("handleClickEvent",handleClickEvent);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "mvccontroller tout : stops");  
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
	    	parg = "consult(\"./resourceModel.pl\")";
	    	//QActorUtils.solveGoal(myself,parg,pengine );  //sets currentActionResult		
	    	solveGoal( parg ); //sept2017
	    	temporaryStr = "qacontrol(starts)";
	    	println( temporaryStr );  
	     connectToMqttServer("tcp://localhost:1883");
	    	//switchTo waitForInputEvent
	        switchToPlanAsNextState(pr, myselfName, "mvccontroller_"+myselfName, 
	              "waitForInputEvent",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForInputEvent = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForInputEvent",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForInputEvent";  
	    	//bbb
	     msgTransition( pr,myselfName,"mvccontroller_"+myselfName,false,
	          new StateFun[]{stateTab.get("handleInputEvent"), stateTab.get("handleClickEvent") }, 
	          new String[]{"true","E","inputCtrlEvent", "true","E","local_click" },
	          6000000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForInputEvent){  
	    	 println( getName() + " plan=waitForInputEvent WARNING:" + e_waitForInputEvent.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForInputEvent
	    
	    StateFun handleInputEvent = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleInputEvent",-1);
	    	String myselfName = "handleInputEvent";  
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("inputEvent(CATEG,NAME,VALUE)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("inputCtrlEvent") && 
	    		pengine.unify(curT, Term.createTerm("inputEvent(CATEG,NAME,VALUE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			String parg="changeModelItem(CATEG,NAME,VALUE)";
	    			/* PHead */
	    			parg =  updateVars( Term.createTerm("inputEvent(CATEG,NAME,VALUE)"), 
	    			                    Term.createTerm("inputEvent(CATEG,NAME,VALUE)"), 
	    				    		  	Term.createTerm(currentEvent.getMsg()), parg);
	    				if( parg != null ) {
	    				    aar = QActorUtils.solveGoal(this,myCtx,pengine,parg,"",outEnvView,86400000);
	    					//println(getName() + " plan " + curPlanInExec  +  " interrupted=" + aar.getInterrupted() + " action goon="+aar.getGoon());
	    					if( aar.getInterrupted() ){
	    						curPlanInExec   = "handleInputEvent";
	    						if( aar.getTimeRemained() <= 0 ) addRule("tout(demo,"+getName()+")");
	    						if( ! aar.getGoon() ) return ;
	    					} 			
	    					if( aar.getResult().equals("failure")){
	    						if( ! aar.getGoon() ) return ;
	    					}else if( ! aar.getGoon() ) return ;
	    				}
	    	}
	    	repeatPlanNoTransition(pr,myselfName,"mvccontroller_"+myselfName,false,true);
	    }catch(Exception e_handleInputEvent){  
	    	 println( getName() + " plan=handleInputEvent WARNING:" + e_handleInputEvent.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleInputEvent
	    
	    StateFun handleClickEvent = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleClickEvent",-1);
	    	String myselfName = "handleClickEvent";  
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "ctrlEvent(CATEG,NAME,CMD)","ctrlEvent(leds,led1,switch)", guardVars ).toString();
	    	emit( "ctrlEvent", temporaryStr );
	    	repeatPlanNoTransition(pr,myselfName,"mvccontroller_"+myselfName,false,true);
	    }catch(Exception e_handleClickEvent){  
	    	 println( getName() + " plan=handleClickEvent WARNING:" + e_handleClickEvent.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleClickEvent
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
