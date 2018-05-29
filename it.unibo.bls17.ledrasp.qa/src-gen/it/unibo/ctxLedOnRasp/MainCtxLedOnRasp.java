/* Generated by AN DISI Unibo */ 
package it.unibo.ctxLedOnRasp;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;
public class MainCtxLedOnRasp  {
  
//MAIN
public static QActorContext initTheContext() throws Exception{
	IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
	String webDir = null;
	return QActorContext.initQActorSystem(
		"ctxledonrasp", "./srcMore/it/unibo/ctxLedOnRasp/ledonrasp.pl", 
		"./srcMore/it/unibo/ctxLedOnRasp/sysRules.pl", outEnvView,webDir,false);
}
public static void main(String[] args) throws Exception{
	QActorContext ctx = initTheContext();
} 	
}