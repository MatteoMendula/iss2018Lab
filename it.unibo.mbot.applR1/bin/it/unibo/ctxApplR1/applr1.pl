%====================================================================================
% Context ctxApplR1  SYSTEM-configuration: file it.unibo.ctxApplR1.applR1.pl 
%====================================================================================
pubsubserveraddr("tcp://localhost:1883").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxapplr1, "localhost",  "TCP", "8039" ).  		 
%%% -------------------------------------------
qactor( applr1agent , ctxapplr1, "it.unibo.applr1agent.MsgHandle_Applr1agent"   ). %%store msgs 
qactor( applr1agent_ctrl , ctxapplr1, "it.unibo.applr1agent.Applr1agent"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh,ctxapplr1,"it.unibo.ctxApplR1.Evh","alarmev").  
%%% -------------------------------------------

