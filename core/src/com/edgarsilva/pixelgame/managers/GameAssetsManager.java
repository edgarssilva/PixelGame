package com.edgarsilva.pixelgame.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GameAssetsManager {

    public final AssetManager manager = new AssetManager();

    // Textures
    public final String gameImages = "";
    public final String loadingImages = "";

    // Sounds
    public final  String boingSound = "";
    public final String pingSound = "";

    // Music
    public final String playingSong = "";

    // a small set of images used by the loading screen
    public void queueAddLoadingImages(){
        //manager.load(loadingImages, TextureAtlas.class);
    }

    public void queueAddImages(){
       // manager.load(gameImages, Texture.class);
    }

    public void queueAddSounds(){
        //manager.load(boingSound,Sound.class);
        //manager.load(pingSound,Sound.class);
    }

    public void queueAddMusic(){
       // manager.load(playingSong, Music.class);
    }

    public void queueAddFonts(){
    }

    public void queueAddParticleEffects(){
    }

}
