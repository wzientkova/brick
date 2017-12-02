package com.wzientkova.brick;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.wzientkova.brick.prop.Brick;

public class ContactHandler implements ContactListener {

    private Game game;

    public ContactHandler(Game game) {
        this.game = game;
    }

    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureA().getFilterData().categoryBits == PropFilter.BRICK) {
            switch (contact.getFixtureB().getFilterData().categoryBits) {
                case PropFilter.BALL:

                    // get brick from the contact
                    Brick brick = (Brick) contact.getFixtureA().getBody().getUserData();

                    // call hit first and wait for brick's response
                    if (brick != null) {

                        brick.hit(); // todo maybe we will here need a power of the hit

                        // check if brick is dead after last hit
                        if (brick.isDead()) {
                            game.destroyProp(brick);
                        }
                    }

                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}