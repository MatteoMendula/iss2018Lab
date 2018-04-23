%====================================================================================
% Context ctxTesterUser2  SYSTEM-configuration: file it.unibo.ctxTesterUser2.testerUser2.pl 
%====================================================================================
context(ctxtesteruser2, "localhost",  "TCP", "8068" ).  		 
context(ctxmbotmanager, "localhost",  "TCP", "8076" ).  		 
%%% -------------------------------------------
qactor( testeruser2 , ctxtesteruser2, "it.unibo.testeruser2.MsgHandle_Testeruser2"   ). %%store msgs 
qactor( testeruser2_ctrl , ctxtesteruser2, "it.unibo.testeruser2.Testeruser2"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

