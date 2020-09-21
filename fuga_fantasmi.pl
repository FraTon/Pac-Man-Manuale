%:-['pac-man'].
%:-[predicati_pacman].
%:-[database].

%potremmo anche fargli fare il percorso ottimo al conrario

%ogni volta che pacman mangia un puntino asserisco che è stato mangiato

:-dynamic pacman/2.
:-dynamic fantasma/3. %ultima posizione conosciuta del fantasma Colore
:-dynamic scatter/3.
:-dynamic obiettivo/4.  %(modalita,colore,X obietivo, Y obiettivo)
:-dynamic possibili/3.
:-dynamic percorso/3.   %percorso(modalita,colore,Percorso)
:-dynamic modalita/2.   %modalita(Colore,std/fuga/mangiato)
:-dynamic vecchio_pacman/2.




%  X= Colonna
%  Y= Riga

%RITRARRE MODALITA'???????????????????????????????????????????????????????????????????
set_iniziale_fuga:-
	(\+pacman(_,_);
	retractall(pacman(_,_))
	),

	(\+obiettivo(fuga,_,_,_);
	retractall(obiettivo(fuga,_,_,_))
	),

	(\+percorso(fuga,_,_);
	retractall(percorso(fuga,_,_))
	).


% FUGA FANTASMI (predicato non utilizzato)
%
% Mette in fuga da Pac-Man tutti i fantasmi in modalità fuga.
%
% Date le coordinate delle posizioni dei fantasmi, la
% direzione di Pac-Man e del fantasma rosso,
% restituisce le coordinate delle posizioni in cui devono spostarsi i
% fantasmi in modalità fuga per fuggire da Pac-Man, per quelli invece
% che sono già stati mangiati e sono in modalità standard, vengono
% restituite le coordinate della cella in cui spostarsi per avicinarsi
% a Pac-Man.
%
/*
fuga_fantasmi(RX,RY,AX,AY,RSX,RSY,ARX,ARY,DirezioneRosso,DirezionePacMan,PX,PY,NRX,NRY,NAX,NAY,NRSX,NRSY,NARX,NARY,DirR,DirA,DirRS,DirAR):-


	assert(pacman(PX,PY)),

	(
            modalita(rosso,fuga),
	    fuga_rosso(RX,RY,NRX,NRY,DirR)
	    ;
	    mossa_fantasma(rosso,RX,RY,NRX,NRY,DirR)
	),
	(
	    modalita(rosa,fuga),
	    fuga_rosa(RSX,RSY,ARX,ARY,NRSX,NRSY,DirRS)
	    ;
	    mossa_fantasma(rosa,RSX,RSY,DirezionePacMan,NRSX,NRSY,DirRS)
	),
	(
	    modalita(azzurro,fuga),
	    fuga_azzurro(azzurro,AX,AY,RX,RY,DirezioneRosso,DirezionePacMan,NAX,NAY,DirA)
	    ;
	    mossa_fantasma(azzurro,AX,AY,RX,RY,DirezioneRosso,DirezionePacMan,NAX,NAY,DirA)
	),
	(
	    modalita(arancione,fuga),
	    fuga_arancione(arancione,ARX,ARY,NARX,NARY,DirAR)
	    ;
	    mossa_fantasma(arancione,ARX,ARY,NARX,NARY,DirAR)
	).
*/

/*(predicato non utilizzato)

fuga(Colore,FX,FY,NX,NY,Dir):-

	pacman(PX,PY),  % istanzia X e Y con l'attuale posizione di pacman

	findall([X,Y],vuota(X,Y),CelleVuote),    %celle che ora sono vuote

	maggiore_distanza(CelleVuote,PX,PY,_,[OX,OY]),  %scelta tra le cella vuote quella a distanza maggiore dalla posizione corrente di pacman
	%write('['),write(OX),write(','),write(OY),write(']'),
	(   \+obiettivo(_,Colore,_,_);
	     retract(obiettivo(_,Colore,_,_))),
	assert(obiettivo(fuga,Colore,OX,OY)),

	best(FX,FY,Colore,Percorso),
	%scrivi(Percorso),
	mossa(Percorso,[NX,NY],NuovoPercorso),    % [NX,NY] coordinate della cella in cui muoversi per avvicinarsi all'obiettivo
        incrementa_posizione(FX,FY,Dir,1,NX,NY),  % conosce la posizione attuale e la successiva,lo spostamento è unitario-> Direzione percorsa

	(   \+percorso(_,Colore,_);
	    retract(percorso(_,Colore,_))),
        assert(percorso(fuga,Colore,NuovoPercorso)),

        (   \+fantasma(Colore,_,_);
	     retract(fantasma(Colore,_,_))),
	assert(fantasma(Colore,FX,FY)).
	
*/


% FUGA ROSSO
%
% Data la posizione attuale del fantasma rosso, colcola le coorinate
% della cella in cui spostarsi per fuggire da Pac-Man
%
% Sfrutta l'idea che Pac-Man per cercare di fare più punti, aprofittando
% del fatto che i fantasmi non possono mangiarlo, preferirà muoversi in
% zone in cui ci sono ancora dei puntini a disposizione da mangiare.
%
% Il fantasma rosso si sposta verso la cella vuota a maggiore distanza
% da Pac-Man.
%
fuga_rosso(FX,FY,NX,NY,Dir):-
	pacman(PX,PY),  % istanzia X e Y con l'attuale posizione di pacman

	findall([X,Y],vuota(X,Y),CelleVuote),    %celle che ora sono vuote

	maggiore_distanza(CelleVuote,PX,PY,_,[OX,OY]),  %scelta tra le cella vuote quella a distanza maggiore dalla posizione corrente di pacman
	%write('['),write(OX),write(','),write(OY),write(']'),
	(   \+obiettivo(_,rosso,_,_);
	     retract(obiettivo(_,rosso,_,_))),
	assert(obiettivo(fuga,rosso,OX,OY)),

	best(FX,FY,rosso,Percorso),
	%scrivi(Percorso),
	mossa(Percorso,[NX,NY],NuovoPercorso),    % [NX,NY] coordinate della cella in cui muoversi per avvicinarsi all'obiettivo
        incrementa_posizione(FX,FY,Dir,1,NX,NY),  % conosce la posizione attuale e la successiva,lo spostamento è unitario-> Direzione percorsa

	ritratta(percorso,rosso),
        assert(percorso(fuga,rosso,NuovoPercorso)),

	ritratta(fantasma,rosso),
	assert(fantasma(rosso,FX,FY)).

% FUGA ROSA
%
% Data la posizione attuale del fantasma rosso, colcola le coorinate
% della cella in cui spostarsi per fuggire da Pac-Man.
%
% Sfrutta il fatto che il fantasma arancione mantiene sempre una certa
% distanza tra lui e Pac-Man per definire dove fuggire.
%
% Il fantasma rosa si sposta verso la cella che ha ancora un puntino e
% si trova massima distanza da Pac-Man e dal fantasma arancione
%
fuga_rosa(RSX,RSY,ARX,ARY,NX,NY,Dir):-
	pacman(X,Y),
	findall([X1,Y1],puntino(X1,Y1),Puntini),

	obiettivo_fuga_rosa(Puntini,ARX,ARY,X,Y,_,[OX,OY]),

	(   \+obiettivo(_,rosa,_,_);
	     retract(obiettivo(_,rosa,_,_))),
        assert(obiettivo(fuga,rosa,OX,OY)),

	best(RSX,RSY,rosa,Percorso),
	%scrivi(Percorso),
	mossa(Percorso,[NX,NY],NuovoPercorso),    % [NX,NY] coordinate della cella in cui muoversi per avvicinarsi all'obiettivo
	incrementa_posizione(RSX,RSY,Dir,1,NX,NY),  % conosce la posizione attuale e la successiva,lo spostamento è unitario-> Direzione percorsa

	ritratta(percorso,rosa),        
        assert(percorso(fuga,rosa,NuovoPercorso)),

	ritratta(fantasma,rosa),  	
	assert(fantasma(rosa,NX,NY)).

% FUGA AZZURRO
%
% Data la posizione attuale del fantasma rosso, colcola le coorinate
% della cella in cui spostarsi per fuggire da Pac-Man.
%
% Il fantasma azzurro si comporta esattamente come nella modalità
% std, con la differenza che questa volta si muove in direzione
% opposta a quella della medusa rossa.
%
fuga_azzurro(azzurro,FX,FY,FRX,FRY,DirezioneFR,DirezionePacMan,NX,NY,Dir):-
	direzione_opposta(DirezioneFR,DirFR),  %direzione opposta rispetto a quella del fantasma rosso
	mossa_fantasma(azzurro,FX,FY,FRX,FRY,DirFR,DirezionePacMan,NX,NY,Dir).



% FUGA ARANCIONE
%
% Data la posizione attuale del fantasma rosso, colcola le coorinate
% della cella in cui spostarsi per fuggire da Pac-Man.
%
% Il fantasma arancione si comporta esattamente come nella modalità std,
% con la differenza che in questo caso può cambiare immediatamente la
% direzione non deve attendere di essere arrivato ad una intersezione.
%
fuga_arancione(arancione,FX,FY,NX,NY,Dir):-
	mossa_fantasma(arancione,FX,FY,NX,NY,Dir).



% DIREZIONE OPPOSTA
%
% Data una direzione, restituisce l'opposta.
%
direzione_opposta(X,Y):-
	(
            X=:=0->Y is 2;
	    X=:=2->Y is 0;
	    X=:=3->Y is 1;
	    X=:=1->Y is 3
	).



% OBIETTIVO FUGA ROSA
%
% Data una lista di celle contenenti puntini,la posizione di Pac-Man e
% quella della medusa, individua la cella a distanza maggiore da Pac-Man
% e dalla medusa arancione.
%
obiettivo_fuga_rosa([],_,_,_,_,0,[]).
obiettivo_fuga_rosa([[X,Y]|C],ARX,ARY,PX,PY,MaxAttuale,CellaAttuale):-
       obiettivo_fuga_rosa(C,ARX,ARY,PX,PY,Max,Cella),
       distanza([X,Y],[ARX,ARY],Distanza1),
       distanza([X,Y],[PX,PY],Distanza2),
       Dist is round(Distanza1+Distanza2),
       MaxAttuale is max(Dist,Max),
       (
	    MaxAttuale is Dist,     %la cella analizzata è alla massima distanza
            !,
	    CellaAttuale = [X,Y]   %aggiorno il valore della cella attuale
	    ;
	    CellaAttuale = Cella   %altrimenti, confermo la cella identificata al passo precedente
       ).



% MAGGIORE DISTANZA
%
% Data una lista di celle vuote e la posizione di Pac-Man, individua la
% cella a distanza maggiore da Pac-Man.
%
maggiore_distanza([],_,_,0,[]).
maggiore_distanza([T|C],PX,PY,MaxAttuale,CellaAttuale):-
	maggiore_distanza(C,PX,PY,Max,Cella),
	distanza(T,[PX,PY],Distanza),
        Dist is round(Distanza),

	MaxAttuale is max(Dist,Max),
	(
            MaxAttuale is Dist,
            !,
	    CellaAttuale = T
	    ;
	    CellaAttuale = Cella
	 ).



