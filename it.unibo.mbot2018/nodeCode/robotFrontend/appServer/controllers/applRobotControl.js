/*
 * it.unibo.mbot2018/nodeCode/robot/controllers/applRobotControl.js
 */
//const robotModel  = require('./../models/robot');
const echannel    = require("./../utils/channel");
var mqttUtils     = require('./../utils/mqttUtils'); 

exports.actuate = function( cmd, req, res ){
	console.log("\t applRobotControl actuate " + cmd  );
	if( cmd === "w" ){ delegate("w(low)", "moving forward", req,res); }
	else if( cmd === "s" ){  delegate("s(low)", "moving backward", req,res); }
	else if( cmd === "h" ){  delegate("h(low)", "stopped", req,res); }
	else if( cmd === "a" ){  delegate("w(low)", "moving left", req,res); }
	else if( cmd === "d" ){  delegate("w(low)", "moving right", req,res); }

	//Application
	else if( cmd === "start" ){   delegate("start", "robot working at application level", req,res);  }
	else if( cmd === "halt" ){    delegate("halt", "robot halting the application level", req,res);  }

}

/*
 * 
 */
var delegate = function ( hlcmd, newState, req, res ){
 	echannel.emit("robotState", newState);
	emitRobotCmd(hlcmd);
}
var emitRobotCmd = function( cmd ){ //no more gui coomands
 	var eventstr = "msg(usercmd,event,js,none,usercmd( " +cmd + "),1)"
  		console.log("\t robotControl emits: "+ eventstr);
  		mqttUtils.publish( eventstr );	//topic  = "unibo/qasys";
}


