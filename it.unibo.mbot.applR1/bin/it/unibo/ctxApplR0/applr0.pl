%====================================================================================
% Context ctxApplR0  SYSTEM-configuration: file it.unibo.ctxApplR0.applR0.pl 
%====================================================================================
pubsubserveraddr("tcp://localhost:1883").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxapplr0, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( applr0agent , ctxapplr0, "it.unibo.applr0agent.MsgHandle_Applr0agent"   ). %%store msgs 
qactor( applr0agent_ctrl , ctxapplr0, "it.unibo.applr0agent.Applr0agent"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh,ctxapplr0,"it.unibo.ctxApplR0.Evh","alarmev").  
eventhandler(evhtask,ctxapplr0,"it.unibo.ctxApplR0.Evhtask","taskexec").  
%%% -------------------------------------------

