%====================================================================================
% Context blsMvcCtx  SYSTEM-configuration: file it.unibo.blsMvcCtx.blsMvc.pl 
%====================================================================================
context(blsmvcctx, "192.168.43.229",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( ledmockgui , blsmvcctx, "it.unibo.ledmockgui.MsgHandle_Ledmockgui"   ). %%store msgs 
qactor( ledmockgui_ctrl , blsmvcctx, "it.unibo.ledmockgui.Ledmockgui"   ). %%control-driven 
qactor( tester , blsmvcctx, "it.unibo.tester.MsgHandle_Tester"   ). %%store msgs 
qactor( tester_ctrl , blsmvcctx, "it.unibo.tester.Tester"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

