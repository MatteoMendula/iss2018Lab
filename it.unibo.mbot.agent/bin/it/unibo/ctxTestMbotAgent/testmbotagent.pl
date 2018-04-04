%====================================================================================
% Context ctxTestMbotAgent  SYSTEM-configuration: file it.unibo.ctxTestMbotAgent.testMbotAgent.pl 
%====================================================================================
pubsubserveraddr("tcp://192.168.43.229:1883").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxtestmbotagent, "1ocalhost",  "TCP", "8043" ).  		 
%%% -------------------------------------------
qactor( testmbotagent , ctxtestmbotagent, "it.unibo.testmbotagent.MsgHandle_Testmbotagent"   ). %%store msgs 
qactor( testmbotagent_ctrl , ctxtestmbotagent, "it.unibo.testmbotagent.Testmbotagent"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

