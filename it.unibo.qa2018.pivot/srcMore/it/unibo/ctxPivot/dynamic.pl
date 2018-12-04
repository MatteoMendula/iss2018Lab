%====================================================================================
% Context ctxPivot  SYSTEM-configuration: file it.unibo.ctxPivot.dynamic.pl 
%====================================================================================
pubsubserveraddr("").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxpivot, "localhost",  "TCP", "8030" ).  		 
%%% -------------------------------------------
qactor( qa0 , ctxpivot, "it.unibo.qa0.MsgHandle_Qa0"   ). %%store msgs 
qactor( qa0_ctrl , ctxpivot, "it.unibo.qa0.Qa0"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

