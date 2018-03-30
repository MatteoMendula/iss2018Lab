%====================================================================================
% Context ctxRoverExecutor  SYSTEM-configuration: file it.unibo.ctxRoverExecutor.roverExecutor.pl 
%====================================================================================
pubsubserveraddr("tcp://192.168.43.229:1883").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxroverexecutor, "192.168.43.229",  "TCP", "8032" ).  		 
%%% -------------------------------------------
qactor( usercmdmanager , ctxroverexecutor, "it.unibo.usercmdmanager.MsgHandle_Usercmdmanager"   ). %%store msgs 
qactor( usercmdmanager_ctrl , ctxroverexecutor, "it.unibo.usercmdmanager.Usercmdmanager"   ). %%control-driven 
qactor( rover , ctxroverexecutor, "it.unibo.rover.MsgHandle_Rover"   ). %%store msgs 
qactor( rover_ctrl , ctxroverexecutor, "it.unibo.rover.Rover"   ). %%control-driven 
qactor( sonardetector , ctxroverexecutor, "it.unibo.sonardetector.MsgHandle_Sonardetector"   ). %%store msgs 
qactor( sonardetector_ctrl , ctxroverexecutor, "it.unibo.sonardetector.Sonardetector"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

