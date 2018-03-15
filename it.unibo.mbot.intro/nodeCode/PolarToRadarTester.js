/*
* =====================================
* PolarToRadarTester.js
* =====================================
*/
var net   = require('net');
var utils = require('./utils');
var host  = "localhost"; //"127.0.0.1"
var port  = 8057;	

var client = new net.Socket();

console.log("PolarToRadarTester  starts");
client.connect(port, host, function() {
	console.log('Connected');
});

client.on('data', function(data) {
	console.log('Received: ' + data);
//	client.destroy(); // kill client after server's response
});

client.on('close', function() {
	console.log('Connection closed');
});


setTimeout( function(){ client.write('p(70, 0)');   },  1000);
setTimeout( function(){ client.write('p(70, 90)');  },  2000);
setTimeout( function(){ client.write('p(70, 180)'); },  3000);
/*
----------------------------------------------
USAGE
netcat 127.0.0.1 1337 --> Echo server
node PolarToRadar.js 
----------------------------------------------
*/