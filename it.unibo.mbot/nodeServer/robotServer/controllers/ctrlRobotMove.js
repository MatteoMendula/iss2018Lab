/*
 * ----------------------------------------------------
 * nodeServer/robotServer/controllers/ctrlRobotMove.js
 * ----------------------------------------------------
 */
var net       = require('net');
var http      = require("http");

var host          = "localhost"; //192.168.43.67
var qaport        = 8029; 
var socketToQaCtx = null; 

var connectedWithQa = false;

//Connect to the QActor application
function connectToQaNode(){
 	try{
		socketToQaCtx = net.connect({ port: qaport, host: host });
		socketToQaCtx.setEncoding('utf8');	
		console.log('connectedWithQa to qa node:' + host + ":" + qaport);
		connectedWithQa = true;
	}catch(e){ 
		console.log(" connectToQaNode ERROR "  + e ); 
 	}
}

module.exports.moveRobotForward = function(request, response){
	if( ! connectedWithQa ) connectToQaNode();
	sendCmdToRover( "moveForward");
	response.end("done moveForward");
}

module.exports.moveRobotBackward= function(request, response){
	if( ! connectedWithQa ) connectToQaNode();
	sendCmdToRover( "moveBackward");
	response.end("done moveBackward");
}

module.exports.moveRobotLeft= function(request, response){
	if( ! connectedWithQa ) connectToQaNode();
	sendCmdToRover( "turnLeft");
	response.end("done turnLeft");
}

module.exports.moveRobotRight= function(request, response){
	if( ! connectedWithQa ) connectToQaNode();
	sendCmdToRover( "turnRight");
	response.end("done turnRight");
}

module.exports.moveRobotStop= function(request, response){
	if( ! connectedWithQa ) connectToQaNode();
	sendCmdToRover( "moveStop");
	response.end("done moveStop");
}


/*
 * sendCmdToRover
 */
var msgNum=1;
function sendCmdToRover( move ) {
 	try{
 		var msg = "msg(moveRover,dispatch,frontend,rover,cmd("+move+")," + msgNum++ +")";
	  	console.log('sendCmdToRover mmsg==' + msg    ); 
 		if(socketToQaCtx !== null ){
  	  		console.log('sendCmdToRover mmsg=' + msg    ); 
  			socketToQaCtx.write(msg+"\n");
  		}
	}catch(e){ 
  		console.log("sendCmdToRover WARNING: "  + e ); 
 	}
}
