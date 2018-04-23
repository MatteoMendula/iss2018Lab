%====================================================================================
% Context ctxMbotExecutor  SYSTEM-configuration: file it.unibo.ctxMbotExecutor.mbotExecutor.pl 
%====================================================================================
context(ctxmbotexecutor, "192.168.43.68",  "TCP", "8029" ).  		 
%%% -------------------------------------------
qactor( mbot , ctxmbotexecutor, "it.unibo.mbot.MsgHandle_Mbot"   ). %%store msgs 
qactor( mbot_ctrl , ctxmbotexecutor, "it.unibo.mbot.Mbot"   ). %%control-driven 
qactor( realsonardetector , ctxmbotexecutor, "it.unibo.realsonardetector.MsgHandle_Realsonardetector"   ). %%store msgs 
qactor( realsonardetector_ctrl , ctxmbotexecutor, "it.unibo.realsonardetector.Realsonardetector"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh,ctxmbotexecutor,"it.unibo.ctxMbotExecutor.Evh","usercmd,mindcmd").  
%%% -------------------------------------------

