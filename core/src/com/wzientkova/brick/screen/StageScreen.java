package com.wzientkova.brick.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wzientkova.brick.BrickGame;
import com.wzientkova.brick.asset.AssetManager;

public abstract class StageScreen implements Screen {

    protected BrickGame brickGame;
    protected Stage stage;
    protected Screen screen;
    protected Runnable backKeyListener;

    public StageScreen(BrickGame brickGame) {

        stage = new Stage() {

            @Override
            public boolean keyDown(int keyCode) {

                if (keyCode == Input.Keys.ESCAPE || keyCode == Input.Keys.BACK) {

                    if (backKeyListener == null) {
                        return false;
                    }

                    backKeyListener.run();
                }

                return false;
            }
        };

        screen = this;

        this.brickGame = brickGame;
    }

    @Override
    public void resume() {
        AssetManager.finishLoading();
    }
}