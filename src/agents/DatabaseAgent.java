package agents;


import behav.AssertCancello;
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
		        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 4, 4, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
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

		Object[] vuota = new Object[100];
		Object[] muro = new Object[1000];
		Object[] puntino = new Object[1000];
		Object[] vitamina = new Object[5];
		Object[] cancello = new Object[3];
		

		int v = 0, m = 0, pu = 0, vi = 0, c = 0;
		
		for(int row = 0; row < 31; row++) {

			for(int col = 0; col < 36; col++) {
		
				if ((col > 3) && (col < 32)){
									
					if (maze1[row][col] == 0) {
						vuota[v] = (col+",-"+row);
						v++;
					}else if (maze1[row][col] == 1) {
						muro[m] = (col+",-"+row);
						m++;							
					}else if (maze1[row][col] == 2) {
						puntino[pu] = (col+",-"+row);
						pu++;							
					}else if (maze1[row][col] == 3) {
						vitamina[vi] = (col+",-"+row);
						vi++;							
					}else if (maze1[row][col] == 4) {
						cancello[c] = (col+",-"+row);
						c++;							
					}
				}
			}
		}	
			
		//Pacman
		this.addBehaviour(new AssertPacman(
				this,
				"vecchio_pacman",
				"18,-23"
				));
		
		//Fantasmi
		this.addBehaviour(new AssertFantasma(
				this,
				"fantasma_start",
				"rosso,18,-11",
				"azzurro,18,-14",
				"rosa,16,-14",
				"arancione,20,-14"
				));
		
		//CelleVuote
		if (vuota != null){
			this.addBehaviour(new AssertCellaVuota(
					this,
					"vuota",
					vuota
					));
			}
		
		//Muri
		if (muro != null){
			this.addBehaviour(new AssertMuro(
					this,
					"muro",
					muro
					));
			}
		
		//Cancelli
		if (cancello != null){
			this.addBehaviour(new AssertCancello(
					this,
					"cancello",
					cancello
					));
			}

		//Vitamina
		if (vitamina != null){
			this.addBehaviour(new AssertVitamina(
					this,
					"vitamina",
					vitamina
					));
			}
		
		//Puntino
		if (puntino != null){
			this.addBehaviour(new AssertPuntino(
					this,
					"puntino",
					puntino
					));
			}
		
		//Crea DB con assert
		this.addBehaviour(new QueryDatabase(
				this,
				"crea_database.pl",
				"esporta"
			));		
		
		//esegui OPERAZIONI_INIZIALI
		this.addBehaviour(new QueryDatabase(
				this,
				"operazioni_iniziali.pl",
				"operazioni_iniziali"
			));
		
		this.addBehaviour(new QueryDatabase(
				this,
				"nuovo_pacman.pl",
				"asserisci_vecchio_pacman,ritratta(pacman)"
			));
		
		this.addBehaviour(new QueryDatabase(
				this,
				"fuga_fantasmi.pl",
				"set_iniziale_fuga"
			));
	}
}
