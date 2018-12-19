/*
 * nodeCode/robot/utils/channel.js
 */
const events     = require('events');
const model      = require('./../models/robot.json');
const io         = require('socket.io');
const channel    = new events.EventEmitter();

channel.setIoSocket = function( iosock ){
	console.log("\t CHANNEL setIoSocket=" + iosock );
	this.io = iosock;
}

channel.on('sonarEvent', function(data) {	 
 	console.log("\t CHANNEL updates the model and sends:" + data ); 
	model.robot.devices.resources.sonarRobot.value=data;
	this.io.sockets.send( data );
});

channel.on('robotState', function(data) {  //emitted by robotControl
	console.log("\t CHANNEL receives: " + data  + " ... and updates the model");
 	model.robot.properties.resources.state=data;  //shown in the page by app render
});



//channel.emit('sonarEvent', 32.5);

module.exports=channel;