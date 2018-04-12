%====================================================================================
% Context ctxMbotExecutor  SYSTEM-configuration: file it.unibo.ctxMbotExecutor.mbotExecutor.pl 
%====================================================================================
pubsubserveraddr("").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxmbotexecutor, "192.168.43.67",  "TCP", "8029" ).  		 
context(ctxradarbase, "192.168.43.229",  "TCP", "8033" ).  		 
%%% -------------------------------------------
qactor( usercmdmanager , ctxmbotexecutor, "it.unibo.usercmdmanager.MsgHandle_Usercmdmanager"   ). %%store msgs 
qactor( usercmdmanager_ctrl , ctxmbotexecutor, "it.unibo.usercmdmanager.Usercmdmanager"   ). %%control-driven 
qactor( rover , ctxmbotexecutor, "it.unibo.rover.MsgHandle_Rover"   ). %%store msgs 
qactor( rover_ctrl , ctxmbotexecutor, "it.unibo.rover.Rover"   ). %%control-driven 
qactor( sonardetector , ctxmbotexecutor, "it.unibo.sonardetector.MsgHandle_Sonardetector"   ). %%store msgs 
qactor( sonardetector_ctrl , ctxmbotexecutor, "it.unibo.sonardetector.Sonardetector"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

