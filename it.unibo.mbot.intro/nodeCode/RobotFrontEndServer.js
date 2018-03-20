/*
* =====================================
* RobotFrontEndServer.js
 * Implements a simple data store that answers to PUT/GET requests
 * For each operation, the server emits (via emitQaEvent) an event of the form
 * 			serverRequest:serverRequest(data(SENDER,DATA))
 * GET: 		serverRequest:serverRequest(data(SENDER,get))
 * PUT/POST : 	serverRequest:serverRequest(data(SENDER,ddd))
 * It also gives a response to the caller (browser/curl/POSTMAN/httpclient)
 * TODO:  the response could be given by the qa application
* =====================================
*/
var net       = require('net');
var http      = require("http");
var utils     = require("./utils"); //to handle uncaughtException

var host          = "localhost";
var port          = 8098; 
var qaport        = 8077; 
var socketToQaCtx = null; 
var dataStore = [];	//Array of Buffers

//Connect to the QActor application
function connectToQaNode(){
 	try{
		socketToQaCtx = net.connect({ port: qaport, host: host });
		socketToQaCtx.setEncoding('utf8');	
		console.log('connected to qa node:' + host + ":" + port);
	}catch(e){ 
		console.log(" ------- connectToQaNode ERROR "  + e + " socketToQaCtx=" + socketToQaCtx); 
 	}
}
connectToQaNode();

/*
 * Handle the qaanswer from the qa node
 */
if( socketToQaCtx != null){
var qaanswer = "";
	console.log("NodeServer SETUP socketToQaCtx ---- ");
	socketToQaCtx.on('data',function(data) {
 		qaanswer = qaanswer + data;
  		if( qaanswer === "" ) return;
		if( data.includes("\n") ){
			console.log(  "RobotFrontEndServer received from qa: "+qaanswer );
			qaanswer = "";
		}
	});  	  
	socketToQaCtx.on('close',function() {
		console.log('connection is closed');
	});
	socketToQaCtx.on('end',function() {
		console.log('connection is ended');
	});
}

/*
 * CREATE a server that handles GET, PUT/POST
 */
http.createServer(function(request, response) {
	//The request object is an instance of IncomingMessage (a ReadableStream; it's also an EventEmitter)
	var headers   = request.headers;
	var method    = request.method;
	var url       = request.url;
 	//console.log('method=' + method );
	if( method == 'GET'){
		var outS = "";
		if( dataStore.length == 0 ) outS ="no data";
 		dataStore.forEach( function(v,i){
			outS = outS +  i + ")" + v + "\n"
		});
		response.write( outS )
		response.end();			//qaanswer to the caller
		emitQaEvent( "get" );	//emit event serverRequest
		//WARNING: The response should be built by the qa
	}//if GET
 	if( method == 'POST' ||  method == 'PUT'){
		var item = '';
		request.setEncoding("utf8"); //a chunk is a utf8 string instead of a Buffer
		request.on('error', function(err) {
		    console.error(err);
		  });
		request.on('data', function(chunk) { //a chunck is a byte array
 		    item = item + chunk; 	
 		    console.log('method=' + method + " data=" + item);
		 });
		request.on('end', function() {
 				dataStore.push(item);
   				emitQaEvent( item );
				buildHtmlResponse( url, method, dataStore, response );
				//WARNING: The response should be given by the qa
 		  });
	}//if POST PUT
}).listen( port, function(){ console.log('bound to port ' + port);} );
console.log('Server running on ' + port);

function buildHtmlResponse( url, method, data, response ){
		response.on('error', function(err) { console.error(err); });	
	    response.statusCode = 200;
	    response.setHeader('Content-Type', 'application/json');
	    //response.writeHead(200, {'Content-Type': 'application/json'}) //compact form	
	    var responseBody = {
	      //headers: headers,	//comment, so to reduce output
	      method: method,
	      url:    url,
	      dataStore:   dataStore 
	    };	
	    response.write( JSON.stringify(responseBody) );
	    response.end();
	    //response.end(JSON.stringify(responseBody)) //compact form		
}

/*
 * emit an event for the QActor application
 */
var msgNum=1;
function emitQaEvent( payload ) {
 	try{
 		var msg = "msg(serverrequest,event,frontend,none,serverrequest( data(frontend,'" +  payload +"') )," + msgNum++ +")";
  		if(socketToQaCtx !== null ){
  	  		console.log('emitQaEvent mmsg=' + msg    ); 
  			socketToQaCtx.write(msg+"\n");
  			//TDOO: wait an qaanswer from the qa before responding to the HTTP user
  		}
	}catch(e){ 
  		console.log("WARNING: "  + e ); 
 	}
}

/*
USAGE
node RobotFrontEndServer.js localhost 8071
node RobotFrontEndServer.js 192.168.137.1 8071
or run nodeServerActivator

curl -X PUT -d  item1 http://localhost:8098
*/
 
