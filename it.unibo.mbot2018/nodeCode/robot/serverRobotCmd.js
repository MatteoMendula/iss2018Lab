/*
* =====================================
* serverRobotCmd.js
* =====================================
*/
const
 	http    = require("http"),
	parse   = require('url').parse,
    join    = require('path').join,
    srvUtil = require("./ServerUtils"),
    fs      = require('fs'),
    url     = require('url'),    
    Readable = require('stream').Readable;


const robotModel     = require('./models/robot');

var serialPort ;
var toVirtualRobot;
var myPort;

const realRobot = false;

if( realRobot ){    
	serialPort = require('./serial');
	console.log("serialPort= " + serialPort.path  );
}
else   toVirtualRobot = require("./clientRobotVirtual");

/*
* --------------------------------------------------------------
* 0) SET UP THE VIEW ENGINE BY USING EXPRESS
* --------------------------------------------------------------
*/
const express  = require('express'); 	//npm install --save express
const app      = express();
const path     = require('path');
app.set('views', path.join(__dirname, '.', 'viewRobot'));	 
app.set("view engine", "ejs");			//npm install --save ejs

/*
* --------------------------------------------------------------
* 1) DEFINE THE SERVER (based on express) that enables socket.io
* --------------------------------------------------------------
*/
    const server  = http.createServer( app );   
	const io      = require('socket.io').listen(server); 	//npm install --save socket.io
 
/*
* --------------------------------------------------------------
* 3a) HANDLE A GET REQUEST
* --------------------------------------------------------------
*/
app.get('/', function(req, res) {
	res.render("access");
});	

app.get('/pi', function (req, res) {
 	  res.end('This is the frontend-Pi!')
});
app.get('/robot/state', function (req, res) {
	var state  = robotModel.robot.state;
	res.end('ROBOT STATE=' + state)
});

/*
* --------------------------------------------------------------
* 3b) HANDLE A POST REQUEST
* --------------------------------------------------------------
*/

app.post("/robot/actions/commands/w", function(req, res) { actuate("w", req, res); });	
app.post("/robot/actions/commands/s", function(req, res) { actuate("s", req, res); });	
app.post("/robot/actions/commands/h", function(req, res) { actuate("h", req, res); });	
app.post("/robot/actions/commands/d", function(req, res) { actuate("d", req, res); });	
app.post("/robot/actions/commands/a", function(req, res) { actuate("a", req, res); });		

/*
* --------------------------------------------------------------
* ACTIONS
* --------------------------------------------------------------
*/

function actuate( cmd, req, res ){
	var newState     = "";
	var cmdToVirtual = "";
	console.log("actuate " + cmd  );
	if( cmd === "w" ){ cmdToVirtual=`{ "type": "moveForward",  "arg": -1 }` ; 
		newState="server moving forward"; }
	else if( cmd === "s" ){ cmdToVirtual=`{ "type": "moveBackward",  "arg": -1 }` ; 
		newState="server moving backward"; }
	else if( cmd === "h" ){ cmdToVirtual=`{ "type": "alarm",  "arg": 1000 }` ; 
			newState="server stopped"; }
	else if( cmd === "a" ){ cmdToVirtual=`{ "type": "turnLeft",  "arg": 1000 }` ; 
			newState="server  moving left"; }
	else if( cmd === "d" ){ cmdToVirtual=`{ "type": "turnRight",  "arg": 1000 }` ; 
		newState="server  moving right"; }
	
	if( realRobot){
		actuateOnArduino( cmd, newState, req,res ) 
	}else{
		actuateOnVirtual( cmdToVirtual, newState, req,res )		
	}
	
}
function actuateOnVirtual(cmd, newState, req, res ){

	console.log("actuateOnVirtual:" + cmd );
  	toVirtualRobot.send( cmd );  	
 	robotModel.robot.state = newState;
 	
		setTimeout( function(){
			//var now = new Date().toUTCString();
			io.sockets.send( newState ); //+ " time=" + now
		}, 200 ) ;

  	res.render("access");
}

function actuateOnArduino(cmd, newState, req, res ){
	console.log("actuateOnArduino: " + cmd + " " + serialPort.path);
	serialPort.write(cmd);	
}

function delegate( hlcmd, newState, req, res ){
 	robotModel.robot.state = newState;
	emitRobotCmd(hlcmd);
    res.render("access");	
}
var emitRobotCmd = function( cmd ){ //called by delegate;
 	var eventstr = "msg(usercmd,event,js,none,usercmd(robotgui( " +cmd + ")),1)"
  		console.log("emits> "+ eventstr);
// 		mqttUtils.publish( eventstr );	//topic  = "unibo/qasys";
}

/*
* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
* UTILITIES
* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
*/
var requestUtil = function(req,res,cb){
	var urlParts    = url.parse(req.url, true),
		urlParams   = urlParts.query, 
		urlPathname = urlParts.pathname,
		body = '',
		reqInfo = {};

	req.on('data', function (data) {
	 body += data; 
	});
	req.on('end', function () {
		reqInfo.method      = req.method;		//'GET'
		reqInfo.urlPathname = urlPathname;  	//'/api/user'
		reqInfo.urlParams   = urlParams;  		//{}
		reqInfo.body        = body;  
		reqInfo.query       = urlParts.query;	//{}
		reqInfo.urlParts    = urlParts;
		//console.log( reqInfo    );
		cb(reqInfo,res);
	 });	
	}

/*
* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
* EXCEPTIONS
* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
*/

//Handle CRTL-C;
process.on('SIGINT', function () {
  console.log('serverRobotCmd Bye, bye!');
  process.exit();
});
process.on('exit', function(code){
	console.log("serverRobotCmd Exiting code= " + code );
});
process.on('uncaughtException', function (err) {
 	console.error('serverRobotCmd uncaught exception:', err.message);
  	process.exit(1);		//MANDATORY!!!;
});
 

/*
* --------------------------------------------------------------
* 2) START THE SERVER
* --------------------------------------------------------------
*/
const initMsg=
	"\n"+
	"------------------------------------------------------\n"+
	"serverRobotCmd bound to port 8080\n"+
	"uses socket.io\n"+
	"------------------------------------------------------\n";

server.listen(8080, function(){console.log(initMsg)}); 



/*
curl -X POST -d "true" http://localhost:8080/robot/actions/commands/w
curl -X GET  http://localhost:8080/robot/state

*/