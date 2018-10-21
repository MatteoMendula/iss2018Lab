/*
* =====================================
* serverHttpToCoap.js
* =====================================
*/
 

const
    app     = require('http').createServer(handler),   
    io      = require('socket.io').listen(app); 	//npm install --save socket.io
	http    = require("http"),
	parse   = require('url').parse,
    coap    = require('coap'),
    join    = require('path').join,
    srvUtil = require("./ServerUtils"),
    fs      = require('fs'),
    Readable = require('stream').Readable;

    const mqtt   = require('mqtt');			//npm install --save mqtt
    const config = require('./config/config');

var root       = __dirname; //set by Node to the directory path to the file;
var indexPath  = root+"/index.html";
var ledViewPath= root+"/ledState.html";
var html       = fs.readFileSync('index.html', 'utf8');

/**
 * Init mqtt
 */
const topic  = config.mqttTopic;
const client = mqtt.connect(config.mqttUrl);

client.on('connect', function() {
    client.subscribe(topic);
    console.log('client mqtt has subscribed successfully ');
});

client.on('message', function(topic, message) {
    console.log('mqtt on server RECEIVES:' + message.toString() );
    io.sockets.send( message.toString());
});

function createHttpServer(port, callback){
	//configure the system;
		var server = http.createServer();
		server.on( 'request' , handleHttpRequest); 
		console.log('serverHttpToCoap register handleRequest root=' + root);
	//start;
 		server.listen(port, callback );	
};

function handler (request, response) {
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
//	response.setHeader('Content-Type', 'text/html');
//	response.setHeader('Content-Length', Buffer.byteLength(html, 'utf8'));
//	response.end(html);

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
var handleCoapAnswer = function(request, response, coapData){
	//response.end("<html>"+"handleCoapAnswer=" + coapData + "</html>");	
	srvUtil.renderStaticFile(indexPath,response);
//	clientMqtt.publish(""+coapData);
//	setTimeout( function(){ io.sockets.send(""+coapData  ) }, 200 ) ;
	//io.sockets.send(""+coapData);
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
 		srvUtil.renderStaticFile(indexPath,response);
		/*
		 * 
		 */
		//console.log("CoAp answer = " + res.payload) ;
 		setTimeout( function(){
			var now = new Date().toUTCString();
			io.sockets.send(""+res.payload + " time=" + now)
		}, 200 ) ;
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


function tick () {
	  var now = new Date().toUTCString();	  
	  io.sockets.send(now);
}

 

//setInterval(tick, 10000);

app.listen(8080, function(){console.log("serverHttpToCoap bound to port 8080")}); 
//createHttpServer( 8080, function() { console.log('serverHttpToCoap bound to port 8080'); } );


/*
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
*/