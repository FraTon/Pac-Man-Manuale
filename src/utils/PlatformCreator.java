package utils;

import javax.swing.SwingUtilities;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;


public class PlatformCreator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
            	
            	//Istanza di una classe singletone che permette di gestire l'ambiente runtime di Jade
				Runtime rt = Runtime.instance();
				
				//Profilo di questa piattaforma: la classe ProfileImpl ha un costruttore i quali parametri vengono settati alla riga successiva
				ProfileImpl p = new ProfileImpl();
				
				//setParameter è il metodo che serve per specificare le opzioni da implementare sulla piattaforma Jade
				p.setParameter("gui", "true");
				
				//Bisogna creare un container wrappato da un containerController che posso creare a partire dal Runtime
				//Il punto di bootstrap di una piattaforma in Jade è il mainContainer
				
				//Metodo per creare un mainContainer passandogli un profilo di una certa implementazione
				//ciò significa: lancia jade con le opzioni settate con il metodo setParameter
				//lo passo ad un metodo create mainContainer che mi ritorna un containerController che è un 
				//istanza della classe Jade wrapper containerController che permette di creare un conteiner 
				//cioè di istanziare la JVM con alcune funzionalità di Jade
				ContainerController cc = rt.createMainContainer(p);

				try {
					//Creazione di un agente, esso viene wrappato da un agentController acdatabase
					//invocando il metodo createNewAgent del containerController cc, passandogli come parametri:
					//-il nome locale dell'agente DatabaseAgent, 
					//-la classe che implementa quell'agente
					//-argomenti da passare a quell'agente
					AgentController acdatabase = cc.createNewAgent("DatabaseAgent", "agents.DatabaseAgent", null);
					
					//Avvio dell'agente acdatabase nella piattaforma grazie al metodo start dell'agentController
					acdatabase.start();
				} catch (StaleProxyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					//Creazione di un agente acgrafica
					AgentController acgrafica = cc.createNewAgent("GraficaAgent", "agents.GraficaAgent", null);
					
					//Avvio dell'agente acgrafica nella piattaforma
					acgrafica.start();
				} catch (StaleProxyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
            }
        });
	}
}
