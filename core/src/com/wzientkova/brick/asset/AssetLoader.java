package com.wzientkova.brick.asset;

public class AssetLoader {

    public static void loadAssets() {

        for (AssetManager.Fonts font : AssetManager.Fonts.values()) {
            font.load();
        }

        AssetManager.Atlases.ALL.load();

    }
}