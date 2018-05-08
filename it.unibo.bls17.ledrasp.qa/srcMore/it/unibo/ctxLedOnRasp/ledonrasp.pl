%====================================================================================
% Context ctxLedOnRasp  SYSTEM-configuration: file it.unibo.ctxLedOnRasp.ledOnRasp.pl 
%====================================================================================
context(ctxledonrasp, "192.168.43.18",  "TCP", "8079" ).  		 
%%% -------------------------------------------
qactor( ledrasp , ctxledonrasp, "it.unibo.ledrasp.MsgHandle_Ledrasp"   ). %%store msgs 
qactor( ledrasp_ctrl , ctxledonrasp, "it.unibo.ledrasp.Ledrasp"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

