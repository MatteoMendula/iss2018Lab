%====================================================================================
% Context ctxRobot  SYSTEM-configuration: file it.unibo.ctxRobot.robot.pl 
%====================================================================================
context(ctxrobot, "localhost",  "TCP", "8030" ).  		 
%%% -------------------------------------------
qactor( player , ctxrobot, "it.unibo.player.MsgHandle_Player"   ). %%store msgs 
qactor( player_ctrl , ctxrobot, "it.unibo.player.Player"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

