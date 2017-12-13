package com.wzientkova.brick.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.wzientkova.brick.BrickGame;
import com.wzientkova.brick.Controller;
import com.wzientkova.brick.Game;
import com.wzientkova.brick.Level;
import com.wzientkova.brick.asset.AssetManager;
import com.wzientkova.brick.prop.Brick;
import com.wzientkova.brick.prop.BrickType;
import com.wzientkova.brick.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends StageScreen implements InputProcessor
{

	private Game game;
	private World world;

	private Box2DDebugRenderer renderer;
	private OrthographicCamera camera;
	private OrthographicCamera textCamera;

	private ShapeRenderer shapeRenderer;

	private int frameCounter;

	private SpriteBatch batch;
	private ExtendViewport gameViewport;

	BitmapFont font;
	private boolean end;
	private TextField name;

	public GameScreen(BrickGame brickGame)
	{
		super(brickGame);
		game = new Game(createLevel());
		init();
	}

	protected void init()
	{
		gameViewport = Controller.getGameViewport();

		world = Controller.getWorld();
		renderer = new Box2DDebugRenderer();
		camera = Controller.getCamera();
		textCamera = new OrthographicCamera(Constant.VIRTUAL_WIDTH, Constant.VIRTUAL_HEIGHT);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		stage.setViewport(new ExtendViewport(Constant.VIRTUAL_WIDTH / 4, Constant.VIRTUAL_HEIGHT / 4));

		batch = new SpriteBatch();
		Music music = Gdx.audio.newMusic(Gdx.files.internal("Music.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);
		music.play();

		font = new BitmapFont();

		font.getData().setScale(4f);

		Skin uiSkin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));

		name = new TextField("", uiSkin);
		name.setMessageText("  Name");
		name.setAlignment(Align.center);
		name.setScale(1);
		name.setVisible(false);

		stage.addActor(name);

		name.setOnscreenKeyboard(new TextField.OnscreenKeyboard()
		{
			@Override
			public void show(boolean visible)
			{
				//Gdx.input.setOnscreenKeyboardVisible(true);

				Gdx.input.getTextInput(new Input.TextInputListener()
				{
					@Override
					public void input(String text)
					{
						name.setText(text);
						Gdx.app.exit();
					}

					@Override
					public void canceled()
					{
						System.out.println("Cancelled.");
					}
				}, game.isWin() ? "WIN: Your score: " + game.getScore() : "LOOSE: Your score: " + game.getScore(), "", "Enter your name");
			}
		});

		Gdx.input.setInputProcessor(this);
	}

	private Level createLevel()
	{ // todo debug remove

		Level level = new Level();

		List<Brick> bricks = new ArrayList<>();


		float brickWidth = AssetManager.Regions.BRICK_NORMAL_BLUE.getWidth();
		float brickHeight = AssetManager.Regions.BRICK_NORMAL_BLUE.getHeight();

		float startX = (Controller.worldToPixels(Controller.getGameViewport().getWorldWidth()) - 5 * AssetManager.Regions.BRICK_NORMAL_BLUE.getWidth()) / 2f;

		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				Vector2 pos = new Vector2(startX + (i * brickWidth), 1600 - (j * brickHeight));
				Brick brick = new Brick(BrickType.NORMAL_BLUE, Controller.pixelsToWorld(pos));
				bricks.add(brick);
			}
		}

		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				Vector2 pos = new Vector2(startX + (i * brickWidth), 1400 - (j * brickHeight));
				Brick brick = new Brick(BrickType.NORMAL_GREEN, Controller.pixelsToWorld(pos));
				bricks.add(brick);
			}
		}

		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				Vector2 pos = new Vector2(startX + (i * brickWidth), 1200 - (j * brickHeight));
				Brick brick = new Brick(BrickType.NORMAL_ORANGE, Controller.pixelsToWorld(pos));
				bricks.add(brick);
			}
		}

		level.setBricks(bricks);

		return level;
	}

	@Override
	public void show()
	{

	}

	@Override
	public void resize(int width, int height)
	{

		gameViewport.update(width, height, true);
		camera.update();

		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render(float delta)
	{

		frameCounter++;

		if (frameCounter % 60 == 0)
		{
			// Log.info("FPS - " + Gdx.graphics.getFramesPerSecond());
		}

		if (Gdx.app.getType() == Application.ApplicationType.Desktop)
		{
			handleInput();
		}

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (!end)
		{

			// main game physics loop step
			world.step(1 / 60f, 8, 3);

			// remove all dead bricks, balls etc.
			game.flushProps();

			camera.update();

			batch.setProjectionMatrix(gameViewport.getCamera().combined);
			batch.begin();

			// render the background

			// render the level
			game.render(batch);

			batch.setProjectionMatrix(textCamera.combined);
			font.draw(batch, game.getScore(), 300, 900);
			batch.end();

			if (game.isEnd())
			{
				end = true;
				showDialog();
			}

		}
		// comment this to remove debug boundaries around box2d bodies
		// renderer.render(world, camera.combined);

		// render ui
		stage.act(delta);
		stage.draw();
	}

	private void showDialog()
	{
		stage.setKeyboardFocus(name);
		name.getOnscreenKeyboard().show(true);
	}

	protected void handleInput()
	{

		if (Gdx.input.isKeyJustPressed(Input.Keys.R))
		{
			game.restart();
		}
	}

	@Override
	public void pause()
	{

	}

	@Override
	public void hide()
	{
		dispose();
	}

	@Override
	public void dispose()
	{
		renderer.dispose();
	}


	@Override
	public boolean keyDown(int keycode)
	{
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{

		game.getPlank().touchDown();

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{

		game.getPlank().touchUp();

		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer)
	{

		Vector3 unproject = gameViewport.getCamera().unproject(new Vector3(x, y, 0));
		game.getPlank().setPosition(new Vector2(unproject.x, unproject.y));

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
}