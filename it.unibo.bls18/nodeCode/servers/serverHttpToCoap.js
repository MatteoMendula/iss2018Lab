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

	/*
	 * DB PART
	 */
    const ctrlGet     = require('./controls/ctrlGetUsersRest');
    const ctrlAdd     = require('./controls/ctrlAddUserRest');
    const ctrlDel     = require('./controls/ctrlDeleteUserRest');
    const ctrlChng    = require('./controls/ctrlChangeUserRest'); 
    const requestUtil = require('./utilsMongoose.js');   //sets connections; 
    
var root       = __dirname; //set by Node to the directory path to the file;
var indexPath  = root+"/index.html";
var ledViewPath= root+"/ledState.html";
var html       = fs.readFileSync('index.html', 'utf8');

/**
 * MQTT section
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

/**
 * CREATE section
 */
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
	
	/*
	 * DB PART
	 */
 
	switch ( method ){
		case 'GET' :
			if( path === '/Led' ){
				sendCoapRequest(request, response, "Led", handleCoapAnswer);
			} else	if( path === "/Button" ) { 
		 		sendCoapRequest(request, response, "Button", handleCoapAnswer);	
 		 	} else if( path === '/api/user' ){
				console.log("serverHttpToCoap switch method=" + method + " path=" + path); 
				ctrlGet.getUsers(  response, doAnswerStr );  //perform an asynch  query
			} else  srvUtil.renderStaticFile(fpath,response);  //default index and ico 
			return;
		case 'POST' : //add 
			if ( path === '/ledSwitch' ) {
				sendCoapCoammnd(request, response, handleCoapAnswer);
 			} else if( path==='/api/user'  ){
				ctrlAdd.addUser(  reqInfo.body, response, doAnswerStr );
			}  	
			return;
		case 'PUT':  //modify
			if ( path === '/ledSwitch' ) {
				sendCoapCoammnd(request, response, handleCoapAnswer);
 			} else if( path === '/api/user' ){  //accepts only JSON format;
				var jsonBody = JSON.parse(  reqInfo.body  );
				console.log("oldUser=" + jsonBody.old);
				console.log("chngUser=" + jsonBody.new);
 				ctrlChng.changeUser(jsonBody.old, jsonBody.new, response, doAnswerStr );
			}  
			return;
		case 'DELETE':
			if( path === '/api/user' ){ // for JSON only;
				ctrlDel.deleteUser( JSON.parse( reqInfo.body ), response, doAnswerStr);
 			} //else{ response.statusCode=400; response.end("ERROR on DLEETE"); }
			return;
		default:{
			response.writeHead(405, {'Content-type':'application/json'});
			response.end(  "METHOD ERROR"  );		
			return;
		}	
		
	}//switch
 	
	
//	response.setHeader('Content-Type', 'text/html');
//	response.setHeader('Content-Length', Buffer.byteLength(html, 'utf8'));
//	response.end(html);

}

var doAnswerStr = function(err, response, msg){
	console.log("serverHttpToCoap doAnswerStr" +msg);
 	if( err ){	response.statusCode=500; response.end(msg); }
	else{ response.statusCode=200; response.end(msg); }
} 
/**
 * COAP messaging
 */
function sendCoapRequest(request, response, resource, callback ){
	console.log("sendCoapRequest " );
 	req   = coap.request('coap://localhost/'+resource)
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

/*
 * io.sockets test section
 */
function tick () {
	  var now = new Date().toUTCString();	  
	  io.sockets.send(now);
}
//setInterval(tick, 10000);

app.listen(8080, function(){console.log("serverHttpToCoap bound to port 8080")}); 
//createHttpServer( 8080, function() { console.log('serverHttpToCoap bound to port 8080'); } );


/*
 curl -X POST -d "{\"name\": \"Alice\",\"age\": \"25\",\"password\": \"pp\"}" http://localhost:3000/api/user
*/