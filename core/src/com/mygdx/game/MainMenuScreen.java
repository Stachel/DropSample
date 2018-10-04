package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.utils.TextFont;
import com.mygdx.game.widget.TextButton;
import com.mygdx.game.widget.ToggleButton;

class MainMenuScreen implements Screen {


    final DropGame game;
    OrthographicCamera camera;
    TextButton btnStart;
    ToggleButton btnMusic;

    Vector3 touchPos;


    public MainMenuScreen(final DropGame game) {
        this.game = game;

        touchPos = new Vector3();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        btnStart = new TextButton("Start game", TextFont.getRusso(), 100, 100);
        btnMusic = new ToggleButton("Music", TextFont.getRusso(), 100, 50);
        btnMusic.setCheck(Gdx.app.getPreferences("Settings").getBoolean("isMusic"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        TextFont.getRusso().draw(game.batch, "Welcome to Drop!", 100, 150);
        btnStart.draw(game.batch);
        btnMusic.draw(game.batch);

        game.batch.end();

        if (Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (btnStart.isClick(touchPos)) {
                game.setScreen(new GameScreen(game));
                dispose();
            }

            if (btnMusic.isClick(touchPos)) {
                btnMusic.toggle();
                Gdx.app.getPreferences("Settings").putBoolean("isMusic", btnMusic.isCheck());
                Gdx.app.getPreferences("Settings").flush();
            }
        }

    }

    @Override
    public void resize(int width, int height) {

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
        btnMusic.dispose();
    }
}
