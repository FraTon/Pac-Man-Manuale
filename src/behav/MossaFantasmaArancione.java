package behav;

import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import pacman.actors.Ghost;

public class MossaFantasmaArancione extends Behaviour {

	private int c = 0;
	private Ghost fantasmaArancione;
	

	public MossaFantasmaArancione(Agent schedAgent, Ghost fantasmaArancione) {
	
		super(schedAgent);
		this.fantasmaArancione = fantasmaArancione;
		
	}

	public void action(){
		
		String goal ="set_modalita("+fantasmaArancione.color+",std),assert(pacman("+fantasmaArancione.pacman.col+",-"+fantasmaArancione.pacman.row+")),mossa_fantasma("+fantasmaArancione.color+","+fantasmaArancione.col+",-"+fantasmaArancione.row+",NX,NY,Dir)";
			
		//System.out.println("Il goal sarebbe: "+ goal);
			
		Query q = new Query(goal);
		
		if (q.hasSolution()) {

			//Memorizzio tutte la soluzione
			Map<String, Term> sol = q.getSolution();
			//Prendo il valore di NX
			Term posX = sol.get("NX");
			//Prendo il valore di NY
			Term posY = sol.get("NY");
			//Prendo il valore di Direction
			Term nuovaDirezione = sol.get("Dir");
			
			//Aggiornamento della grafica per il tunnel
			if (((Integer.parseInt(posY.toString()) == -14) && (Integer.parseInt(posX.toString()) == 31)) || 
				((Integer.parseInt(posY.toString()) == -14) && (Integer.parseInt(posX.toString()) == 4))) {

				fantasmaArancione.col = Integer.parseInt(posX.toString());
			
				fantasmaArancione.x = fantasmaArancione.col * 8 - 4 - 24;
			
			} else fantasmaArancione.col = Integer.parseInt(posX.toString());
			
			fantasmaArancione.row = -(Integer.parseInt(posY.toString()));
			
			fantasmaArancione.direction = Integer.parseInt(nuovaDirezione.toString());
			
			//System.out.println("Mossa "+fantasmaArancione.col+": " + fantasmaArancione.col + " " + fantasmaArancione.row + " " + fantasmaArancione.direction);

			//E' necessario tenere traccia di questi valori, per poter calcolare la fuga_rosa
			//Ghost.colonnaArancione = fantasmaArancione.col;
    		//Ghost.rigaArancione = fantasmaArancione.row;	    		

			c++;

		}
		
		String goal2 = "asserisci_vecchio_pacman,ritratta(pacman)";
		
		q = new Query(goal2);
		
		if (q.hasSolution()) {
			
			//System.out.println(goal2);
			
		}
	}	
	
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		if (c > 0) return true;
	 		else return false;
	}
}
