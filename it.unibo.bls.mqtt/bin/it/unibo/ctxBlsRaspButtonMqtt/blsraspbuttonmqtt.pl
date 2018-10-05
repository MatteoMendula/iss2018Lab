%====================================================================================
% Context ctxBlsRaspButtonMqtt  SYSTEM-configuration: file it.unibo.ctxBlsRaspButtonMqtt.blsRaspButtonMqtt.pl 
%====================================================================================
context(ctxblsraspbuttonmqtt, "localhost",  "TCP", "8020" ).  		 
%%% -------------------------------------------
qactor( buttonhl , ctxblsraspbuttonmqtt, "it.unibo.buttonhl.MsgHandle_Buttonhl"   ). %%store msgs 
qactor( buttonhl_ctrl , ctxblsraspbuttonmqtt, "it.unibo.buttonhl.Buttonhl"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

