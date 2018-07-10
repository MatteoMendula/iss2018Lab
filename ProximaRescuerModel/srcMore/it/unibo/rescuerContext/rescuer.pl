%====================================================================================
% Context rescuerContext  SYSTEM-configuration: file it.unibo.rescuerContext.rescuer.pl 
%====================================================================================
context(rescuercontext, "192.168.1.2",  "TCP", "7890" ).  		 
%%% -------------------------------------------
qactor( qarescuer , rescuercontext, "it.unibo.qarescuer.MsgHandle_Qarescuer"   ). %%store msgs 
qactor( qarescuer_ctrl , rescuercontext, "it.unibo.qarescuer.Qarescuer"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

