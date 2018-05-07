%====================================================================================
% Context bls3Ctx  SYSTEM-configuration: file it.unibo.bls3Ctx.bls3.pl 
%====================================================================================
context(bls3ctx, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( controllerlogic , bls3ctx, "it.unibo.controllerlogic.MsgHandle_Controllerlogic"   ). %%store msgs 
qactor( controllerlogic_ctrl , bls3ctx, "it.unibo.controllerlogic.Controllerlogic"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

