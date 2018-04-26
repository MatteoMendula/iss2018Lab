
/*
 * =====================================
 * gpioOnOffbutton.js

 * See https://github.com/fivdi/onoff
 * =====================================
*/
var onoff = require( 'onoff' );
var buttonPin = 24;
var ledPin    = 25;
var Gpio      = onoff.Gpio,

button = new Gpio(buttonPin, 'in', 'both');
led    = new Gpio(ledPin, 'out'),

/*
Watch for hardware interrupts on the GPIO. 
The edge argument that was passed to the constructor 
determines which hardware interrupts to watch for.
*/
button.watch(function (err, level) {
  if (err) {
    throw err;
  }
  console.log('Interrupt level =' + level);
  led.writeSync(level);
});

/*
 * Read GPIO value asynchronously.
 */
button.read( function(value){
   console.log('read value= ' + value);
});

/*
 * Look at the Button every second for 10 seconds 
 */
for( i=1; i<=10; i++){
    setTimeout( function(){console.log("button=" + button.readSync() );}, 1000*i);
}

/*
 * Listen to the event triggered on CTRL+C
 */
process.on('SIGINT', function () {  
	  led.writeSync(0);  
	  led.unexport();
	  button.unexport();
	  console.log('Bye, bye!');
	  process.exit();
	});
console.log('Waiting for clicks on pin=' + buttonPin);