package com.wzientkova.brick;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.wzientkova.brick.prop.Ball;
import com.wzientkova.brick.prop.Brick;
import com.wzientkova.brick.prop.Plank;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents running game.
 * of code.. consider making it a singleton and storing it in controller.
 */
public class Game {

    private List<Ball> balls = new ArrayList<>();
    private Plank plank;
    private List<Body> bodiesToDestroy = new ArrayList<>();
    private Level level;


    /**
     * This constructor can only be used to load a custom level.
     */
    public Game(Level level) {
        this.level = level;

        this.plank = new Plank();
        this.balls.add(new Ball(new Vector2(0, -9)));

        start();

        for (Ball ball : balls) {
            ball.launch(new Vector2(1f, 2));
        }

        Controller.getWorld().setContactListener(new ContactHandler(this));
    }

    /**
     * Starts the game.
     */
    public void start() {
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
        level.render(spriteBatch);

        plank.render(spriteBatch);

        updateBalls(spriteBatch);

    }

    private void updateBalls(SpriteBatch spriteBatch) {

        Iterator<Ball> iterator = balls.iterator();

        while (iterator.hasNext()) {

            Ball ball = iterator.next();

            // remove coins which are already bellow the screen
            if (ball.getPosition().y < Controller.getCamera().getUnprojectedScreenVector().y) {
                iterator.remove();
                continue;
            }

            ball.render(spriteBatch);
        }
    }

    public void destroyProp(Prop prop) {
        Body body = prop.getBody();

        // whatever object it is, remove its body
        if (body != null) {
            if (!bodiesToDestroy.contains(prop)) {
                // Log.info("added a prop for deletion");
                bodiesToDestroy.add(body);
            }
        }

        // brick is being destroyed
        if (prop instanceof Brick) {

            level.getBricks().remove(prop);
        }
    }

    /* -- getters and setters -- */
    public Plank getPlank() {
        return plank;
    }

}