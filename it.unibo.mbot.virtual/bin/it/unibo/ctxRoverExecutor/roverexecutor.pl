%====================================================================================
% Context ctxRoverExecutor  SYSTEM-configuration: file it.unibo.ctxRoverExecutor.roverExecutor.pl 
%====================================================================================
context(ctxroverexecutor, "localhost",  "TCP", "8032" ).  		 
%%% -------------------------------------------
qactor( mindtobody , ctxroverexecutor, "it.unibo.mindtobody.MsgHandle_Mindtobody"   ). %%store msgs 
qactor( mindtobody_ctrl , ctxroverexecutor, "it.unibo.mindtobody.Mindtobody"   ). %%control-driven 
qactor( rover , ctxroverexecutor, "it.unibo.rover.MsgHandle_Rover"   ). %%store msgs 
qactor( rover_ctrl , ctxroverexecutor, "it.unibo.rover.Rover"   ). %%control-driven 
qactor( sonardetector , ctxroverexecutor, "it.unibo.sonardetector.MsgHandle_Sonardetector"   ). %%store msgs 
qactor( sonardetector_ctrl , ctxroverexecutor, "it.unibo.sonardetector.Sonardetector"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh,ctxroverexecutor,"it.unibo.ctxRoverExecutor.Evh","usercmd,mindcmd").  
%%% -------------------------------------------

