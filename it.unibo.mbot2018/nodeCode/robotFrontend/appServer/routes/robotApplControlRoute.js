/*
 * appServer/routes/robotApplControlRoute.js
 */
var express     = require('express'),
  router        = express.Router() ;

const robotControl   = require('../controllers/applRobotControl');

router.post("/explore", function(req, res, next) { 
	robotControl.actuate("explore", req, res ); 
	next();
});
router.post("/halt", function(req, res, next) { 
	robotControl.actuate("halt", req, res ); 
	next(); 
}); 

module.exports = router;