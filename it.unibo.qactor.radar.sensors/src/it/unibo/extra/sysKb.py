#!/usr/bin/python           	# This is SysKb.py file

class SysKb(object) :
	host = "localhost"
	port = 8111
	delay = 20		# delay between steps in millisecs
	delta = 360.0 / 128.0   # 2,8125
	steps = 80 / delta 		# 512 is a full rotation =   17,7
	maxAngle = steps * delta * 2
	
	delayValue = int(delay) / 1000.0    #0,02
	rgbdelayValue = 0.2
	
	intruderDistance = 20
	limitDistance = 254
	
#print "delta="+str( SysKb.delta )