package behav;

import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

import jade.core.Agent;
import pacman.actors.Ghost;

public class MossaFantasmaArancione extends MyOneShot {

	private String filename;
	private String predName;
	private String colore;	
	private int X;
	private int Y;
	private String NX;
	private String NY;
	private String direction;


	public MossaFantasmaArancione(Agent schedAgent,  String filename, String predName, String colore, int X, int Y,String NX, String NY,String direction) {
	
		super(schedAgent);
		this.filename = filename;
		this.predName = predName;
		this.colore = colore;
		this.X = X;
		this.Y = Y;
		this.NX = NX;
		this.NY = NY;
		this.direction = direction;
	}

	public void action(){
		
		String consult = "consult('" + filename + "')";
		Query q = new Query(consult);
		
		if (q.hasSolution()){
			
			//myPrint(consult);
		
			String goal = predName + "("+colore+","+X+","+Y+",NX,NY,"+direction+")";
				
			myPrint("Il goal sarebbe: "+ goal);
				
			q = new Query(goal);
			
			//myPrint(q.toString());
			
			if (q.hasSolution()) {

				//Memorizzio tutte la soluzione
				Map<String, Term> sol = q.getSolution();
				//Prendo il valore di NX
				Term posX = sol.get(NX);
				//Prendo il valore di NY
				Term posY = sol.get(NY);
				//Prendo il valore di Direction
				Term nuovaDirezione = sol.get(direction);
				
				Ghost.col = Integer.parseInt(posX.toString());
				Ghost.row = -(Integer.parseInt(posY.toString()));
				Ghost.direction = Integer.parseInt(nuovaDirezione.toString());
				
				myPrint("Mossa: " + Ghost.col + " " + Ghost.row + " " + Ghost.direction);

			}			
		}
	}	
}
