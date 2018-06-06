package com.edgarsilva.pixelgame.ashley.components;

import com.badlogic.ashley.core.Component;

public class StateComponent  implements Component {
    public static final int STATE_NORMAL = 0;
    public static final int STATE_JUMPING = 1;
    public static final int STATE_FALLING = 2;
    public static final int STATE_MOVING = 3;
    public static final int STATE_HIT = 4;

    private int state = 0;
    public float time = 0.0f;

    public boolean isLooping = true;

    public void set(int newState){
        if (state!=newState) {
            state = newState;
            time = 0.0f;
        }
    }

    public int get(){
        return state;
    }
}