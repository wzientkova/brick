package com.wzientkova.brick;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wzientkova.brick.prop.Brick;

import java.util.List;


public class Level {

    private List<Brick> bricks; // bricks of the level


    public Level() {
    }

    public void render(SpriteBatch spriteBatch) {
        for (Brick brick : bricks) {
            brick.render(spriteBatch);
        }
    }

    public void setBricks(List<Brick> bricks) {
        this.bricks = bricks;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public void destroy() {
        // todo destroy all level bricks, fixtures..
    }
}