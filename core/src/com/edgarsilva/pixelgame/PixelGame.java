package com.edgarsilva.pixelgame;

import com.badlogic.gdx.Game;
import com.edgarsilva.pixelgame.managers.GameAssetsManager;
import com.edgarsilva.pixelgame.screens.*;


public class PixelGame extends Game {

    public static final float WIDTH = 960;
    public static final float HEIGHT = 540;

    private AppPreferences preferences;
    public GameAssetsManager assets = new GameAssetsManager();

    private LoadingScreen loadingScreen;
    private SettingsScreen settingsScreen;
    private MenuScreen menuScreen;
    private PlayScreen playScreen;
    private EndScreen endScreen;

    public final static int MENU = 0;
    public final static int SETTINGS = 1;
    public final static int APPLICATION = 2;
    public final static int ENDGAME = 3;


    public void changeScreen(int screen){
        switch(screen){
            case MENU:
                if(menuScreen == null) menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;
            case SETTINGS:
                if(settingsScreen == null) settingsScreen = new SettingsScreen(this);
                this.setScreen(settingsScreen);
                break;
            case APPLICATION:
                if(playScreen == null) playScreen = new PlayScreen(this);
                this.setScreen(playScreen);
                break;
            case ENDGAME:
                if(endScreen == null) endScreen = new EndScreen(this);
                this.setScreen(endScreen);
                break;
        }
    }


	@Override
	public void create () {
        preferences = new AppPreferences();
        loadingScreen = new LoadingScreen(this);
		setScreen(new PlayScreen(this));
	}

	@Override
	public void dispose () {

	}

    public AppPreferences getPreferences() {
        return preferences;
    }
}
