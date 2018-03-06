/*
* =====================================
* TcpClientToRadar.js
* =====================================
*/
var net  = require('net');
var host = "localhost";
var port = 8033;	

console.log('connecting to ' + host + ":" + port);
var conn = net.connect({ port: port, host: host });
conn.setEncoding('utf8');

// when receive data back, print to console
conn.on('data',function(data) {
	console.log(data);
});
// when server closed
conn.on('close',function() {
	console.log('connection is closed');
});
conn.on('end',function() {
	console.log('connection is ended');
});

/*
===============================================================
Interaction
===============================================================
*/
function sendMsg( msg ){
 	try{
 		console.log("SENDING " + msg  );
 		conn.write(msg+"\n");	//Asynchronous!!!
	}catch(e){ 
		console.log("ERROR  "  + e ); 
 	}
}
 
function sendMsgAfterTime( msg, delay ){
	setTimeout( function(){ sendMsg( msg ); },  delay);
}  
 
var msgNum=1;
 
sendMsgAfterTime("msg(polarMsg,dispatch,jsSource,radarguibase, p(50,30),"  + msgNum++ +")", 1000);
sendMsgAfterTime("msg(polarMsg,dispatch,jsSource,radarguibase, p(50,90),"  + msgNum++ +")", 2000);
sendMsgAfterTime("msg(polarMsg,dispatch,jsSource,radarguibase, p(50,150)," + msgNum++ +")", 3000);

setTimeout(function(){ conn.end(); }, 4000);
//Half-closes the conn, i.e., it sends a FIN packet. It is possible the server will still send some data. 
/*
----------------------------------------------
USAGE	 
node TcpClientToRadar.js  
----------------------------------------------
*/