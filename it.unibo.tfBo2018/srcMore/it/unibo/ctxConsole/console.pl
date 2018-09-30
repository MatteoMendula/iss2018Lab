%====================================================================================
% Context ctxConsole  SYSTEM-configuration: file it.unibo.ctxConsole.console.pl 
%====================================================================================
context(ctxconsole, "localhost",  "TCP", "8029" ).  		 
context(ctxrobot, "localhost",  "TCP", "8030" ).  		 
%%% -------------------------------------------
qactor( mvccontroller0 , ctxconsole, "it.unibo.mvccontroller0.MsgHandle_Mvccontroller0"   ). %%store msgs 
qactor( mvccontroller0_ctrl , ctxconsole, "it.unibo.mvccontroller0.Mvccontroller0"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

