#!/usr/bin/python

import sys
import mosquitto
import serial

ser = serial.Serial('/dev/ttyS0', 38400, timeout=1)

def on_connect(mqttc, obj, rc):
    print("rc: "+str(rc))

def on_message(mqttc, obj, msg):
    print(msg)
    if msg.topic == '/api/led1':
        ser.write(chr(32 + int(msg.payload)))
    elif msg.topic == '/api/led2':
        ser.write(chr(64 + int(msg.payload)))

def on_publish(mqttc, obj, mid):
    print("mid: "+str(mid))

def on_subscribe(mqttc, obj, mid, granted_qos):
    print("Subscribed: "+str(mid)+" "+str(granted_qos))

def on_log(mqttc, obj, level, string):
    print(string)

    
if __name__ == "__main__":
    
    args = sys.argv
    
    if len(args) != 3:
        sys.exit("Not valid arguments. Please provide ip address of mqqt broker and port number.")
    
    brokerIpAddress = str(args[1])
    brokerPort = int(args[2])
     
    mqttc = mosquitto.Mosquitto() 
    mqttc.on_message = on_message
    mqttc.on_connect = on_connect
    mqttc.on_publish = on_publish
    mqttc.on_subscribe = on_subscribe

    mqttc.connect(brokerIpAddress, brokerPort, 60)

    mqttc.subscribe("/api/+", 0)

    mqttc.loop_forever()
 