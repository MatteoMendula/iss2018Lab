/*
* =====================================
* serverHttpToCoap.js
* =====================================
*/

const
	http    = require("http"),
	parse   = require('url').parse,
    coap  = require('coap'),
    Readable = require('stream').Readable

function createHttpServer(port, callback){
	//configure the system;
		console.log('serverHttpToCoap creates a server and register handleRequest');
		var server = http.createServer();
		server.on( 'request' , handleHttpRequest); 
	//start;
 		server.listen(port, callback );	
};
 
var handleHttpRequest = function (request, response) { 
	var url  = parse(request.url);
	console.log("serverHttpToCoap request.method=" + request.method + " url.pathname=" + url.pathname); 
	sendCoapRequest(request, response, handleCoapAnswer);
} 

var handleCoapAnswer = function(request, response, coapData){
	response.end("<html>"+"handleCoapAnswer=" + coapData + "</html>");	
}

function sendCoapRequest(request, response, callback ){
	console.log("sendCoapRequest " );
 	req   = coap.request('coap://localhost/Led')
	req.on('response', function(res) {
		//res.pipe( process.stdout );
		callback(request, response, res.payload);
	}) 
	req.end()
}

/*
 * Another way to perform the request
 * A request to an HTTP server is a stream instance
 * Data is buffered in Readable streams when the implementation calls stream.push(chunk). 
 * If the consumer of the Stream does not call stream.read(), 
 * the data will sit in the internal queue until it is consumed.
 */
function sendCoapRequestOther(request, response, callback ){
	console.log("sendCoapRequest " );
 	 
 	request = coap.request('coap://localhost/Led');
	rs      = new Readable();
 	rs.push('MSG_' );
    rs.push(null);
	rs.pipe(request);	//attaches a Writable stream to the readable
 
	request.on('response', function(res) {
	   console.log('Coap request receives [%s] in port [%s] from [%s]', res.payload, res.outSocket.port, res.rsinfo.port);
	   callback(request, response, res.payload);
	});
}
 
createHttpServer( 8080, function() { console.log('serverHttpToCoap bound to port 8080'); } );
 