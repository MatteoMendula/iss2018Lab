/*
 * appServer/routes/actuators.js
 */
var express     = require('express'),
  router        = express.Router(),
  resourceModel = require('../models/model');

var serverWithSocket = require('../../socketIofrontendServer.js');
 
router.route('/').get(function (req, res, next) {
	  //console.info( resourceModel.pi.actuators );
	  req.result = resourceModel.pi.actuators;
	  next();
	});

router.route('/leds').get(function (req, res, next) {
  req.result = resourceModel.pi.actuators.leds;
  //serverWithSocket.updateClient( JSON.stringify(resourceModel.pi.actuators.leds) );
  next();
});

router.route('/leds/:id').get(function (req, res, next) { 
//(curl) http://localhost:3000/pi/actuators/leds/1;
  req.result = resourceModel.pi.actuators.leds[req.params.id];
  next();
})
.put(function(req, res, next) { 
//curl -H "Content-Type: application/json" -X PUT -d "{\"value\": \"true\" }" http://localhost:3000/pi/actuators/leds/1;
  var selectedLed = resourceModel.pi.actuators.leds[req.params.id];
  selectedLed.value = req.body.value;   	//CHANGE THE MODEL;
  console.info('route LED  Changed LED %s value to %s', req.params.id, selectedLed.value);
  req.result = selectedLed;
  serverWithSocket.updateClient( JSON.stringify(selectedLed.value) );
//  emitInfo(selectedLed.value); 		//EMIT STATE CHANGE EVENT;
  next();
});

/*
 * Emit the new led value according to the blsMVC model
 */
var mqttUtils     = require('./../../uniboSupports/mqttUtils'); 	 

var emitInfo = function( ledValue ){
	var val =  "off";
	if( ledValue === "true" ) val = "on";
	var eventstr = "msg(ctrlEvent,event,js,none,ctrlEvent(leds, led1, " +val + "),1)"
 		console.log("	actuators LED emits> "+ eventstr);
 		mqttUtils.publish( eventstr );
}

module.exports = router; 