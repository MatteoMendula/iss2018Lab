%====================================================================================
% Context bls2Ctx  SYSTEM-configuration: file it.unibo.bls2Ctx.bls2.pl 
%====================================================================================
context(bls2ctx, "localhost",  "TCP", "8029" ).  		 
%%% -------------------------------------------
qactor( ledimpl , bls2ctx, "it.unibo.ledimpl.MsgHandle_Ledimpl"   ). %%store msgs 
qactor( ledimpl_ctrl , bls2ctx, "it.unibo.ledimpl.Ledimpl"   ). %%control-driven 
qactor( ledmodel , bls2ctx, "it.unibo.ledmodel.MsgHandle_Ledmodel"   ). %%store msgs 
qactor( ledmodel_ctrl , bls2ctx, "it.unibo.ledmodel.Ledmodel"   ). %%control-driven 
qactor( buttonimpl , bls2ctx, "it.unibo.buttonimpl.MsgHandle_Buttonimpl"   ). %%store msgs 
qactor( buttonimpl_ctrl , bls2ctx, "it.unibo.buttonimpl.Buttonimpl"   ). %%control-driven 
qactor( control , bls2ctx, "it.unibo.control.MsgHandle_Control"   ). %%store msgs 
qactor( control_ctrl , bls2ctx, "it.unibo.control.Control"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

