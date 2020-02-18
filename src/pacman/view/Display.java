package pacman.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import jade.core.Agent;

/**
 * @author dzimiks
 */
public class Display extends Canvas {

	private Game game;
    private boolean running;
    private BufferStrategy bs;
    
    //6
    public Display(Game game) {
        this.game = game;
        int sx = (int) (game.screenSize.width * game.screenScale.getX());
        int sy = (int) (game.screenSize.height * game.screenScale.getY());
        
        setPreferredSize(new Dimension(sx, sy));
        addKeyListener(new Keyboard());
    }

    public void start(Agent grafica) {    //19
        
    	if (running) 
            return;
        
        createBufferStrategy(3);
        bs = getBufferStrategy();
        game.init(grafica);     
        running = true;
        
        
        //inizia il gioco 
        Thread thread = new Thread(new MainLoop());
        thread.start();
    }
    
    
    
    //loop
    private class MainLoop implements Runnable {

        @Override
        public void run() {//56
        	
            long desiredFrameRateTime = 1000 / 60;
            long currentTime = System.currentTimeMillis();
            long lastTime = currentTime - desiredFrameRateTime;
            long unprocessedTime = 0;
            boolean needsRender = false;
            
            while (running) {
                currentTime = System.currentTimeMillis();
                unprocessedTime += currentTime - lastTime;
                lastTime = currentTime;
                
                while (unprocessedTime >= desiredFrameRateTime) {
                    unprocessedTime -= desiredFrameRateTime;
                    update();    //AGGIONRNAMENTO DI OGNI ATTORE
                    needsRender = true;
                }
                
                if (needsRender) {
                    Graphics2D g = (Graphics2D) bs.getDrawGraphics();
                    g.setBackground(Color.BLACK);
                    g.clearRect(0, 0, getWidth(), getHeight());
                    g.scale(game.screenScale.getX(), game.screenScale.getY());
                    draw(g);
                    g.dispose();
                    bs.show();
                    needsRender = false;
                }
                else {
                    try {
                        Thread.sleep(1);
                    } 
                    catch (InterruptedException ex) {
                    	
                    }
                }
            }
        }
    }
    
    public void update() {//57
        game.update();
    }
    
    public void draw(Graphics2D g) {//63
        game.draw(g);
    }
}