%====================================================================================
% Context ctxEvlog  SYSTEM-configuration: file it.unibo.ctxEvlog.evlog.pl 
%====================================================================================
pubsubserveraddr("tcp://localhost:1883").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxevlog, "localhost",  "TCP", "8179" ).  		 
%%% -------------------------------------------
qactor( evlogagent , ctxevlog, "it.unibo.evlogagent.MsgHandle_Evlogagent"   ). %%store msgs 
qactor( evlogagent_ctrl , ctxevlog, "it.unibo.evlogagent.Evlogagent"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

