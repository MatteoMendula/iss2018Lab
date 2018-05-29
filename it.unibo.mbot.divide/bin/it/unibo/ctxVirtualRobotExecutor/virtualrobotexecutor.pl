%====================================================================================
% Context ctxVirtualRobotExecutor  SYSTEM-configuration: file it.unibo.ctxVirtualRobotExecutor.virtualRobotExecutor.pl 
%====================================================================================
context(ctxvirtualrobotexecutor, "localhost",  "TCP", "8029" ).  		 
%%% -------------------------------------------
qactor( rover , ctxvirtualrobotexecutor, "it.unibo.rover.MsgHandle_Rover"   ). %%store msgs 
qactor( rover_ctrl , ctxvirtualrobotexecutor, "it.unibo.rover.Rover"   ). %%control-driven 
qactor( sonardetector , ctxvirtualrobotexecutor, "it.unibo.sonardetector.MsgHandle_Sonardetector"   ). %%store msgs 
qactor( sonardetector_ctrl , ctxvirtualrobotexecutor, "it.unibo.sonardetector.Sonardetector"   ). %%control-driven 
qactor( polarlogagent , ctxvirtualrobotexecutor, "it.unibo.polarlogagent.MsgHandle_Polarlogagent"   ). %%store msgs 
qactor( polarlogagent_ctrl , ctxvirtualrobotexecutor, "it.unibo.polarlogagent.Polarlogagent"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh,ctxvirtualrobotexecutor,"it.unibo.ctxVirtualRobotExecutor.Evh","usercmd,mindcmd").  
%%% -------------------------------------------

