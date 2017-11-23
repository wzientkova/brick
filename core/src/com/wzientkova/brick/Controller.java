package com.wzientkova.brick;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

/**
 * This class is used to store commonly accessed objects. It is then easier to access them and
 * it is not needed to pass them as parameters.
 */
public class Controller {

    // todo make everything in this class a singleton
    private static World world;
    private static Random random;
    private static Camera camera;

    public static World getWorld() {

        if (world == null) {
            world = new World(new Vector2(0, 0), true);
        }

        return world;
    }

    public static Random getRandom() {

        if (random == null) {
            random = new Random();
        }

        return random;
    }

    public static Camera getCamera() {

        if (camera == null) {
            camera = new Camera();
            camera.viewportWidth = 18f;
            camera.viewportHeight = 32f;
            camera.update();
        }

        return camera;
    }


}