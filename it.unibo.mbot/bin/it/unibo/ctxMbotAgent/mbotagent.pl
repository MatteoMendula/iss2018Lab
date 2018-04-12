%====================================================================================
% Context ctxMbotAgent  SYSTEM-configuration: file it.unibo.ctxMbotAgent.mbotAgent.pl 
%====================================================================================
pubsubserveraddr("").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxmbotagent, "192.168.43.229",  "TCP", "8039" ).  		 
context(ctxmbotexecutor, "192.168.43.67",  "TCP", "8029" ).  		 
%%% -------------------------------------------
qactor( roveragent , ctxmbotagent, "it.unibo.roveragent.MsgHandle_Roveragent"   ). %%store msgs 
qactor( roveragent_ctrl , ctxmbotagent, "it.unibo.roveragent.Roveragent"   ). %%control-driven 
qactor( evlogagent , ctxmbotagent, "it.unibo.evlogagent.MsgHandle_Evlogagent"   ). %%store msgs 
qactor( evlogagent_ctrl , ctxmbotagent, "it.unibo.evlogagent.Evlogagent"   ). %%control-driven 
qactor( testsystem , ctxmbotagent, "it.unibo.testsystem.MsgHandle_Testsystem"   ). %%store msgs 
qactor( testsystem_ctrl , ctxmbotagent, "it.unibo.testsystem.Testsystem"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

