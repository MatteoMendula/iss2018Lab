%====================================================================================
% Context ctxMbotAgent  SYSTEM-configuration: file it.unibo.ctxMbotAgent.mbotAgent.pl 
%====================================================================================
context(ctxmbotagent, "localhost",  "TCP", "8039" ).  		 
context(ctxradarbase, "localhost",  "TCP", "8033" ).  		 
context(ctxmbotexecutor, "localhost",  "TCP", "8029" ).  		 
%%% -------------------------------------------
qactor( roveragent , ctxmbotagent, "it.unibo.roveragent.MsgHandle_Roveragent"   ). %%store msgs 
qactor( roveragent_ctrl , ctxmbotagent, "it.unibo.roveragent.Roveragent"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

