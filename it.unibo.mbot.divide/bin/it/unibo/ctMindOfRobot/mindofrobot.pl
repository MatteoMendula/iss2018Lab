%====================================================================================
% Context ctMindOfRobot  SYSTEM-configuration: file it.unibo.ctMindOfRobot.mindOfRobot.pl 
%====================================================================================
context(ctmindofrobot, "192.168.43.229",  "TCP", "8039" ).  		 
context(ctxradarbase, "192.168.43.229",  "TCP", "8033" ).  		 
%%% -------------------------------------------
qactor( mind , ctmindofrobot, "it.unibo.mind.MsgHandle_Mind"   ). %%store msgs 
qactor( mind_ctrl , ctmindofrobot, "it.unibo.mind.Mind"   ). %%control-driven 
qactor( evlogagent , ctmindofrobot, "it.unibo.evlogagent.MsgHandle_Evlogagent"   ). %%store msgs 
qactor( evlogagent_ctrl , ctmindofrobot, "it.unibo.evlogagent.Evlogagent"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

