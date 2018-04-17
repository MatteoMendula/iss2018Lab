#include <Arduino.h>
#include <Wire.h>
#include <SoftwareSerial.h>


double angle_rad = PI/180.0;
double angle_deg = 180.0/PI;
double input;



void setup(){
    Serial.begin(115200);
    Serial.println("blsmblock START");
    pinMode(13,OUTPUT);
    pinMode(3,INPUT);
    for(int __i__=0;__i__<10;++__i__)
    {
        digitalWrite(13,1);
        _delay(0.5);
        digitalWrite(13,0);
        _delay(0.5);
    }
    
}

void loop(){
    
    input = digitalRead(3);
    Serial.println(String(input));
    if(((input)==(0))){
        digitalWrite(13,1);
    }else{
        digitalWrite(13,0);
    }
    _delay(0.4);
    
    _loop();
}

void _delay(float seconds){
    long endTime = millis() + seconds * 1000;
    while(millis() < endTime)_loop();
}

void _loop(){
    
}

