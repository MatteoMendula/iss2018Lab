/*
* =====================================
* coapServer.js
* =====================================
*/

const coap    = require('coap')  
    , server  = coap.createServer()

server.on('request', function(req, res) {
  console.log(req.url);
  res.end('Hello ' + req.url.split('/')[1] + '\n')
})

server.listen(function() {
  console.log('server started')
})

 