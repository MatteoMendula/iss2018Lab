/*
* =====================================
* proxy.js
* =====================================
*/

const
    coap  = require('coap'),
    async = require('async'),				//npm install asynch --save
    Readable = require('stream').Readable,
    requestNumber = 1;

var targetServer,
    proxy,
    client1,
    client2,
    targetResults;

 

function formatTitle(msg) {
    return '\n\n' + msg + '\n-------------------------------------';
}

function requestHandler(req, res) {
    console.log('Target receives [%s] in port [8976] from port [%s]', req.payload, req.rsinfo.port);
    res.end('RES_' + req.payload);
}

/*
 * The final callback is of the form callback(err, results...)
 */
function createTargetServer(callback) {
    console.log('Creating target server at port 8976 ' ); //+ callback

    targetServer = coap.createServer(requestHandler);

    targetServer.listen(8976, '0.0.0.0', callback);
    
}

function proxyHandler(req, res) {
    console.log('Proxy handled [%s]', req.payload);
    res.end('RES_' + req.payload);
}

function createProxy(callback) {
    console.log('Creating proxy at port 6780 ' ); // + callback

    proxy = coap.createServer({ proxy: true }, proxyHandler);

    proxy.listen(6780, '0.0.0.0', callback);
}

function sendRequest(proxied) {
    return function(n, callback) {
        var req = {
                host: 'localhost',
                port: 8976,
                agent: false
            },
            rs = new Readable();

        if (proxied) {
            req.port = 6780;
            req.proxyUri = 'coap://localhost:8976';
        }

        request = coap.request(req);

        request.on('response', function(res) {
            console.log('Client receives [%s] in port [%s] from [%s]', res.payload, res.outSocket.port, res.rsinfo.port);
            callback();
        });

        rs.push('MSG_' + n);
        rs.push(null);
        rs.pipe(request);
    }
}

function executeTest(proxied) {
    return function(callback) {
        if (proxied) {
            console.log(formatTitle('Executing tests with proxy'));
        } else {
            console.log(formatTitle('Executing tests without proxy'));
        }

        async.times(requestNumber, sendRequest(proxied), callback);
    }
}

function cleanUp(callback) {
    targetServer.close(function () {
        proxy.close(callback);
    });
}

function checkResults(callback) {
    console.log(formatTitle('Finish'));
 }

/*
 * The callback passed to each of the functions to be executed is internal to the async library. 
 */

 
async.series([
    createTargetServer,
    createProxy,
    executeTest(false),
    executeTest(true),
    cleanUp
], //checkResults);
  function (err, results) {
    // Here, results is an array of the value from each function
    console.log(results);  
});
 
//createTargetServer( function(res){ console.log( "createTargetServer:"+res) })


/*
https://www.npmjs.com/package/ponte
PUBLIC SERVERS
 coap://californium.eclipse.org/ or coap://vs0.inf.ethz.ch/, coap://coap.me/.
*/