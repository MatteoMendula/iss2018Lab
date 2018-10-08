/*
* =====================================
* coapClient.js
* =====================================
*/
const coap  = require('coap')  
    , req   = coap.request('coap://localhost/Led')

req.on('response', function(res) {
  res.pipe(process.stdout)
})

req.end()