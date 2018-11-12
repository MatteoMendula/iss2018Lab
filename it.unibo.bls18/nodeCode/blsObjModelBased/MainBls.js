/*
 * =====================================
 * blsObjModelBased/MainBls.js
 * =====================================
*/
const ledModel    = require("./LedModel");
const buttonModel = require("./ButtonModel");

const l1 = new ledModel.LedModel("l1");
const b1 = new buttonModel.ButtonModel("b1");

controller = function( evMsg ){
	console.log("	controller for event msg=" + evMsg + " when led=" + l1.getState());
	l1.switchState();
}

var count = 0;
ledObserver = function( evMsg ){
	console.log("	ledObserver for event msg=" + evMsg + " count=" + count++ );
	console.log( l1.getDefaultRep()  );
}

function configure(){
 	b1.on( b1.evId, controller );
 	l1.on( l1.evId, ledObserver );	
} 

function mainBls(){
	configure();
    setInterval( b1.press.bind(b1),  2000 );
  	console.log("CONFIGURATION DONE. NOW I'M WORKING ");
  }

if( process.argv[1].toString().includes("MainBls") ) 
	mainBls();