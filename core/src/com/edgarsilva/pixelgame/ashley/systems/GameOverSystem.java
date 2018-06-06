package com.edgarsilva.pixelgame.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.edgarsilva.pixelgame.ashley.components.PlayerComponent;
import com.edgarsilva.pixelgame.screens.PlayScreen;

public class GameOverSystem extends IteratingSystem {

    PlayScreen screen;

    public GameOverSystem(PlayScreen screen) {
        super(Family.one(PlayerComponent.class).get());
        this.screen = screen;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent player = entity.getComponent(PlayerComponent.class);
        if (!player.alive) screen.gameOver();
    }
}
