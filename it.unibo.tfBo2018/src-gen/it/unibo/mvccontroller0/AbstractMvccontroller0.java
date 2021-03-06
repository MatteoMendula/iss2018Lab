/* Generated by AN DISI Unibo */ 
package it.unibo.mvccontroller0;
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
public abstract class AbstractMvccontroller0 extends QActor { 
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
		public AbstractMvccontroller0(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/mvccontroller0/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/mvccontroller0/plans.txt";
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
	    	stateTab.put("sendStart",sendStart);
	    	stateTab.put("sendStop",sendStop);
	    	stateTab.put("end",end);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "mvccontroller0 tout : stops");  
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
	    	temporaryStr = "\"MVCCONTROLLER starts...\"";
	    	println( temporaryStr );  
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(1000,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "init";
	    	if( ! aar.getGoon() ) return ;
	    	//switchTo sendStart
	        switchToPlanAsNextState(pr, myselfName, "mvccontroller0_"+myselfName, 
	              "sendStart",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun sendStart = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("sendStart",-1);
	    	String myselfName = "sendStart";  
	    	temporaryStr = "\"MVCCONTROLLER send start to robot...\"";
	    	println( temporaryStr );  
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine,"consoleCmd(X)","consoleCmd(start)", guardVars ).toString();
	    	sendExtMsg("consoleCmdMsg","player", "ctxRobot", QActorContext.dispatch, temporaryStr ); 
	    	//delay  ( no more reactive within a plan)
	    	aar = delayReactive(3000,"" , "");
	    	if( aar.getInterrupted() ) curPlanInExec   = "sendStart";
	    	if( ! aar.getGoon() ) return ;
	    	//switchTo sendStop
	        switchToPlanAsNextState(pr, myselfName, "mvccontroller0_"+myselfName, 
	              "sendStop",false, false, null); 
	    }catch(Exception e_sendStart){  
	    	 println( getName() + " plan=sendStart WARNING:" + e_sendStart.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//sendStart
	    
	    StateFun sendStop = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("sendStop",-1);
	    	String myselfName = "sendStop";  
	    	temporaryStr = "\"MVCCONTROLLER send stop to robot...\"";
	    	println( temporaryStr );  
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine,"consoleCmd(X)","consoleCmd(halt)", guardVars ).toString();
	    	sendExtMsg("consoleCmdMsg","player", "ctxRobot", QActorContext.dispatch, temporaryStr ); 
	    	//switchTo end
	        switchToPlanAsNextState(pr, myselfName, "mvccontroller0_"+myselfName, 
	              "end",false, false, null); 
	    }catch(Exception e_sendStop){  
	    	 println( getName() + " plan=sendStop WARNING:" + e_sendStop.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//sendStop
	    
	    StateFun end = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("end",-1);
	    	String myselfName = "end";  
	    	temporaryStr = "\"MVCCONTROLLER ends...\"";
	    	println( temporaryStr );  
	    	repeatPlanNoTransition(pr,myselfName,"mvccontroller0_"+myselfName,false,false);
	    }catch(Exception e_end){  
	    	 println( getName() + " plan=end WARNING:" + e_end.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//end
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
