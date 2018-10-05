/*
* =====================================
* coapJson.js
* =====================================
*/
 const coap = require('coap')  
    , bl   = require('bl')

coap.createServer(function(req, res) {
	console.log("Accept=" + req.headers['Accept'] );
 if (req.headers['Accept'] != 'application/json') {
    res.code = '4.06'
    return res.end()
  }

  res.setOption('Content-Format', 'application/json')

  res.end(JSON.stringify({ hello: "world" }))
}).listen(function() {
	console.log("REQUEST START");
  coap
    .request({
      pathname: '/Led',
      options:  { 'Accept' : 'application/json' }
       
    })
    .on('response', function(res) {
      console.log('response code', res.code)
      if (res.code !== '2.05')
        return process.exit(1)

      res.pipe(bl(function(err, data) {
        var json = JSON.parse(data)
         console.log(json)
         process.exit(0)
      }))
    })
    .end()
})