package behav;

import org.jpl7.Query;

import jade.core.Agent;

public class RetractPacman extends MyOneShot {

	private String filename; //stringa contenente il nome del file

	public RetractPacman(Agent schedAgent, String filename) {
		
		super(schedAgent); //chiama il costruttore della superclasse
		//inizializzo delle variabili private
		this.filename = filename;

	}


	public void action(){
		
		String consult = "consult('" + filename + "')"; //stringa da lanciare come goal per eseguire il file
		Query q = new Query(consult); //creazione di una query per il lancio del goal
		
		//lancio del goal e controllo se ha almeno una soluzione
		if (q.hasSolution()){
					
			//goal: retract(pacman(_,_)), ovvero va a ritrattare tutte la posizione attuale di pacman
			String goal = "retract(pacman(_,_))";
			q = new Query(goal);  //creazione di una query per il lancio del goal
	
			//lancio del goal e se ha almeno una solzione
			if (q.hasSolution()){
				
				//myPrint(goal); //stampa di debug
				
			}
		}
	}
}
