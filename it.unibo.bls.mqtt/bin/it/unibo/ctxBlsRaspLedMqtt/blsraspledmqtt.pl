%====================================================================================
% Context ctxBlsRaspLedMqtt  SYSTEM-configuration: file it.unibo.ctxBlsRaspLedMqtt.blsRaspLedMqtt.pl 
%====================================================================================
context(ctxblsraspledmqtt, "localhost",  "TCP", "8062" ).  		 
%%% -------------------------------------------
qactor( ledmqtt , ctxblsraspledmqtt, "it.unibo.ledmqtt.MsgHandle_Ledmqtt"   ). %%store msgs 
qactor( ledmqtt_ctrl , ctxblsraspledmqtt, "it.unibo.ledmqtt.Ledmqtt"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

