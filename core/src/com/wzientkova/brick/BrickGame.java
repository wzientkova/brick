package com.wzientkova.brick;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.wzientkova.brick.asset.AssetManager;
import com.wzientkova.brick.screen.SplashScreen;

public class BrickGame extends Game {

    @Override
    public void create() {

        AssetManager.init();

        Texture.setAssetManager(AssetManager.getAssetManager());


        AssetManager.finishLoading();

        setScreen(new SplashScreen(this), false);
    }

    /**
     * Custom setScreen method which is also able to dispose the previous one.
     *
     * @param screen          target screen which can be shown
     * @param disposePrevious boolean value which specifies if previous screen should be disposed or not
     */
    public void setScreen(Screen screen, boolean disposePrevious) {

        Screen previousScreen = getScreen();

        super.setScreen(screen);

        if (disposePrevious && previousScreen != null) {
            previousScreen.dispose();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}