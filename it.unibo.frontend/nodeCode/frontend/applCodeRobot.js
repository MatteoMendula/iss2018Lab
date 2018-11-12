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
var serverWithSocket= require('./robotFrontendServer');
var cors            = require('cors');
var robotModel      = require('./appServer/models/robot');
var User            = require("./appServer/models/user");
var mqttUtils       ; 	//to be set later;
var session         ; 	//to be set later for AUTH;
var passport        ; 	//to be set later for AUTH;
var setUpPassport   ; 	//to be set later for AUTH;
var mongoose        ; 	//to be set later for AUTH;
var flash           ; 	//to be set later for AUTH; 

var app              = express();



var index           = require('./appServer/routes/index');				 
var actuatorsRoutes = require('./appServer/routes/actuators');
var sensorsRoutes   = require('./appServer/routes/sensors');
var robotRoutes   = require('./appServer/routes/robot');


 

// view engine setup;
app.set('views', path.join(__dirname, 'appServer', 'viewRobot'));	 
app.set("view engine", "ejs");

//create a write stream (in append mode) ;
var accessLogStream = fs.createWriteStream(path.join(__dirname, 'morganLog.log'), {flags: 'a'})
app.use(logger("short", {stream: accessLogStream}));

app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));				//shows commands, e.g. GET /pi 304 23.123 ms - -;
app.use(bodyParser.json());
app.use( cors() );  //npm install cors --save ;
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

app.use(express.static(path.join(__dirname, 'jsCode')))

var externalActuator = false;	//when true, the application logic is external to the server;
var withAuth         = false;


//DEFINE THE ROUTES ;
//app.use('/', index);		 
app.use('/pi/actuators', actuatorsRoutes);
app.use('/pi/sensors',   sensorsRoutes);
app.use('/robot',   robotRoutes);




if( externalActuator ) mqttUtils  = require('./uniboSupports/mqttUtils');
if( withAuth ){
	 session          = require("express-session");	 
	 passport         = require("passport");			 
	 setUpPassport    = require("./setuppassport");   
	 mongoose         = require("mongoose");			 
	 flash            = require("connect-flash");     	
	
	 setUpAuth();
}

/*
 * ====================== AUTH ================
 */	
	app.get('/', function(req, res) {
 		if( withAuth ) res.render("login");
 		else 
 			res.render("access");
  	});	
if (passport)
	app.get("/login", function(req, res) {
		 res.render("login");
	});

if (passport)
	app.post("/login", passport.authenticate("login", {
		  successRedirect: "/access",			 
		  failureRedirect: "/login",
		  failureFlash: true
	}));

if (passport)
	app.get("/access", ensureAuthenticated, function(req, res, next) {	 
		res.render("access");		 
	});

if (passport)
//Creates a default route for /pi;
app.get('/pi', function (req, res) {
  //for( i in req.body ){ console.info('req body field %s   ',   i ); };
  //console.info('	get /pi req URL = %s  ',   req.url  );
  res.send('This is the frontend-Pi!')
});


if (passport)
	app.get("/logout", function(req, res) {
	  req.logout();	//a new function added by Passport;
	  res.redirect("/");
	});
if (passport)
	app.get("/signup", function(req, res) {
	  res.render("signup");
	});

if (passport)	
	app.post("/signup", function(req, res, next) {  
	  var username = req.body.username;
	  var password = req.body.password;
	  User.findOne({ username: username }, function(err, user) {
	    if (err) { return next(err); }
	    if (user) {
	      req.flash("error", "User already exists");
	      return res.redirect("/signup");
	    }
	    var newUser = new User({
	      username: username,
	      password: password
	    });
	    newUser.save(next);
	  });
	}, passport.authenticate("login", {
	  successRedirect: "/",
	  failureRedirect: "/signup",
	  failureFlash: true
	}));

if (passport)
	app.get("/users/:username", function(req, res, next) {
	  User.findOne({ username: req.params.username }, function(err, user) {
	    if (err) { return next(err); }
	    if (!user) { return next(404); }
	    res.render("profile", { user: user });
	  });
	});
if (passport)
	app.get("/edit", ensureAuthenticated, function(req, res) {
	  res.render("edit");
	});
if (passport)
	app.post("/edit", ensureAuthenticated, function(req, res, next) {console.log("edittttttttttttttttttttt " + req.body.displayName);
	  req.user.displayName = req.body.displayname;
	  req.user.bio = req.body.bio;
	  req.user.save(function(err) {
	    if (err) {
	      next(err);
	      return;
	    }
	    req.flash("info", "Profile updated!");
	    res.redirect("/edit");
	  });
	});

/*
 * ====================== COMMANDS ================
 */
/*
	app.post("/robot/actions/commands/start", function(req, res) {
		console.info("START THE APPLICATION "   );
		if( externalActuator ) delegate( "x(low)", "started", req, res);
 	});
	app.post("/robot/actions/commands/restart", function(req, res) {
		console.info("BACK TO THE START!");
		if( externalActuator ) delegate( "r(low)", "restarted", req, res);
	});
*/
	app.post("/robot/actions/commands/w", function(req, res) {
		if( externalActuator ) delegate( "w(low)", "moving forward", req, res);
		else actuate( `{ "type": "moveForward",  "arg": -1 }`, "server moving forward", req, res);
		
	});	
	app.post("/robot/actions/commands/s", function(req, res) {
		if( externalActuator ) delegate( "s(low)", "moving backward", req, res );
		else actuate( `{ "type": "moveBackward",  "arg": -1 }`, "server moving backward", req, res);
	});	
	app.post("/robot/actions/commands/a", function(req, res) {
 		if( externalActuator ) delegate( "a(low)", "moving left", req, res );
		else actuate( `{ "type": "turnLeft",  "arg": 1000 }`, "server moving left", req, res);
	});	
	app.post("/robot/actions/commands/d", function(req, res) {
  		if( externalActuator ) delegate( "d(low)", "moving right", req, res );
		else actuate( `{ "type": "turnRight",  "arg": 1000 }`, "server moving right", req, res);
	});	
	app.post("/robot/actions/commands/h", function(req, res) {
  		if( externalActuator ) delegate( "h(low)", "stopped", req, res );
		else actuate( `{  "type": "alarm",  "arg": 1000 }`, "server stopped", req, res);
	});		
	
	/*
	 * ====================== REPRESENTATION ================
	 */
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
	  res.send("SORRY, ERROR=" + err.status );
	});

	
//=================== UTILITIES =========================


function setUpAuth(){ //AUTH
	try{	
		console.log("\tWORKING WITH AUTH ... "  ) ;
		mongoose.connect("mongodb://localhost:27017/test");
		setUpPassport();	
		app.use(session({	 
			  secret: "LUp$Dg?,I#i&owP3=9su+OB%`JgL4muLF5YJ~{;t",
			  resave: true,
			  saveUninitialized: true
		}));
		app.use(flash());
		app.use(passport.initialize());
		app.use(passport.session());
		app.use(function(req, res, next) {
			  res.locals.currentUser = req.user;
			  res.locals.errors      = req.flash("error");
			  res.locals.infos       = req.flash("info");
			  next();
			});
	}catch( e ){
		console.log("SORRY, ERROR ... " + e) ;
	}	
}


function ensureAuthenticated(req, res, next) {
	  if (req.isAuthenticated()) {
		  next();
	  } else {
		    req.flash("info", "You must be logged in to see this page.");
		    res.redirect("/login");
	  }
}//

function delegate( hlcmd, newState, req, res ){
//	robotModel.robot.state = newState;
	emitRobotCmd(hlcmd);
    res.render("access");	
}
function actuate(cmd, newState, req, res ){
 	toRobot.send( cmd );
	robotModel.robot.state = newState;
	res.render("access");
}
var emitRobotCmd = function( cmd ){ //called by delegate;
 	var eventstr = "msg(usercmd,event,js,none,usercmd(robotgui( " +cmd + ")),1)"
  		console.log("emits> "+ eventstr);
 		mqttUtils.publish( eventstr );	//topic  = "unibo/qasys";
}

module.exports = app;