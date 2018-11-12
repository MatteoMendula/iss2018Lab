function a0(){ console.log("a0");  }
function a1(){ console.log("a1");  }
function a2(){ console.log("a2");  }

function t0(message){ 
	console.log("t0");   
	if( message === 'm1' ){
		plan( 'p1', a1, t1 );
		return;
	}
}
function t1(message){ 
	console.log("t1");  
	if( message === 'm3' ){
		plan( 'p3', a3, null );
		return;
	}
}
function t2(){ console.log("t2");  }

function plan( pname, action, transition ){
	curPlan = pname;
	console.log( "curPlan= " + curPlan );
	action();
	if( transition != null ){
		msg = getMessage();
		transition( msg );
	}else console.log( "end in plan= " + curPlan );
}

var count = 1;
function getMessage(){
	return 'm'+count++;
}

var curPlan = "p0";

plan( curPlan, a0, t0 );