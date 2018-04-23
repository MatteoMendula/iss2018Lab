%====================================================================================
% Context ctxTestMbotAgent  SYSTEM-configuration: file it.unibo.ctxTestMbotAgent.testMbotAgent.pl 
%====================================================================================
context(ctxtestmbotagent, "1ocalhost",  "TCP", "8043" ).  		 
%%% -------------------------------------------
qactor( testmbotagent , ctxtestmbotagent, "it.unibo.testmbotagent.MsgHandle_Testmbotagent"   ). %%store msgs 
qactor( testmbotagent_ctrl , ctxtestmbotagent, "it.unibo.testmbotagent.Testmbotagent"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

