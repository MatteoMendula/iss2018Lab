%====================================================================================
% Context ctxMmon  SYSTEM-configuration: file it.unibo.ctxMmon.mmon.pl 
%====================================================================================
context(ctxmmon, "localhost",  "TCP", "8062" ).  		 
%%% -------------------------------------------
qactor( operatorconsole , ctxmmon, "it.unibo.operatorconsole.MsgHandle_Operatorconsole"   ). %%store msgs 
qactor( operatorconsole_ctrl , ctxmmon, "it.unibo.operatorconsole.Operatorconsole"   ). %%control-driven 
qactor( orchestrator , ctxmmon, "it.unibo.orchestrator.MsgHandle_Orchestrator"   ). %%store msgs 
qactor( orchestrator_ctrl , ctxmmon, "it.unibo.orchestrator.Orchestrator"   ). %%control-driven 
qactor( elabactor , ctxmmon, "it.unibo.elabactor.MsgHandle_Elabactor"   ). %%store msgs 
qactor( elabactor_ctrl , ctxmmon, "it.unibo.elabactor.Elabactor"   ). %%control-driven 
qactor( presslogical , ctxmmon, "it.unibo.presslogical.MsgHandle_Presslogical"   ). %%store msgs 
qactor( presslogical_ctrl , ctxmmon, "it.unibo.presslogical.Presslogical"   ). %%control-driven 
qactor( pressphysicalsimulator , ctxmmon, "it.unibo.pressphysicalsimulator.MsgHandle_Pressphysicalsimulator"   ). %%store msgs 
qactor( pressphysicalsimulator_ctrl , ctxmmon, "it.unibo.pressphysicalsimulator.Pressphysicalsimulator"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

