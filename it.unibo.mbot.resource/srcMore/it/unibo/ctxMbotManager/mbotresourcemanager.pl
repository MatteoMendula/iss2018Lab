%====================================================================================
% Context ctxMbotManager  SYSTEM-configuration: file it.unibo.ctxMbotManager.mbotResourceManager.pl 
%====================================================================================
context(ctxmbotmanager, "localhost",  "TCP", "8076" ).  		 
%%% -------------------------------------------
qactor( mbotmanager , ctxmbotmanager, "it.unibo.mbotmanager.MsgHandle_Mbotmanager"   ). %%store msgs 
qactor( mbotmanager_ctrl , ctxmbotmanager, "it.unibo.mbotmanager.Mbotmanager"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

