/*
 * appServer/routes/robot.js
 */
var express     = require('express'),
  router        = express.Router(),
  resourceModel = require('../models/robot');

router.route('/').get(function (req, res, next) {
	  req.result = resourceModel.robot;
	  next();
});

router.route('/state').get(function (req, res, next) {
	req.result = resourceModel.robot.state;
  next();
})
.put(function(req, res, next) { 
resourceModel.robot.state = req.body.value;	//CHANGE THE MODEL;
console.info('route Robot  Changed STATE value to %s', resourceModel.robot.state);
req.result = "STATE =" + resourceModel.robot.state;
next();
});



module.exports = router;