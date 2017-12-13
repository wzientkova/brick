package com.wzientkova.brick.prop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.wzientkova.brick.Controller;
import com.wzientkova.brick.Prop;
import com.wzientkova.brick.PropFilter;

public class SideWall implements Prop {

    private static final float DEFAULT_POS_Y = Controller.pixelsToWorld(300);

    private float posX, posY;
    private float width, height;
    private float halfWidth; // for optimizing purposes

    private Body body;

    private boolean dead;

    private int health;

    public SideWall(float posX) {
        this.posX = posX;
        build();
    }

    @Override
    public void build() {
        // default wall position
        posY = Controller.getGameViewport().getWorldHeight() / 2;

        width = 0.01f;
        halfWidth = width / 2f;

        height = Controller.getGameViewport().getWorldHeight();

        // create body definitions required to create a body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(posX, posY);
        bodyDef.awake = false;

        // add the body to the world
        body = Controller.getWorld().createBody(bodyDef);

        // make shape for the body's fixture
        PolygonShape box = new PolygonShape();
        box.setAsBox(halfWidth, height / 2);

        // the fixture definition used to make a fixture
        FixtureDef tmpFixtureDef = new FixtureDef();
        tmpFixtureDef.shape = box;
        tmpFixtureDef.density = 10000f;
        tmpFixtureDef.friction = 0f;

        // add the fixture to the body
        body.createFixture(tmpFixtureDef);

        // filter for collisions and contact handler
        Filter filter = new Filter();
        filter.categoryBits = PropFilter.WALL; // category code for the plank
        filter.maskBits = PropFilter.BALL; // wall will collide with the ball only

        // add whole plank object as user data of it's body because of collision handling
        body.setUserData(this);

        // only one fixture, therefore the first item in the list
        body.getFixtureList().get(0).setFilterData(filter);

        // wall will never rotate
        body.setFixedRotation(true);

        box.dispose();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
    }


    @Override
    public Body getBody() {
        return body;
    }

}
