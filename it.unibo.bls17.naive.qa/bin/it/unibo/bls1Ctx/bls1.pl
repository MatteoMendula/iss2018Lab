%====================================================================================
% Context bls1Ctx  SYSTEM-configuration: file it.unibo.bls1Ctx.bls1.pl 
%====================================================================================
context(bls1ctx, "localhost",  "TCP", "8029" ).  		 
%%% -------------------------------------------
qactor( ledimpl , bls1ctx, "it.unibo.ledimpl.MsgHandle_Ledimpl"   ). %%store msgs 
qactor( ledimpl_ctrl , bls1ctx, "it.unibo.ledimpl.Ledimpl"   ). %%control-driven 
qactor( ledmodel , bls1ctx, "it.unibo.ledmodel.MsgHandle_Ledmodel"   ). %%store msgs 
qactor( ledmodel_ctrl , bls1ctx, "it.unibo.ledmodel.Ledmodel"   ). %%control-driven 
qactor( buttonimpl , bls1ctx, "it.unibo.buttonimpl.MsgHandle_Buttonimpl"   ). %%store msgs 
qactor( buttonimpl_ctrl , bls1ctx, "it.unibo.buttonimpl.Buttonimpl"   ). %%control-driven 
qactor( control , bls1ctx, "it.unibo.control.MsgHandle_Control"   ). %%store msgs 
qactor( control_ctrl , bls1ctx, "it.unibo.control.Control"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

