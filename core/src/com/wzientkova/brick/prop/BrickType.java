package com.wzientkova.brick.prop;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wzientkova.brick.asset.AssetManager;

/**
 * Defines all brick types.
 *
 */
public enum BrickType {

    NORMAL_BLUE(AssetManager.Regions.BRICK_NORMAL_BLUE),
    NORMAL_GREEN(AssetManager.Regions.BRICK_NORMAL_GREEN),
    NORMAL_ORANGE(AssetManager.Regions.BRICK_NORMAL_ORANGE);

    private AssetManager.Regions region;

    BrickType(AssetManager.Regions region) {
        this.region = region;
    }

    public TextureRegion getTextureRegion() {
        return region.getRegion();
    }

    public Sprite getSprite() {
        return new Sprite(region.getRegion());
    }


    public AssetManager.Regions getRegion() {
        return region;
    }

}