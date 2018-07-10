%====================================================================================
% Context ctxQaCoap  SYSTEM-configuration: file it.unibo.ctxQaCoap.qacoap.pl 
%====================================================================================
context(ctxqacoap, "localhost",  "TCP", "8032" ).  		 
context(ctxqacoapserver, "localhost",  "TCP", "8042" ).  		 
%%% -------------------------------------------
qactor( qacoapobserver , ctxqacoap, "it.unibo.qacoapobserver.MsgHandle_Qacoapobserver"   ). %%store msgs 
qactor( qacoapobserver_ctrl , ctxqacoap, "it.unibo.qacoapobserver.Qacoapobserver"   ). %%control-driven 
qactor( qacoapanotherobserver , ctxqacoap, "it.unibo.qacoapanotherobserver.MsgHandle_Qacoapanotherobserver"   ). %%store msgs 
qactor( qacoapanotherobserver_ctrl , ctxqacoap, "it.unibo.qacoapanotherobserver.Qacoapanotherobserver"   ). %%control-driven 
qactor( qacoapsender , ctxqacoap, "it.unibo.qacoapsender.MsgHandle_Qacoapsender"   ). %%store msgs 
qactor( qacoapsender_ctrl , ctxqacoap, "it.unibo.qacoapsender.Qacoapsender"   ). %%control-driven 
qactor( qareceiver , ctxqacoap, "it.unibo.qareceiver.MsgHandle_Qareceiver"   ). %%store msgs 
qactor( qareceiver_ctrl , ctxqacoap, "it.unibo.qareceiver.Qareceiver"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

