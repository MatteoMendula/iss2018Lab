/*
* =====================================
ServerNaive.js
* =====================================
*/
 var http  = require('http');
// require('../utils.js');

 
/*
 * Function used to handle requests
 */
var handleRequest = function (request, response) { //request has many info
	console.log('ServerNaive REQUEST METHOD=', request.method); 
	console.log('ServerNaive REQUEST URL=',    request.url);  
 	if( request.url==="/" ) {
 	  	response.end("wlecome to ServerNaive");
   		return;
 	}
   	response.end("ServerNaive does not understand");
};


 

/*
 * Configure and start the server
 */ 
function main(){
	var server = http.createServer();
	server.on( 'request' , handleRequest); 
 	
	server.listen(8080, function() { 
		console.log('ServerNaive bound to port 8080');
	});	
}

main();

