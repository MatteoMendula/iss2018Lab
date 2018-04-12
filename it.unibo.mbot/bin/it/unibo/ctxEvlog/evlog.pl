%====================================================================================
% Context ctxEvlog  SYSTEM-configuration: file it.unibo.ctxEvlog.evlog.pl 
%====================================================================================
pubsubserveraddr("").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxevlog, "localhost",  "TCP", "8179" ).  		 
context(ctxmbotexecutor, "localhost",  "TCP", "8029" ).  		 
%%% -------------------------------------------
qactor( evlogagent , ctxevlog, "it.unibo.evlogagent.MsgHandle_Evlogagent"   ). %%store msgs 
qactor( evlogagent_ctrl , ctxevlog, "it.unibo.evlogagent.Evlogagent"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

