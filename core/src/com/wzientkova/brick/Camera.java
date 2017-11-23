package com.wzientkova.brick;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.wzientkova.brick.util.Constant;

/**
 * Custom orthographic camera. Used mainly for optimizing purposes, asi it also stores already
 * unprojected values of certain objects.
 */
public class Camera extends OrthographicCamera {

    private Vector3 unprojectedScreenVector;

    /**
     * Returns unprojected screen dimensions.
     */
    public Vector3 getUnprojectedScreenVector() {

        if (unprojectedScreenVector == null) {
            unprojectedScreenVector = unproject(new Vector3(Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT, 0));
        }

        return unprojectedScreenVector;
    }
}