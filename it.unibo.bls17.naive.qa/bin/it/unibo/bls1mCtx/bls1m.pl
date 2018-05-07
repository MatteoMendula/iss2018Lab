%====================================================================================
% Context bls1mCtx  SYSTEM-configuration: file it.unibo.bls1mCtx.bls1m.pl 
%====================================================================================
context(bls1mctx, "localhost",  "TCP", "8049" ).  		 
%%% -------------------------------------------
qactor( qaledm , bls1mctx, "it.unibo.qaledm.MsgHandle_Qaledm"   ). %%store msgs 
qactor( qaledm_ctrl , bls1mctx, "it.unibo.qaledm.Qaledm"   ). %%control-driven 
qactor( qacontrolm , bls1mctx, "it.unibo.qacontrolm.MsgHandle_Qacontrolm"   ). %%store msgs 
qactor( qacontrolm_ctrl , bls1mctx, "it.unibo.qacontrolm.Qacontrolm"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

