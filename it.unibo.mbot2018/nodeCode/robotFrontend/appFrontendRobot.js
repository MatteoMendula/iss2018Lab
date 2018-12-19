/*
 * -----------------------------------------------
 * it.unibo.mbot2018/nodeCode/robotFrontend/appFrontEndRobot.js
 * -----------------------------------------------
 */
var createError  = require('http-errors');
var express      = require('express');
var path         = require('path');
var cookieParser = require('cookie-parser');
var logger       = require('morgan');
const cors       = require("cors");
const modelutils = require('./appServer/utils/modelUtils');
 
const app        = express();
 
 
//IMPORTANT POINT %%%%%%%%%%%%%%%%%%%%%
const robotModel        = require('./appServer/models/robot');
const robotControl      = require('./appServer/controllers/robotControl');

const routeInfo         = require('./appServer/routes/robotInfoRoute');
const robotApplRoute    = require('./appServer/routes/robotApplControlRoute');
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 

/*
 * Cross-origin resource sharing (CORS) is a mechanism that allows 
 * restricted resources on a web page to be requested from another 
 * domain outside the domain from which the first resource was served
 */
app.use( cors() );

/*
* --------------------------------------------------------------
 * SET UP THE RENDERING ENGINE
* --------------------------------------------------------------
 */
app.set('views', path.join(__dirname, './appServer', 'views'));	 
app.set("view engine", "ejs");	//npm install --save ejs

/*
* --------------------------------------------------------------
* Static files with middleware
* --------------------------------------------------------------
*/
app.use(express.static(path.join(__dirname, './appServer/public')));

/*
* --------------------------------------------------------------
* Routes
* --------------------------------------------------------------
*/
//IMPORTANT POINT %%%%%%%%%%%%%%%%%%%%%
app.use('/info',          routeInfo);
app.use('/applCommand',   robotApplRoute);
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

/*
* --------------------------------------------------------------
* 3a) HANDLE A GET REQUEST
* --------------------------------------------------------------
*/
app.get('/', function(req, res) {
 	var state  = robotModel.robot.properties.resources.state;
  	res.render('access', 
		{'title': 'Robot Control', 'res': "Welcome", 'model': robotModel.robot,
		'robotstate': state, 'refToEnv': req.headers.host+"/robotenv"} 
	); 
});	

app.get('/robotenv', function (req, res) {
	//console.log( req.headers.host ); 
	var state     =  robotModel.robot.properties.resources.state;
	var withValue = false;
	var envToShow = JSON.stringify( 
			modelutils.modelToResources(robotModel.robotenv.devices.resources, withValue)
	);
 	res.render('robotenv', 
 		{'title': 'Robot Environment', 'res': envToShow, 
 		'model': robotModel.robotenv, 'host': req.headers.host, 'refToEnv': req.headers.host+"/robotenv" } 
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
		
/*
 * RENDER A PAGE AS LAST ACTION
 */ 
app.use( function(req,res){ 
    //console.log("last use - req.myresult=" + req.myresult );
	if( req.myresult === 'showInfo' ){
		res.render('robotInfo', 
				{'title': 'Robot Info', 'model': robotModel.robot } 
		); 
		return;
	}
    var state  = robotModel.robot.properties.resources.state;
	res.render('access', 
		{'title': 'Robot Control', 'res': "Welcome", 'model': robotModel.robot,
		'robotstate': state, 'refToEnv': req.headers.host+"/robotenv"} 
	); 
} );

//---------------------------------------------------------------------

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  var state  = robotModel.robot.properties.resources.state;
	res.render('access', 
		{'title': 'Robot Control', 'res': "Welcome", 'model': robotModel.robot,
		'robotstate': state, 'refToEnv': req.headers.host+"/robotenv"} 
	); 
});

//IMPORTANT POINT %%%%%%%%%%%%%%%%%%%%%
module.exports = app;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%