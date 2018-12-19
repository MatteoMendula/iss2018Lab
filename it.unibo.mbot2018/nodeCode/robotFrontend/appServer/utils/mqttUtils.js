/*
* =====================================
* uniboSupports/mqttUtils.js
* =====================================
*/
const mqtt      = require ('mqtt');	//npm install --save mqtt
const topic     = "unibo/qasys";
const echannel  =  require("./channel");

//var client   = mqtt.connect('mqtt://iot.eclipse.org');
//var client   = mqtt.connect('mqtt://192.168.1.100');
 var client    = mqtt.connect('mqtt://localhost');

//console.log("\t MQTT echannel= " + echannel );

client.on('connect', function () {
	  client.subscribe( topic );
	  console.log('\t MQTT client has subscribed successfully ');
});

//The message usually arrives as buffer, so I had to convert it to string data type;
client.on('message', function (topic, message){
	var msg = message.toString();
	console.log("\t MQTT RECEIVES:"+ msg); //if toString is not given, the message comes as buffer
	if( msg.indexOf( "sonarEvent" ) > 0 || msg.indexOf( "sonarDetect" ) > 0 )
		echannel.emit("sonarEvent",  "remoteApp : todo"  );  //to allow update of the WebPage
});

exports.publish = function( msg ){
	console.log('mqtt publish ' + client);
	client.publish(topic, msg);
}



