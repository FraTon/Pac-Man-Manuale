%:-use_module(library(csv)).


:-dynamic pacman_start/2.
:-dynamic fantasma_start/3.
:-dynamic muro/2.
:-dynamic cancello/2.
:-dynamic vitamina/2.
:-dynamic puntino/2.
:-dynamic vuota/2.

/*
% CREA DATABASE
%
% Importa i dati dai file csv specificati e li esporta creando un
% database.
%
crea_database(Csv1,Csv2):-
	working_directory(_,'C:/Users/Utente/Desktop/PAC-MAN modificabili'),   %specifica la directory in cui si trovano i file csv

	importa(Csv1,Csv2),
	esporta.


% IMPORTA
%
% Importa dati dai file csv.
%
importa(Csv1,Csv2) :-

	csv_read_file(Csv1,Righe,[separator(0',),ignore_quotes(true),convert(true),functor(cella),arity(6)]),
	maplist(assert,Righe),

	csv_read_file(Csv2,Righe2,[separator(0',),ignore_quotes(true),convert(true),functor(personaggio),arity(18)]),
	maplist(assert,Righe2).
*/

% ESPORTA
%
% Esporta i dati importati in un database.
%
esporta:-
        %working_directory(_,'C:/Users/Utente/Desktop/PAC-MAN modificabili'),
        dinamicita,
	esporta_pacman,  %posizione iniziale pacman
	esporta_fantasma, %posizioni iniziali fantasmi

	esporta_cancello, %posizione cancello

	esporta_vuota,  %celle vuote

	esporta_muro,  %pareti

	esporta_puntino,   %cibo
	esporta_vitamina.   %vitamine


dinamicita:-
	tell('database.pl'),

	write(':-dynamic vitamina/2.'),
	nl,
	write(':-dynamic puntino/2.'),
	nl,
	write(':-dynamic vuota/2.'),
	nl,
	nl,
	fail.
dinamicita:-
	told.

% ESPORTA PACMAN
%
% Esporta la posizione iniziale di Pac-Man asserendola nel database.
%
esporta_pacman:-
	pacman_start(X,Y),
	append('database.pl'),

	write('pacman_start('),write(X),
	write(','),write(Y),
	write(').'),
	nl,
	fail.

esporta_pacman:-
	nl,
	told.


% ESPORTA FANTASMA
%
% Esporta la posizione iniziale di tutti i fantasmi asserendola nel
% database.
%
esporta_fantasma:-
	fantasma_start(rosso,X,Y),

	append('database.pl'),

	write('fantasma_start('),write(rosso),
	write(','),write(X),
	write(','),write(Y),
	write(').'),
	nl,
	fail.


esporta_fantasma:-
        fantasma_start(azzurro,X,Y),

	append('database.pl'),

	write('fantasma_start('),write(azzurro),
	write(','),write(X),
	write(','),write(Y),
	write(').'),
	nl,
	fail.


esporta_fantasma:-
	fantasma_start(rosa,X,Y),

	append('database.pl'),

	write('fantasma_start('),write(rosa),
	write(','),write(X),
	write(','),write(Y),
	write(').'),
	nl,
	fail.


esporta_fantasma:-

	fantasma_start(arancione,X,Y),

	append('database.pl'),

	write('fantasma_start('),write(arancione),
	write(','),write(X),
	write(','),write(Y),
	write(').'),
	nl,
	fail.

esporta_fantasma:-
	nl,
	told.


% ESPORTA MURO
%
% Esporta la posizione di tutti i muri asserendola nel
% database.
%
esporta_muro:-
	muro(X,Y),
	append('database.pl'),

	write('muro('),write(X),
	write(','),write(Y),
	write(').'),
	nl,
	fail.

esporta_muro:-
	nl,
	told.

% ESPORTA MURO
%
% Esporta la posizione del cancello asserendola nel
% database.
%
esporta_cancello:-
	cancello(X,Y),
	append('database.pl'),

	write('cancello('),write(X),
	write(','),write(Y),
	write(').'),
	nl,
	fail.

esporta_cancello:-
	nl,
	told.


% ESPORTA VUOTA
%
% Esporta la posizione delle celle vuote asserendola nel
% database.
%
esporta_vuota:-
	vuota(X,Y),
	append('database.pl'),

	write('vuota('),write(X),
	write(','),write(Y),
	write(').'),
	nl,
	fail.

esporta_vuota:-
	nl,
	told.



% ESPORTA PUNTINO
%
% Esporta la posizione delle celle che contengo del cibo asserendola nel
% database.
%
esporta_puntino:-
	puntino(X,Y),
	append('database.pl'),

        write('puntino('),
	write(X),
	write(','),
	write(Y),
	write(').'),
	nl,
	fail.

esporta_puntino:-
	nl,
	told.


% ESPORTA VITAMINA
%
% Esporta la posizione delle vitamine che contengo del cibo asserendola
% nel database.
%
esporta_vitamina:-
	vitamina(X,Y),

	append('database.pl'),

	write('vitamina('),write(X),
	write(','),write(Y),
	write(').'),
	nl,
	fail.

esporta_vitamina:-
	nl,
	told.



