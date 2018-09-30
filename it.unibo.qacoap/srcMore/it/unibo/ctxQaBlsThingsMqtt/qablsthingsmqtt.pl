%====================================================================================
% Context ctxQaBlsThingsMqtt  SYSTEM-configuration: file it.unibo.ctxQaBlsThingsMqtt.qaBlsThingsMqtt.pl 
%====================================================================================
context(ctxqablsthingsmqtt, "localhost",  "TCP", "8052" ).  		 
%%% -------------------------------------------
qactor( qabuttonthingmqtt , ctxqablsthingsmqtt, "it.unibo.qabuttonthingmqtt.MsgHandle_Qabuttonthingmqtt"   ). %%store msgs 
qactor( qabuttonthingmqtt_ctrl , ctxqablsthingsmqtt, "it.unibo.qabuttonthingmqtt.Qabuttonthingmqtt"   ). %%control-driven 
qactor( qaledthingmqtt , ctxqablsthingsmqtt, "it.unibo.qaledthingmqtt.MsgHandle_Qaledthingmqtt"   ). %%store msgs 
qactor( qaledthingmqtt_ctrl , ctxqablsthingsmqtt, "it.unibo.qaledthingmqtt.Qaledthingmqtt"   ). %%control-driven 
qactor( qablslogicmqtt , ctxqablsthingsmqtt, "it.unibo.qablslogicmqtt.MsgHandle_Qablslogicmqtt"   ). %%store msgs 
qactor( qablslogicmqtt_ctrl , ctxqablsthingsmqtt, "it.unibo.qablslogicmqtt.Qablslogicmqtt"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

