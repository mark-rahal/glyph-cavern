package com.markrahal.glyphcavern.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.markrahal.glyphcavern.GlyphCavern;

public class MenuScreen implements Screen {
    private GlyphCavern game;
    private FitViewport port;
    private OrthographicCamera cam;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter param;
    private BitmapFont font;
    private BitmapFont smallFont;
    private Label title;
    private Label play;
    private Label.LabelStyle style;
    private Stage stage;
    private Table table;

    private boolean isClicked;
    private String clickedItem;

    public MenuScreen(GlyphCavern game) {
        this.game = game;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("C:\\Users\\mark\\Desktop\\Glyph Cavern\\android\\assets\\Pixeled.ttf"));
        param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 70;
        font = generator.generateFont(param);
        param.size = 30;
        smallFont = generator.generateFont(param);
        style = new Label.LabelStyle(font, Color.ORANGE);
        title = new Label("GLYPH CAVERN", style);
        play = new Label("New Game", new Label.LabelStyle(smallFont, Color.BLUE));
        play.setName("Play");

        cam = new OrthographicCamera();
        cam.setToOrtho(false);
        port = new FitViewport(GlyphCavern.WIDTH, GlyphCavern.HEIGHT, cam);

        isClicked = false;
        clickedItem = null;

        stage = new Stage(port, game.getBatch());
        table = new Table();
        table.center();
        table.setFillParent(true);
        table.add(title);
        table.row();
        table.add(play);
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked " + event.getListenerActor().getName());
                isClicked = true;
                clickedItem = event.getListenerActor().getName();

            }
        });
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private void handleInput(float dt) {
        if (isClicked) {
            game.setScreen(new PlayScreen(game));
        }
    }

    private void update(float dt) {
        handleInput(dt);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        port.update(width, height);
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
        stage.dispose();
    }
}
