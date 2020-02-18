package behav;

import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import pacman.actors.Ghost;

public class MossaFantasmaAzzurro extends Behaviour{

	private int c = 0;
	private Ghost fantasmaAzzurro;


	public MossaFantasmaAzzurro(Agent schedAgent,  Ghost fantasmaAzzurro) {
	
		super(schedAgent);
		this.fantasmaAzzurro = fantasmaAzzurro;
	}

	public void action(){
		
		String goal = "set_modalita("+fantasmaAzzurro.color+",std),assert(pacman("+fantasmaAzzurro.pacman.col+",-"+fantasmaAzzurro.pacman.row+")),mossa_fantasma("+fantasmaAzzurro.color+","+fantasmaAzzurro.col+",-"+fantasmaAzzurro.row+","+Ghost.colonnaRosso+",-"+Ghost.rigaRosso+","+Ghost.direzioneRosso+","+fantasmaAzzurro.pacman.direction+",NX,NY,Dir)";
		
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

				fantasmaAzzurro.col = Integer.parseInt(posX.toString());
			
				fantasmaAzzurro.x = fantasmaAzzurro.col * 8 - 4 - 24;
			
			} else fantasmaAzzurro.col = Integer.parseInt(posX.toString());
			
			fantasmaAzzurro.row = -(Integer.parseInt(posY.toString()));
			
			fantasmaAzzurro.direction = Integer.parseInt(nuovaDirezione.toString());

			//System.out.println("Mossa "+fantasmaAzzurro.color+": " + fantasmaAzzurro.col + " " + fantasmaAzzurro.row + " " + fantasmaAzzurro.direction);

			c++;

		}	
		
		String goal2 = "asserisci_vecchio_pacman,ritratta(pacman)";
		
		q = new Query(goal2);
		
		if (q.hasSolution()) {
			
			//System.out.println(goal2.toString());
			
		}		
	}
	
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		if (c > 0) return true;
	 		else return false;
	}
}
