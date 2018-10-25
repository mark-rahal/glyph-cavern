package com.markrahal.glyphcavern.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.markrahal.glyphcavern.GlyphCavern;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 480;
		config.title = "Glyph Cavern";
		new LwjglApplication(new GlyphCavern(), config);
	}
}
