package behav;

import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import pacman.actors.Ghost;

public class FugaFantasmaRosa extends Behaviour {

	private int c = 0; //inizializzazione contatore
	private Ghost fantasmaRosa; //definizione oggetto Ghost
	

	public FugaFantasmaRosa(Agent schedAgent, Ghost fantasmaRosa) {
	
		super(schedAgent); //chiama il costruttore della superclasse
		//inizializzo delle variabili private
		this.fantasmaRosa = fantasmaRosa;
	}

	public void action(){
		
		//goal: assert(pacman(x,y)),assert(modalita(rosa,fuga)),fuga_rosa(RSX,RSY,ARX,ARY,NRSX,NRSY,DirRS)
		String goal = "assert(pacman("+fantasmaRosa.pacman.col+",-"+fantasmaRosa.pacman.row+")),assert(modalita("+fantasmaRosa.color+",fuga)),fuga_rosa("+fantasmaRosa.col+",-"+fantasmaRosa.row+","+Ghost.colonnaArancione+",-"+Ghost.rigaArancione+",NX,NY,Dir)";	
				
		//System.out.println("Fuga goal sarebbe: "+ goal); //stampa di debug
			
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
			
			//System.out.println("Fuga mossa "+fantasmaRosa.color+": " + fantasmaRosa.col + " " + fantasmaRosa.row + " " + fantasmaRosa.direction); //stampa di debug
			
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
