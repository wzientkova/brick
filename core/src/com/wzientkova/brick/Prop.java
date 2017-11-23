package com.wzientkova.brick;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public interface Prop {

    void build();

    void render(SpriteBatch spriteBatch);

    void setPosition(Vector2 vector2);

    Body getBody();

    void destroy();
}