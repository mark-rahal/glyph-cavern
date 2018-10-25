package com.markrahal.glyphcavern;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.markrahal.glyphcavern.Screens.MenuScreen;

public class GlyphCavern extends Game {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;
	public static final float PPM = 100;
	private SpriteBatch batch;

	@Override
	public void create () {

		batch = new SpriteBatch();
		setScreen(new MenuScreen(this));
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
