%====================================================================================
% Context ctxRealRobotExecutor  SYSTEM-configuration: file it.unibo.ctxRealRobotExecutor.realRobotExecutor.pl 
%====================================================================================
pubsubserveraddr("").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxrealrobotexecutor, "192.168.43.67",  "TCP", "8029" ).  		 
context(ctxradarbase, "192.168.43.229",  "TCP", "8033" ).  		 
%%% -------------------------------------------
qactor( mbot , ctxrealrobotexecutor, "it.unibo.mbot.MsgHandle_Mbot"   ). %%store msgs 
qactor( mbot_ctrl , ctxrealrobotexecutor, "it.unibo.mbot.Mbot"   ). %%control-driven 
qactor( sonardetector , ctxrealrobotexecutor, "it.unibo.sonardetector.MsgHandle_Sonardetector"   ). %%store msgs 
qactor( sonardetector_ctrl , ctxrealrobotexecutor, "it.unibo.sonardetector.Sonardetector"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh,ctxrealrobotexecutor,"it.unibo.ctxRealRobotExecutor.Evh","usercmd,mindcmd").  
%%% -------------------------------------------

