%====================================================================================
% Context bls1Ctx  SYSTEM-configuration: file it.unibo.bls1Ctx.bls1.pl 
%====================================================================================
context(bls1ctx, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( qasensor , bls1ctx, "it.unibo.qasensor.MsgHandle_Qasensor"   ). %%store msgs 
qactor( qasensor_ctrl , bls1ctx, "it.unibo.qasensor.Qasensor"   ). %%control-driven 
qactor( qacontrol , bls1ctx, "it.unibo.qacontrol.MsgHandle_Qacontrol"   ). %%store msgs 
qactor( qacontrol_ctrl , bls1ctx, "it.unibo.qacontrol.Qacontrol"   ). %%control-driven 
qactor( qaactuator , bls1ctx, "it.unibo.qaactuator.MsgHandle_Qaactuator"   ). %%store msgs 
qactor( qaactuator_ctrl , bls1ctx, "it.unibo.qaactuator.Qaactuator"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

