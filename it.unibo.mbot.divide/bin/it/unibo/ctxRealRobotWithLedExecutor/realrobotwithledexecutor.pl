%====================================================================================
% Context ctxRealRobotWithLedExecutor  SYSTEM-configuration: file it.unibo.ctxRealRobotWithLedExecutor.realRobotWithLedExecutor.pl 
%====================================================================================
context(ctxrealrobotwithledexecutor, "192.168.43.68",  "TCP", "8019" ).  		 
context(ctxradarbase, "192.168.43.229",  "TCP", "8033" ).  		 
%%% -------------------------------------------
qactor( mbotled , ctxrealrobotwithledexecutor, "it.unibo.mbotled.MsgHandle_Mbotled"   ). %%store msgs 
qactor( mbotled_ctrl , ctxrealrobotwithledexecutor, "it.unibo.mbotled.Mbotled"   ). %%control-driven 
qactor( sonardetector , ctxrealrobotwithledexecutor, "it.unibo.sonardetector.MsgHandle_Sonardetector"   ). %%store msgs 
qactor( sonardetector_ctrl , ctxrealrobotwithledexecutor, "it.unibo.sonardetector.Sonardetector"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh,ctxrealrobotwithledexecutor,"it.unibo.ctxRealRobotWithLedExecutor.Evh","usercmd,mindcmd").  
%%% -------------------------------------------

