package behav;

import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import pacman.actors.Ghost;

public class FugaFantasmaAzzurro extends Behaviour {

	private int c = 0;
	private Ghost fantasmaAzzurro;


	public FugaFantasmaAzzurro(Agent schedAgent,  Ghost fantasmaAzzurro) {
	
		super(schedAgent);
		this.fantasmaAzzurro = fantasmaAzzurro;
	}

	public void action(){
			
		String goal = "set_iniziale_fuga";
		
		Query q = new Query(goal);
		
		if (q.hasSolution()) {
			
			//System.out.println(goal.toString());
			
		}
	
		String goal2 = "assert(pacman("+fantasmaAzzurro.pacman.col+",-"+fantasmaAzzurro.pacman.row+")),assert(modalita("+fantasmaAzzurro.color+",fuga)),fuga("+fantasmaAzzurro.color+","+fantasmaAzzurro.col+",-"+fantasmaAzzurro.row+",NX,NY,Dir)";
		
		//System.out.println("Fuga goal sarebbe: "+ goal2);
			
		q = new Query(goal2);
		
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

				fantasmaAzzurro.col = Integer.parseInt(posX.toString());
			
				fantasmaAzzurro.x = fantasmaAzzurro.col * 8 - 4 - 24;
			
			} else fantasmaAzzurro.col = Integer.parseInt(posX.toString());
			
			fantasmaAzzurro.row = -(Integer.parseInt(posY.toString()));
			
			fantasmaAzzurro.direction = Integer.parseInt(nuovaDirezione.toString());

			//System.out.println("Fuga mossa "+fantasmaAzzurro.color+": " + fantasmaAzzurro.col + " " + fantasmaAzzurro.row + " " + fantasmaAzzurro.direction);

			c++;

		}	
	}
	
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		if (c > 0) return true;
	 		else return false;
	}

}
