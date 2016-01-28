package com.mygdx.gametesttest.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.gametesttest.MyGdxGametest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
	    cfg.title = "my-gdx-game";
	    //cfg.useGL30 = false;
        cfg.width = 720;
	    cfg.height = 480;
	    cfg.foregroundFPS = 60;
	    cfg.backgroundFPS = 60;
		new LwjglApplication(new MyGdxGametest(), cfg);
	}
}
