/*
 * --------------------------------------
 * nodeServer/robotServer/routes/app.js
 * --------------------------------------
 */
var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Rover' });
});


/*
ADDED FOR ROBOT FRONT-END
*/

var ctrlMove = require("../controllers/ctrlRobotMove");

/*
router.put('/rover/w', function(req, res, next) {
  //curl -X PUT  -d "" http://localhost:3000/rover/w
  res.end("done forward");
});
*/

router.put('/rover/w', ctrlMove.moveRobotForward );
router.put('/rover/a', ctrlMove.moveRobotLeft );
router.put('/rover/d', ctrlMove.moveRobotRight );
router.put('/rover/s', ctrlMove.moveRobotBackward );
router.put('/rover/h', ctrlMove.moveRobotStop );


module.exports = router;
