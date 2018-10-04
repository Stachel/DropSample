package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {

	final DropGame game;

	OrthographicCamera camera;
	Texture imageDrop;
	Texture imageBucket;
	Texture imageBackground;
	Sound soundDrop;
	Music musicRain;

	Rectangle rectBucket;
	Array<Rectangle> drops;

	Vector3 touchPos;
	long lastDropTime;

	int dropsGatchered;
	boolean isPause = false;

	public GameScreen (final DropGame game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		imageDrop = new Texture("droplet.png");
		imageBucket = new Texture("bucket.png");
		imageBackground = new Texture("bg.png");
		soundDrop = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		musicRain = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		touchPos = new Vector3();
		musicRain.setLooping(true);


		rectBucket = new Rectangle();
		rectBucket.height = 64;
		rectBucket.width = 64;
		rectBucket.x = 800/2 - 64/2;
		rectBucket.y = 20;

		drops = new Array<Rectangle>();
		createDrop();
	}

	private void createDrop() {
		Rectangle drop = new Rectangle();
		drop.height = 64;
		drop.width = 64;
		drop.x = MathUtils.random(0, 800-64);
		drop.y = 480 - 64;
		drops.add(drop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void pause() {
		musicRain.pause();
		isPause = true;
	}

	@Override
	public void resume() {
		if (Gdx.app.getPreferences("Settings").getBoolean("isMusic")) {
			musicRain.play();
		}
	}

	@Override
	public void hide() {

	}

	@Override
	public void show() {
		if (Gdx.app.getPreferences("Settings").getBoolean("isMusic")) {
			musicRain.play();
		}
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0.1f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

		if (isPause) {
			game.font.draw(game.batch, "Tap for continue", 800/2, 480/2);
		} else {

			// background image
			game.batch.disableBlending();
			game.batch.draw(imageBackground, 0, 0);
			game.batch.enableBlending();


			game.font.draw(game.batch, "Drops Collected: " + dropsGatchered, 0, 480);
			game.batch.draw(imageBucket, rectBucket.x, rectBucket.y);

			for (Rectangle drop : drops) {
				game.batch.draw(imageDrop, drop.x, drop.y);
			}
		}

		game.batch.end();

		if (isPause) {
			if (Gdx.input.isTouched()) {
				isPause = false;
			}
			return;
		}

		if (Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			rectBucket.x = touchPos.x - 64/2;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			rectBucket.x -= 200 * Gdx.graphics.getDeltaTime();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			rectBucket.x += 200 * Gdx.graphics.getDeltaTime();
		}

		if (rectBucket.x < 0) {
			rectBucket.x = 0;
		}
		if (rectBucket.x > 800 - 64) {
			rectBucket.x = 800 - 64;
		}

		if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
			createDrop();
		}

		Iterator<Rectangle> iter = drops.iterator();
		while (iter.hasNext()) {
			Rectangle drop = iter.next();
			drop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (drop.y + 64 < 0) {
				iter.remove();
			}
			if (drop.overlaps(rectBucket)) {
				soundDrop.play();
				dropsGatchered++;
				iter.remove();
			}
		}

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void dispose () {
		imageBucket.dispose();
		imageDrop.dispose();
		imageBackground.dispose();
		soundDrop.dispose();
		musicRain.dispose();
	}
}
