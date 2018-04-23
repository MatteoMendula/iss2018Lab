%====================================================================================
% Context ctxEvlog  SYSTEM-configuration: file it.unibo.ctxEvlog.evlog.pl 
%====================================================================================
context(ctxevlog, "localhost",  "TCP", "8179" ).  		 
%%% -------------------------------------------
qactor( evlogagent , ctxevlog, "it.unibo.evlogagent.MsgHandle_Evlogagent"   ). %%store msgs 
qactor( evlogagent_ctrl , ctxevlog, "it.unibo.evlogagent.Evlogagent"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

