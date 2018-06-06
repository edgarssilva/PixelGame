package com.edgarsilva.pixelgame.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.edgarsilva.pixelgame.ashley.components.B2dBodyComponent;
import com.edgarsilva.pixelgame.ashley.components.PlayerComponent;
import com.edgarsilva.pixelgame.ashley.components.StateComponent;
import com.edgarsilva.pixelgame.ashley.components.TextureComponent;
import com.edgarsilva.pixelgame.ashley.components.TransformComponent;
import com.edgarsilva.pixelgame.utils.KeyboardController;

public class PlayerControlSystem extends IteratingSystem {

    ComponentMapper<PlayerComponent> pm;
    ComponentMapper<B2dBodyComponent> bodm;
    ComponentMapper<StateComponent> sm;
    ComponentMapper<TransformComponent> tm;

    KeyboardController controller;


    public PlayerControlSystem(KeyboardController keyCon) {
        super(Family.all(PlayerComponent.class).get());
        controller = keyCon;
        pm = ComponentMapper.getFor(PlayerComponent.class);
        bodm = ComponentMapper.getFor(B2dBodyComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        B2dBodyComponent b2body = bodm.get(entity);
        StateComponent state = sm.get(entity);
        TransformComponent tc = tm.get(entity);


        if(b2body.body.getLinearVelocity().y > 0)
            state.set(StateComponent.STATE_JUMPING);

        else if(b2body.body.getLinearVelocity().y < 0)
            state.set(StateComponent.STATE_FALLING);


        else if(b2body.body.getLinearVelocity().x != 0){
            state.set(StateComponent.STATE_MOVING);
            state.isLooping = true;
        }else{
                state.set(StateComponent.STATE_NORMAL);
                state.isLooping = false;
        }

        if(controller.left){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, -3f, 0.1f),b2body.body.getLinearVelocity().y);
           tc.flipX = true;
        }
        if(controller.right){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 3f, 0.1f),b2body.body.getLinearVelocity().y);
            tc.flipX = false;
        }

        if(!controller.left && ! controller.right){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 0, 0.2f),b2body.body.getLinearVelocity().y);
        }

        if(controller.up &&
                (state.get() == StateComponent.STATE_NORMAL || state.get() == StateComponent.STATE_MOVING)){
            b2body.body.applyLinearImpulse(0, 4f, b2body.body.getWorldCenter().x,b2body.body.getWorldCenter().y, true);
        }

    }
}
