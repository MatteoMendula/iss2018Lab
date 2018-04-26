/*
 * =====================================
 * ledGpio.js
 * /etc/resolv.conf nameserver 8.8.8.8
 * sudo npm install -g onoff
 * npm install onoff --save
 * sudo npm install -g node-gyp
 * 
 * sudo npm install node-wiringpi
 * https://github.com/WiringPi/WiringPi-Node/blob/master/DOCUMENTATION.md
 * 
 * node ledGpio.js
 * https://hackerstribe.com/2015/node-js-interfacciarsi-ai-pin-gpio-del-raspberry-pi/
 * =====================================
 */

var onoff = require('onoff'); //#A

var Gpio = onoff.Gpio,
  led = new Gpio(25, 'out'), //#B
  interval;

interval = setInterval(function () { //#C
  var value = (led.readSync() + 1) % 2; //#D
  led.write(value, function() { //#E
    console.log("Changed LED state to: " + value);
  });
}, 500);

process.on('SIGINT', function () { //#F
  clearInterval(interval);
  led.writeSync(0); //#G
  led.unexport();
  console.log('Bye, bye!');
  process.exit();
});

// #A Import the onoff library
// #B Initialize pin 25 to be an output pin
// #C This interval will be called every 2 seconds
// #D Synchronously read the value of pin 25 and transform 1 to 0 or 0 to 1
// #E Asynchronously write the new value to pin 25
// #F Listen to the event triggered on CTRL+C
// #G Cleanly close the GPIO pin before exiting
