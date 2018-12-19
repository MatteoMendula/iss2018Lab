/*
 * appServer/routes/robotApplControlRoute.js
 */
var express     = require('express'),
  router        = express.Router() ;

const robotControl   = require('../controllers/applRobotControl');

router.post("/start", function(req, res, next) { 
	robotControl.actuate("start", req, res ); 
	next();
});
router.post("/halt", function(req, res, next) { 
	robotControl.actuate("halt", req, res ); 
	next(); 
}); 

module.exports = router;