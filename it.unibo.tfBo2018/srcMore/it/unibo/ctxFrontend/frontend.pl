%====================================================================================
% Context ctxFrontend  SYSTEM-configuration: file it.unibo.ctxFrontend.frontend.pl 
%====================================================================================
context(ctxfrontend, "localhost",  "TCP", "8031" ).  		 
context(ctxconsole, "localhost",  "TCP", "8029" ).  		 
%%% -------------------------------------------
qactor( frontendmanager , ctxfrontend, "it.unibo.frontendmanager.MsgHandle_Frontendmanager"   ). %%store msgs 
qactor( frontendmanager_ctrl , ctxfrontend, "it.unibo.frontendmanager.Frontendmanager"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

