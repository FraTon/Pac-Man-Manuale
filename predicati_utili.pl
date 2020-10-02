

%MAX MIN X Y
%
% Data una lista di coordinate, individua la massima e la minima X e Y.
%
% La lista utilizata in questo caso è quella contenente tuti i muri che
% compongono il labirinto e la clausola consente di individuare gli
% estremi del campo da gioco e quindi il valore della Max X e Y
% accettabili e della Min X e Y accettabili.
%
max_min_x_y([],-10000,-10000,10000,10000).
max_min_x_y([[X,Y]|C],MaxAttualeX,MaxAttualeY,MinAttualeX,MinAttualeY):-
	max_min_x_y(C,MaxX,MaxY,MinX,MinY),
	MaxAttualeX is max(MaxX,X),
	MaxAttualeY is max(MaxY,Y),

	MinAttualeX is min(MinX,X),
	MinAttualeY is min(MinY,Y).



% PRIMO ELEMENTO
%
% Individua il primo elemento di una lista.
%
primo_elemento([T|_],T).



% ULTIMO ELEMENTO
%
% Individua l'ultimo elemento di una lista
%
ultimo_elemento([Ultimo],Ultimo).
ultimo_elemento([_|C],Ultimo):-
	ultimo_elemento(C,Ultimo).



% MASSIMO LISTA
%
% Individua il massimo valore contenuto in una lista.
%
massimo_lista([],-10000).
massimo_lista([T],T):-
	!.
massimo_lista([T|C],MaxAttuale):-
	massimo_lista(C,Max),
	MaxAttuale is max(T,Max).



% MINIMO LISTA
%
% Individua il minimo valore contenuto in una lista.
%
minimo_lista([],10000).
minimo_lista([T],T):-
	!.
minimo_lista([T|C],MinAttuale):-
	minimo_lista(C,Min),
	MinAttuale is min(T,Min).
