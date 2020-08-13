package behav;

import org.jpl7.Query;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class RetractPuntino extends MyOneShot {

	private String predName; //stringa contenente il predicato
	private String argument; //stringa contenente l'argomento del predicato

	public RetractPuntino(Agent schedAgent, String predName, String argument) {
		
		super(schedAgent); //chiama il costruttore della superclasse
		//inizializzo delle variabili private
		this.predName = predName;
		this.argument = argument;
	}


	public void action(){
			
		//goal: retract(predName+argument)
		String goal = "retract(" + predName + argument + ")";
		Query q = new Query(goal); //creazione di una query per il lancio del goal

		//lancio del goal e se ha almeno una solzione
		if (q.hasSolution()){
			
			//myPrint(goal); //stampa di debug
		}
	}	
}
