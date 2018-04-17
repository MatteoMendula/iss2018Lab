/*
=======================================
 buttonPolling.ino
 project it.unibo.arduino.intro
 Input from Pin 3 (Button)
 ARDUINO UNO
 =======================================
 Arduino has internal PULLUP resistors to tie the input high.  
 Hook one end of the switch to the input (5V) and the other end to ground: 
 when we PUSH the switch the input goes LOW. 
 */
 
int pinButton = 3;   
int pinLed    = 13;  //internal led

void initLed(){
   pinMode(pinLed, OUTPUT);  
}
void initButton(){
  pinMode(pinButton, INPUT); 
  digitalWrite(pinButton, HIGH);  //set PULLUP : PUSH=>0
}

void setup(){  
  Serial.begin(9600);
  Serial.println( "--------------------------------------"  );
  Serial.println( "project it.unibo.arduino.intro"  );
  Serial.println( "buttonPolling/buttonPolling.ino"  );
  Serial.println( "--------------------------------------"  );
  initButton();
  initLed();
}

/*
 * When the button is PUSHED, the led is ON
 */
void loop(){
  int v = digitalRead( pinButton );
  Serial.println( "write " + v );
  digitalWrite( pinLed,  v );
  delay(100);
}
