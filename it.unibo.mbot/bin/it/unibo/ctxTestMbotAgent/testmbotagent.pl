%====================================================================================
% Context ctxTestMbotAgent  SYSTEM-configuration: file it.unibo.ctxTestMbotAgent.testMbotAgent.pl 
%====================================================================================
context(ctxtestmbotagent, "localhost",  "TCP", "8143" ).  		 
context(ctxmbotexecutor, "localhost",  "TCP", "8029" ).  		 
%%% -------------------------------------------
qactor( testmbotagent , ctxtestmbotagent, "it.unibo.testmbotagent.MsgHandle_Testmbotagent"   ). %%store msgs 
qactor( testmbotagent_ctrl , ctxtestmbotagent, "it.unibo.testmbotagent.Testmbotagent"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

