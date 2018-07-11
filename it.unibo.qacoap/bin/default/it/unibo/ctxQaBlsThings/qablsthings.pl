%====================================================================================
% Context ctxQaBlsThings  SYSTEM-configuration: file it.unibo.ctxQaBlsThings.qaBlsThings.pl 
%====================================================================================
context(ctxqablsthings, "localhost",  "TCP", "8032" ).  		 
%%% -------------------------------------------
qactor( qabuttonthing , ctxqablsthings, "it.unibo.qabuttonthing.MsgHandle_Qabuttonthing"   ). %%store msgs 
qactor( qabuttonthing_ctrl , ctxqablsthings, "it.unibo.qabuttonthing.Qabuttonthing"   ). %%control-driven 
qactor( qaledthing , ctxqablsthings, "it.unibo.qaledthing.MsgHandle_Qaledthing"   ). %%store msgs 
qactor( qaledthing_ctrl , ctxqablsthings, "it.unibo.qaledthing.Qaledthing"   ). %%control-driven 
qactor( qablslogic , ctxqablsthings, "it.unibo.qablslogic.MsgHandle_Qablslogic"   ). %%store msgs 
qactor( qablslogic_ctrl , ctxqablsthings, "it.unibo.qablslogic.Qablslogic"   ). %%control-driven 
qactor( qabuttonclient , ctxqablsthings, "it.unibo.qabuttonclient.MsgHandle_Qabuttonclient"   ). %%store msgs 
qactor( qabuttonclient_ctrl , ctxqablsthings, "it.unibo.qabuttonclient.Qabuttonclient"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

