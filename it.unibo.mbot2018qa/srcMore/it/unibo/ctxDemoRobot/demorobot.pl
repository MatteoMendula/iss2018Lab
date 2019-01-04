%====================================================================================
% Context ctxDemoRobot  SYSTEM-configuration: file it.unibo.ctxDemoRobot.demoRobot.pl 
%====================================================================================
pubsubserveraddr("").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxdemorobot, "localhost",  "TCP", "8078" ).  		 
%%% -------------------------------------------
qactor( robotdemo , ctxdemorobot, "it.unibo.robotdemo.MsgHandle_Robotdemo"   ). %%store msgs 
qactor( robotdemo_ctrl , ctxdemorobot, "it.unibo.robotdemo.Robotdemo"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

