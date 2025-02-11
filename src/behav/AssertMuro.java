package behav;

import org.jpl7.Query;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class AssertMuro extends Behaviour {
	
	private String predName; //stringa contenente il predicato
	private Object[] argument; //stringa contenente l'argomento del predicato
	private Integer nmuri = 0; //inizializzazione contatore per il numero di celle muro

	//costruttore al quale servono come parametri l'agente che schedula il behav e la stringa o gli elementi della stringa con la quale deve lanciare il goal
	public AssertMuro(Agent schedAgent, String predName, Object[] argument) {
		
		super(schedAgent); //chiama il costruttore della superclasse
		//inizializzo delle variabili private
		this.predName = predName;
		this.argument = argument;
	}


	public void action(){

		//per tutta la lunghezza dell'array, se l'argomento non � nullo
		for(int h = 0; h < argument.length; h++) if(argument[nmuri] != null) {
			
			//trasforma l'elemento in stringa per poterla usare come parte del goal da lanciare 
			String argument1 = argument[nmuri].toString();			

			//goal: assert(muro(x,y))
			String goal = "assert(" + predName + "(" + argument1 + "))";
			Query q = new Query(goal); //creazione di una query per il lancio del goal
	
			//lancio del goal e se ha almeno una solzione
			if (q.hasSolution()){
				
				//System.out.println(goal); //stampa la stringa goal
				nmuri++; //incrementa il contatore
			}
		}
	}
	
	//metodo che definisce fino a quando l'agente deve eseguire questo behaviour
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		if (nmuri < 803) return false;
	 		else return true;
	}

}
