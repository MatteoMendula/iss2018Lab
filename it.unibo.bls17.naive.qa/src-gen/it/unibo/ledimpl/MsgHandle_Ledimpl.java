/* Generated by AN DISI Unibo */ 
package it.unibo.ledimpl;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.akka.QActorMsgQueue;

public class MsgHandle_Ledimpl extends QActorMsgQueue{
	public MsgHandle_Ledimpl(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  {
		super(actorId, myCtx, outEnvView);
	}
}