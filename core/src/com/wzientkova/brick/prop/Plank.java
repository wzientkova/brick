package com.wzientkova.brick.prop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.wzientkova.brick.Controller;
import com.wzientkova.brick.Prop;
import com.wzientkova.brick.asset.AssetManager;

public class Plank implements Prop {

    private static final int DEFAULT_WIDTH = 5;
    private static final int DEFAULT_POS_Y = -10;

    private float posY;
    private float width, height;

    private float halfWidth; // for optimizing purposes
    private float margin = 0.1f; // to make the ball go slightly "into" 3d plank texture

    private boolean active;

    private Sprite sprite;
    private Body body;

    public Plank() {

        build();
    }

    @Override
    public void build() {

        // default plank position
        float x = 0;
        float y = -10;

        width = DEFAULT_WIDTH;
        halfWidth = width / 2;
        posY = DEFAULT_POS_Y;

        // define plank's dimensions
        height = 1;

        // set the plank's sprite
        sprite = new Sprite(AssetManager.Regions.PLANK.getRegion());
        sprite.setX(x - halfWidth);
        sprite.setY(y - height / 2 + margin);
        sprite.setSize(width, height);

        // create body definitions required to create a body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, y);
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


        // during collision detection it is then like contact.fixtureA.getBody.getUserData..

        // filter for collisions and contact handler
        Filter filter = new Filter();

        // add whole plank object as user data of it's body because of collision handling
        body.setUserData(this);

        // only one fixture, therefore the first item in the list
        body.getFixtureList().get(0).setFilterData(filter);

        // plank will never rotate
        body.setFixedRotation(true);

        box.dispose();
    }

    public void render(SpriteBatch spriteBatch) {

        if (active) {

            // get mouse X position and unproject it using camera
            float mousePosX = Controller.getCamera().unproject(new Vector3(Gdx.input.getX(), 0, 0)).x;

            // get unprojected screen size using camera
            float unprojectedScreenX = Controller.getCamera().getUnprojectedScreenVector().x;

            float speed;

            // prevent moving the plank past the screen borders
            mousePosX = getRestrictedMousePosX(mousePosX, unprojectedScreenX);

            // calculate the speed, which will be applied to the plank
            speed = mousePosX - body.getPosition().x;

            body.setLinearVelocity(speed * 20, 0);

            // synchronize sprite with the body
            sprite.setPosition(body.getPosition().x - halfWidth, posY - height / 2 + margin);
        }

        sprite.draw(spriteBatch);
    }

    /**
     * Restricts mouse position so the plank doesn't move past the screen borders.
     */
    private float getRestrictedMousePosX(float mousePosX, float unprojectedScreenX) {

        float rightWall = unprojectedScreenX - halfWidth;
        float leftWall = rightWall * -1;

        if (mousePosX < leftWall) {
            mousePosX = leftWall;
        } else if (mousePosX > rightWall) {
            mousePosX = rightWall;
        }

        return mousePosX;
    }

    @Override
    public void setPosition(Vector2 vector2) {
    }

    @Override
    public void destroy() {

    }

    public void touchDown() {
        active = true;
    }

    public void touchUp() {
        active = false;
        body.setLinearVelocity(0, 0);
    }

    public Body getBody() {
        return body;
    }

}