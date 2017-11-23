package com.wzientkova.brick;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.wzientkova.brick.prop.Plank;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents running game.
 * of code.. consider making it a singleton and storing it in controller.
 */
public class Game {

    private boolean running;
    private Plank plank;
    private List<Body> bodiesToDestroy = new ArrayList<>();


    /**
     * This constructor can only be used to load a custom level.
     */
    public Game() {

        this.plank = new Plank();
        start();
    }

    /**
     * Starts the game.
     */
    public void start() {
        // todo set the ball in start position
        // todo enable user input
        running = true;
    }

    public void flushProps() {

        for (Body body : bodiesToDestroy) {
            Controller.getWorld().destroyBody(body);
        }
        bodiesToDestroy.clear();
    }


    /**
     * Restarts current level.
     */
    public void restart() {
        // level = Util.loadLevelFromFile(level.getName());
        start();
    }

    public void render(SpriteBatch spriteBatch) {

        // render the plank
        plank.render(spriteBatch);
    }

    /* -- getters and setters -- */
    public Plank getPlank() {
        return plank;
    }

}