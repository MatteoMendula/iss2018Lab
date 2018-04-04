%====================================================================================
% Context ctxMbotAgent  SYSTEM-configuration: file it.unibo.ctxMbotAgent.testMbotAgent.pl 
%====================================================================================
pubsubserveraddr("tcp://localhost:1883").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxmbotagent, "1ocalhost",  "TCP", "8043" ).  		 
%%% -------------------------------------------
qactor( testmbotagent , ctxmbotagent, "it.unibo.testmbotagent.MsgHandle_Testmbotagent"   ). %%store msgs 
qactor( testmbotagent_ctrl , ctxmbotagent, "it.unibo.testmbotagent.Testmbotagent"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

