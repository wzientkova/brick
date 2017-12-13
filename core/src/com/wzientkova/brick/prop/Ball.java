package com.wzientkova.brick.prop;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.wzientkova.brick.Controller;
import com.wzientkova.brick.Prop;
import com.wzientkova.brick.PropFilter;
import com.wzientkova.brick.asset.AssetManager;

/**
 * Game object. Ball which a player needs to shoot on goal.
 */
public class Ball implements Prop {

    private static final float DEFAULT_SIZE = 0.7f;
    private static final float SPEED = 5f;

    private Sprite sprite;
    private Body body;
    private Vector2 position;

    private float radius;
    private float margin = 0.3f; // compensates for a shadow underneath the ball


    public Ball(Vector2 position) {
        this.position = position;
        build();
    }

    @Override
    public void build() {

        radius = DEFAULT_SIZE / 2;

        sprite = new Sprite(AssetManager.Regions.BALL_CLASSIC.getRegion());
        sprite.setSize(DEFAULT_SIZE + margin * 2, DEFAULT_SIZE + margin * 2);
        sprite.setPosition(position.x - margin, position.y - margin);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x, position.y);
        //bodyDef.fixedRotation = true;

        body = Controller.getWorld().createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.1f;
        fixtureDef.restitution = 1f;

        body.createFixture(fixtureDef);

        // used for collision detection
        body.setUserData(this);

        // filter for collisions and ContactHandler
        Filter filter = new Filter();
        filter.categoryBits = PropFilter.BALL;

        // there is always only one fixture per body, so its always the first item in the list
        body.getFixtureList().get(0).setFilterData(filter); // todo do we need this? check also other props with bodies

        // wouldn't want it to shift
        body.setFixedRotation(false);
    }

    // todo important optimize.. isnt there a way to make this smarter? we can probably cache some
    // numbers here - e.g. (Controller.getCamera().getUnprojectedScreenVector().x * -1) + radius)
    // will be the same entire game.. also, is there a better way of updating linear velocity?
    // not with re-setting it to the object
    @Override
    public void render(SpriteBatch spriteBatch) {

        // check for collision with the left side of the screen
        /*if (body.getPosition().x < (Controller.getCamera().getUnprojectedScreenVector().x * -1) + radius) {
            body.setLinearVelocity(body.getLinearVelocity().x * -1, body.getLinearVelocity().y);
        }

        // check for collision with the right side of the screen
        if (body.getPosition().x > Controller.getCamera().getUnprojectedScreenVector().x - radius) {
            body.setLinearVelocity(body.getLinearVelocity().x * -1, body.getLinearVelocity().y);
        }*/

        // check for collision with the top side of the screen
        if (body.getPosition().y > Controller.getGameViewport().getWorldHeight() - radius) {
            body.setLinearVelocity(body.getLinearVelocity().x, Math.abs(body.getLinearVelocity().y) * -1);
        }

        // synchronize sprite with the body
        position.set(body.getPosition().x - radius - margin, body.getPosition().y - radius - margin);
        sprite.setPosition(position.x, position.y);
        sprite.draw(spriteBatch);

        // todo the ball should be destroyed when it is below the screen (and invalidated once it is below the plank)
        // there should be some kind of container of balls (there can be more balls in the game) and once
        // all balls are invalidated, they should be destroyed and game should be failed
    }


    public void launch(Vector2 launchVector) {
        body.setLinearVelocity(launchVector.scl(SPEED)); // todo we need to find proper scale for speed
    }

    public Vector2 getPosition() {
        return position;
    }

    public Body getBody() {
        return body;
    }
}