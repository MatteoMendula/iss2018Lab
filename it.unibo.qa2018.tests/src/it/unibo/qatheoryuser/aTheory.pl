/* =========================================================== 
aTheory.pl 
=============================================================== */
 data(sonar, 1, 10).
 data(sonar, 2, 20).
 data(sonar, 3, 30).
 data(sonar, 4, 40).
 
 validDistance( N,V ) :-  data(sonar, N, V), V>10, V<50.
 nearDistance(  N,X ) :-  validDistance( N,X ), X < 40.
 nears( D ) :-  findall( d( N,V ), nearDistance(N,V), D).

aTheoryInit  :- output("initializing the aTheory ...").
:- initialization(aTheoryInit).


