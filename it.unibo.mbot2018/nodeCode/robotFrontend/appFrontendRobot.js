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
const routesGen  = require('./appServer/routes/routesGenerator');
const app        = express();
 
 
//IMPORTANT POINT %%%%%%%%%%%%%%%%%%%%%
const modelInterface    = require('./appServer/models/robot');
const robotModel        = modelInterface.getResourceModel();
const robotControl      = require('./appServer/controllers/robotControl');

const routeInfo         = require('./appServer/routes/robotInfoRoute');
const robotApplRoute    = require('./appServer/routes/robotApplControlRoute');
//Other routes are generated by the routesGen.create (see later)
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
//Application command route
app.use('/applCommand', robotApplRoute);

//Create Other Routes
app.use('/', routesGen.create(robotModel) );

/*
* --------------------------------------------------------------
* 3a) HANDLE GET REQUESTS
* --------------------------------------------------------------
*/
app.get('/robotenv', function (req, res) {
	//console.log( req.headers.host ); 
	var state     =  robotModel.links.robot.resources.robotstate.state;
	var withValue = false;
	var envToShow = JSON.stringify( 
			modelutils.modelToResources(robotModel.links.robotenv.devices.resources, withValue)
	);
 	res.render('robotenv', 
 		{'title': 'Robot Environment', 'res': envToShow, 
 		'model': robotModel.robotenv, 'host': req.headers.host, 'refToEnv': req.headers.host+"/robotenv" } 
 	); 
});

//to promote M2M interaction
app.get( modelInterface.actionsLinkUrlStr, function( req, res ) { 	   
  	res.send( modelInterface.actionsLinkUrlStr );
});
 
/*
* --------------------------------------------------------------
* 3b) HANDLE A POST REQUEST
* --------------------------------------------------------------
*/
app.post("/commands/w", function(req, res, next) { 
	robotControl.actuate("w", req, res ); next();});
app.post("/commands/s", function(req, res, next) { 
	robotControl.actuate("s", req, res ); next();});
app.post("/commands/h", function(req, res, next) { 
	robotControl.actuate("h", req, res ); next(); });
app.post("/commands/d", function(req, res, next) { 
	robotControl.actuate("d", req, res ); next(); });
app.post("/commands/a", function(req, res, next) { 
	robotControl.actuate("a", req, res ); next(); });
		
/*
 * LAST ACTION
 */ 
app.use( function(req,res){ 
    //console.log("last use - req.myresult=" + req.myresult );
    renderResponse( req, robotModel.links.robot.resources.robotstate.state, res );
} );

var renderResponse = function(req, state, res){
	if (req.accepts('html')) {
		console.info('\t appFrontEndRobot renderResponse req.type=' + req.type );
		 // Check if there's a custom renderer for this media type and resource;
        if (req.type) res.render(req.type, {'title': 'Resource Model', 'req': req});
        else //res.render('default', {req: req, helpers: helpers});
		res.render('access', 
				{'title': 'Robot Control Page', 'res': "Welcome", 'model': robotModel.links.robot,
				'robotstate': state, 'refToEnv': req.headers.host+"/robotenv"}); 	
	}else if (req.accepts('application/json')) {
		//CREATE the Link header containing links to the other resources (HATEOAS);
	    type = 'http://model.webofthings.io/';
	    res.links({
	        model: '/model/',
	        properties: '/properties/',
	        actions: '/actions/',
	        help: '/help/',
	        type: type
	      });
		var fields = ['id', 'name', 'description', 'customFields', 'help'];
		req.myresult = modelInterface.extractFields(fields, robotModel);
		console.info('\t appFrontEndRobot: Defaulting to JSON representation!  '  );
		//console.info( req.myresult );
	    res.send(req.myresult);		
	}else if (req.accepts('application/x-msgpack')) {
		 res.send("Sorry, application/x-msgpack todo ... ");	
	}else{
		 console.info('Defaulting to JSON representation! req.result='  );
		 console.info( req.result );		
	}
}

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
  var state  = robotModel.links.robot.resources.robotstate.state;
	res.render('access', 
		{'title': 'Robot Control Page', 'res': "Welcome", 'model': robotModel.links.robot,
		'robotstate': state, 'refToEnv': req.headers.host+"/robotenv"} 
	); 
});

//IMPORTANT POINT %%%%%%%%%%%%%%%%%%%%%
module.exports = app;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%