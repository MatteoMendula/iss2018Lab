/*
* =====================================
* blsObjModelBased/LedModel.js
* =====================================
*/
var evUtil         = require('./ApplEventUtility') ;
var nodevents    = require('events') ;
var EventEmitter = nodevents.EventEmitter;

LedModel = function( name ){
	this.name      = name;
 	this.ledState  = false;
 	this.evId      = "ledChanged";
}

LedModel.prototype = new EventEmitter();

//Methods (shared)
LedModel.prototype.turnOn  = function(){
	this.ledState = true;
	evUtil.emitApplEvent(this.name, this.evId, "turnedOn", this);
}
LedModel.prototype.turnOff = function(){
 	this.ledState = false;
	evUtil.emitApplEvent(this.name, this.evId, "turnedOff", this );
 	}
LedModel.prototype.switchState = function(){
	this.ledState = ! this.ledState;
	evUtil.emitApplEvent(this.name, this.evId, "switched", this );
	}
LedModel.prototype.getState = function(){
	return this.ledState;
}
LedModel.prototype.getName = function(){
	return this.name;
}
LedModel.prototype.getDefaultRep = function(){
	return  this.name+"||"+ this.ledState
}
 
// EXPORTS
if(typeof document == "undefined") 
	module.exports.LedModel = LedModel;
//To work is a browser, do: browserify LedModel.js -o LedBro.js