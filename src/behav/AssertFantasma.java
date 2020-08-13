package behav;

import org.jpl7.Query;

import jade.core.Agent;

public class AssertFantasma extends MyOneShot {

	private String predName; //stringa contenente il predicato
	private String argument1; //stringa contenente la posizione del fantasma rosso
	private String argument2; //stringa contenente la posizione del fantasma azzurro
	private String argument3; //stringa contenente la posizione del fantasma rosa
	private String argument4; //stringa contenente la posizione del fantasma arancione

	//costruttore al quale servono come parametri l'agente che schedula il behav e la stringa o gli elementi della stringa con la quale deve lanciare il goal
	public AssertFantasma(Agent schedAgent, String predName, String argument1, String argument2, String argument3, String argument4) {
		
		super(schedAgent); //chiama il costruttore della superclasse
		//inizializzo delle variabili private
		this.predName = predName;
		this.argument1 = argument1;
		this.argument2 = argument2;
		this.argument3 = argument3;
		this.argument4 = argument4;

	}


	public void action(){

		//goal: assert(fantasma_start(rosso,x,y))
		String goal1 = "assert(" + predName + "(" + argument1 + "))";
		Query q1 = new Query(goal1); ////creazione di una query per il lancio del goal

		//creazione di una query per il lancio del goal
		if (q1.hasSolution()){
			
			//stampa la stringa del goal (non il risultato), serve come debug
			//myPrint(goal1);
		}

		//goal: assert(fantasma_start(azzurro,x,y))
		String goal2 = "assert(" + predName + "(" + argument2 + "))";
		Query q2 = new Query(goal2); //creazione di una query per il lancio del goal

		//lancio del goal e se ha almeno una solzione
		if (q2.hasSolution()){
			
			//stampa la stringa del goal (non il risultato), serve come debug
			//myPrint(goal2);
		}

		//goal: assert(fantasma_start(rosa,x,y))
		String goal3 = "assert(" + predName + "(" + argument3 + "))";
		Query q3 = new Query(goal3); //creazione di una query per il lancio del goal

		//lancio del goal e se ha almeno una solzione
		if (q3.hasSolution()){
			
			//stampa la stringa del goal (non il risultato), serve come debug
			//myPrint(goal3);
		}

		//goal: assert(fantasma_start(arancione,x,y))
		String goal4 = "assert(" + predName + "(" + argument4 + "))";
		Query q4 = new Query(goal4); //creazione di una query per il lancio del goal

		//lancio del goal e se ha almeno una solzione
		if (q4.hasSolution()){
			
			//stampa la stringa del goal (non il risultato), serve come debug
			//myPrint(goal4);
		}
	}
}