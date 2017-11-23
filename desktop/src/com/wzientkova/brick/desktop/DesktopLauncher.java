package com.wzientkova.brick.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.wzientkova.brick.BrickGame;
import com.wzientkova.brick.util.Constant;

public class DesktopLauncher {

    public static void main(String[] arg) {

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = Constant.TITLE;
        config.width = Constant.WINDOW_WIDTH;
        config.height = Constant.WINDOW_HEIGHT;

        new LwjglApplication(new BrickGame(), config);
    }
}