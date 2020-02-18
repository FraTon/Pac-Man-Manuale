:-['pac-man'].
:-[database].
%NECESSARIO ASSERIRE POSIZIONE FANTASMA FX FY ?????????????????????
%
%
%solo gli occhi del fantasma
%
% all'inizio chiamiamo questa, poi siccome non deve fare solo un passo
% ma deve proprio arrivarci, controlliamo ad ogni istante se ci
% è arrivato e solo dopo che ci è arrivato
%
%
% OPPURE GLI RITORNO L'INTERO PERCORSO

% fantasma colore da
% fuga-> std
% controllare
% bene tutti i passaggi di modalita
%
% forse la posizione non gli serve perchè è quella asserita
%
% torna indietro la nuova posizione del fantasma
%
%
%
% FANTASMA MANGIATO
%
% Data un fantasma, la sua posizione attuale restituisce la posizione
% dove deve spostarsi per tornare alla casa base.
%

fantasma_mangiato(rosso,FX,FY,NX,NY,Dir):-

	set_modalita(rosso,mangiato),

	fantasma_start(rosso,X,Y),

	retract(obiettivo(mangiato,rosso,_,_)),
	assert(obiettivo(mangiato,rosso,X,Y)),  %nuovo obiettivo è tornare al punto di partenza

	best(FX,FY,rosso,Percorso), %calcola percorso ottimo verso il suo obiettivo
	scrivi(Percorso),

	mossa(Percorso,[NX,NY],NuovoPercorso),  % [NX,NY] coordinate della cella in cui muoversi per avvicinarsi all'obiettivo
	incrementa_posizione(FX,FY,Dir,1,NX,NY),  % conosce la posizione attuale e la successiva,lo spostamento è unitario-> Direzione percorsa

	retract(percorso(_,rosso,_)),
	assert(percorso(mangiato,rosso,NuovoPercorso)). %asserisce percorso


fantasma_mangiato(arancione,FX,FY,NX,NY,Dir):-

	set_modalita(arancione,mangiato),

	fantasma_start(arancione,X,Y),

	retract(obiettivo(_,arancione,_,_)),
	assert(obiettivo(mangiato,arancione,X,Y)),  %nuovo obiettivo è tornare al punto di partenza

	best(FX,FY,arancione,Percorso), %calcola percorso ottimo verso il suo obiettivo
	scrivi(Percorso),

	mossa(Percorso,[NX,NY],NuovoPercorso),  % [NX,NY] coordinate della cella in cui muoversi per avvicinarsi all'obiettivo
	incrementa_posizione(FX,FY,Dir,1,NX,NY),  % conosce la posizione attuale e la successiva,lo spostamento è unitario-> Direzione percorsa

	retract(percorso(_,arancione,_)),
        assert(percorso(mangiato,arancione,NuovoPercorso)). %asserisce percorso


fantasma_mangiato(rosa,FX,FY,NX,NY,Dir):-

	set_modalita(rosa,mangiato),

	fantasma_start(rosa,X,Y),

	retract(obiettivo(_,rosa,_,_)),
	assert(obiettivo(mangiato,rosa,X,Y)),  %nuovo obiettivo è tornare al punto di partenza

	best(FX,FY,rosa,Percorso), %calcola percorso ottimo verso il suo obiettivo
	scrivi(Percorso),

	mossa(Percorso,[NX,NY],NuovoPercorso),  % [NX,NY] coordinate della cella in cui muoversi per avvicinarsi all'obiettivo
	incrementa_posizione(FX,FY,Dir,1,NX,NY),  % conosce la posizione attuale e la successiva,lo spostamento è unitario-> Direzione percorsa

	retract(percorso(_,rosa,_)),
        assert(percorso(mangiato,rosa,NuovoPercorso)). %asserisce percorso


fantasma_mangiato(azzurro,FX,FY,NX,NY,Dir):-

	set_modalita(azzurro,mangiato),

	fantasma_start(azzurro,X,Y),

	retract(obiettivo(_,azzurro,_,_)),
	assert(obiettivo(mangiato,azzurro,X,Y)),  %nuovo obiettivo è tornare al punto di partenza

	best(FX,FY,azzurro,Percorso), %calcola percorso ottimo verso il suo obiettivo
	scrivi(Percorso),

	mossa(Percorso,[NX,NY],NuovoPercorso),  % [NX,NY] coordinate della cella in cui muoversi per avvicinarsi all'obiettivo
	incrementa_posizione(FX,FY,Dir,1,NX,NY),  % conosce la posizione attuale e la successiva,lo spostamento è unitario-> Direzione percorsa

	retract(percorso(_,azzurro,_)),
        assert(percorso(mangiato,azzurro,NuovoPercorso)). %asserisce percorso

