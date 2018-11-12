var net       = require('net');

var textChunk = '';
var servSocket    = null;
var port          = 1337;

var server = net.createServer(function(socket) {
	servSocket = socket;
//	socket.write('Echo server\r\n');
	/*
	 * makes the socket writes whatever it reads from the client 
	 * back to him, hence the "Echo server"
	 */
//	socket.pipe(socket);
	socket.on('data', function(data){
//		//console.log(data);
//		textChunk = data.toString('utf8');
//		console.log('Server: Received ... ' + textChunk);
		var textChunk = action1(data);
		socket.write(textChunk);
	});
});


action1 = function(data){
	//console.log(data);
	var textChunk = data.toString('utf8');
	console.log('Server: Received ... ' + textChunk);
	return 	textChunk;
}

server.listen(port, '127.0.0.1');

//netcat 127.0.0.1 1337


/*
 * CLIENT
 */

 var i = 0;
 var client = new net.Socket();
 
 client.connect(port, '127.0.0.1', function() {
 	console.log('Client: Connected');
 	client.write('Hello, server! Love, Client.');
 });
 
client.on('data', function(data) {
	console.log('Client: Received ... ' + data);
	i++;
	if(i==2) 
		client.destroy(); 
});
 
client.on('close', function() {
	console.log('Client: Connection closed');
});