/*
 * it.unibo.frontend/nodeCode/frontend/applCodeRobot.js
 */
var express     	= require('express');
var path         	= require('path');
var favicon      	= require('serve-favicon');
var logger       	= require('morgan');	//see 10.1 of nodeExpressWeb.pdf;
var cookieParser 	= require('cookie-parser');
var bodyParser   	= require('body-parser');
var fs           	= require('fs');
var toRobot         = require("./jsCode/clientRobotVirtual");
var serverWithSocket= require('./socketIofrontendServer');
var cors            = require('cors');

var app              = express();


// view engine setup;
app.set('views', path.join(__dirname, 'appServer', 'viewRobot'));	 
app.set("view engine", "ejs");

//create a write stream (in append mode) ;
var accessLogStream = fs.createWriteStream(path.join(__dirname, 'morganLog.log'), {flags: 'a'})
app.use(logger("short", {stream: accessLogStream}));

app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));				//shows commands, e.g. GET /pi 304 23.123 ms - -;
app.use(bodyParser.json());
app.use( cors() );  //npm install cors --save 
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

 
//app.use(express.static(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'jsCode')))

//DEFINE THE ROUTES ;
app.get('/', function(req, res) {
	  res.render("access");
});	

app.post("/robot/actions/commands/w", function(req, res) {
	  toRobot.send(`{ "type": "moveForward",  "arg": -1 }`);
	  res.render("access");
});	

app.post("/robot/actions/commands/s", function(req, res) {
	  toRobot.send(`{ "type": "moveBackward",  "arg": -1 }`);
	  res.render("access");
});	

app.post("/robot/actions/commands/a", function(req, res) {
	  toRobot.send(`{ "type": "turnLeft",  "arg": 1000 }`);
	  res.render("access");
});	

app.post("/robot/actions/commands/d", function(req, res) {
	  toRobot.send(`{ "type": "turnRight",  "arg": 1000 }`);
	  res.render("access");
});	

app.post("/robot/actions/commands/h", function(req, res) {
	  toRobot.send(`{ "type": "alarm",  "arg": 1000 }`);
	  res.render("access");
});	


//REPRESENTATION;
app.use( function(req,res){
	console.info("SENDING THE ANSWER " + req.result  );
	try{
		if( req.result != undefined)
			serverWithSocket.updateClient( JSON.stringify(req.result ) );
		res.send(req.result);
	}catch(e){console.info("SORRY ...");}
	} 
);

// catch 404 and forward to error handler;
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler;
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page;
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;