/*
* =====================================
* serverHttpToCoapNoMqttNoMongo.js
* =====================================
*/
const
 	http    = require("http"),
	parse   = require('url').parse,
    coap    = require('coap'),
    join    = require('path').join,
    srvUtil = require("./ServerUtils"),
    fs      = require('fs'),
    url     = require('url'),    
    Readable = require('stream').Readable;
	
/*
* --------------------------------------------------------------
* 1) DEFINE THE SERVER that enables socket.io
* --------------------------------------------------------------
*/
    const app  = http.createServer(
    		function (request, response) {  //request is an IncomingMessage;
    			//requestUtil facilitates the access to the request
    			//and at the end calls the function handler
    			//with a more structured request info
    				requestUtil( request, response, handler );	
    });   
	const io   = require('socket.io').listen(app); 	//npm install --save socket.io
     
/*
 * VARS 
 */    
var root       	= __dirname; //set by Node to the directory path to the file;
var indexPath  	= root+"/index.html";
var ledViewPath	= root+"/ledState.html";
var html      	 = fs.readFileSync('index.html', 'utf8');


/*
* --------------------------------------------------------------
* 3) HANDLE A REQUEST
* --------------------------------------------------------------
*/
function handler (reqInfo, response) {
	var method = reqInfo.method;
	var path   = reqInfo.urlPathname;
	if( path === "/" ) path = "/index.html";
	
	console.log("serverHttpToCoapNoMqttNoMongo request.method=" + 
			method + " path=" + path + " request=" + reqInfo.body); 

	var fpath = join(root, path);
 
	/*
	* --------------------------------------------------------------
	* 3a) LOOK AT HTTP VERB
	* --------------------------------------------------------------
	*/
	switch ( method ){
		case 'GET' :
			if( path === '/Led' ){
				sendCoapRequest(reqInfo, response, "Led", handleCoapAnswer);
			}else  srvUtil.renderStaticFile(fpath,response);  //default index and ico 
			return;
		case 'POST' : //add (sent by browser)
			if ( path === '/ledSwitch' ) {
				sendCoapCoammnd(reqInfo, response, handleCoapAnswer);
 			}    	
			return;
		case 'PUT':  //modify
			if ( path === '/ledSwitch' ) {
				sendCoapCoammnd(reqInfo, response, handleCoapAnswer);
 			}    
			return;
		case 'DELETE':
 			return;
		default:{
			response.writeHead(405, {'Content-type':'application/json'});
			response.end(  "METHOD ERROR"  );		
			return;
		}	
		
	}//switch
 
}
/*
* --------------------------------------------------------------
* 4) CALLBACK FOR THE CoAP ANSWER
* --------------------------------------------------------------
*/

var doAnswerStr = function(err, response, msg){
	console.log("serverHttpToCoapNoMqttNoMongo doAnswerStr" +msg);
 	if( err ){	response.statusCode=500; response.end(msg); }
	else{ response.statusCode=200; response.end(msg); }
} 

/*
* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
* UTILITIES
* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
*/

/*
 * LOOK AT A REQUEST and 
 */
var requestUtil = function(req,res,cb){
	var urlParts    = url.parse(req.url, true),
		urlParams   = urlParts.query, 
		urlPathname = urlParts.pathname,
		body = '',
		reqInfo = {};

	req.on('data', function (data) {
	 body += data; 
	});
	req.on('end', function () {
		reqInfo.method      = req.method;		//'GET'
		reqInfo.urlPathname = urlPathname;  	//'/api/user'
		reqInfo.urlParams   = urlParams;  		//{}
		reqInfo.body        = body;  
		reqInfo.query       = urlParts.query;	//{}
		reqInfo.urlParts    = urlParts;
		//console.log( reqInfo    );
		cb(reqInfo,res);
	 });	
	}

/**
 * COAP messaging
 */
function sendCoapRequest(request, response, resource, callback ){
	console.log("sendCoapRequest " + resource);
 	req   = coap.request('coap://localhost/'+resource)
	req.on('response', function(res) {
		//res.pipe( process.stdout );
		console.log("sendCoapRequest answer=" + res.payload);
		callback(request, response, res.payload);
	}) 
	req.end()
}
var handleCoapAnswer = function(request, response, coapData){
	//response.end("<html>"+"handleCoapAnswer=" + coapData + "</html>");	
	console.log("handleCoapAnswer " + ""+coapData );
	srvUtil.renderStaticFile(indexPath,response);
//	clientMqtt.publish(""+coapData);
 	setTimeout( function(){ io.sockets.send(""+coapData  ) }, 200 ) ;
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
 	
 	req.write("switch");		//From the Vocabulary of Led(Coap)Resource
 	
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


/*
 * io.sockets test section
 */
function tick () {
	  var now = new Date().toUTCString();	  
	  io.sockets.send(now);
}
//setInterval(tick, 1000);



/*
* --------------------------------------------------------------
* 2) START THE SERVER
* --------------------------------------------------------------
*/
const initMsg=
	"\n"+
	"------------------------------------------------------\n"+
	"serverHttpToCoapNoMqttNoMongo bound to port 8080\n"+
	"uses socket.io\n"+
 	"DYNAMICALLY CONNECTS (as client) TO  CoAP server at coap://localhost:5683\n"+
	"work as an HTTP-to-CoaP proxy\n"+
	"with reference to resource URI = /Led    (by MainCoapBasicLed / LedCoapResource)\n"+
	"with reference to resource URI = /Led    (by BlsHexagSystem / LedResource)\n"+
	"with reference to resource URI = /Button (by BlsHexagSystem / ButtonResource )\n"+
	"------------------------------------------------------\n";

app.listen(8080, function(){console.log(initMsg)}); 

/*
 * ACTIVATE WITHOUT socket.io
 */
/**
 * CREATE section
 */
function createHttpServer(port, callback){
	//configure the system;
		var server = http.createServer();
		server.on( 'request' , handleHttpRequest); 
		console.log('serverHttpToCoapNoMqttNoMongo register handleRequest root=' + root);
	//start;
 		server.listen(port, callback );	
};
//createHttpServer( 8080, function() { console.log('serverHttpToCoapNoMqttNoMongo bound to port 8080'); } );


/*
/Led requires MainCoapBasicLed / LedCoapResource or BlsHexagSystem / LedResource
curl -X PUT -d "true" http://localhost:8080/ledSwitch
curl -X GET  http://localhost:8080/Led

curl -X PUT -d "true" http://192.168.0.50:8080/ledSwitch
curl -X GET  http:///192.168.0.50:8080/Led


/Button requires BlsHexagSystem / ButtonResource
curl -X GET  http://localhost:8080/Button  ()
curl -X PUT -d "pressed" http://localhost:8080/Button


 curl -X POST -d "{\"name\": \"Bob\",\"age\": \"35\",\"password\": \"qq\"}" http://localhost:8080/api/user
 curl -X POST -d "{ \"log\": \"xxx\" }" http://localhost:8080/api/log
 curl -X PUT -d "true" http://localhost:8080/ledSwitch
*/