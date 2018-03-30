%====================================================================================
% Context ctxMbotExecutor  SYSTEM-configuration: file it.unibo.ctxMbotExecutor.mbotExecutor.pl 
%====================================================================================
pubsubserveraddr("tcp://192.168.43.229:1883").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxmbotexecutor, "192.168.43.67",  "TCP", "8029" ).  		 
%%% -------------------------------------------
qactor( mbotusercmdmanager , ctxmbotexecutor, "it.unibo.mbotusercmdmanager.MsgHandle_Mbotusercmdmanager"   ). %%store msgs 
qactor( mbotusercmdmanager_ctrl , ctxmbotexecutor, "it.unibo.mbotusercmdmanager.Mbotusercmdmanager"   ). %%control-driven 
qactor( mbot , ctxmbotexecutor, "it.unibo.mbot.MsgHandle_Mbot"   ). %%store msgs 
qactor( mbot_ctrl , ctxmbotexecutor, "it.unibo.mbot.Mbot"   ). %%control-driven 
qactor( realsonardetector , ctxmbotexecutor, "it.unibo.realsonardetector.MsgHandle_Realsonardetector"   ). %%store msgs 
qactor( realsonardetector_ctrl , ctxmbotexecutor, "it.unibo.realsonardetector.Realsonardetector"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

