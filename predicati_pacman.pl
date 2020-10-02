:-dynamic adiacente/2.



% DISTANZA MANHATTAN TRA DUE PUNTI
%
% Calcola la distanza Manhattan tra due punti (X1,Y1) e
% l'obiettivo del fantasma con cui si stà lavorando.
%
/*manhattan([X1,Y1],Distanza):-
	b_getval(colore,Colore),
	modalita(Colore,Modalita),
	obiettivo(Modalita,Colore,X2,Y2),

	valore_assoluto(X1-X2,X),
	valore_assoluto(Y1-Y2,Y),
	Distanza is (X+Y).**/

manhattan([X1,Y1],Distanza):-
	b_getval(colore,Colore),
	modalita(Colore,Modalita),
	obiettivo(Modalita,Colore,X2,Y2),

	limiti_x(MinX,MaxX),
     
	%Distanza considerando ingresso tunnel destra
	valore_assoluto(X1-MaxX,A),
	%valore_assoluto(Y1+14,B),
	valore_assoluto(MinX-X2,B),
	valore_assoluto(Y1-Y2,C),	
	%valore_assoluto(-14-Y2,D),
	Distanza1 is (A+B+C+1),

	%Distanza considerando ingresso tunnel sinistra
	valore_assoluto(X1-MinX,A1),
	%valore_assoluto(Y1+14,B1),
	valore_assoluto(MaxX-X2,B1),
	valore_assoluto(Y1-Y2,C1),
	%valore_assoluto(-14-Y2,D1),
	Distanza2 is (A1+B1+C1+1),

	%Distanza manhattan, senza considerare il tunnel
	valore_assoluto(X1-X2,X),
	valore_assoluto(Y1-Y2,Y),
	Distanza3 is (X+Y),

	%Distanza è la minore delle tre possibilità
	(Distanza1=<Distanza2,Distanza1=<Distanza3 -> Distanza is Distanza1;
	Distanza2=<Distanza1,Distanza2=<Distanza3 -> Distanza is Distanza2;
	Distanza is Distanza3).

% VALORE ASSOLUTO
%
% Dato un valore X restituisce il suo valore assoluto AX.
%
valore_assoluto(X,AX):-
	X<0,!,
	AX is -(X).
valore_assoluto(X,X).



% altra implementazione di A
% si parte espandendo un albero che ha solo una foglia e costo 0
% assumiamo 9999 maggiore di qualunque valore di F
%
best(FantasmaX,FantasmaY,Colore,Percorso) :-
	b_setval(colore,Colore),
	espande([], l([FantasmaX,FantasmaY], 0/0), 9999, _, si, Percorso).
  %scrivi(Percorso).

% espande(+Perc, +Alb, +Lim, -Alb1, -Risolto, -Percorso):
% Perc:  percorso fra il nodo radice ed il sottoalbero Alb
% Alb: sottoalbero corrente
% Costo: numero di mosse effettuate dalla ride al nodo in considerazione
% Lim: valore di F massimo entro cui espandere Alb
% Alb1: Alb espanso fino al limite Lim; conseguentemente il
%   valore di F di Alb1 è maggiore di quello di Alb, a meno che
%   non si è trovato il goal
% Risolto: [sì,no,mai] indica se si è trovato un nodo goal
%   nell'espansione di Alb
% Percorso: un percorso fra il nodo radice ed il goal passando per
%   Alb1 all'interno del limite Lim

%  1: Risolto=si
%  se si trova il goal si aggiunge il sottoalbero (che è solo una
%  foglia) al Percorso e lo si restituisce in Percorso
espande(Percorso,l([X,Y],_/_),_,_,si,[[X,Y]|Percorso]) :-
	b_getval(colore,Colore),    %legge il colore del fantasma di cui deve trovare il percorso ottimo
	modalita(Colore,Modalita),    %legge la modalità di gioco attuale
	obiettivo(Modalita,Colore,X,Y).


%  2: nodo-foglia, valore di f minore di Lim
%  genera i successori e li espande fino a Lim
espande(Percorso, l(N,F/Costo),Lim, Alb1, Risolto, Sol)  :-
  F  =<  Lim,
    (                             %A COSA SERVONO LE PARENTESI ?????????????????????????????????????????????????????????????????????
     successori(N,Successori),   %successori possibili dei nodo X,Y
     rimuovi_celle_non_ammissibili(Successori,Percorso,NuoviSuccessori),   %successori ammissibili del nodo X Y
     !,				      % il nodo N ha dei successoriiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
     sottoalberi(Costo,NuoviSuccessori,Sottoalberi),
     migliore_f(Sottoalberi, F1),	      % valore di F del miglior sottoalbero (il primo della lista)
     espande(Percorso, t(N,F1/Costo,Sottoalberi), Lim, Alb1, Risolto, Sol);  %passa la radice del sottoalbero appena espanso, il valore F del nodo da espandere e la lista dei sotoalberi del nodo radice espanso
       %??????????????????????????????????????
     Risolto = mai    % il nodo N non ha successori - ramo morto
  ) .

%  3: nodo-NON-foglia (perchè voglio espanderlo), F < Lim
%  espande il sottoalbero più promettente; a seconda del
%  risultato il predicato continua decide come procedere
%
%  Ts contiene i sottoalberi non ancora espansi
%  T è il sottoalbero che stiamo espandendo
%
%  %nell'espansione del sottoalbero devo tenere conto dei sottoalberi che ho già e del valore F del migliore tra essi
espande(P, t(N,F/Costo,[T|Ts]), Lim, Alb1, Risolto, Sol)  :-
  F =<	Lim,   %se è superiore significa che c'è un sottoalbero migliore da espandere, la cui radice ha F pari a Lim
  migliore_f(Ts,BF),   %cerca la F del migliore dei sottoalberi non espansi
  Lim1 is min(Lim,BF),
  %Costo2 is Costo+1,
  espande([N|P], T,Lim1, T1, Risolto1, Sol),  %espando il primo nodo (foglia) della lista dei sottoalberi e metto suo padre in testa al percorso verso l'ottimo
  continua(P, t(N,F/Costo,[T1|Ts]), Lim, Alb1, Risolto1, Risolto, Sol).

%  4: nodo-NON-foglia che non ha più sottoalberi da processare
%  è un ramo morto che non sarà mai risolto
espande( _, t(_,_,[]),_, _, mai, _) :- !.

%  5: Risolto=no, F > Lim qualche sottoalbero in analisi ha superato il
%  limite-->Alb non cresce, non risolto-> bisogna cambiare ramo
espande(_, Alb, Lim, Alb, no, _)  :-
  estrai_f(Alb, F),
  F > Lim.  % l'albero non è risolto se il migliore F è superiore al limite

% continue(Perc, Alb, Lim, NAlb, SottoAlbRisolto, AlbRisolto, Percorso)
% controlla se nelle altre fogli dell'albero si arriva a soluzioni che
% non superano il limite
continua(_, _,_,_,  si, si, _).

continua(P, t(N,_/Costo,[T1|Ts]), Lim, Alb1, no, Risolto, Sol)  :-
  inserisci(T1, Ts, NTs),  %crea un'unica lista che contiene tutte le foglie (anche quelle dell'albero fallito)
  migliore_f(NTs, F1), %estrai il miglior F dei nodi dell'albero
  espande(P, t(N,F1/Costo,NTs), Lim, Alb1, Risolto, Sol).

% il sottoalbero espanso non aveva figli quindi l'albero fallito è vuoto
% T1=[]
continua(P, t(N,_/Costo,[_|Ts]), Lim, Alb1, mai, Risolto, Sol)  :-
  migliore_f(Ts, F1),
  espande(P, t(N,F1/Costo,Ts), Lim, Alb1, Risolto, Sol).




% SUCCESSORI
%
% Data l'attuale posizione del fantasma restituisce una lista delle
% celle in cui potrebbe spostarsi.
%
% Prima di calcolare tutte le celle in cui è possibile spostarsi,
% verifica che non sia già stato fatto in precedenza.
%

successori([X1,Y1],Successori):-
	possibili(X1,Y1,Successori).  %siamo già stati in questa posizione e conosciamo già le direzioni possibili


successori([X1,Y1],Successori):-
	findall([X2,Y2],(adiacente([X1,Y1],[X2,Y2]),\+muro(X2,Y2)),Successori),  %trova tutte le direzioni possibili
	assert(possibili(X1,Y1,Successori)). %asserisce tutte le direzioni ammissibili quando si è in posizione X1,Y1




% RIMUOVI CELLE NON AMMISSIBILI
%
% Data la lista delle celle in cui è possible spostarsi, individua le
% celle in cui è ammissible lo spostamento.
%
% Non è ammissibile lo spostamento in celle già facenti parte del
% percorso o in celle di tipo "cancello" (a meno che il fantasma non sia
% stato mangiato)
%
rimuovi_celle_non_ammissibili([],_,[]).
rimuovi_celle_non_ammissibili([T|C],Percorso,NuoviSuccessori):-
	rimuovi_celle_non_ammissibili(C,Percorso,Successori),
	inserisci(T,Percorso,Successori,NuoviSuccessori).

% INSERISCI/4
%
% Data una cella, la inserisce nella lista @Successori, solo se non fà
% già parte del @Percorso e il fantasma non è in modalità mangiato e la
% cella coincide con un cancello o se il fantasma è quello arancione ed
% è in modalità scatter e quella cella coincide con la sua precedente
% posizione.
%
% SERVE UN CUT DA QUALCHE PARTE??????????????????????
%
inserisci([X,Y],Percorso,Successori,Successori):-
	(
            member([X,Y],Percorso)   %già visitata
	    ;
            b_getval(colore,Colore),
	    modalita(Colore,Modalita),
            (
		\+ Modalita = 'mangiato',   %modalità NON è mangiato
		cancello(X,Y)	    %cancello non è accessibile
		;
		obiettivo(Modalita-scatter,arancione,_,_),
		fantasma(arancione,X,Y)   %non può tornare nella posizione precedente
	    )
	).
inserisci(T,_,Successori,[T|Successori]).


% SOTTOALBERI
%
% Data la lista dei successori di una cella, restituisce la lista dei
% possibili sottoalberi.
%
sottoalberi(_,[],[]).
sottoalberi(Costo,[N|NCoda],Sottoalbero):-
	Costo2 is Costo+1,   %incrementa il costo->mossa in più
	manhattan(N,Euristica),   %calcolo euristica
	F is Euristica+Costo2,

	sottoalberi(Costo,NCoda,Ts),
	inserisci(l(N,F/Costo2),Ts,Sottoalbero).


% ADIACENTE
%
% Data una cella, individua una cella adiacente.
%
% Ricordare che l'asse X coincide con le colonne di una matrice (cresce
% verso destra), e l'asse Y coincide con le righe della matrice (cresce
% verso il basso).
%
adiacente([X,Y1],[X,Y2]):-
        Y2 is Y1+1, %nord
	limiti_y(_,MaxY),
	Y2 =< MaxY.

adiacente([X1,Y],[X2,Y]):-
	X2 is X1-1, %ovest
	limiti_x(MinX,_),
	X2 >= MinX.

adiacente([X,Y1],[X,Y2]):-
        Y2 is Y1-1, %sud
	limiti_y(MinY,_),
	Y2 >= MinY.

adiacente([X1,Y],[X2,Y]):-
	X2 is X1+1, %est
	limiti_x(_,MaxX),
	X2 =< MaxX.


% INSERISCI/3
%
% Inserisce @T nella lista, mantenendo l'ordinamento per
% F non decrescente.
%
inserisci(T,Ts,[T|Ts])  :-
  estrai_f(T,F),    %estrae il valore F del nodo T da inserire
  migliore_f(Ts,F1),  %legge il valore di F del primo elemento della lista -> F1
  F  =<  F1,  %inserisce T in testa, se la sua F è minore di quella del primo elemento della lista
  !.

inserisci(T, [T1|Ts], [T1|Ts1]) :-  % T è maggiore della F del primo nodo della lista, quindi viene inserito in una posizione successiva.
  inserisci(T,Ts,Ts1).   %inserisco T nella cosa dell'attuale lista


% ESTRAI F
%
% Estari il valore F dal nodo dell'albero.
%
estrai_f(l(_,F/_), F).        % da una foglia
estrai_f(t(_,F/_,_), F).      % da un albero

% MIGLIORE F
%
% Individua la migliore F tra i nodi dell'albero.
%
% Essendo i sottoalberi ordinati per valore di F non decrescente, il
% migliore valore (il più piccolo) è quello che corrisponde al primo
% nodo della lista dei sottoalberi.
%
migliore_f([T|_], F)	:-
	estrai_f(T, F).  % valore di F del miglior albero
migliore_f([], 9999).       % nessun albero, F massimo





