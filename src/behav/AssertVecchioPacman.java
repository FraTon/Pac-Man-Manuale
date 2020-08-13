package behav;

import org.jpl7.Query;

import jade.core.Agent;

public class AssertVecchioPacman extends MyOneShot {

	private String filename; //stringa contenente il nome del file prolog

	public AssertVecchioPacman(Agent schedAgent, String filename) {
		
		super(schedAgent); //chiama il costruttore della superclasse
		//inizializzo delle variabili private
		this.filename = filename;

	}


	public void action(){
		
		String consult = "consult('" + filename + "')"; //stringa da lanciare come goal per eseguire il file
		Query q = new Query(consult); //creazione di una query per il lancio del goal
		
		//lancio del goal e controllo se ha almeno una soluzione
		if (q.hasSolution()){
					
			String goal = "asserisci_vecchio_pacman"; //stringa da lanciare come goal
			//A questo punto posso utilizzare l'oggetto q, con una nuova query, 
			//creando una quary che ha come goal quello creato
			q = new Query(goal);
	
			//lancio del goal e controllo se ha almeno una soluzione
			if (q.hasSolution()){
				
				//myPrint(goal);
				
			}
		}
	}
}
