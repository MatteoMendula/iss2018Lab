%====================================================================================
% Context ctxWebGuiClient  SYSTEM-configuration: file it.unibo.ctxWebGuiClient.webGuiClient.pl 
%====================================================================================
context(ctxwebguiclient, "localhost",  "TCP", "8042" ).  		 
context(ctxwebguiexecutor, "82.49.115.40",  "TCP", "8032" ).  		 
%%% -------------------------------------------
qactor( webguiclient , ctxwebguiclient, "it.unibo.webguiclient.MsgHandle_Webguiclient"   ). %%store msgs 
qactor( webguiclient_ctrl , ctxwebguiclient, "it.unibo.webguiclient.Webguiclient"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

