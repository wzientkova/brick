package com.wzientkova.brick.prop;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.wzientkova.brick.Controller;
import com.wzientkova.brick.Prop;
import com.wzientkova.brick.PropFilter;

public class Brick implements Prop {

    private BrickType brickType;

    private int health;
    private boolean dead;

    private Body body;
    private Sprite sprite;
    private float x, y, width, height;

    private Vector2 position;

    public Brick(BrickType brickType, float x, float y) {

        this(brickType, new Vector2(x, y));
    }

    /**
     *
     * @param brickType
     * @param xy in world coordinates
     */
    public Brick(BrickType brickType, Vector2 xy) {

        position = xy;

        setBrickType(brickType);

        sprite = brickType.getSprite();

        // this defines the width and stuff
        width = brickType.getRegion().getWorldWidth();
        height = brickType.getRegion().getWorldHeight();

        this.x = position.x - width / 2;
        this.y = position.y - height / 2;

        sprite.setX(position.x - width / 2);
        sprite.setY(position.y - height / 2);
        sprite.setSize(width, height);

        // body definitions required to create a body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(position);
        bodyDef.awake = false;

        // adds a body to the world
        body = Controller.getWorld().createBody(bodyDef);

        // making a shape for the body's fixture, which takes up space in the world
        PolygonShape box = new PolygonShape();
        box.setAsBox(width / 2, height / 2);

        // the fixture definition used to make a fixture
        FixtureDef tmpFixtureDef = new FixtureDef();
        tmpFixtureDef.shape = box;
        tmpFixtureDef.density = 1.0f;
        tmpFixtureDef.friction = 0f;

        // add the fixture to the body
        body.createFixture(tmpFixtureDef);

        // filter for collisions and ContactHandler
        Filter filter = new Filter();
        filter.categoryBits = PropFilter.BRICK;

        // there is always only one fixture per body, so its always the first item in the list
        body.getFixtureList().get(0).setFilterData(filter);

        // wouldn't want it to shift
        body.setFixedRotation(false);

        // gotta dispose it
        box.dispose();

        // attaching a BrickData, which allows for accessing the parent brick from the body.getUserData() method.
        body.setUserData(this);
    }

    private void setBrickType(BrickType brickType) {

        this.brickType = brickType;

        health = 1;

        /*if (brickType.equals(BrickType.NORMAL)) {
            health = 1;
        } else {
            health = 1;
        }*/

    }

    public void hit() {

        health--;

        if (health <= 0) {
            die();
        }
    }

    private void die() {
        dead = true;
    }

    public Body getBody() {
        return body;
    }

    @Override
    public void build() {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }

    public boolean isDead() {
        return dead;
    }

    public Vector2 getPosition() {
        return position;
    }

}
