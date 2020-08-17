package behav;

import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import pacman.actors.Ghost;

public class FugaFantasmaRosso extends Behaviour {

	private int c = 0; //inizializzazione contatore
	private Ghost fantasmaRosso; //definizione oggetto Ghost


	public FugaFantasmaRosso(Agent schedAgent, Ghost fantasmaRosso) {
	
		super(schedAgent); //chiama il costruttore della superclasse
		//inizializzo delle variabili private
		this.fantasmaRosso = fantasmaRosso;
	}

	public void action(){
		
		//goal: set_iniziale_fuga che permette di settare i parametri iniziali per la modalità fuga
		String goal = "set_iniziale_fuga";
		
		Query q = new Query(goal); //creazione di una query per il lancio del goal 
		
		//lancio del goal e se ha almeno una solzione
		if (q.hasSolution()) {
			
			//System.out.println(goal); //stampa di debug
			
		}
		
		//goal: assert(pacman(x,y)),assert(modalita(rosso,fuga)),fuga_rosso(RX,RY,NRX,NRY,DirR)
		String goal2 = "assert(pacman("+fantasmaRosso.pacman.col+",-"+fantasmaRosso.pacman.row+")),assert(modalita("+fantasmaRosso.color+",fuga)),fuga_rosso("+fantasmaRosso.col+",-"+fantasmaRosso.row+",NX,NY,Dir)";
		
		//System.out.println("Fuga goal sarebbe: "+ goal2); //stampa di debug
			
		q = new Query(goal2); //creazione di una query per il lancio del goal
		
		//lancio del goal e se ha almeno una solzione
		if (q.hasSolution()) {
			
			//Memorizzio tutte la soluzione
			Map<String, Term> sol = q.getSolution();
			//Prendo il valore di NX
			Term posX = sol.get("NX");
			//Prendo il valore di NY
			Term posY = sol.get("NY");
			//Prendo il valore di Direction
			Term nuovaDirezione = sol.get("Dir");
			
			//se la nuova mossa è agli estremi del tunnel, allora aggiorno con la posizione x giusta della grafica
			if (((Integer.parseInt(posY.toString()) == -14) && (Integer.parseInt(posX.toString()) == 31)) || 
				((Integer.parseInt(posY.toString()) == -14) && (Integer.parseInt(posX.toString()) == 4))) {

				fantasmaRosso.col = Integer.parseInt(posX.toString());
			
				fantasmaRosso.x = fantasmaRosso.col * 8 - 4 - 24;
			
			} else fantasmaRosso.col = Integer.parseInt(posX.toString()); //aggiornamento della posizione del fantasma lungo x
			
			fantasmaRosso.row = -(Integer.parseInt(posY.toString())); //aggiornamento della posizione del fantasma lungo y
			
			fantasmaRosso.direction = Integer.parseInt(nuovaDirezione.toString()); //aggiornamento della direzione del fantasma	
	
			//System.out.println("Fuga mossa "+ fantasmaRosso.color +": "+ fantasmaRosso.col + " " + fantasmaRosso.row + " " + fantasmaRosso.direction); //stampa di debug
			
    		Ghost.colonnaRosso = fantasmaRosso.col; //salvataggio della nuova posizione lungo x in una variabile aggiuntiva in quanto necessaria per il calcolo della nuova posizione del fantasma arancione
    		Ghost.rigaRosso = fantasmaRosso.row; //salvataggio della nuova posizione lungo y in una variabile aggiuntiva in quanto necessaria per il calcolo della nuova posizione del fantasma arancione
    		Ghost.direzioneRosso = fantasmaRosso.direction; //salvataggio della nuova direzione posizione in una variabile aggiuntiva in quanto necessaria per il calcolo della nuova posizione del fantasma arancione

			c++; //incremento del contatore

		}
	}
	
	//metodo che definisce fino a quando l'agente deve eseguire questo behaviour
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		if (c > 0) return true;
	 		else return false;
	}

}
