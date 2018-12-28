/*
 * it.unibo.mbot2018/nodeCode/robotFrontend/appServer/controllers/robotControl
 */
const robotConfig = require("./../../robotConfig");
const echannel    = require("./../utils/channel");

exports.actuate = function( cmd, req, res ){
	//console.log("\t robotControl actuate " + cmd  );
	var newState     = "";
	if(      cmd === "w" ){ newState="robot moving forward"; }
	else if( cmd === "s" ){ newState="robot moving backward"; }
	else if( cmd === "h" ){ newState="robot stopped"; }
	else if( cmd === "a" ){ newState="robot  moving left"; }
	else if( cmd === "d" ){ newState="robot  moving right"; }
	else { console.log("\t robotControl actuate cmd unknown "  ); return; }  //cmd unknown
	var robotPlugin = robotConfig.getRobotPlugin();
 	robotPlugin.actuate( cmd  );
  	echannel.emit("robotState", newState);  //used to update on the Web page
  	req.myresult = "move  "+cmd+ " done";	//used by LAST ACTION in appFrontEndRobot
}
