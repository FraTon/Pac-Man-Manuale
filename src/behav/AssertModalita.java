package behav;

import org.jpl7.Query;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class AssertModalita extends Behaviour {
	
	private String filename; //stringa contenente il nome del file prolog
	private String goal; //stringa corrispondente al goal da lanciare

	private int c = 0; //inizializzazione contatore
	
	//costruttore al quale servono come parametri l'agente che schedula il behav e la stringa che deve lanciare il goal
	public AssertModalita(Agent schedAgent, String filename, String goal){
		
		super(schedAgent); //chiama il costruttore della superclasse
		//inizializzo delle variabili private
		this.filename = filename;
		this.goal = goal;
	}
	
	public void action(){
		
		String consult = "consult('" + filename + "')"; //stringa da lanciare come goal per eseguire il file
		Query q = new Query(consult); //creazione di una query per il lancio del goal
		
		//lancio del goal e controllo se ha almeno una soluzione
		if (q.hasSolution()){
				
			//A questo punto posso utilizzare l'oggetto q, con una nuova query, 
			//creando una quary che ha come goal quello passato al costruttore
			q = new Query(goal);
			
			//System.out.println(q);
			
			
			q.hasSolution(); //lancio del goal e controllo se ha almeno una soluzione
				
			c++; //incremento del contatore

		}
	}
	
	//metodo che definisce fino a quando l'agente deve eseguire questo behaviour
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		if (c > 0) return true;
	 		else return false;
	}


}
