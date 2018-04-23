%====================================================================================
% Context ctxTesterUser1  SYSTEM-configuration: file it.unibo.ctxTesterUser1.testerUser1.pl 
%====================================================================================
context(ctxtesteruser1, "localhost",  "TCP", "8078" ).  		 
context(ctxmbotmanager, "localhost",  "TCP", "8076" ).  		 
%%% -------------------------------------------
qactor( testeruser1 , ctxtesteruser1, "it.unibo.testeruser1.MsgHandle_Testeruser1"   ). %%store msgs 
qactor( testeruser1_ctrl , ctxtesteruser1, "it.unibo.testeruser1.Testeruser1"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

