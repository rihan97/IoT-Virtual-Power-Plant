#include <Adafruit_Sensor.h> //library for the wind sensor
#include <Adafruit_BMP280.h> //library for the barometric sensor
#include <dht.h>             //library for the humidity sensor
#include <Wire.h>            
#include <SPI.h>


///////////////////////////////////////////////////////////////////////////////////

dht DHT;

#define BMP_SCK A5     // Wind
#define BMP_SDI A4     // Barometric
#define DHT11_PIN 9    // Humidity
//int Pin = A0;          // TMP36's Vout
//int sensorPin = A1;    // LDR

//#define BMP_MOSI 11 
//#define BMP_CS 10

//////////////////////////////////////////////////////////////////////////////////////

Adafruit_BMP280 bmp; // I2C
//Adafruit_BMP280 bmp(BMP_CS); // hardware SPI
//Adafruit_BMP280 bmp(BMP_CS, BMP_MOSI, BMP_MISO,  BMP_SCK);

///////////////////////////////////////////////////////////////////////////////////////

//TMP36 Pin Variables
int Pin = A0; //the analog pin the TMP36's Vout  pin is connected to
                        //the resolution is 10 mV / degree centigrade with a
                        //500 mV offset to allow for negative temperatures

/////////////////////////////////////////////////////////////////////////////////////
//LDR
int sensorPin = A1; // LDR
int sensorValue = 0; // variables being stored into the LDR


//////////////////////////////////////////////////////////////////////////////////////////////////////

// Anemometer

//Setup Variables
 
const int sensorPinWind = A2; //Defines the pin that the anemometer output is connected to
int sensorValueWind = 0; //Variable stores the value direct from the analog pin
float sensorVoltage = 0; //Variable that stores the voltage (in Volts) from the anemometer being sent to the analog pin
float windSpeed = 0; // Wind speed in meters per second (m/s)
 
float voltageConversionConstant = .004882814; //This constant maps the value provided from the analog read function, which ranges from 0 to 1023, to actual voltage, which ranges from 0V to 5V
//int sensorDelay = 1000; //Delay between sensor readings, measured in milliseconds (ms)
 
//Anemometer Technical Variables
 
float voltageMin = 0.4; // Mininum output voltage from anemometer in mV.
float windSpeedMin = 0; // Wind speed in meters/sec corresponding to minimum voltage
 
float voltageMax = 2.0; // Maximum output voltage from anemometer in mV.
float windSpeedMax = 32; // Wind speed in meters/sec corresponding to maximum voltage
 
  
/////////////////////////////////////////////////////////////////////////////////////////

void setup()
{
  Serial.begin(9600); 



if (!bmp.begin()) {  
    Serial.println(F("Could not find a valid BMP280 sensor, check wiring!"));
    while (1);
  }
}
 

void loop()                     
{

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//LDR

sensorValue=analogRead(sensorPin);// LDR Action to take
 //Serial.println(sensorValue);
  if (sensorValue >1000)  { //if the LDRLDRValue is greater than 400 then it prints 1 to indicate light //ORIGINALLY WAS WORKING AT 400 PERFECTLY//
  //370
   Serial.print("1");
   Serial.print(","); 
   }
   else {// prints 0 to say there is No light
   Serial.print("0");
   Serial.print(",");
   }
 /////////////////////////////////////////////////////////////////////////////////// 
 

 ///////////////////////////////////////////////////////////////////////////////////////// 
// getting the voltage reading from the temperature sensor
 int reading = analogRead(Pin);  
 
  //converting  reading to voltage, for 3.3v arduino use 3.3
 float voltage = reading * 5.0;
 voltage /= 1024.0; 
 
 
  //print out the temperature
 float temperatureC = (voltage - 0.5) * 100 ;  //converting from 10 mv per degree with 500 mV offset
                                               //to degrees ((voltage - 500mV) times 100)
 Serial.print(temperatureC); 
 Serial.print(",");

 // Option to display conversion to Fahrenheit
 //float temperatureF = (temperatureC * 9.0 / 5.0) + 32.0;
 //Serial.print(temperatureF); Serial.println(" degrees F");

 ///////////////////////////////////////////humidity///////////////////////////////////////////////////
    int chk = DHT.read11(DHT11_PIN);
    Serial.print(DHT.humidity);
    Serial.print(",");
////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////Anemometer////////////////////////////////////////////////////////////

sensorValueWind = analogRead(sensorPinWind); //Get a value between 0 and 1023 from the analog pin connected to the anemometer
 
sensorVoltage = sensorValueWind * voltageConversionConstant; //Convert sensor value to actual voltage
 
//Convert voltage value to wind speed using range of max and min voltages and wind speed for the anemometer
if (sensorVoltage <= voltageMin){
  
 windSpeed = 0; //Check if voltage is below minimum value. If so, set wind speed to zero.
}else {
  windSpeed = ((sensorVoltage - voltageMin)*windSpeedMax)/(voltageMax - voltageMin); //For voltages above minimum value, use the linear relationship to calculate wind speed.
}
 
 //Print voltage and windspeed to serial
  Serial.print(windSpeed); 
  Serial.print(",");
////////////////////////////////////////////////////////////////////////////////////////////////

 ///////////////////////////////////bmp//////////////////////////////////////////////////////
    Serial.print(bmp.readPressure());
 ///////////////////////////////////////////////////////////////////////////////////  

Serial.println();



 delay(2000); 
 //Serial.println();

}
   
