package it.unibo.actionAsynch;
import alice.tuprolog.Struct;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.action.ActionObservableGenericActor;
import it.unibo.qactors.akka.QActor;
 
public class ActionActorFibonacci extends ActionObservableGenericActor<String> {
private long myresult = 0;
private int n         = 0;
 	public ActionActorFibonacci(String name,  QActor actor, IOutputEnvView outEnvView) {
		super(name, actor, outEnvView);
   	}
 	@Override
	public void execTheAction(Struct actionInput) throws Exception {
  	  //actionInput : data( n ) 
 	  n = Integer.parseInt( actionInput.getArg(0).toString() );
 	  myresult = fibonacci( n );
//    throw new Exception("simulateFault");		//(1)
    } 	
 	protected long fibonacci( int n ){ 
		if(   n == 1 || n == 2 ) return 1;
		else return fibonacci(n-1) + fibonacci(n-2);
	}
	@Override
	protected String endOfAction() throws Exception {
 		return "fibo("+n+","+this.myresult+",timemsec("+durationMillis+")"+")";
	}
}