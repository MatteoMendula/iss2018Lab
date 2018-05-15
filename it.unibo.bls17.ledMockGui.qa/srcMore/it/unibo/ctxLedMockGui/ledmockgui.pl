%====================================================================================
% Context ctxLedMockGui  SYSTEM-configuration: file it.unibo.ctxLedMockGui.ledMockGui.pl 
%====================================================================================
context(ctxledmockgui, "192.168.137.1",  "TCP", "8039" ).  		 
%%% -------------------------------------------
qactor( ledmockgui , ctxledmockgui, "it.unibo.ledmockgui.MsgHandle_Ledmockgui"   ). %%store msgs 
qactor( ledmockgui_ctrl , ctxledmockgui, "it.unibo.ledmockgui.Ledmockgui"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

