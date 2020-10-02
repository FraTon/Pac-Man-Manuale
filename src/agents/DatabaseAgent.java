package agents;


import behav.AssertCellaVuota;
import behav.AssertFantasma;
import behav.AssertMuro;
import behav.AssertPacman;
import behav.AssertPuntino;
import behav.AssertVitamina;
import behav.QueryDatabase;
import jade.core.Agent;

public class DatabaseAgent extends Agent {

	public void setup(){
		
		//Definizione della matrice di gioco, per rimanere coerenti con la grafica implementata		
		int maze1[][] = {
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 3, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 3, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1},
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
		    };

				
		//Stampa nome agente inizializzato
		System.out.println(this.getLocalName() + ": init...");

		//Definizione di un array per ogni tipologia di celle e elementi nel labirinto
		Object[] vuota = new Object[100]; //array per le celle vuote
		Object[] muro = new Object[1000]; //array per le celle muro
		Object[] puntino = new Object[1000]; //array per i puntini non mangiati
		Object[] vitamina = new Object[5]; //array per le vitamine non mangiate
		

		//inizializzazione dei contatori a 0
		int v = 0, m = 0, pu = 0, vi = 0;
		
		//per ogni riga
		for(int row = 0; row < 31; row++) {
			
			//per ogni colonna
			for(int col = 0; col < 36; col++) {
		
				//esclusi i bordi laterali
				if ((col > 3) && (col < 32)){
					
					//se la cella vale 0, inserisci nell'array la posizione dell'elemento vuoto in cui
					//le colonne sono crescenti verso destra (verso positivo dell'assse x)
					//le righe sono decrescenti verso il basso (verso negativo dell'asse y)
					if (maze1[row][col] == 0) {
						vuota[v] = (col+",-"+row);
						v++;
					
					}else if (maze1[row][col] == 1) { //se la cella vale 1, inserisci nell'array la posizione dell'elemento muro
						muro[m] = (col+",-"+row);
						m++;				
					
					}else if (maze1[row][col] == 2) { //se la cella vale 2, inserisci nell'array la posizione dell'elemento puntino
						puntino[pu] = (col+",-"+row);
						pu++;	
						
					}else if (maze1[row][col] == 3) { //se la cella vale 3, inserisci nell'array la posizione dell'elemento vitamina
						vitamina[vi] = (col+",-"+row);
						vi++;							
					}
				}
			}
		}	
			
		
		//Pacman
		//Aggiunta di un behaviour per asserire la posizione di pacman su prolog
		this.addBehaviour(new AssertPacman(
				//agente che deve schedulare il behaviour
				this,
				//nome del file
				"vecchio_pacman",
				//goal
				"18,-23"
				));
		
		//Fantasmi
		//Aggiunta di un behaviour per asserire la posizione dei fantasmi su prolog
		this.addBehaviour(new AssertFantasma(
				this,
				"fantasma_start",
				"rosso,18,-11",
				"azzurro,18,-14",
				"rosa,16,-14",
				"arancione,20,-14"
				));
		
		//CelleVuote
		//Se l'array delle celle vuore non è vuoto
		if (vuota != null){
			//Aggiunta di un behaviour per asserire la posizione delle celle vuote su prolog
			this.addBehaviour(new AssertCellaVuota(
					this,
					"vuota",
					vuota
					));
			}
		
		//Muri
		//Se l'array dei muri non è vuoto
		if (muro != null){
			//Aggiunta di un behaviour per asserire la posizione dei muri su prolog
			this.addBehaviour(new AssertMuro(
					this,
					"muro",
					muro
					));
			}

		//Vitamina
		//Se l'array delle vitamine non è vuoto
		if (vitamina != null){
			//Aggiunta di un behaviour per asserire la posizione delle vitamine su prolog
			this.addBehaviour(new AssertVitamina(
					this,
					"vitamina",
					vitamina
					));
			}
		
		//Puntino
		//Se l'array dei puntini non è vuoto
		if (puntino != null){
			//Aggiunta di un behaviour per asserire la posizione dei puntini su prolog
			this.addBehaviour(new AssertPuntino(
					this,
					"puntino",
					puntino
					));
			}
		
		//Creazione Database
		//Aggiunta di un behaviour per asserire la creazione del database
		this.addBehaviour(new QueryDatabase(
				this,
				"crea_database.pl",
				"esporta"
			));		
		
		//Aggiunta di un behaviour per settare delle operazioni iniziali di partenza
		this.addBehaviour(new QueryDatabase(
				this,
				"operazioni_iniziali.pl",
				"operazioni_iniziali"
			));

		//Aggiunta di un behaviour per ritrarre la vecchia posizione di pacman che 
		//prima viene asserita per mantenere in memoria la vecchia posizione di pacman
		this.addBehaviour(new QueryDatabase(
				this,
				"nuovo_pacman.pl",
				"asserisci_vecchio_pacman,ritratta(pacman)"
			));
		
		//Aggiunta di un behaviour per settare la fuga dei fantasmi
		this.addBehaviour(new QueryDatabase(
				this,
				"fuga_fantasmi.pl",
				"set_iniziale_fuga"
			));
	}
}
