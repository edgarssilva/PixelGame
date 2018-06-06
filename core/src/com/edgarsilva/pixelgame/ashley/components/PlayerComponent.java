package com.edgarsilva.pixelgame.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;

public class PlayerComponent implements Component {

    public int health = 100;
    public int mana = 100;

    public int armor = 10;
    public int damage = 10;
    public int magic = 10;


    Array<Entity> items = new Array<Entity>();

    public boolean alive = true;


}
