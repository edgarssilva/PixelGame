package com.edgarsilva.pixelgame.utils;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.edgarsilva.pixelgame.ashley.systems.RenderSystem;

public class KeyboardController implements InputProcessor {

    public boolean left,right,up,down;
    public boolean attack = false;
    public Vector3 position = new Vector3();
    @Override
    public boolean keyDown(int keycode) {

        boolean keyProcessed = false;

        switch (keycode) {
            case Input.Keys.A:
                left = true;
                keyProcessed = true;
                break;
            case Input.Keys.D:
                right = true;
                keyProcessed = true;
                break;
            case Input.Keys.S:
                down = true;
                keyProcessed = true;
                break;
            case Input.Keys.SPACE:
                up = true;
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }
    @Override
    public boolean keyUp(int keycode) {

        boolean keyProcessed = false;

        switch (keycode) {
            case Input.Keys.A:
                left = false;
                keyProcessed = true;
                break;
            case Input.Keys.D:
                right = false;
                keyProcessed = true;
                break;
            case Input.Keys.W:
                up = false;
                keyProcessed = true;
                break;
            case Input.Keys.S:
                down = false;
                keyProcessed = true;
                break;
            case Input.Keys.SPACE:
                up = false;
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return false;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        position.set(screenX,screenY, 0);
        attack = true;
        return true;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
