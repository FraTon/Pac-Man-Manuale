package behav;

import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import pacman.actors.Ghost;

public class MossaFantasmaRosa extends Behaviour {

	private int c = 0; //inizializzazione contatore
	private Ghost fantasmaRosa; //definizione oggetto Ghost
	

	public MossaFantasmaRosa(Agent schedAgent, Ghost fantasmaRosa) {
	
		super(schedAgent); //chiama il costruttore della superclasse
		//inizializzo delle variabili private
		this.fantasmaRosa = fantasmaRosa;
	}

	public void action(){
		
		//goal: set_modalita(rosa,std),assert(pacman(x,y)),mossa_fantasma(rosa,x,y,dirPacman,NX,NY,Dir)
		String goal = "set_modalita("+fantasmaRosa.color+",std),assert(pacman("+fantasmaRosa.pacman.col+",-"+fantasmaRosa.pacman.row+")),mossa_fantasma("+fantasmaRosa.color+","+fantasmaRosa.col+",-"+fantasmaRosa.row+","+fantasmaRosa.pacman.direction+",NX,NY,Dir)";
		
		//System.out.println("Il goal sarebbe: "+ goal); //stampa di debug
			
		Query q = new Query(goal); //creazione di una query per il lancio del goal 
		
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

				fantasmaRosa.col = Integer.parseInt(posX.toString());
			
				fantasmaRosa.x = fantasmaRosa.col * 8 - 4 - 24;
			
			} else fantasmaRosa.col = Integer.parseInt(posX.toString()); //aggiornamento della posizione del fantasma lungo x
			
			fantasmaRosa.row = -(Integer.parseInt(posY.toString())); //aggiornamento della posizione del fantasma lungo y
			
			fantasmaRosa.direction = Integer.parseInt(nuovaDirezione.toString()); //aggiornamento della direzione del fantasma
			
			//System.out.println("Mossa "+fantasmaRosa.color+": " + fantasmaRosa.col + " " + fantasmaRosa.row + " " + fantasmaRosa.direction); //stampa di debug
			
			c++; //incremento del contatore

		}
		
		String goal2 = "asserisci_vecchio_pacman,ritratta(pacman)"; //aggiornamento prolog che asserisce la vecchia posizione di pacman e ritrae la posizione attuale
		
		q = new Query(goal2); //creazione di una query per il lancio del goal 
		
		//lancio del goal e se ha almeno una solzione
		if (q.hasSolution()) {
			
			//System.out.println(goal2.toString()); //stampa di debug
			
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
