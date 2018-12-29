/*
 * nodeCode/robotFrontEnd/utils/channel.js
 */
const events     = require('events');
const model      = require('./../models/robot.json');
const io         = require('socket.io');
const channel    = new events.EventEmitter();
const mqttUtils  = null;

channel.setIoSocket = function( iosock ){
	console.log("\t CHANNEL setIoSocket=" + iosock );
	this.io = iosock;
}
channel.on('sonar', function(data) {	//emitted by clientRobotVirtual or mqtt support; 
 	console.log("\t CHANNEL sonar updates the model and the page with:" + data ); 
	model.links.robotenv.envdevices.resources.sonar2.value=data;
	this.io.sockets.send( data );
});
channel.on('sonarDetect', function(data) {	//emitted by clientRobotVirtual or mqtt support; 
 	console.log("\t CHANNEL sonarDetect updates the model and the page with:" + data ); 
	model.links.robot.resources.robotdevices.resources.sonarRobot.value=data;
	this.io.sockets.send( data );
});
channel.on('robotState', function(data) {  //emitted by robotControl or applRobotControl;
	//console.log("\t CHANNEL receives: " + data  );
 	model.links.robot.resources.robotstate=data;  //shown in the page by app renderMainPage
 	//not propagated via io.sockets since a robot state can change only after a user command (??)
 	//CLEAR THE sonar
	model.links.robotenv.envdevices.resources.sonar2.value="";
	this.io.sockets.send( data );
	model.links.robot.envdevices.resources.sonarRobot.value="";
	this.io.sockets.send( data );
});
channel.on('publishcmd', function(data) {  //emitted by robotControl;
	console.log("\t CHANNEL publishcmd: " + data + " on topic unibo/qasys" + " mqttUtils=" + mqttUtils);
//	if( mqttUtils == null )  mqttUtils  = require('./../utils/mqttUtils');		//CIRCULAR!!!
	publish( data );	//topic  = "unibo/qasys";
});

module.exports=channel;

/*
 * ------------------------------------------------
 * MQTT support
 * ------------------------------------------------
 */
const systemConfig = require("./../../systemConfig");
const mqtt    = require ('mqtt');	 
const topic   = "unibo/qasys";
var client    = mqtt.connect(systemConfig.mqttbroker);

client.on('connect', function () {
	  client.subscribe( topic );
	  console.log('\t MQTT client has subscribed successfully ');
});

//The message usually arrives as buffer, so we convert it to string data type;
client.on('message', function (topic, message){
	var msg = message.toString();
	console.log("\t MQTT RECEIVES:"+ msg); //if toString is not given, the message comes as buffer;
	if( msg.indexOf( "sonarDetect" ) > 0 ){
		channel.emit("sonarDetect",  msg  );  //to allow update of the WebPage
	}else if( msg.indexOf( "sonar" ) > 0   ){
		channel.emit("sonar",  msg  );  //to allow update of the WebPage
	}
});

publish = function( msg ){
	//console.log('\t MQTT  publish ' + client);
	client.publish(topic, msg);
}