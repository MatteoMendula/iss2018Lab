/*
 * appServer/routes/sensors.js
 */
var express     = require('express'),
  router        = express.Router(),
  resourceModel = require('../models/robot');

//router.route('/').get(function (req, res, next) {
//    req.type = "defaultView" ;
//  	req.result = resourceModel.pi.sensors;  
//    next();  
//});

//router.route('/pir').get(function (req, res, next) {
//  req.result = resourceModel.pi.sensors.pir;
//  next();
//});

router.route('/').get(function (req, res, next) {
	  req.result = resourceModel.pi.sensors;
	  next();
});

router.route('/temperature').get(function (req, res, next) {
	  console.log( "................" );
	  console.log( req.result );
    req.result = resourceModel.pi.sensors.temperature;
	console.log( req.result );
  console.log( "................" );
  next();
});
//
//router.route('/temperatureProlog').get(function (req, res, next) {
//  var tval =  resourceModel.pi.sensors.temperature.value ;
//  console.log(tval);
//  req.result = "msg( sensor, event, temperatureDev, none, "+ tval+", 0 )";
//  next();
//});
//
//router.route('/humidity').get(function (req, res, next) {
//  req.result = resourceModel.pi.sensors.humidity;
//  next();
//});



router.route('/temperature/:id').get(function (req, res, next) { 
req.result = resourceModel.pi.sensors.temperature[req.params.id];
next();
})
.put(function(req, res, next) { 
var selectedTemp = resourceModel.pi.sensors.temperature[req.params.id];
selectedTemp.value = req.body.value;   	//CHANGE THE MODEL;
console.info('route Temperature  Changed T %s value to %s', req.params.id, selectedTemp.value);
req.result = "TEMP " + req.params.id + "= " + selectedLed.value;
//emitLedInfo(selectedLed.value); 		//EMIT STATE CHANGE EVENT;
next();
});












module.exports = router;