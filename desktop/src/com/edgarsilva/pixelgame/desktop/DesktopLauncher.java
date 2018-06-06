package com.edgarsilva.pixelgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.edgarsilva.pixelgame.PixelGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pixel Game";
		config.width = (int) PixelGame.WIDTH;
		config.height = (int) PixelGame.HEIGHT;
		config.allowSoftwareMode = true;
		config.resizable = true;
		new LwjglApplication(new PixelGame(), config);
	}
}
