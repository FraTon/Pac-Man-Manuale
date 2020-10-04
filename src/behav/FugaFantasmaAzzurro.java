package behav;

import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import pacman.actors.Ghost;

public class FugaFantasmaAzzurro extends Behaviour {

	private int c = 0; //inizializzazione contatore
	private Ghost fantasmaAzzurro; //definizione oggetto Ghost


	public FugaFantasmaAzzurro(Agent schedAgent,  Ghost fantasmaAzzurro) {
	
		super(schedAgent); //chiama il costruttore della superclasse
		//inizializzo delle variabili private
		this.fantasmaAzzurro = fantasmaAzzurro;
	}

	public void action(){
			
		//goal: assert(pacman(x,y)),assert(modalita(azzurro,fuga)),fuga_azzurro(AX,AY,RX,RY,DirezioneRosso,DirezionePacMan,NAX,NAY,DirA)
		String goal = "assert(pacman("+fantasmaAzzurro.pacman.col+",-"+fantasmaAzzurro.pacman.row+")),assert(modalita("+fantasmaAzzurro.color+",fuga)),fuga_azzurro("+fantasmaAzzurro.col+",-"+fantasmaAzzurro.row+","+Ghost.colonnaRosso+",-"+Ghost.rigaRosso+","+Ghost.direzioneRosso+","+fantasmaAzzurro.pacman.direction+",NX,NY,Dir)";
				
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

				fantasmaAzzurro.col = Integer.parseInt(posX.toString());
			
				fantasmaAzzurro.x = fantasmaAzzurro.col * 8 - 4 - 24;
			
			} else fantasmaAzzurro.col = Integer.parseInt(posX.toString()); //aggiornamento della posizione del fantasma lungo x
			
			fantasmaAzzurro.row = -(Integer.parseInt(posY.toString())); //aggiornamento della posizione del fantasma lungo y
			
			fantasmaAzzurro.direction = Integer.parseInt(nuovaDirezione.toString()); //aggiornamento della direzione del fantasma	

			//System.out.println("Fuga mossa "+fantasmaAzzurro.color+": " + fantasmaAzzurro.col + " " + fantasmaAzzurro.row + " " + fantasmaAzzurro.direction); //stampa di debug

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
