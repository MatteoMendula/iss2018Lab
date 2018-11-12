/*
* =====================================
* blsObjModelBased/ApplEventUtility.js
* =====================================
*/
emitApplEvent = function(source, evId, payload, emitter){
	console.log( "	" + source + "	 emits the event  "  +  evId  + ":" + payload);
	emitter.emit( evId, payload ) ;
}

if(typeof document == "undefined") {
	module.exports.emitApplEvent=emitApplEvent;
}