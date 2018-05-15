%====================================================================================
% Context ctxTestEventEmitter  SYSTEM-configuration: file it.unibo.ctxTestEventEmitter.testEventEmitter.pl 
%====================================================================================
context(ctxtesteventemitter, "192.168.137.1",  "TCP", "8119" ).  		 
%%% -------------------------------------------
qactor( testeventemitter , ctxtesteventemitter, "it.unibo.testeventemitter.MsgHandle_Testeventemitter"   ). %%store msgs 
qactor( testeventemitter_ctrl , ctxtesteventemitter, "it.unibo.testeventemitter.Testeventemitter"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

