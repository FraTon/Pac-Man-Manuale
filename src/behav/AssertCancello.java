package behav;

import org.jpl7.Query;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class AssertCancello extends Behaviour {
	
	private String predName; //stringa contenente il predicato
	private Object[] argument; //stringa contenente l'argomento del predicato
	private Integer ncancelli = 0; //inizializzazione contatore per il numero di elementi cancello

	//costruttore al quale servono come parametri l'agente che schedula il behav e la stringa o gli elementi della stringa con la quale deve lanciare il goal
	public AssertCancello(Agent schedAgent, String predName, Object[] argument) {
		
		super(schedAgent); //chiama il costruttore della superclasse
		//inizializzo delle variabili private
		this.predName = predName;
		this.argument = argument;
	}


	public void action(){

		//per tutta la lunghezza dell'array, se l'argomento non è nullo
		for(int h = 0; h < argument.length; h++) if(argument[ncancelli] != null) {
			
			//trasforma l'elemento in stringa per poterla usare come parte del goal da lanciare 
			String argument1 = argument[ncancelli].toString();			

			//goal: assert(cancello(x,y))
			String goal = "assert(" + predName + "(" + argument1 + "))";
			Query q = new Query(goal); //creazione di una query per il lancio del goal
	
			//lancio del goal e se ha almeno una solzione
			if (q.hasSolution()){

				
				//System.out.println(goal); //stampa la stringa goal
				ncancelli++; //incrementa il contatore
			}
		}
	}
	
	//metodo che definisce fino a quando l'agente deve eseguire questo behaviour
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		if (ncancelli < 3) return false;
	 		else return true;
	}
}
