/*
* =====================================
* serverRobotCmd.js
* =====================================
*/
const http    = require("http"),
      join    = require('path').join ;

const robotModel     = require('./models/robot');
const robotControl   = require('./controllers/robotControl');
const modelutils     = require('./utils/modelUtils');

var serialPort ;
var toVirtualRobot;
var myPort;

const realRobot = false;

if( realRobot ){    
	serialPort = require('./utils/serial');
	console.log("serialPort= " + serialPort.path  );
}
else toVirtualRobot = require("./utils/clientRobotVirtual");

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
	
	robotControl.setRealRobot( false );
 
	app.use(express.static(__dirname + '/public'));
/*
* --------------------------------------------------------------
* 3a) HANDLE A GET REQUEST
* --------------------------------------------------------------
*/
app.get('/', function(req, res) {
 	//console.log( req.headers.host ); 
	var state  = robotModel.robot.state;
	res.render('access', 
		{'title': 'Robot Control', 'res': "Welcome", 
		'robotstate': state, 'refToEnv': req.headers.host+"/robotenv"} 
	); 
});	

app.get('/robotenv', function (req, res) {
	console.log( req.headers.host ); 
	var state     = robotModel.robot.state;
 	var envToShow = JSON.stringify( 
			modelutils.modelToResources(robotModel.robotenv, false)
			);
 	res.render('robotenv', 
 		{'title': 'Robot Environment', 'res': envToShow , 
 		'model': robotModel.robotenv, 'host': req.headers.host } 
 	); 
});
app.get('/robotstate', function (req, res) {
	var state  = robotModel.robot.state;
	res.render('access', 
			{'title': 'Robot Control', 'res': robotModel.robotenv , 'robotstate': state} 
		); 
});

/*
* --------------------------------------------------------------
* 3b) HANDLE A POST REQUEST
* --------------------------------------------------------------
*/

 
app.post("/robot/actions/commands/w", function(req, res, next) { 
	robotControl.actuate("w", req, res ); next();});
app.post("/robot/actions/commands/s", function(req, res, next) { 
	robotControl.actuate("s", req, res ); next();});
app.post("/robot/actions/commands/h", function(req, res, next) { 
	robotControl.actuate("h", req, res ); next(); });
app.post("/robot/actions/commands/d", function(req, res, next) { 
	robotControl.actuate("d", req, res ); next(); });
app.post("/robot/actions/commands/a", function(req, res, next) { 
	robotControl.actuate("a", req, res ); next(); });
		
 


app.use( function(req,res){ 
	console.log("last " + req.myresult );
	//console.log(  req  );
	res.render('access', 
		{'title': 'Robot Control', 'res': req.myresult, 'robotstate':robotModel.robot.state} 
	); 
} );
 
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