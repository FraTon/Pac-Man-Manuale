package pacman;

import jade.core.Agent;
import pacman.view.Actor;

/**
 * @author dzimiks
 */
public class PacmanActor extends Actor<PacmanGame> {

	public PacmanActor(PacmanGame game,Agent grafica) {  //22
		super(game,grafica);  
	}

	//CONTROLLA LO STATO ATTUALE DEL GIOCO E CHIAMA DI CONSEGUENZA UN'AZIONE
	@Override
    public void update() {//59
        switch (game.getState()) {
            case INITIALIZING: 
            	updateInitializing(); //61
            	break;
            case WELCOME_MESSAGE: 
            	updateWelcomeMessage(); //82
            	break;
            case TITLE: 
            	updateTitle(); 
            	break;
            case READY: 
            	updateReady();   //metodo solo di Ready
            	break;
            case READY2: 
            	updateReady2(); 
            	break;
            case PLAYING:  //qui inizia il gioco
            	updatePlaying(); //modifiche
            	break;
            case PACMAN_DIED: 
            	updatePacmanDied();   // va bene così
            	break;
            case GHOST_CATCHED: 
            	updateGhostCatched(); 
            	break;
            case LEVEL_CLEARED: 
            	updateLevelCleared(); 
            	break;
            case GAME_OVER:
            	updateGameOver();
            	break;
        }
    }

    public void updateInitializing() {
    	
    }

    public void updateWelcomeMessage() {

    }

    public void updateTitle() {
    
    }

    public void updateReady() {
    
    }

    public void updateReady2() {
    
    }

    public void updatePlaying() {
    
    }

    public void updatePacmanDied() {
    
    }

    public void updateGhostCatched() {
    
    }

    public void updateLevelCleared() {
    
    }

    public void updateGameOver() {
    
    }

    public void stateChanged() {
    
    }
}