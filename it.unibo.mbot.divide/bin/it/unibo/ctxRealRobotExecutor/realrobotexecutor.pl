%====================================================================================
% Context ctxRealRobotExecutor  SYSTEM-configuration: file it.unibo.ctxRealRobotExecutor.realRobotExecutor.pl 
%====================================================================================
context(ctxrealrobotexecutor, "192.168.43.68",  "TCP", "8019" ).  		 
context(ctxradarbase, "192.168.43.229",  "TCP", "8033" ).  		 
%%% -------------------------------------------
qactor( mbot , ctxrealrobotexecutor, "it.unibo.mbot.MsgHandle_Mbot"   ). %%store msgs 
qactor( mbot_ctrl , ctxrealrobotexecutor, "it.unibo.mbot.Mbot"   ). %%control-driven 
qactor( sonardetector , ctxrealrobotexecutor, "it.unibo.sonardetector.MsgHandle_Sonardetector"   ). %%store msgs 
qactor( sonardetector_ctrl , ctxrealrobotexecutor, "it.unibo.sonardetector.Sonardetector"   ). %%control-driven 
qactor( polarlogagent , ctxrealrobotexecutor, "it.unibo.polarlogagent.MsgHandle_Polarlogagent"   ). %%store msgs 
qactor( polarlogagent_ctrl , ctxrealrobotexecutor, "it.unibo.polarlogagent.Polarlogagent"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh,ctxrealrobotexecutor,"it.unibo.ctxRealRobotExecutor.Evh","usercmd,mindcmd").  
%%% -------------------------------------------

