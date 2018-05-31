%====================================================================================
% Context ctxWebGuiExecutor  SYSTEM-configuration: file it.unibo.ctxWebGuiExecutor.webGuiExecutor.pl 
%====================================================================================
context(ctxwebguiexecutor, "localhost",  "TCP", "8032" ).  		 
%%% -------------------------------------------
qactor( player , ctxwebguiexecutor, "it.unibo.player.MsgHandle_Player"   ). %%store msgs 
qactor( player_ctrl , ctxwebguiexecutor, "it.unibo.player.Player"   ). %%control-driven 
qactor( robotpfrs , ctxwebguiexecutor, "it.unibo.robotpfrs.MsgHandle_Robotpfrs"   ). %%store msgs 
qactor( robotpfrs_ctrl , ctxwebguiexecutor, "it.unibo.robotpfrs.Robotpfrs"   ). %%control-driven 
qactor( sonarguidetector , ctxwebguiexecutor, "it.unibo.sonarguidetector.MsgHandle_Sonarguidetector"   ). %%store msgs 
qactor( sonarguidetector_ctrl , ctxwebguiexecutor, "it.unibo.sonarguidetector.Sonarguidetector"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evh,ctxwebguiexecutor,"it.unibo.ctxWebGuiExecutor.Evh","usercmd").  
%%% -------------------------------------------

