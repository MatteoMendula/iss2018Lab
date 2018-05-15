%====================================================================================
% Context blsMvcCtx  SYSTEM-configuration: file it.unibo.blsMvcCtx.blsMvc.pl 
%====================================================================================
context(blsmvcctx, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( mvccontroller , blsmvcctx, "it.unibo.mvccontroller.MsgHandle_Mvccontroller"   ). %%store msgs 
qactor( mvccontroller_ctrl , blsmvcctx, "it.unibo.mvccontroller.Mvccontroller"   ). %%control-driven 
qactor( ledmockgui , blsmvcctx, "it.unibo.ledmockgui.MsgHandle_Ledmockgui"   ). %%store msgs 
qactor( ledmockgui_ctrl , blsmvcctx, "it.unibo.ledmockgui.Ledmockgui"   ). %%control-driven 
qactor( ledarduino , blsmvcctx, "it.unibo.ledarduino.MsgHandle_Ledarduino"   ). %%store msgs 
qactor( ledarduino_ctrl , blsmvcctx, "it.unibo.ledarduino.Ledarduino"   ). %%control-driven 
qactor( qatemperature , blsmvcctx, "it.unibo.qatemperature.MsgHandle_Qatemperature"   ). %%store msgs 
qactor( qatemperature_ctrl , blsmvcctx, "it.unibo.qatemperature.Qatemperature"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evadapter,blsmvcctx,"it.unibo.blsMvcCtx.Evadapter","sensorEvent").  
%%% -------------------------------------------

