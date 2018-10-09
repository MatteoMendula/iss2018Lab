/*
* =====================================
* serverHttpToCoap.js
* =====================================
*/

const
	http    = require("http"),
	parse   = require('url').parse,
    coap    = require('coap'),
    join    = require('path').join,
    srvUtil = require("./ServerUtils"),
    Readable = require('stream').Readable

var root      = __dirname; //set by Node to the directory path to the file;
var indexPath = root+"/index.html";

function createHttpServer(port, callback){
	//configure the system;
		var server = http.createServer();
		server.on( 'request' , handleHttpRequest); 
		console.log('serverHttpToCoap register handleRequest root=' + root);
	//start;
 		server.listen(port, callback );	
};
 
var handleHttpRequest = function (request, response) { 
	var method = request.method;
	var url    = parse(request.url);
	var path   = url.pathname;
	if( path === "/" ) path = "/index.html";
	
	console.log("serverHttpToCoap request.method=" + method + " path=" + path); 
	
	var fpath = join(root, path);
	if( method === 'GET' && path === "/Led" ) { 
 		sendCoapRequest(request, response, handleCoapAnswer);	
		return; 
 	}
	if( method === 'GET'   ) {
		srvUtil.renderStaticFile(fpath,response);
 		return; 		
 	}
	if (method === 'POST' && path === '/adduser' ) {
		var inData = '';
		request.on('data', function (data) { //data is of type Buffer;
	        inData += data;	
	        // Too much data, kill the connection!;
	        if (inData.length > 1e6) request.connection.destroy();
	    });
		request.on('end', function () { //data are all available;
			response.end("<html>"+"inData=" + inData + "</html>");
 	    });
 		return;		
	}
	if (method === 'POST' && path === '/ledSwitch' ) {
		sendCoapCoammnd(request, response, handleCoapAnswer);
		return;	
	}
	response.end( "Sorry, I don't understand" );
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


function sendCoapCoammnd(request, response, callback ){
	console.log("sendCoapCoammnd " );
	var requestOptions = {
			  host: 'localhost',
			  port: 5683,
			  pathname: '/Led',
			  method: 'PUT',
	};
 	req   = coap.request(requestOptions);
 	var payload = {
// 			  title: 'this is a test payload',
// 			  body: 'containing nothing useful'
 			value : 'true'
 	}

 	//req.write(JSON.stringify(payload));
 	
 	req.write("switch");
 	
	req.on('response', function(res) {
 		//callback(request, response, res.payload);
		srvUtil.renderStaticFile(indexPath,response);
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
 