/*
===========================================================
 project it.unibo.buttonLedSystem.arduino
 Led13/Led13.ino ARDUINO UNO
 Pin 13 has a internal LED connected on  ARDUINO UNO. 
===========================================================
 */
int ledPin   = 13; 
int count    = 1;
boolean on   = false;

void setup(){  
  Serial.begin(9600);  
  Serial.println( "--------------------------------------"  );
  Serial.println( "project it.unibo.buttonLedSystem.arduino"  );
  Serial.println( "Led13/Led13.ino"  );
  Serial.println( "--------------------------------------"  );
  configure();
}
void configure(){
  pinMode( ledPin, OUTPUT );
  turnOff();  
}
/* -----------------------------
LED primitives
-------------------------------- */
void turnOn(){
  digitalWrite(ledPin, HIGH);
  on = true;
}
void turnOff(){
  digitalWrite(ledPin, LOW);
  on = false;
}
/* -----------------------------
Blinck the led (non primtive)
-------------------------------- */
void blinkTheLed(){
    turnOn(); 
    delay(500);
    turnOff();  
    delay(500);
}

void loop(){  
  if( count <= 10 ){
    Serial.println("Blinking the led on 13 count=" + String(count)  );
    blinkTheLed();
    count++;
  }
}
