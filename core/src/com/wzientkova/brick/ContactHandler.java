package com.wzientkova.brick;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.wzientkova.brick.prop.Ball;
import com.wzientkova.brick.prop.Brick;

public class ContactHandler implements ContactListener {

    private Game game;

    public ContactHandler(Game game) {
        this.game = game;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureB = contact.getFixtureB();
        Fixture fixtureA = contact.getFixtureA();
        if (fixtureA.getFilterData().categoryBits == PropFilter.BRICK) {
            if (fixtureB.getFilterData().categoryBits == PropFilter.BALL) {// get brick from the contact
                Brick brick = (Brick) fixtureA.getBody().getUserData();

                // call hit first and wait for brick's response
                if (brick != null) {

                    brick.hit(); // todo maybe we will here need a power of the hit

                    // check if brick is dead after last hit
                    if (brick.isDead()) {
                        game.destroyProp(brick);
                    }
                }


            } else {
            }
        }

        if (fixtureA.getFilterData().categoryBits == PropFilter.PLANK) {
            if (fixtureB.getFilterData().categoryBits == PropFilter.BALL) {
                if (fixtureB.getBody().getUserData() instanceof Ball) {
                    game.plankHit((Ball) fixtureB.getBody().getUserData());
                }


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