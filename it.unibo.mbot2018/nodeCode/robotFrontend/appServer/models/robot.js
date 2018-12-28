/*
 * -----------------------------------------------
 * it.unibo.mbot2018/nodeCode/robotFrontend/appServer/models/robot.js
 * -----------------------------------------------
*/
var resources  = require('./robot.json');

exports.actionsLinkUrlStr = resources.links.robot.resources.commands.link;		 //	"/commands";
exports.stateLinkUrlStr   = resources.links.robot.resources.robotstate.link; 		 // "/robotState";
 
exports.getResourceModel = function(){ return resources; }

exports.getRobotState = function(){ return resources.links.robot.resources.robotstate.state; }

exports.extractFields = function(fields, object, target) {
	  if(!target) var target = {};
	  var arrayLength = fields.length;
	  for (var i = 0; i < arrayLength; i++) {
	    var field = fields[i];
	    target[field] = object[field];
	  }
	  return target;
}


//module.exports = resources;

