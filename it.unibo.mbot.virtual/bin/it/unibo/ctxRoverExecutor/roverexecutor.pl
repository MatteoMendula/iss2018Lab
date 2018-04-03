%====================================================================================
% Context ctxRoverExecutor  SYSTEM-configuration: file it.unibo.ctxRoverExecutor.roverExecutor.pl 
%====================================================================================
pubsubserveraddr("tcp://192.168.43.229:1883").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxroverexecutor, "localhost",  "TCP", "8032" ).  		 
%%% -------------------------------------------
qactor( rover , ctxroverexecutor, "it.unibo.rover.MsgHandle_Rover"   ). %%store msgs 
qactor( rover_ctrl , ctxroverexecutor, "it.unibo.rover.Rover"   ). %%control-driven 
qactor( sonardetector , ctxroverexecutor, "it.unibo.sonardetector.MsgHandle_Sonardetector"   ). %%store msgs 
qactor( sonardetector_ctrl , ctxroverexecutor, "it.unibo.sonardetector.Sonardetector"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh,ctxroverexecutor,"it.unibo.ctxRoverExecutor.Evh","usercmd").  
%%% -------------------------------------------

