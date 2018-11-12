/*
* =====================================
* blsObjModelBased/ButtonModel.js
* =====================================
*/
var evUtil         = require('./ApplEventUtility') ;
var nodevents    = require('events') ;
var EventEmitter = nodevents.EventEmitter;

ButtonModel = function( name ){
	this.name         = name;
 	this.buttonState  = false;
 	this.evId         = "buttonChanged";
}

ButtonModel.prototype = new EventEmitter();

ButtonModel.prototype.press = function(){
	evUtil.emitApplEvent(this.name, this.evId, "pressed", this);
}

//EXPORTS
if(typeof document == "undefined") 
	module.exports.ButtonModel = ButtonModel;
//To work is a browser, do: browserify ButtonModel.js -o ButtonModelBro.js