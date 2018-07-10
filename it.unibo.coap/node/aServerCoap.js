var coap        = require('coap')
  , server      = coap.createServer()

server.on('request', function(req, res) {
  console.log("handling a request ... ");
  res.end('Hello ' + req.url.split('/')[1] + '\n')
})

server.listen(function() {
  console.log('server started')
})


console.log("BYE");

var arequest = function(){
	var req = coap.request('coap://localhost/Pippo');
	req.on('response', function(res) {
		//console.log("response ... " + res);
		res.pipe(process.stdout);
	})
	req.end()
}

arequest();
/*
server.listen( arequest );

server.listen(function() {
	console.log("listen ... " );
	var req = coap.request('coap://localhost/Matteo');
	req.on('response', function(res) {
		//console.log("response ... " + res);
		res.pipe(process.stdout);
	})
	req.end()
})
*/
//coap://127.0.0.1:5683/Matteo

// the default CoAP port is 5683
/*
server.listen(function() {
  var req = coap.request('coap://localhost/Matteo')

  req.on('response', function(res) {
	console.log("response ... ");
    res.pipe(process.stdout);
    res.on('end', function() {
      console.log("response end ");
//      process.exit(0)
    })
  })

  req.end()
})
*/