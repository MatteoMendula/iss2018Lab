/*
 * -----------------------------------------------
 * it.unibo.mbot2018/nodeCode/robotFrontend/frontendServerRobot.js
 * -----------------------------------------------
 */
/**
 * Module dependencies.
 */
var app   = require('./appFrontEndRobot');
var debug = require('debug')('robotfrontend:server');
var http  = require('http');

require('dotenv').config();  //added by AN

/**
 * Get port from environment and store in Express.
 */
var port = normalizePort( process.env.PORT || '3000' );
app.set('port', port);

/**
 * Create HTTP server.
 */
var server = http.createServer(app);

/*
* --------------------------------------------------------------
* EXTENSION 1): CREATE A  EVENT HANDLER and give the iosocket to it
* --------------------------------------------------------------
*/
const io      = require('socket.io').listen(server); //npm install --save socket.io
var echannel  = require("./appServer/utils/channel");
echannel.setIoSocket(io);

/*
* --------------------------------------------------------------
* EXTENSION 2): START THE SERVER
* --------------------------------------------------------------
*/
const robotControl   = require('./appServer/controllers/robotControl');
const initMsg=
	"\n"+
	"------------------------------------------------------\n"+
	"serverRobotCmd bound to port: "+ port + "\n" +
	"uses socket.io\n"+
	"------------------------------------------------------\n";
 
process.argv.forEach(function (val, index, array) {
	  console.log("input args[" + index + ']: ' + val ); //(val=='true') + " " + array.length);
	  if( index == 2 ) //the user has specified if we must work with a real robot or not
		  	 robotControl.setRealRobot( val=='true' );
	  if( index == (array.length-1) ) 
	  	server.listen(port, function(){console.log(initMsg)}); 
});



server.on('error', onError);
server.on('listening', onListening);

/**
 * Normalize a port into a number, string, or false.
 */
function normalizePort(val) {
 //console.log( process.env );
  var port = parseInt(val, 10);
  if (isNaN(port)) {
    // named pipe
    return val;
  }
  if (port >= 0) {
    // port number
    return port;
  }
  return false;
}

/**
 * Event listener for HTTP server "error" event.
 */
function onError(error) {
  if (error.syscall !== 'listen') {
    throw error;
  }

  var bind = typeof port === 'string'
    ? 'Pipe ' + port
    : 'Port ' + port;
  // handle specific listen errors with friendly messages
  switch (error.code) {
    case 'EACCES':
      console.error(bind + ' requires elevated privileges');
      process.exit(1);
      break;
    case 'EADDRINUSE':
      console.error(bind + ' is already in use');
      process.exit(1);
      break;
    default:
      throw error;
  }
}

/**
 * Event listener for HTTP server "listening" event.
 */
function onListening() {
  var addr = server.address();
  var bind = typeof addr === 'string'
    ? 'pipe ' + addr
    : 'port ' + addr.port;
  debug('Listening on ' + bind);
}
/**
 * HANDLE User interruption commands.
 */
//Handle CRTL-C;
process.on('SIGINT', function () {
//  ledsPlugin.stop();
  console.log('serverRobot Bye, bye!');
  process.exit();
});
process.on('exit', function(code){
	console.log("Exiting code= " + code );
});
process.on('uncaughtException', function (err) {
 	console.error('serverRobot got uncaught exception:', err.message);
  	process.exit(1);		//MANDATORY!!!;
});


/*
curl -H "Content-Type: application/json" -X POST -d "{\"value\": \"true\" }"
*/