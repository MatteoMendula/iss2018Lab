/*
* =====================================
* PolarToRadar.js
* =====================================
*/
var net  = require('net');
var utils = require('./utils'); //for handling uncaughtException

/*
 * -----------------------------------------------
 * CONNECTION TO RADAR 
 * -----------------------------------------------
 */
var radarhost   = "localhost";
var radarport   = 8033;	
var msgNum      = 1;

console.log('connecting to RADAR at ' + radarhost + ":" + radarport);
var conn = net.connect({ port: radarport, host: radarhost });
conn.setEncoding('utf8');

var emitEventForRadar = function( data ){
 	try{
  		var msg = "msg(polar,event,jsSource,none," + data + ","  + msgNum++ +")";
 		console.log("SENDING " + msg  );
 		conn.write(msg+"\n");	//Asynchronous!!!
	}catch(e){ 
		console.log("ERROR  "  + e ); 
 	}
}

/*
 * -----------------------------------------------
 * SERVER
 * -----------------------------------------------
 */
var host   = "localhost";
var port   = 8057;	

var server = net.createServer(function(socket) {
	socket.on('data', function( data ) { //data has the form p(DISTANCE,ANGLE)
		var dataNoWhiteSpaces = (""+data).trim();
	    emitEventForRadar( dataNoWhiteSpaces );  
	  });
	  
	socket.on('end', socket.end);  
});

//STARTING
emitEventForRadar("p(70, 0)");	//just to show 
console.log("PolarToRadar starts at " + port);
server.listen(port, host );


/*
----------------------------------------------
USAGE
netcat localshot 8057 --> Echo server
node PolarToRadar.js  (and after this run PolarToRadarTester)
----------------------------------------------
*/
/*
 * The pipe() function reads data from a readable stream as it becomes available 
 *  and writes it to a destination writable stream.
 *  The socket object implements both the readable and writable stream interface, 
 *  so it is therefore writing any data it receives back to the socket.
 */
	//socket.pipe(socket);	 
