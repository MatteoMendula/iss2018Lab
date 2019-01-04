%====================================================================================
% Context ctxRobotExplore  SYSTEM-configuration: file it.unibo.ctxRobotExplore.robotWenvExplore.pl 
%====================================================================================
pubsubserveraddr("tcp://localhost:1883").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxrobotexplore, "localhost",  "TCP", "8028" ).  		 
%%% -------------------------------------------
qactor( cmdrobotconverter , ctxrobotexplore, "it.unibo.cmdrobotconverter.MsgHandle_Cmdrobotconverter"   ). %%store msgs 
qactor( cmdrobotconverter_ctrl , ctxrobotexplore, "it.unibo.cmdrobotconverter.Cmdrobotconverter"   ). %%control-driven 
qactor( player , ctxrobotexplore, "it.unibo.player.MsgHandle_Player"   ). %%store msgs 
qactor( player_ctrl , ctxrobotexplore, "it.unibo.player.Player"   ). %%control-driven 
qactor( mind , ctxrobotexplore, "it.unibo.mind.MsgHandle_Mind"   ). %%store msgs 
qactor( mind_ctrl , ctxrobotexplore, "it.unibo.mind.Mind"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

