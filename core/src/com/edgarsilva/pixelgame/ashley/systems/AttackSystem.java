package com.edgarsilva.pixelgame.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.edgarsilva.pixelgame.ashley.components.B2dBodyComponent;
import com.edgarsilva.pixelgame.ashley.components.PlayerComponent;
import com.edgarsilva.pixelgame.ashley.components.TransformComponent;
import com.edgarsilva.pixelgame.screens.PlayScreen;
import com.edgarsilva.pixelgame.utils.KeyboardController;

public class AttackSystem extends IteratingSystem {

    private PlayScreen screen;
    private KeyboardController controller;

    ComponentMapper<PlayerComponent> pcm;
    ComponentMapper<B2dBodyComponent> tcm;

    public AttackSystem(PlayScreen screen, KeyboardController controller) {
        super(Family.all(PlayerComponent.class).get());
        this.screen = screen;
        this.controller = controller;
        pcm = ComponentMapper.getFor(PlayerComponent.class);
        tcm = ComponentMapper.getFor(B2dBodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent pc =  pcm.get(entity);
        B2dBodyComponent bc = tcm.get(entity);
        if (controller.attack) {
            controller.attack = false;
            screen.getCamera().unproject(controller.position);
            System.out.println(controller.position.toString());
            screen.createAttack(bc.body, controller.position.x, controller.position.y);
        }
    }
}
