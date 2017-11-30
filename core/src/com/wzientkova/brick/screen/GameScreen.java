package com.wzientkova.brick.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.wzientkova.brick.BrickGame;
import com.wzientkova.brick.Controller;
import com.wzientkova.brick.Game;
import com.wzientkova.brick.util.Constant;

public class GameScreen extends StageScreen implements InputProcessor {

    private Game game;
    private World world;

    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;

    private ShapeRenderer shapeRenderer;

    private int frameCounter;

    private SpriteBatch batch;

    public GameScreen(BrickGame brickGame) {
        super(brickGame);
        game = new Game();
        init();
    }


    @Override
    public void show() {

    }

    protected void init() {

        world = Controller.getWorld();
        renderer = new Box2DDebugRenderer();
        camera = Controller.getCamera();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        stage.setViewport(new ExtendViewport(Constant.VIRTUAL_WIDTH, Constant.VIRTUAL_HEIGHT));

        batch = new SpriteBatch();

        Vector3 unprojectedScreenVector = Controller.getCamera().getUnprojectedScreenVector();

        //GestureDetector gd = new GestureDetector(this);
        //InputMultiplexer multi = new InputMultiplexer(stage, gd);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {

        camera.update();

        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {

        frameCounter++;

        if (frameCounter % 60 == 0) {
            // Log.info("FPS - " + Gdx.graphics.getFramesPerSecond());
        }

        if (Gdx.app.getType() == Application.ApplicationType.Desktop) handleInput();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // main game physics loop step
        world.step(1 / 60f, 8, 3);

        // remove all dead bricks, balls etc.
        game.flushProps();

        camera.update();

        batch.setProjectionMatrix(camera.combined);  // todo does this need to be set on every render call?
        batch.begin();

        // render the background

        // render the level
        game.render(batch);

        batch.end();

        // comment this to remove debug boundaries around box2d bodies
        // renderer.render(world, camera.combined);

        // render ui
        stage.act(delta);
        stage.draw();
    }

    protected void handleInput() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.restart();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        game.getPlank().touchDown();

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        game.getPlank().touchUp();

        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}