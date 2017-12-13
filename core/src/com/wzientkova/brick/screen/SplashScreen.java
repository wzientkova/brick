package com.wzientkova.brick.screen;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.wzientkova.brick.BrickGame;
import com.wzientkova.brick.BrickGame;
import com.wzientkova.brick.asset.AssetLoader;
import com.wzientkova.brick.asset.AssetManager;
import com.wzientkova.brick.util.Constant;

public class SplashScreen extends StageScreen {

    private boolean done = false;

    public SplashScreen(BrickGame brickGame) {

        super(brickGame);

        init();

        AssetLoader.loadAssets();
    }

    public void init() {

        stage.setViewport(new ExtendViewport(Constant.VIRTUAL_WIDTH, Constant.VIRTUAL_HEIGHT));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Util.graphics.glBlack();

        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();

        if (AssetManager.update(100) && !done) {

            done = true;

            if (Constant.SHOW_INTRO) {

                stage.addAction(Actions.sequence(Actions.delay(2f), Actions.fadeOut(1f), Actions.run(new Runnable() {
                    public void run() {
                        brickGame.setScreen(new GameScreen(brickGame), true);
                    }
                })));

            } else {
                brickGame.setScreen(new GameScreen(brickGame), true);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        AssetManager.finishLoading();
    }
}