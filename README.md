# PacMan-Prolog
Pacman Manuale implementato attraverso Prolog e jade

# Configurazione
Per poter utilizzare Jade è stato necessario scaricare dal link: https://jade.tilab.com/, e dopo aver creato il progetto, abbiamo dovuto importare le librerie di Jade, nello specifico:

- jade.jar
- commons-codec

Il passaggio successivo è stato quello di configurare l'avvio della piattaforma *PlatformCreator*. Ciò è stato possibile andando a settare su "Run Configurazione", una volta selezionato il progetto Jade creato,  specificando nel campo "Main Class" *utils.PlatformCreator*.
E' necessario specificare che al momento di creazione del progetto ci sono stati problemi con la libreria jade.jar e la versione di Java 11, motivo per il quale abbiamo dovuto scaricare una versione precedente. Nello specifico abbiamo utilizzato Java8 (JRE 1.8) risolvendo così i problemi di compatibilità.
