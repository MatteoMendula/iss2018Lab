/*
* =====================================
* TcpClientToRadar.js
* =====================================
*/
var net  = require('net');
var host = "localhost";
var port = 8033;	

console.log('connecting to ' + host + ":" + port);
var socket = net.connect({ port: port, host: host });
socket.setEncoding('utf8');

// when receive data back, print to console
socket.on('data',function(data) {
	console.log(data);
});
// when server closed
socket.on('close',function() {
	console.log('connection is closed');
});
socket.on('end',function() {
	console.log('connection is ended');
});

/*
===============================================================
Interaction
===============================================================
*/
var msgNum=1;

function sendMsg( msg ){
 	try{
 		console.log("SENDING " + msg  );
 		socket.write(msg+"\n");	//Asynchronous!!!
	}catch(e){ 
		console.log("ERROR  "  + e ); 
 	}
}
 
function sendMsgAfterTime( msg, time ){
	setTimeout(function(){ sendMsg( msg ); },  time);
} 
 
sendMsgAfterTime("msg(polarMsg,dispatch,jsSource,radarguibase, p(50,30)," + msgNum++ +")",  1000);
sendMsgAfterTime("msg(polarMsg,dispatch,jsSource,radarguibase, p(50,90)," + msgNum++ +")",  2000);
sendMsgAfterTime("msg(polarMsg,dispatch,jsSource,radarguibase, p(50,150)," + msgNum++ +")", 3000);

setTimeout(function(){ socket.end(); }, 4000);
//Half-closes the socket, i.e., it sends a FIN packet. It is possible the server will still send some data. 
/*
----------------------------------------------
USAGE	 
node TcpClientToRadar.js  
----------------------------------------------
*/