/*
 * =====================================
 * blsObjModelBased/MainLedModelUsage.js
 * =====================================
*/

const ledModel    = require("./LedModel");
const l1          = new ledModel.LedModel("l1");

handler1 = function( evMsg ){
	console.log("	handler1 for event msg=" + evMsg + " when led=" + l1.getState());
	console.log(  l1.getDefaultRep() );
}

var count = 0;
handler2 = function( evMsg ){
	console.log("	handler2 for event msg=" + evMsg + " count=" + count++ );
//	setTimeout( l1.turnOn.bind(l1),  count*300 );
}


function mainLedModelUsage(){
 	l1.on( l1.evId, handler1 );
 	l1.on( l1.evId, handler2 );
  	l1.turnOn();
  	l1.turnOff();
//  setTimeout( l1.turnOn.bind(l1),  500 );
// 	setTimeout( l1.turnOff.bind(l1), 1000 );
  	console.log("BYE");
  }

if( process.argv[1].toString().includes("MainLedModelUsage") ) 
	mainLedModelUsage();