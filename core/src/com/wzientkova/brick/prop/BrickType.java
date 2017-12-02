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

    private AssetManager.Regions texture;

    BrickType(AssetManager.Regions texture) {
        this.texture = texture;
    }

    public TextureRegion getTextureRegion() {
        return texture.getRegion();
    }

    public Sprite getSprite() {
        return new Sprite(texture.getRegion());
    }
}