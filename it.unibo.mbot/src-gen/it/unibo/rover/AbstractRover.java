/* Generated by AN DISI Unibo */ 
package it.unibo.rover;
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
public abstract class AbstractRover extends QActor { 
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
		public AbstractRover(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/rover/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/rover/plans.txt";
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
	    	stateTab.put("waitForCmd",waitForCmd);
	    	stateTab.put("execMove",execMove);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "rover tout : stops");  
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
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?onRaspberry" )) != null ){
	    	it.unibo.rover.mbotConnArduino.initRasp( myself  );
	    	}
	    	temporaryStr = "\"rover START\"";
	    	println( temporaryStr );  
	    	//switchTo waitForCmd
	        switchToPlanAsNextState(pr, myselfName, "rover_"+myselfName, 
	              "waitForCmd",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForCmd = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("waitForCmd",-1);
	    	String myselfName = "waitForCmd";  
	    	//bbb
	     msgTransition( pr,myselfName,"rover_"+myselfName,false,
	          new StateFun[]{stateTab.get("execMove") }, 
	          new String[]{"true","M","moveRover" },
	          3600000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForCmd){  
	    	 println( getName() + " plan=waitForCmd WARNING:" + e_waitForCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForCmd
	    
	    StateFun execMove = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("execMove",-1);
	    	String myselfName = "execMove";  
	    	printCurrentMessage(false);
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("cmd(moveForward)");
	    	if( currentMessage != null && currentMessage.msgId().equals("moveRover") && 
	    		pengine.unify(curT, Term.createTerm("cmd(CMD)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		{/* JavaLikeMove */ 
	    		String arg1 = "forward" ;
	    		//end arg1
	    		String arg2 = "40" ;
	    		//end arg2
	    		String arg3 = "0" ;
	    		//end arg3
	    		it.unibo.utils.robotMixMoves.moveRobotAndAvatar(this,arg1,arg2,arg3 );
	    		}
	    	}
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("cmd(moveBackward)");
	    	if( currentMessage != null && currentMessage.msgId().equals("moveRover") && 
	    		pengine.unify(curT, Term.createTerm("cmd(CMD)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		{/* JavaLikeMove */ 
	    		String arg1 = "backward" ;
	    		//end arg1
	    		String arg2 = "40" ;
	    		//end arg2
	    		String arg3 = "0" ;
	    		//end arg3
	    		it.unibo.utils.robotMixMoves.moveRobotAndAvatar(this,arg1,arg2,arg3 );
	    		}
	    	}
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("cmd(turnLeft)");
	    	if( currentMessage != null && currentMessage.msgId().equals("moveRover") && 
	    		pengine.unify(curT, Term.createTerm("cmd(CMD)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		//println("WARNING: variable substitution not yet fully implemented " ); 
	    		{//actionseq
	    		it.unibo.rover.mbotConnArduino.mbotLeft( myself  );
	    		if( (guardVars = QActorUtils.evalTheGuard(this, " !?unityOn" )) != null ){
	    		execUnity("rover","left",750, 40,0); //rover: default namefor virtual robot		
	    		}
	    		else{ //delay  ( no more reactive within a plan)
	    		aar = delayReactive(900,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "execMove";
	    		if( ! aar.getGoon() ) return ;
	    		}it.unibo.rover.mbotConnArduino.mbotStop( myself  );
	    		};//actionseq
	    	}
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("cmd(turnRight)");
	    	if( currentMessage != null && currentMessage.msgId().equals("moveRover") && 
	    		pengine.unify(curT, Term.createTerm("cmd(CMD)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		//println("WARNING: variable substitution not yet fully implemented " ); 
	    		{//actionseq
	    		it.unibo.rover.mbotConnArduino.mbotRight( myself  );
	    		if( (guardVars = QActorUtils.evalTheGuard(this, " !?unityOn" )) != null ){
	    		execUnity("rover","right",750, 40,0); //rover: default namefor virtual robot		
	    		}
	    		else{ //delay  ( no more reactive within a plan)
	    		aar = delayReactive(900,"" , "");
	    		if( aar.getInterrupted() ) curPlanInExec   = "execMove";
	    		if( ! aar.getGoon() ) return ;
	    		}it.unibo.rover.mbotConnArduino.mbotStop( myself  );
	    		};//actionseq
	    	}
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("cmd(moveStop)");
	    	if( currentMessage != null && currentMessage.msgId().equals("moveRover") && 
	    		pengine.unify(curT, Term.createTerm("cmd(CMD)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		{/* JavaLikeMove */ 
	    		String arg1 = "stop" ;
	    		//end arg1
	    		String arg2 = "40" ;
	    		//end arg2
	    		String arg3 = "0" ;
	    		//end arg3
	    		it.unibo.utils.robotMixMoves.moveRobotAndAvatar(this,arg1,arg2,arg3 );
	    		}
	    	}
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("cmd(connectToUnity)");
	    	if( currentMessage != null && currentMessage.msgId().equals("moveRover") && 
	    		pengine.unify(curT, Term.createTerm("cmd(CMD)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		//println("WARNING: variable substitution not yet fully implemented " ); 
	    		{//actionseq
	    		if( (guardVars = QActorUtils.evalTheGuard(this, " not !?unityOn" )) != null )
	    		{
	    		{//actionseq
	    		if( (guardVars = QActorUtils.evalTheGuard(this, " !?unityConfig(UNITYADDR,BATCH)" )) != null ){
	    		it.unibo.utils.external.connectRoverToUnity( myself ,guardVars.get("UNITYADDR"), guardVars.get("BATCH")  );
	    		}
	    		temporaryStr = "unityOn";
	    		addRule( temporaryStr );  
	    		execUnity("rover","backward",1000, 70,0); //rover: default namefor virtual robot		
	    		execUnity("rover","right",1000, 70,0); //rover: default namefor virtual robot		
	    		};//actionseq
	    		}
	    		else{ temporaryStr = "\"UNITY ALREADY ACTIVE\"";
	    		println( temporaryStr );  
	    		}};//actionseq
	    	}
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("cmd(followLine)");
	    	if( currentMessage != null && currentMessage.msgId().equals("moveRover") && 
	    		pengine.unify(curT, Term.createTerm("cmd(CMD)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		{/* JavaLikeMove */ 
	    		it.unibo.rover.mbotConnArduino.mbotLinefollow(this );
	    		}
	    	}
	    	//switchTo waitForCmd
	        switchToPlanAsNextState(pr, myselfName, "rover_"+myselfName, 
	              "waitForCmd",false, true, null); 
	    }catch(Exception e_execMove){  
	    	 println( getName() + " plan=execMove WARNING:" + e_execMove.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//execMove
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}
