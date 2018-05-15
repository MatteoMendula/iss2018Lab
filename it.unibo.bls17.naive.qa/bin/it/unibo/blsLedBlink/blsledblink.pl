%====================================================================================
% Context blsLedBlink  SYSTEM-configuration: file it.unibo.blsLedBlink.blsLedBlink.pl 
%====================================================================================
context(blsledblink, "localhost",  "TCP", "8029" ).  		 
%%% -------------------------------------------
qactor( control , blsledblink, "it.unibo.control.MsgHandle_Control"   ). %%store msgs 
qactor( control_ctrl , blsledblink, "it.unibo.control.Control"   ). %%control-driven 
qactor( ledmodel , blsledblink, "it.unibo.ledmodel.MsgHandle_Ledmodel"   ). %%store msgs 
qactor( ledmodel_ctrl , blsledblink, "it.unibo.ledmodel.Ledmodel"   ). %%control-driven 
qactor( buttonimpl , blsledblink, "it.unibo.buttonimpl.MsgHandle_Buttonimpl"   ). %%store msgs 
qactor( buttonimpl_ctrl , blsledblink, "it.unibo.buttonimpl.Buttonimpl"   ). %%control-driven 
qactor( ledimpl , blsledblink, "it.unibo.ledimpl.MsgHandle_Ledimpl"   ). %%store msgs 
qactor( ledimpl_ctrl , blsledblink, "it.unibo.ledimpl.Ledimpl"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

