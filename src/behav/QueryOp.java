package behav;

import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class QueryOp extends MyOneShot {
	
	private String predName; //stringa contenente il predicato
	
	public QueryOp(Agent schedAgent, String predName) {
		
		super(schedAgent); //chiama il costruttore della superclasse
		//inizializzo delle variabili private
		this.predName = predName;
	}


	public void action(){
			
		//goal: predName(x,y)
		String goal = predName+"(X,Y)";
		Query q = new Query(goal);  //creazione di una query per il lancio del goal

		//q = new Query(goal);

		//Memorizzo tutte le soluzioni
		Map<String, Term>[] solutions = q.allSolutions();

		//Per ogni soluzione trovata
		for (Map<String, Term> sol : solutions){

			//Prendo il valore di X
			Term posx = sol.get("X");
			//Prendo il valore di Y
			Term posy = sol.get("Y");
			
			//Stampo la posizione (x,y)
			System.out.println("posizione ppppacman: " + posx.toString() + " " + posy.toString());	
		}
	}
}
