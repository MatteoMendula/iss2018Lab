%====================================================================================
% Context ctxQaCoapServer  SYSTEM-configuration: file it.unibo.ctxQaCoapServer.qacoapServer.pl 
%====================================================================================
context(ctxqacoapserver, "localhost",  "TCP", "8042" ).  		 
%%% -------------------------------------------
qactor( qacoapserver , ctxqacoapserver, "it.unibo.qacoapserver.MsgHandle_Qacoapserver"   ). %%store msgs 
qactor( qacoapserver_ctrl , ctxqacoapserver, "it.unibo.qacoapserver.Qacoapserver"   ). %%control-driven 
qactor( globalkb , ctxqacoapserver, "it.unibo.globalkb.MsgHandle_Globalkb"   ). %%store msgs 
qactor( globalkb_ctrl , ctxqacoapserver, "it.unibo.globalkb.Globalkb"   ). %%control-driven 
qactor( kbcaller , ctxqacoapserver, "it.unibo.kbcaller.MsgHandle_Kbcaller"   ). %%store msgs 
qactor( kbcaller_ctrl , ctxqacoapserver, "it.unibo.kbcaller.Kbcaller"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

