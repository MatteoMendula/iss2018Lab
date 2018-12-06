/*
*	curConfigTheory.pl
*/
getTheContexts(Ctx,CTXS) :-
 	Ctx <- solvegoal("getTheContexts(X)","X") returns CTXS .
getTheActors(Ctx,ACTORS) :-
 	Ctx <- solvegoal("getTheActors(X)","X") returns ACTORS.
 
 /*
 ----------------------------------
Show system configuration
 ----------------------------------
 */	
showSystemConfiguration :-
	actorPrintln('-------------------------------------------------'),
	actorobj(A),
	A <- getQActorContext returns Ctx,
   	%% Ctx <- getName returns CtxName, actorPrintln( CtxName ), 	 
 	getTheContexts(Ctx,CTXS),
	actorPrintln('CONTEXTS IN THE SYSTEM:'),
	showElements(CTXS),
	actorPrintln('ACTORS   IN THE SYSTEM:'),
	getTheActors(Ctx,ACTORS),
	showElements(ACTORS),
	actorPrintln('-------------------------------------------------').
 
showElements(ElementListString):- 
	text_term( ElementListString, ElementList ),
	%% actorPrintln( list(ElementList) ),
	showListOfElements(ElementList).
showListOfElements([]).
showListOfElements([C|R]):-
	actorPrintln( C ),
	showElements(R).
	
/*	
%% CONTENT OF./srcMore/it/unibo/ctxPivot/dynamic.pl
pubsubserveraddr("").
pubsubsystopic("unibo/qasys").
%%% -------------------------------------------
context(ctxpivot, "localhost",  "TCP", "8030" ).  		 
%%% -------------------------------------------
qactor( qa0 , ctxpivot, "it.unibo.qa0.MsgHandle_Qa0"   ). %%store msgs 
qactor( qa0_ctrl , ctxpivot, "it.unibo.qa0.Qa0"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------
*/	