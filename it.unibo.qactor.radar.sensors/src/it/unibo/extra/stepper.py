#!/usr/bin/python           	# This is stepper.py file
import RPi.GPIO as GPIO
import time
import sysKb				 
import socket                

stopped = False

# =====================================================
# STEPPER MOTOR
# =====================================================
GPIO.setmode(GPIO.BCM)

enable_pin = 1
coil_A_1_pin = 27	# PIN 13
coil_A_2_pin = 2	# PIN 3
coil_B_1_pin = 22	# PIN 15
coil_B_2_pin = 3	# PIN 5
		

GPIO.setup(enable_pin, GPIO.OUT)
GPIO.setup(coil_A_1_pin, GPIO.OUT)
GPIO.setup(coil_A_2_pin, GPIO.OUT)
GPIO.setup(coil_B_1_pin, GPIO.OUT)
GPIO.setup(coil_B_2_pin, GPIO.OUT)


GPIO.output(enable_pin, 1)


def setStep(w1, w2, w3, w4):
	GPIO.output(coil_A_1_pin, w1)
	GPIO.output(coil_A_2_pin, w2)
	GPIO.output(coil_B_1_pin, w3)
	GPIO.output(coil_B_2_pin, w4)

# =====================================================

# =====================================================
# TCP CONNECTION
# =====================================================
connected = False
sock = socket.socket()		# Create a socket object
def doConnect(host,port) :
	global connected, sock
	print 'client connecting on ' + str( port ) + " " + str( sock )
	sock.connect( (host, port) )   
	connected = True

def doSend(msg) :
	global connected, sock
	if connected :
		sock.sendall( msg )
def doReceive() :
	global connected, sock
	if connected :
		return sock.recv(1024)
	else :
		return "none"

# =====================================================
# MOVES
# =====================================================
 
def moveforward(delay, steps, emit):
	global stopped
	print '		moveforward.'
	for i in range(0, steps):
		setStep(1, 0, 1, 0)
		time.sleep(delay)
		setStep(0, 1, 1, 0)
		time.sleep(delay)
		setStep(0, 1, 0, 1)
		time.sleep(delay)
		setStep(1, 0, 0, 1)
		time.sleep(delay)
		if emit : sendUpdateRequest( i, True )
 		if stopped : return
		
def movebackward(delay, steps, emit):
	global stopped
	print '		movebackward.'
	for i in range(0, steps):
		setStep(1, 0, 0, 1)
 		time.sleep(delay)
		setStep(0, 1, 0, 1)
 		time.sleep(delay)
		setStep(0, 1, 1, 0)
 		time.sleep(delay)
		setStep(1, 0, 1, 0)
 		time.sleep(delay)
 		if emit : sendUpdateRequest( i, False )
 		if stopped : return

def sendUpdateRequest(  n , forward) :
	if forward : arg = int( sysKb.SysKb.delta * n )
	else : arg = int( sysKb.SysKb.maxAngle - int( sysKb.SysKb.delta * n ) )
	#vs = "p( step=" + str(n) +  ", angle=" + str(arg) + "," + str(forward) + ")\n"	
	vs = "msg(pos,dispatch,python,qastepper,p("+str(arg)  + ")," + str(n) + ")\n"		 
 	print  vs 
 	doSend( vs )
 	  
  			
def doJobstepper(delay, steps) :
	if not stopped : 
 		movebackward( delay, steps, False )	
 	while not stopped :
		moveforward(delay, steps * 2, True)
		if  stopped : 
			break
		movebackward(delay, steps * 2, True)
	if not stopped : 
		moveforward(delay, steps, False)
	GPIO.cleanup()


####################################################
# Main
####################################################
print '		stepper.py START'
try :
	doConnect( sysKb.SysKb.host, sysKb.SysKb.port)
	print '		Connection with local Java server done.'	
except:
	print '		Connection not possible.'
print '		doJobstepper STEPS=' +  str( sysKb.SysKb.steps 	)
doJobstepper( sysKb.SysKb.delayValue, int( sysKb.SysKb.steps ) )	
print '		stepper.py ENDS'
