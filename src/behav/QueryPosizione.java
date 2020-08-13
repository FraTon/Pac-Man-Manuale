package behav;

import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;

import jade.core.Agent;

public class QueryPosizione extends MyOneShot {
	
	private String filename; //stringa contenente il nome del file prolog
	private String goal; //stringa corrispondente al goal da lanciare
	private String coordinataX; //stringa corrispondente alla coordinata x
	private String coordinataY; //stringa corrispondente alla coordinata y
	
	public QueryPosizione(Agent schedAgent, String filename, String goal, String coordinataX, String coordinataY){
		
		super(schedAgent); //chiama il costruttore della superclasse
		//inizializzo delle variabili private
		this.filename = filename;
		this.goal = goal;
		this.coordinataX = coordinataX;
		this.coordinataY = coordinataY;
	}
	
	public void action(){
		
		String consult = "consult('" + filename + "')"; //stringa da lanciare come goal per eseguire il file
		Query q = new Query(consult); //creazione di una query per il lancio del goal
		
		//lancio del goal e controllo se ha almeno una soluzione
		if (q.hasSolution()){
			
			myPrint(consult); //stampa di debug
			
			//A questo punto posso utilizzare l'oggetto q, con una nuova query, 
			//creando una quary che ha come goal quello passato al costruttore
			q = new Query(goal);

			//Memorizzo tutte le soluzioni
			Map<String, Term>[] solutions = q.allSolutions();

			//Per ogni soluzione trovata
			for (Map<String, Term> sol : solutions){

				//Prendo il valore di X
				Term posX = sol.get(coordinataX);
				//Prendo il valore di Y
				Term posY = sol.get(coordinataY);
				
				//Stampo la posizione (x,y)
				myPrint("coordinate " + posX.toString() + " " + posY.toString());	
				
			}			
		}
	}
}
