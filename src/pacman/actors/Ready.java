package pacman.actors;

import jade.core.Agent;
import pacman.PacmanActor;
import pacman.PacmanGame;
import pacman.enums.State;

/**
 * @author dzimiks
 */
public class Ready extends PacmanActor {

	public Ready(PacmanGame game,Agent grafica) {
        super(game,grafica);
    }

    @Override
    public void init() {
        x = 11 * 8;
        y = 20 * 8;
        loadFrames("/res/ready.png");
    }

    @Override
    public void updateReady() {
    	
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    game.restoreCurrentFoodCount();
//                    game.sounds.get("start").play();
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 1;
                case 1:
                    if (System.currentTimeMillis() - waitTime < 2000) // || game.sounds.get("start").isPlaying()) {
                        break yield;
                    
                    game.setState(State.READY2);
                    break yield;
            }
        }
    }
    
    @Override
    public void updateReady2() {
    	
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    game.broadcastMessage("showAll");  //rende visibile il gioco
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 1;
                case 1:
                    if (System.currentTimeMillis() - waitTime < 2000) // || game.sounds.get("start").isPlaying()) {
                        break yield;
                    
                    game.setState(State.PLAYING);
                    break yield;
            }
        }
    }

    @Override
    public void stateChanged() {  
        
    	visible = false;
        
        if (game.getState() == State.READY ||
            game.getState() == State.READY2) {
            visible = true;
            instructionPointer = 0;
        }
    }
}