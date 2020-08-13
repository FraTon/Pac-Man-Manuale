package behav;

import org.jpl7.Query;

import jade.core.Agent;

public class NuovaCellaVuota extends MyOneShot {
	
	private String predName; //stringa contenente il predicato
	private String argument; //stringa contenente l'argomento del predicato

	//costruttore al quale servono come parametri l'agente che schedula il behav e la stringa o gli elementi della stringa con la quale deve lanciare il goal
	public NuovaCellaVuota(Agent schedAgent, String predName, String argument) {
		
		super(schedAgent); //chiama il costruttore della superclasse
		//inizializzo delle variabili private
		this.predName = predName;
		this.argument = argument;
	}


	public void action(){
			
		//goal: assert(vuota(x,y))
		String goal = "assert(" + predName + argument + ")";
		Query q = new Query(goal); //creazione di una query per il lancio del goal

		//lancio del goal e controllo se ha almeno una soluzione
		if (q.hasSolution()){
			
			//stampa la stringa del goal (non il risultato), serve come debug
			//myPrint(goal);
		}
	}
}
