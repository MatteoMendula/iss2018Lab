%====================================================================================
% Context ctxVirtualRobotExecutor  SYSTEM-configuration: file it.unibo.ctxVirtualRobotExecutor.virtualRobotExecutor.pl 
%====================================================================================
pubsubserveraddr("").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxvirtualrobotexecutor, "192.168.43.229",  "TCP", "8029" ).  		 
context(ctxradarbase, "192.168.43.229",  "TCP", "8033" ).  		 
%%% -------------------------------------------
qactor( rover , ctxvirtualrobotexecutor, "it.unibo.rover.MsgHandle_Rover"   ). %%store msgs 
qactor( rover_ctrl , ctxvirtualrobotexecutor, "it.unibo.rover.Rover"   ). %%control-driven 
qactor( sonardetector , ctxvirtualrobotexecutor, "it.unibo.sonardetector.MsgHandle_Sonardetector"   ). %%store msgs 
qactor( sonardetector_ctrl , ctxvirtualrobotexecutor, "it.unibo.sonardetector.Sonardetector"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh,ctxvirtualrobotexecutor,"it.unibo.ctxVirtualRobotExecutor.Evh","usercmd,mindcmd").  
%%% -------------------------------------------

